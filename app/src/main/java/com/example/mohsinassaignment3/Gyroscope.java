package com.example.mohsinassaignment3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class Gyroscope {

    public interface Listener
    {
        void onRotation(float rx,float ry,float rz);
    }

    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    SensorManager sensorManager;
    Sensor mSensor;
    SensorEventListener sensorEventListener;
    Gyroscope(Context c)
    {
        sensorManager= (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
        mSensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
               if(listener!=null)
               {
                   listener.onRotation(event.values[0],event.values[1],event.values[2]);
               }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void Register()
    {
        sensorManager.registerListener(sensorEventListener,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void UnRegister()
    {
        sensorManager.unregisterListener(sensorEventListener);
    }


}
