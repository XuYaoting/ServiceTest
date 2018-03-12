package com.example.apple.servicetest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class MyService extends Service {


    //我要定的时间数（到时候要变成输入的）
    // 到时候就不死板定时间了，到时候跟着世界时间
    //守护进程
    int min=5;
    int second=0;


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private MediaPlayer mediaPlayer=new MediaPlayer();

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("MyService","onCreate executed");

        //前台服务代码块
        Intent intent=new Intent(this,TickerActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("前台服务提示")
                .setContentText("前台服务正在运行")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);

            initMediaPlayer();//初始化MediaPlayer
    }

    private void initMediaPlayer(){
        try{
            File file= new File(Environment.getExternalStorageDirectory(),
                    "music.mp3");
            mediaPlayer.setDataSource(file.getPath());//指定音频文件路径
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onRequestRemissionsResult(int requestCode,String[] permissions,
                                          int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.
                        PERMISSION_GRANTED){
                    initMediaPlayer();
                }else{
                    Toast.makeText(this, "拒绝权限者不可使用程序",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }



    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.i("MyService","onStartCommand executed");

        //下面一块是定时代码
        new Thread(new Runnable(){
            @Override
            public void run(){
                //想办法在这里插入需要定时的操作
                mediaPlayer.start();//begin to play
            }
        }).start();
        AlarmManager manager=(AlarmManager) getSystemService(ALARM_SERVICE);
        int fiveMin=(min*60+second)*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+fiveMin;
        Intent i=new Intent(this,MyService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("MyService","onDestroy executed");
    }

}
