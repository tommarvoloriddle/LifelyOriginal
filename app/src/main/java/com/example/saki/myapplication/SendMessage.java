package com.example.saki.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendMessage extends AppCompatActivity implements LocationListener {

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView display;
    double latitude;
    double longitude;
    String lat;
    boolean gps_enabled, netwrok_enabled;
    Location location;
    EditText dec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        //text viwes
        //display = (TextView) findViewById(R.id.location);
       // dec = (EditText) findViewById(R.id.editText);

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(SendMessage.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();

            }
            else{
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            }
     //  display.setText(latitude +"latitude");


        if(( latitude !=0 && longitude!=0)){

            try {
                sendsms();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public void sendsms() throws IOException {

        Log.i("Send SMS", "");
        String phoneNo1,phoneNo2,phoneNo3,msg;
        String number1,num2,num3;

        FileInputStream fis1 = openFileInput("contacts1.txt");
        InputStreamReader inputStreamReader1 =new InputStreamReader(fis1);
        BufferedReader bufferedReader1 =new BufferedReader(inputStreamReader1);
        StringBuffer stringBuffer1 = new StringBuffer();
        while ((number1 = bufferedReader1.readLine())!= null)
        {
            stringBuffer1.append(number1 + "\n");
        }
        phoneNo1 = stringBuffer1.toString();

        phoneNo2 =phoneNo1;
        phoneNo3 = phoneNo1;
        msg =("I am in an emergency situation , contact me .My location is -"+"http://maps.google.com?q=" + latitude+',' +longitude);
        phoneNo1  = phoneNo1.substring(0,10);
        phoneNo2 = phoneNo2.substring(8,20);
       phoneNo3 = phoneNo3.substring(20,30);
        SmsManager smsmgr = SmsManager.getDefault();
             smsmgr.sendTextMessage(phoneNo1, null , msg , null, null);
           smsmgr.sendTextMessage(phoneNo2, null , msg , null, null);
         smsmgr.sendTextMessage(phoneNo3, null , msg , null, null);
    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
