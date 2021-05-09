package org.techtown.iot_platform_smart_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Realtime_seats extends AppCompatActivity {
    public int seat_number;

    public void showToast(String data){
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realtime_seats);


        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number=1;
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);
                //1번이 클릭됐을때 전역변수 seat_number에 현재 선택한 좌석을 넣어놓고
//                booking에서 seat_number를 출력한다. -> 전역 변수 seat_number를 만든 뒤 상속.
            }
        });


        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number = 2;
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);
            }
        });


        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number = 3;
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);
            }
        });


        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number = 4;
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);
            }
        });


        Button button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number = 5;
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);
            }
        });


        Button button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number = 6;
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);
            }
        });
    }


}