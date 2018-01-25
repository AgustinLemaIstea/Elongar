package com.istea.agustinlema.elongar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        context.startActivity(new Intent(context,AlarmActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


        // For our recurring task, we'll just display a message
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        Log.d("Elongar", "onReceive: Received");
    }


}

