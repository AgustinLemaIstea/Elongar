package com.istea.agustinlema.elongar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int CADENCE=5; //5 seconds for each reminder

    private AlarmManager alarmMgr;
    private PendingIntent pendingIntent;

    Button doneBtn;
    TextView txtNextAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        setupDoneBtn();
        Log.d("Elongar", "onCreate: creado");
    }

    @Override
    protected void onResume() {
        LoadNextAlarmTime();
        super.onResume();
    }

    private void LoadNextAlarmTime() {
        MyAlarmManager myAlarmMgr = MyAlarmManager.getInstance();
        long nextTime = myAlarmMgr.GetNextTime(this);

        Log.d("Elongar", "LoadNextAlarmTime: nextTime is "+nextTime);

        long now = SystemClock.elapsedRealtime();
        long pendingSeconds=(nextTime-now)/1000;
        pendingSeconds++; //Ugly fix
        if (pendingSeconds>0) {
            txtNextAlarm.setText("Proxima alarma en: " + pendingSeconds + " segundos.");
            makeCountdownTimer(pendingSeconds);
        } else
            txtNextAlarm.setText("No hay alarmas.");
    }

    private void makeCountdownTimer(long pendingSeconds) {
        new CountDownTimer(pendingSeconds*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                long seconds=(millisUntilFinished / 1000);
                txtNextAlarm.setText("Proxima alarma en: " + seconds + " segundos.");
            }

            public void onFinish() {
                txtNextAlarm.setText("No hay alarmas.");
            }

        }.start();
    }

    private void setupUI(){
        this.doneBtn=(Button) findViewById(R.id.doneBtn);
        this.txtNextAlarm=(TextView) findViewById(R.id.txtNextAlarm);
    }

    private void setupDoneBtn(){
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "La alarma sonará en "+CADENCE+" segundos.", Toast.LENGTH_SHORT).show();

                Context context = MainActivity.this;

                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

                //Intent intent = new Intent(MainActivity.this, MainActivity.class);
                //pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                MyAlarmManager myAlarmManager = MyAlarmManager.getInstance();
                myAlarmManager.SetNewAlarm(CADENCE,pendingIntent, context);

                LoadNextAlarmTime();

                Log.d("Elongar", "setupDoneBtn: start with cadence "+CADENCE);
            }
        });
    }
}