package com.example.ronak.demonew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;

/**
 * Created by Ronak on 12/14/2016.
 */
public abstract class WakeLocker {
    private static PowerManager.WakeLock wakeLock;

    @SuppressLint("Wakelock")
    @SuppressWarnings("deprecation")
    public static void acquire(Context context) {
        if (wakeLock != null)
            wakeLock.release();

        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "WakeLock");
        wakeLock.acquire();
    }

    public static void release() {
        if (wakeLock != null)
            wakeLock.release();
        wakeLock = null;
    }
}
