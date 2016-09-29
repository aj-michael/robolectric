package org.robolectric.shadows;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import java.lang.reflect.InvocationTargetException;

@Implements(BroadcastReceiver.PendingResult.class)
public final class ShadowBroadcastPendingResult {
  @RealObject BroadcastReceiver.PendingResult pendingResult;

  static BroadcastReceiver.PendingResult create(int resultCode, String resultData, Bundle resultExtras, boolean ordered) {
    try {
      if (Build.VERSION.SDK_INT < 17) {
        return BroadcastReceiver.PendingResult.class
            .getConstructor(int.class, String.class, Bundle.class, int.class, boolean.class, boolean.class, IBinder.class)
            .newInstance(
                resultCode,
                resultData,
                resultExtras,
                0 /* type */,
                ordered,
                false /*sticky*/,
                null /* ibinder token */);
      } else if (Build.VERSION.SDK_INT < 23) {
        return BroadcastReceiver.PendingResult.class
            .getConstructor(int.class, String.class, Bundle.class, int.class, boolean.class, boolean.class, IBinder.class, int.class)
            .newInstance(
                resultCode,
                resultData,
                resultExtras,
                0 /* type */,
                ordered,
                false /*sticky*/,
                null /* ibinder token */,
                0 /* userid */);

      } else {
        return new BroadcastReceiver.PendingResult(
            resultCode,
            resultData,
            resultExtras,
            0 /* type */,
            ordered,
            false /*sticky*/,
            null /* ibinder token */,
            0 /* userid */,
            0 /* flags */);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private final SettableFuture<BroadcastReceiver.PendingResult> finished = SettableFuture.create();

  @Implementation
  public final void finish() {
    Preconditions.checkState(finished.set(pendingResult), "Broadcast already finished");
  }

  public ListenableFuture<BroadcastReceiver.PendingResult> getFuture() {
    return finished;
  }
}
