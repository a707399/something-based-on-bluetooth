package com.example.lenovo.planb;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public String message;
    private Vibrator vibrator;

   /* private static final String TAG = MainActivity.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mSensor;
    public static boolean sensor_isOn = false;

    private TextView sensor_info;*/                  //说实话我也不知道这段是干嘛的，不过既然有人把他注释了那就这样放着吧

    private int ENABLE_BLUETOOTH = 2;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket = null;
    OutputStream outputStream = null;
    private static final UUID MY_UUID_SECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private String blueAddress = "98:D3:34:90:C7:4C";   //蓝牙模块的MAC地址，另一个是啥来着

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(bluetoothAdapter == null){
            Toast.makeText(this,"不支持蓝牙",Toast.LENGTH_LONG).show();   //现在还会有不支持蓝牙的手机？
            finish();
        }else if(!bluetoothAdapter.isEnabled()){
            Log.d("true","开始连接");
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,ENABLE_BLUETOOTH);
        }
        ButtonListener bt = new ButtonListener();
        Button up = (Button)findViewById(R.id.button);
        Button down = (Button)findViewById(R.id.button4);
        Button left = (Button)findViewById(R.id.button2);
        Button right =(Button)findViewById(R.id.button3);
        Button stop = (Button)findViewById(R.id.button5);
        Button start = (Button)findViewById(R.id.button6);
        Button safe_mode = (Button)findViewById(R.id.button7);
        Button full_mode = (Button)findViewById(R.id.button8);
        up.setOnTouchListener(bt);
        down.setOnTouchListener(bt);
        left.setOnTouchListener(bt);
        right.setOnTouchListener(bt);
        stop.setOnTouchListener(bt);
        start.setOnTouchListener(bt);
        safe_mode.setOnTouchListener(bt);
        full_mode.setOnTouchListener(bt);
    }
    class ButtonListener implements View.OnTouchListener{

        public boolean onTouch(View v, MotionEvent event){
            switch (v.getId()){
                case R.id.button:
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        message = "";//松开按钮时要发送的数据
                        bluesend(message);
                    }
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        message = "";
                        bluesend(message);
                    }
                    break;

                case R.id.button2:
                    if(event.getAction() ==MotionEvent.ACTION_UP){
                        message = "";
                        bluesend(message);
                    }
                    if(event.getAction() ==MotionEvent.ACTION_DOWN){
                        message = "";
                        bluesend(message);
                    }
                    break;

                case R.id.button3:
                    if(event.getAction() ==MotionEvent.ACTION_UP){
                        message = "";
                        bluesend(message);
                    }
                    if(event.getAction() ==MotionEvent.ACTION_DOWN){
                        message = "";
                        bluesend(message);
                    }
                    break;

                case R.id.button4:
                    if(event.getAction() ==MotionEvent.ACTION_UP){
                        message = "";
                        bluesend(message);
                    }
                    if(event.getAction() ==MotionEvent.ACTION_DOWN){
                        message = "";
                        bluesend(message);
                    }
                    break;

                case R.id.button5:
                    message = "AD";
                    bluesend(message);
                    break;

                case R.id.button6:
                    message = "80";
                    bluesend(message);
                    break;

                case R.id.button7:
                    message = "83";
                    bluesend(message);
                    break;

                case R.id.button8:
                    message = "84";
                    bluesend(message);
                    break;


                default:
                    break;
            }
            return false;
        }
    }
    //发送数据函数
    public void bluesend(String message){
        try{
            outputStream = bluetoothSocket.getOutputStream();
            Log.d("send",message);
            outputStream.write(getHexBytes(message));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void vibrator(){
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        try{
            bluetoothSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(blueAddress);
        try{
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
            Log.d("true","开始连接");
            bluetoothSocket.connect();
            Log.d("true","完成连接");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //我还得把字符串变成16进制好让这个zz机器人读懂
    private byte[] getHexBytes(String message){
        int len = message.length()/2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for(int i = 0,j = 0;j<len;i +=2, j++){
            hexStr[j] = ""+chars[i]+chars[i+1];
            bytes[j] = (byte)Integer.parseInt(hexStr[j],16);
        }
        return bytes;
    }

}
