package com.istea.agustinlema.elongar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    final int CADENCE=5; //5 seconds for each reminder

    private AlarmManager alarmMgr;
    private PendingIntent pendingIntent;

    View doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        setupDoneBtn();
        Log.d("Elongar", "onCreate: creado");
    }

    private void setupUI(){
        this.doneBtn=findViewById(R.id.doneBtn);
    }

    private void setupDoneBtn(){
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "La alarma sonará en "+CADENCE+" segundos.", Toast.LENGTH_SHORT).show();

                Context context = MainActivity.this;
                alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);



                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

                //Intent intent = new Intent(MainActivity.this, MainActivity.class);
                //pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);





                alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() +
                                CADENCE * 1000, pendingIntent);

                Log.d("Elongar", "setupDoneBtn: start with cadence "+CADENCE);
            }
        });
    }
}