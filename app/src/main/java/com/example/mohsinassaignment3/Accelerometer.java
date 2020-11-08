package com.example.mohsinassaignment3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer {
    public  int x = 0;
    public  int y = 0;

    public interface Listener
    {
        void onTranslation(float tx,float ty,float tz);
    }

    Listener listener;

    public void setListener(Listener l)
    {
        listener=l;
    }





    SensorManager sensorManager;
    Sensor mSensor;
    SensorEventListener sensorEventListener;

    public Accelerometer(Context context) {
        sensorManager=(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                if(listener!=null)
                {
                    listener.onTranslation(event.values[0],event.values[1],event.values[2]);
                    x -= (int) event.values[0];
                    y += (int) event.values[1];
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public  void Register()
    {
        sensorManager.registerListener(sensorEventListener,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }


    public  void UnRegister()
    {
        sensorManager.unregisterListener(sensorEventListener);
    }

}
