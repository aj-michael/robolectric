package org.robolectric.shadows;

import android.media.SoundPool;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.util.ReflectionHelpers;

import android.media.AudioAttributes;

@Implements(SoundPool.class)
public class ShadowSoundPool {
  @RealObject
  SoundPool realObject;

//#if ($api >= 23) todo
  public void __constructor__(int maxStreams, AudioAttributes attributes) {
    ReflectionHelpers.setField(realObject, "mLock", new Object());
  }
}
