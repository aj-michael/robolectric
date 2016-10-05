package org.robolectric.shadows;

import android.app.ResourcesManager;

import org.robolectric.annotation.Implements;
import org.robolectric.annotation.Resetter;
import org.robolectric.util.ReflectionHelpers;

@Implements(value = ResourcesManager.class, isInAndroidSdk = false, from = 19)
public class ShadowResourceManager {

  @Resetter
  public static void reset() {
    ReflectionHelpers.setStaticField(ResourcesManager.class, "sResourcesManager", null);
  }
}