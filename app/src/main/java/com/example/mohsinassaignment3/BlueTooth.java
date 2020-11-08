package com.example.mohsinassaignment3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class BlueTooth extends AppCompatActivity {
    Button on,off, pair,search;
    BluetoothAdapter bluetoothAdapter;
    TextView tv;
    Set<BluetoothDevice> PairedDeviceSet;
    ListView lv;
    //ArrayAdapter mArrayAdapter;

    ArrayList<Device> d1;
    MyAdapter myAdapter;
    Device d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        on=findViewById(R.id.on);
        off=findViewById(R.id.off);
        pair=findViewById(R.id.paired);
        search=findViewById(R.id.find);
        lv=findViewById(R.id.lv);
        tv=findViewById(R.id.tv);
        d=new Device();


        //checkBluetooth();
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null)
        {
            on.setEnabled(false);
            off.setEnabled(false);
            pair.setEnabled(false);
            search.setEnabled(false);
            tv.setText("Status : Not Supported");
            Toast.makeText(this,"Your Device does not support this servise ",Toast.LENGTH_LONG).show();
        }
        else
        {
            on.setOnClickListener(v -> OnButtonMethod(v));
            off.setOnClickListener(v -> OffButtonMethod(v));
            pair.setOnClickListener(v -> ShowPairedDevices(v));
            search.setOnClickListener(v -> SearchButtonMethod(v));
            // mArrayAdapter=new ArrayAdapter(BlueTooth.this, android.R.layout.simple_list_item_1);
            // lv.setAdapter(mArrayAdapter);


          d1=new ArrayList<>();
          d1.add(d);
          myAdapter=new MyAdapter(BlueTooth.this,d1);
          lv.setAdapter(myAdapter);


        }


    }

    //    public void checkBluetooth()
//    {
//
//    }
    final BroadcastReceiver breceiver = (new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra
                        (BluetoothDevice.EXTRA_DEVICE);
                ///myAdapter.add(device.getName(),device.getAddress());
                myAdapter.notifyDataSetChanged();
                d.setName(device.getName());
                d.setDis(device.getAddress());
                d.setImage(R.drawable.images);
                d1.add(d);
                myAdapter.al.add(d);




            }

            myAdapter.notifyDataSetChanged();
            myAdapter=new MyAdapter(BlueTooth.this,d1);
            lv.setAdapter(myAdapter);

        }
    });
    public void OnButtonMethod(View v)
    {
        if(!bluetoothAdapter.isEnabled())
        {

            Intent turnonintent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnonintent,1);
            Toast.makeText(this,"Bluetooth is on",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Bluetooth is already on",Toast.LENGTH_LONG).show();
        }
    }

    public void OffButtonMethod(View View)
    {
        bluetoothAdapter.disable();
        tv.setText("Bluetooth Status : :Disconnected");
        Toast.makeText(this,"Bluetooth turned off",Toast.LENGTH_LONG).show();

    }

    public  void ShowPairedDevices(View view)
    {
        PairedDeviceSet=bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice bd:PairedDeviceSet) {
            d.setName(bd.getName());
            d.setDis(bd.getAddress());
            d.setImage(R.drawable.images);
            d1.add(d);
            myAdapter.al.add(d);
        }
        myAdapter=new MyAdapter(BlueTooth.this,d1);
        lv.setAdapter(myAdapter);

        Toast.makeText(this,"Show Pared Device",Toast.LENGTH_LONG).show();

    }


    public void SearchButtonMethod(View v)
    {
        if(bluetoothAdapter.isDiscovering())
        {
            bluetoothAdapter.cancelDiscovery();
        }
        else
        {
            //myAdapter.clear();
            Toast.makeText(this,"From Search Button",Toast.LENGTH_LONG).show();
            myAdapter.notifyDataSetChanged();
            myAdapter.al.clear();
            bluetoothAdapter.startDiscovery();
            registerReceiver(breceiver,new IntentFilter(BluetoothDevice.ACTION_FOUND));

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(bluetoothAdapter.isEnabled())
            {
                tv.setText("Bluetooth Status : Enabled");
            }
            else
            {
                tv.setText("Bluetooth Status : Disabled");
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(breceiver, filter);
    }
}