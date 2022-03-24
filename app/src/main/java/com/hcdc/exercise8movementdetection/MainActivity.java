package com.hcdc.exercise8movementdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tiltText,xstatus,ystatus,zstatus;
    Sensor tiltSensor;
    SensorManager tiltManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tiltText = findViewById(R.id.gyrotext);
        xstatus = findViewById(R.id.x);
        ystatus = findViewById(R.id.y);
        zstatus = findViewById(R.id.z);
        tiltManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        tiltSensor = tiltManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Toast x = Toast.makeText(MainActivity.this, "You have rotated the phone too much on X-Axis!", Toast.LENGTH_SHORT);
        Toast y = Toast.makeText(MainActivity.this, "You have rotated the phone too much on Y-Axis!", Toast.LENGTH_SHORT);
        Toast z = Toast.makeText(MainActivity.this, "You have rotated the phone too much on Z-Axis!", Toast.LENGTH_SHORT);
        SensorEventListener sel = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                tiltText.setText("X: " + sensorEvent.values[0] + "\nY: " + sensorEvent.values[1] + "\nZ: " + sensorEvent.values[2]+"\n");

                if(sensorEvent.values[0] >= 8 ){
                    xstatus.setText("You have rotated the phone too much on X-Axis!");
                    x.show();
                }
                else if(sensorEvent.values[1] >= 8 ){
                    ystatus.setText("You have rotated the phone too much on Y-Axis!");
                    y.show();
                }
                else if(sensorEvent.values[2] >= 8 ){
                    zstatus.setText("You have rotated the phone too much on Z-Axis!");
                    z.show();
                }

                else {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            x.cancel();
                            y.cancel();
                            z.cancel();
                        }
                    }, 2000);

                    xstatus.setText("Your phone is in default axis.");
                    ystatus.setText("Your phone is in default axis.");
                    zstatus.setText("Your phone is in default axis.");
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        tiltManager.registerListener(sel,tiltSensor,tiltManager.SENSOR_DELAY_NORMAL);



    }
}