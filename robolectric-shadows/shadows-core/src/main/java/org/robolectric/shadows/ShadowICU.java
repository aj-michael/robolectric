package org.robolectric.shadows;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.util.Locale;

/**
 * Shadow for {@link libcore.icu.ICU}.
 */
@Implements(value = libcore.icu.ICU.class, isInAndroidSdk = false)
public class ShadowICU {

  @Implementation
  public static String addLikelySubtags(String locale) {
    return "en-US";
  }

  @Implementation(from = 21)
  public static String getBestDateTimePattern(String skeleton, Locale locale) {
    return skeleton;
  }

  @Implementation(to = 20)
  public static String getBestDateTimePattern(String skeleton, String locale) {
    return skeleton;
  }
}
