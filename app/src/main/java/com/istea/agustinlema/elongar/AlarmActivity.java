package com.istea.agustinlema.elongar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {

    Button btnDone;
    Button btnSnooze2;
    Button btnSnooze5;
    Button btnSnooze15;
    Button btnSnooze30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setupUI();
        setupButtons();
        vibrate();
        setAlarm(5);
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        long[] pattern={200, 500, 200, 500, 500, 500, 500, 500, 200};
        v.vibrate(pattern,-1);
    }

    private void setupButtons() {
        setupSnoozeButton(btnDone,60);
        setupSnoozeButton(btnSnooze2,2);
        setupSnoozeButton(btnSnooze5,5);
        setupSnoozeButton(btnSnooze15,15);
        setupSnoozeButton(btnSnooze30,30);
    }

    private void setupSnoozeButton(Button button, final int minutes) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm(minutes);

                Toast.makeText(AlarmActivity.this, "La alarma sonará en "+minutes+" minutos."
                        , Toast.LENGTH_SHORT).show();
                Log.d("Elongar", "setupDoneBtn: start with minutes "+minutes);
                finish();
            }
        });
    }

    private void setAlarm(int minutes){
        Context context = AlarmActivity.this;

        Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        MyAlarmManager myAlarmMgr = MyAlarmManager.getInstance();
        myAlarmMgr.SetNewAlarm(minutes, pendingIntent, context);
    }

    private void setupUI() {
        btnDone= (Button) findViewById(R.id.btnDone);
        btnSnooze2= (Button) findViewById(R.id.btnSnooze2);
        btnSnooze5= (Button) findViewById(R.id.btnSnooze5);
        btnSnooze15= (Button) findViewById(R.id.btnSnooze15);
        btnSnooze30= (Button) findViewById(R.id.btnSnooze30);
    }
}
