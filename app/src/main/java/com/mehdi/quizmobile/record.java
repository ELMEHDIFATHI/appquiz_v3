package com.mehdi.quizmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class record extends AppCompatActivity {
    private static int MICROPHONE_PERMISSION_CODE = 200;
    MediaRecorder mediaRecorder;
    MediaPlayer mMediaPlayer;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        b1= (Button) findViewById(R.id.Button1);

        if(isMicrophonePresent()){
            getMicrophonePermission();
        }


    }






    public void  bntrecord(View v){
        try {
            mediaRecorder = new MediaRecorder();
           mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
           mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getRecordingFilePath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
           mediaRecorder.prepare();
           mediaRecorder.start();
            Toast.makeText(this,"Recording is started",Toast.LENGTH_LONG).show();


        }catch(Exception E){
                E.printStackTrace();
        }



    }

    public void  bntplay(View v){

        try{
          mMediaPlayer=new MediaPlayer();
           mMediaPlayer.setDataSource(getRecordingFilePath());
           mMediaPlayer.prepare();
          mMediaPlayer.start();

            Toast.makeText(this,"Recording is played",Toast.LENGTH_LONG).show();


        }catch(Exception E){
            E.printStackTrace();
        }


    }

    public void  bntstop(View v){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;

        Toast.makeText(this,"Recording is stoped",Toast.LENGTH_LONG).show();


    }

    private boolean isMicrophonePresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return  true;
        }else{
            return false;
        }
    }

    private void getMicrophonePermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION_CODE);
        }
    }
    private String getRecordingFilePath(){
        ContextWrapper contextWrapper=new ContextWrapper(getApplicationContext());
        File musicDirectory=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file=new File(musicDirectory,"test"+".mp3");
        return file.getPath();

    }



}