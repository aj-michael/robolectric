package org.robolectric.shadows;

import com.android.internal.os.BackgroundThread;

import org.robolectric.annotation.Implements;
import org.robolectric.annotation.Resetter;
import org.robolectric.internal.Shadow;
import org.robolectric.util.ReflectionHelpers;

/**
 * Shadow for {@link com.android.internal.os.BackgroundThread}.
 */
@Implements(value = BackgroundThread.class, isInAndroidSdk = false, inheritImplementationMethods = true, from = 19)
public class ShadowBackgroundThread {

  @Resetter
  public static void reset() {
    BackgroundThread instance = ReflectionHelpers.getStaticField(BackgroundThread.class, "sInstance");
    if (instance != null) {
      instance.quit();
      ReflectionHelpers.setStaticField(BackgroundThread.class, "sInstance", null);
      ReflectionHelpers.setStaticField(BackgroundThread.class, "sHandler", null);
    }
  }
}
