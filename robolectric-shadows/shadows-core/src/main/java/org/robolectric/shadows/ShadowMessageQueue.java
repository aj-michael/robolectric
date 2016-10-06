package org.robolectric.shadows;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.MessageQueue;

import javax.annotation.Generated;

import org.robolectric.annotation.HiddenApi;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.util.Scheduler;

import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.internal.Shadow.*;
import static org.robolectric.util.ReflectionHelpers.*;
import static org.robolectric.util.ReflectionHelpers.ClassParameter.from;

/**
 * Shadow for {@link android.os.MessageQueue}.
 *
 * <p>This class puts {@link android.os.Message}s into the scheduler queue instead of sending
 * them to be handled on a separate thread. {@link android.os.Message}s that are scheduled to
 * be dispatched can be triggered by calling {@link ShadowLooper#idleMainLooper}.</p>
 *
 * @see ShadowLooper
 */
@Implements(MessageQueue.class)
public class ShadowMessageQueue {

  @RealObject
  private MessageQueue realQueue;

  private Scheduler scheduler;

  // Stub out the native peer - scheduling
  // is handled by the Scheduler class which is user-driven
  // rather than automatic.
  @HiddenApi
  @Implementation
  public static Number nativeInit() {
    return 1;
  }

  @HiddenApi
  @Implementation(to = 20)
  public static void nativeDestroy(int ptr) {
    nativeDestroy((long) ptr);
  }

  @Implementation(from = 21)
  public static void nativeDestroy(long ptr) {
  }

  @HiddenApi
  @Implementation(to = 20)
  public static void nativePollOnce(int ptr, int timeoutMillis) {
    nativePollOnce((long) ptr, timeoutMillis);
  }

  @Implementation(from = 21)
  public static void nativePollOnce(long ptr, int timeoutMillis) {
    throw new AssertionError("Should not be called");
  }

  @HiddenApi
  @Implementation(to = 20)
  public static void nativeWake(int ptr) {
    nativeWake((long) ptr);
  }

  @Implementation(from = 21)
  public static void nativeWake(long ptr) {
    throw new AssertionError("Should not be called");
  }

  @HiddenApi
  @Implementation(to = 20)
  public static boolean nativeIsIdling(int ptr) {
    return nativeIsIdling((long) ptr);
  }

  @Implementation(from = 21)
  public static boolean nativeIsIdling(long ptr) {
    return false;
  }

  public Scheduler getScheduler() {
    return scheduler;
  }

  public void setScheduler(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  public Message getHead() {
    return getField(realQueue, "mMessages");
  }

  public void setHead(Message msg) {
    setField(realQueue, "mMessages", msg);
  }

  public void reset() {
    setHead(null);
  }

  @Implementation
  public boolean enqueueMessage(final Message msg, long when) {
    final boolean retval = directlyOn(realQueue, MessageQueue.class, "enqueueMessage", from(Message.class, msg), from(long.class, when));
    if (retval) {
      final Runnable callback = new Runnable() {
        @Override
        public void run() {
          synchronized (realQueue) {
            Message m = getHead();
            if (m == null) {
              return;
            }

            Message n = shadowOf(m).getNext();
            if (m == msg) {
              setHead(n);
              dispatchMessage(msg);
              return;
            }

            while (n != null) {
              if (n == msg) {
                n = shadowOf(n).getNext();
                shadowOf(m).setNext(n);
                dispatchMessage(msg);
                return;
              }
              m = n;
              n = shadowOf(m).getNext();
            }
          }
        }
      };
      shadowOf(msg).setScheduledRunnable(callback);
      if (when == 0) {
        scheduler.postAtFrontOfQueue(callback);
      } else {
        scheduler.postDelayed(callback, when - scheduler.getCurrentTime());
      }
    }
    return retval;
  }

  @HiddenApi @Implementation
  public void removeSyncBarrier(int token) {
  }

  private static void dispatchMessage(Message msg) {
    final Handler target = msg.getTarget();

    shadowOf(msg).setNext(null);
    // If target is null it means the message has been removed
    // from the queue prior to being dispatched by the scheduler.
    if (target != null) {
      callInstanceMethod(msg, "markInUse");
      target.dispatchMessage(msg);

      if (Build.VERSION.SDK_INT >= 21) {
        callInstanceMethod(msg, "recycleUnchecked");
      } else {
        callInstanceMethod(msg, "recycle");
      }
    }
  }
}
