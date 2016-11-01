package com.example.ansan.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        if(isStoragePermissionGranted() == false){
            Toast.makeText(this,"SD Card 사용 에러",Toast.LENGTH_SHORT).show();
            return;
        }
        String strPath = Environment.getExternalStorageDirectory().getAbsolutePath();   //외장메모리 경로 찾기
        Toast.makeText(this,strPath,Toast.LENGTH_SHORT).show();
        File mydir = new File(strPath + "/test");
        switch (v.getId()){
            case R.id.button:
                mydir.mkdir();
                Toast.makeText(this,"폴더 생성 완료",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                mydir.delete();
                Toast.makeText(this,"폴더 삭제 완료",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3: String filename = strPath +"/test.txt";
                try {
                    FileOutputStream fos = new FileOutputStream(filename);
                    fos.write("머엉~".getBytes());
                    fos.close();
                    Toast.makeText(this,"저장 완료",Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button4: filename = strPath +"/test.txt";
                try {
                    FileInputStream fos = new FileInputStream(filename);
                    byte arr[] = new byte[fos.available()];
                    fos.read(arr);
                    fos.close();
                    String str = new String(arr);
                    Toast.makeText(this,"파일내용 : "+ str,Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;


        }
    }

    String TAG = "SDCARD";
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}
