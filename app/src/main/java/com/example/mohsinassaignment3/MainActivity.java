package com.example.mohsinassaignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    TextView X_Axis,Y_Axis,Z_Axis;
    TextView GX_Axis,GY_Axis,GZ_Axis;
    ImageView imageView;
    Button bluetooth;
    Accelerometer accelerometer;
    Gyroscope gyroscope;
    public static int x = 0;
    public static int y = 0;
    RelativeLayout rl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        X_Axis=findViewById(R.id.X_axis);
        Y_Axis=findViewById(R.id.Y_axis);
        Z_Axis=findViewById(R.id.Z_axis);
        GX_Axis=findViewById(R.id.GX);
        GY_Axis=findViewById(R.id.GY);
        GZ_Axis=findViewById(R.id.GZ);
        bluetooth=findViewById(R.id.blu);
        imageView=findViewById(R.id.imageView);
        rl=findViewById(R.id.rl);
        accelerometer=new Accelerometer(this);
        gyroscope=new Gyroscope(this);

        accelerometer.setListener((tx, ty, tz) -> {
            if(tx>1.0)
            {
                getWindow().getDecorView().setBackgroundColor(Color.RED);
                x -=accelerometer.x;
                y += accelerometer.y;
                imageView.setY(y);
                imageView.setX(x);

            }
            else if(tx<-1.0)
            {
                getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                x +=accelerometer.x;
                y -= accelerometer.y;
                imageView.setY(y);
                imageView.setX(x);

            }
            X_Axis.setText("  X-Axis = "+tx);
            Y_Axis.setText("  Y-Axis = "+ty);
            Z_Axis.setText("  Z-Axis = "+tz);
        });

        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if(rx>1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
                else if(rx<-1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                }
                GX_Axis.setText("  X-Axis = "+rx);
                GY_Axis.setText("  Y-Axis = "+ry);
                GZ_Axis.setText("  Z-Axis = "+rz);
            }
        });
        bluetooth.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,BlueTooth.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.Register();
        gyroscope.Register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.UnRegister();
        gyroscope.UnRegister();
    }

}