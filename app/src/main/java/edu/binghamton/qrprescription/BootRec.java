package edu.binghamton.qrprescription;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootRec extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            // TODO: 10/9/2019 set up all the alarms again here 
        }
    }
}
