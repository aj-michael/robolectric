package org.robolectric.shadows;

import android.os.Looper;
import android.view.WindowManagerGlobal;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Shadow for {@link android.view.WindowManagerGlobal}.
 */
@Implements(value = WindowManagerGlobal.class, isInAndroidSdk = false, from = 17)
public class ShadowWindowManagerGlobal {

  @Implementation
  public static Object getWindowSession() {
    return null;
  }

  @Implementation
  public static Object getWindowSession(Looper looper) {
    return null;
  }

  @Implementation
  public static Object getWindowManagerService() {
    return null;
  }

}