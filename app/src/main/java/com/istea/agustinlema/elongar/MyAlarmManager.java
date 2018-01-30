package com.istea.agustinlema.elongar;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

public class MyAlarmManager {

    private static MyAlarmManager instance;
    private AlarmManager alarmMgr;

    private PendingIntent pendingIntent;
    private Context context;

    private long nextAlarmTime;


    private MyAlarmManager() {}

    public static MyAlarmManager getInstance(){
        if (instance==null){
            instance=new MyAlarmManager();
        }
        return instance;

    }

    public void SetNewAlarm(int seconds, final PendingIntent pendingIntent, Context context){
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        nextAlarmTime=SystemClock.elapsedRealtime() +
                seconds * 1000;

        this.SaveNextTime(nextAlarmTime,context );

        this.pendingIntent=pendingIntent;
        this.context=context;

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                nextAlarmTime, pendingIntent);
    }

    private void SaveNextTime(long nextAlarmTime, Context context){
        SharedPreferences preferences =
                context.getSharedPreferences("MyAlarmManager",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("NextAlarm",nextAlarmTime);
        this.nextAlarmTime=nextAlarmTime;
    }

    public long GetNextTime(Context context){
        if (nextAlarmTime == 0) {
            SharedPreferences preferences =
                    context.getSharedPreferences("MyAlarmManager", Context.MODE_PRIVATE);
            nextAlarmTime=preferences.getLong("NextAlarm", 0);
        }
        return nextAlarmTime;
    }

    public void cancelAlarm(){
        this.pendingIntent.cancel();
        alarmMgr.cancel(this.pendingIntent);
        this.SaveNextTime(0,context );
    }
}
