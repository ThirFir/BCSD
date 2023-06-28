package com.bcsd.bcsd_week3.week10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log

class BatteryReceiver(): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context?.registerReceiver(null, ifilter)
        }

        val batteryPct: Float? = batteryStatus?.let {
            val level: Int = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale.toFloat()
        }
        if(batteryPct!! < 30){
            Log.d("BatterReceiver", "onReceive: Battery is low")
        }
        else{
            Log.d("BatterReceiver", "onReceive: Battery is not low")
        }

    }
}