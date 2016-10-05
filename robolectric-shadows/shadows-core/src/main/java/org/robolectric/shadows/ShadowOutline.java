package org.robolectric.shadows;

import android.graphics.Path;
import android.graphics.Outline;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.Implementation;

/**
 * Shadow for {@link android.graphics.Outline}.
 */
@Implements(value = Outline.class, from = 21)
public class ShadowOutline {

  @Implementation
  public void setConvexPath(Path convexPath) {
  }
}