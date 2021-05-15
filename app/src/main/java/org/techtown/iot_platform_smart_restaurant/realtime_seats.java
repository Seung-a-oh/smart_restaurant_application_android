package org.techtown.iot_platform_smart_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Realtime_seats extends AppCompatActivity {
    static int seat_number;
    static int[] YesOrNo = new int[7];

    public void showToast(String data){
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    // [코드 작성] 설정 시간(t)이 지나고, 테이블의 이용이 없다면 예약 가능하게 변경.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realtime_seats);

        //잔여석 표시하는 코드
        TextView available = findViewById(R.id.textView2);
        available.setText("16"); //서버에서 받아온 값으로 수정해야함.

        final Button button1 = findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number=1;
                if (YesOrNo[seat_number] == 1){
                    button1.setEnabled(false);
                    return;
                }
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);
            }
        });

        final Button button2 = (Button)findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number = 2;
                if (YesOrNo[seat_number] == 1){
                    button2.setEnabled(false);
                    return;
                }
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);

            }
        });

        final Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number = 3;
                if (YesOrNo[seat_number] == 1){
                    button3.setEnabled(false);
                    return;
                }
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);
            }
        });


        final Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number = 4;
                if (YesOrNo[seat_number] == 1){
                    button4.setEnabled(false);
                    return;
                }
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);

            }
        });


        final Button button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number = 5;
                if (YesOrNo[seat_number] == 1){
                    button5.setEnabled(false);
                    return;
                }
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);
            }
        });


        final Button button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seat_number = 6;
                if (YesOrNo[seat_number] == 1){
                    button6.setEnabled(false);
                    return;
                }
                Intent intent = new Intent(Realtime_seats.this,
                        Booking.class);
                startActivity(intent);
            }
        });
    }
}