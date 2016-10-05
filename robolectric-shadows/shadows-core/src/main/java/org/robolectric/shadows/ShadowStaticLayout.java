package org.robolectric.shadows;

import android.text.StaticLayout;
import org.robolectric.annotation.HiddenApi;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Shadow for {@link android.text.StaticLayout}.
 */
@Implements(value = StaticLayout.class, looseSignatures = true)
public class ShadowStaticLayout {

  @Implementation(from = 21, to = 22) @HiddenApi
  public static int[] nLineBreakOpportunities(String locale, char[] text, int length, int[] recycle) {
    return new int[] {-1};
  }

  @Implementation(from = 23) @HiddenApi
  public static int nComputeLineBreaks(Object nativePtr, Object recycle,
            Object recycleBreaks, Object recycleWidths, Object recycleFlags, Object recycleLength) {
    return 1;
  }
}