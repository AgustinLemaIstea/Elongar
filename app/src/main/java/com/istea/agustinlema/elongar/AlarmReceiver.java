package com.istea.agustinlema.elongar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        context.startActivity(new Intent(context,AlarmActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        Log.d("Elongar", "onReceive: "+intent.getComponent().getClassName());
        Log.d("Elongar", "onReceive: "+context.getClass().getName());
        if (intent.getComponent().getClassName() == context.getClass().getName()){
            ((Activity) context).finish();
        }

        setReminder(context, intent, 5);

        // For our recurring task, we'll just display a message
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        Log.d("Elongar", "onReceive: Received");
    }

    private void setReminder(Context context, Intent intent, int minutes){

        Intent newIntent = new Intent(context, AlarmReceiver.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, newIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        MyAlarmManager myAlarmMgr = MyAlarmManager.getInstance();
        myAlarmMgr.SetNewAlarm(minutes*60, pendingIntent, context);
    }

}

