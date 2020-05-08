package com.fpnnsdk_dmo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.fpnn.sdk.ConnectionConnectedCallback;
import com.fpnn.sdk.ConnectionWillCloseCallback;
import com.fpnn.sdk.ErrorCode;
import com.fpnn.sdk.ErrorRecorder;
import com.fpnn.sdk.TCPClient;
import com.fpnn.sdk.proto.Answer;
import com.fpnn.sdk.proto.Quest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public void  jiamitest()
    {
        byte[] derdata = null;
        try {
            InputStream in = this.getAssets().open("secp256k1-public.der");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while((rc=in.read(buff, 0, 100))>0) {
                baos.write(buff, 0, rc);
            }

            derdata =  baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TCPClient nihao = new TCPClient("52.83.245.22",9876);

        //for fpnn encrypt
        if (!nihao.enableEncryptorByDerData("secp256k1",derdata)){
            ErrorRecorder recorder = (ErrorRecorder)ErrorRecorder.getInstance();
            recorder.println();
            return;
        }

        //for connect callback
        nihao.setConnectedCallback(new ConnectionConnectedCallback() {
            @Override
            public void connectResult(InetSocketAddress inetSocketAddress, boolean b) {
                Log.i("login", "--- opened ----");
            }
        });

        //for disconnect callback
        nihao.setWillCloseCallback(new ConnectionWillCloseCallback() {
            @Override
            public void connectionWillClose(InetSocketAddress inetSocketAddress, boolean causedByError) {
                Log.i("login","Connection closed by error? " + causedByError);
            }
        });

        Quest quest = new Quest("two way demo");
        try {
            Answer answer = nihao.sendQuest(quest);
            ErrorRecorder recorder = (ErrorRecorder)ErrorRecorder.getInstance();
            recorder.println();

            if (answer == null) {
                Log.e("login", "login error");
                return;
            }
            int code = answer.getErrorCode();
            if (code != ErrorCode.FPNN_EC_OK.value()) {
                Log.e("login", "login return error :" + code + "-" + answer.getErrorMessage());
                return;
            }

            Map ret = answer.getPayload();
            JSONObject json =new JSONObject(ret);
            Log.i("login",json.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                jiamitest();}}).start();
    }
}
