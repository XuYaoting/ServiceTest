package com.example.apple.servicetest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TickerActivity extends AppCompatActivity implements View.OnClickListener{



    void getTime3(){
        Calendar calendar = Calendar.getInstance();
        String created = calendar.get(Calendar.YEAR) + "年"
                + (calendar.get(Calendar.MONTH)+1) + "月"//从0计算
                + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                + calendar.get(Calendar.HOUR_OF_DAY) + "时"
                + calendar.get(Calendar.MINUTE) + "分"+calendar.get(Calendar.SECOND)+"s";
        Log.e("Time2", created);
    }



//    void getTime4(){
//        Time t=new Time("GMT+8"); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
//        t.setToNow(); // 取得系统时间。
//        String time=t.year+"年 "+(t.month+1)+"月 "+t.monthDay+"日 "+t.hour+"h "+t.minute+"m "+t.second;
//        Log.e("Time4", time);
//    }
//
//    Timestamp t ;
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//    try ...{
//        t = new Timestamp(format.parse("2007-07-19 00:00:00").getTime());
//    } catch (ParseException e) ...{
//        e.printStackTrace();
//    }
//    Timestamp t ;
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//    t = new Timestamp(new Date().getTime());







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(TickerActivity.this, Manifest.permission.
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TickerActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE },1);          //提示的其实不一定是错的地方，只是机械显示而已
        }

        Button startService=(Button) findViewById(R.id.start_service);
        Button stopService=(Button) findViewById(R.id.stop_service);
        Button ShowTime=(Button) findViewById(R.id.button3);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);


        //时间输出
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String today = null;
        if (day == 2) {
            today = "Monday";
        } else if (day == 3) {
            today = "Tuesday";
        } else if (day == 4) {
            today = "Wednesday";
        } else if (day == 5) {
            today = "Thursday";
        } else if (day == 6) {
            today = "Friday";
        } else if (day == 7) {
            today = "Saturday";
        } else if (day == 1) {
            today = "Sunday";
        }
        System.out.println("Today is:" + today);
        final String weekday=today;


        //时间输出
        SimpleDateFormat f4= new SimpleDateFormat("今天是"+"yyyy年MM月dd日 E kk点mm分  ");
        //可根据不同样式请求显示不同日期格式，要显示星期可以添加E参数
        final String Time5=f4.format(new Date());
        System.out.println(Time5);
        //代码输出的日期格式为：今天是2012年03月22日 星期四 16点46分
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
        System.out.println("Date to String "+formater.format(new Date()));
        //相近的常用形式还有 yyMMdd hh:mm:ss yyyy-MM-dd hh:mm:ss dd-MM-yyyy hh:mm:ss

        ShowTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TickerActivity.this,Time5+weekday,Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.start_service:
                Intent startIntent=new Intent(this,MyService.class);
                startService(startIntent);//启动服务
                break;
            case R.id.stop_service:
                Intent stopIntent=new Intent(this,MyService.class);
                stopService(stopIntent);//停止服务
                break;
            default:
                break;
        }
    }

}
