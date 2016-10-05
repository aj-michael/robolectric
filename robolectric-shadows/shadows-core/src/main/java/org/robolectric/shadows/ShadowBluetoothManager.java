package org.robolectric.shadows;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.Implementation;

/**
 * Shadow for {@link android.bluetooth.BluetoothManager}.
 */
@Implements(value = BluetoothManager.class, from = 18)
public class ShadowBluetoothManager {

    @Implementation
    public BluetoothAdapter getAdapter() {
      return BluetoothAdapter.getDefaultAdapter();
    }
}