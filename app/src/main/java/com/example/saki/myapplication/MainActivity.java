package com.example.saki.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private Sensor mySensor;
    private SensorManager sm;
    boolean isCrashcar = false ;
    Button contacts , alert, emergency;
    Boolean isCon=  false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Sensor manager
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        //accelorometer
        mySensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Register
        sm.registerListener(this, mySensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        //buttons
        contacts = (Button) findViewById(R.id.button1);
        emergency = (Button) findViewById(R.id.button2);
        alert = (Button) findViewById(R.id.button3);

        contacts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Intent code for open new activity through intent.

                Intent intent = new Intent(MainActivity.this, SetContacts.class);
                startActivity(intent);

            }
        });

        alert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Intent code for open new activity through intent.
                isCon = true;
                Intent intent = new Intent(MainActivity.this, SendMessage.class);
                startActivity(intent);

            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Intent code for open new activity through intent.

                Intent intent = new Intent(MainActivity.this, Track.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void showNotification() {

        Intent intent = new Intent(this , MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pendingIntent);
        //builder.setColor(15);
        builder.setContentTitle("You are in emergency");
        builder.setContentText("Automatic alert in 30 seconds");
        builder.setSmallIcon(R.drawable.ic_stat_name);
        Notification notification = builder.build();
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(alarmSound == null){
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if(alarmSound == null){
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }
        notification.sound = alarmSound;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(1 ,notification);



    }
    @Override
    public void onSensorChanged(SensorEvent event){

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float gX = x / 9.8f;
        float gY = y / 9.8f;
        float gZ = z / 9.8f;
        boolean flag1 = false;

        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);
        if(gForce > 40){

            isCrashcar = true;
            showNotification();
            if(isCon != true) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Intent mainIntent = new Intent(MainActivity.this, SendMessage.class);
                        MainActivity.this.startActivity(mainIntent);
                        MainActivity.this.finish();
                    }
                }, 30000);
            }
        }
    }

}