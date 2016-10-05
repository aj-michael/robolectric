package org.robolectric.shadows;

import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.util.Collections;
import java.util.List;

@Implements(value = UserManager.class, from = 17)
public class ShadowUserManager {

  @Implementation(from = 18)
  public Bundle getApplicationRestrictions(String packageName) {
    return null;
  }

  @Implementation(from = 21)
  public List<UserHandle> getUserProfiles(){
    return Collections.emptyList();
  }

}