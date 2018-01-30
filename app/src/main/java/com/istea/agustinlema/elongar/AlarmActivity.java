package com.istea.agustinlema.elongar;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {

    Button btnDone;
    Button btnSnooze5;
    Button btnSnooze15;
    Button btnSnooze30;
    Button btnSnooze60;
    Button btnCancel;

    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setupUI();
        setupButtons();
        vibrate();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        long[] pattern={200, 500, 200, 500, 500, 500, 500, 500, 200};
        v.vibrate(pattern,-1);
    }

    private void setupButtons() {
        setupDoneButton();
        setupSnoozeButton(btnSnooze5,5);
        setupSnoozeButton(btnSnooze15,15);
        setupSnoozeButton(btnSnooze30,30);
        setupSnoozeButton(btnSnooze60,60);
        setupCancelButton();
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

    private void setupCancelButton(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAlarmManager.getInstance().cancelAlarm();

                Toast.makeText(AlarmActivity.this, "Alarma cancelada."
                        , Toast.LENGTH_SHORT).show();
                Log.d("Elongar", "setupDoneBtn: cancelAlarm");
                finish();
            }
        });
    }

    private void setupDoneButton() {

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int minutes=60;

                setAlarm(minutes);

                Toast.makeText(AlarmActivity.this, "La alarma sonará en "+minutes+" minutos."
                        , Toast.LENGTH_SHORT).show();
                Log.d("Elongar", "setupDoneBtn: start with minutes "+minutes);

                Date currentTime = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat hf = new SimpleDateFormat("HH:mm:ss");

                EventoElongacion evtElongacion = new EventoElongacion(df.format(currentTime),hf.format(currentTime));
                dbHelper.insertarElongacion(evtElongacion);

                finish();
            }
        });
    }

    private void setAlarm(int minutes){
        Context context = AlarmActivity.this;

        Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        MyAlarmManager myAlarmMgr = MyAlarmManager.getInstance();
        myAlarmMgr.SetNewAlarm(minutes*60, pendingIntent, context);
    }

    private void setupUI() {
        btnDone= (Button) findViewById(R.id.btnDone);
        btnSnooze5 = (Button) findViewById(R.id.btnSnooze5);
        btnSnooze15 = (Button) findViewById(R.id.btnSnooze15);
        btnSnooze30 = (Button) findViewById(R.id.btnSnooze30);
        btnSnooze60 = (Button) findViewById(R.id.btnSnooze60);
        btnCancel= (Button) findViewById(R.id.btnCancel);
    }
}
