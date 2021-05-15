package org.techtown.iot_platform_smart_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Booking extends Realtime_seats {
    int t = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.booking);

        //선택한 좌석 번호 출력
        TextView booking_ment = (TextView)findViewById(R.id.booking_ment);
        booking_ment.setText(String.format("%d 번 좌석을 예약하시겠습니까?", seat_number));


        Button time_minus = (Button)findViewById(R.id.time_minus);
        time_minus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView available = (TextView)findViewById(R.id.time);
                if (t==10){
                    ;
                }
                else {
                    available.setText("" + (t - 10));
                    t -= 10;
                }
            }
        });

        Button time_plus = (Button)findViewById(R.id.time_plus);
        time_plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView available = (TextView)findViewById(R.id.time);
                if (t==30){
                    ;
                }
                else {
                    available.setText(""+ (t+10));
                    t += 10;
                }
            }
        });
//      +)t 시간이 지나면 예약 취소, t시간 안에 예약이 이루어지면 바로 취소


        //"예" 클릭시 창 닫히고, 토스트 메세지 출력
        Button yes_button = findViewById(R.id.yes_button);
        yes_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                YesOrNo[seat_number] = 1;
                finish();
                showToast(String.format("%d번 좌석이 예약되었습니다.", seat_number));
                //서버에 예약 되었다는 정보 전송하는 코드 작성 필요
            }
        });

        //"아니오" 클릭시 창 닫히고, 토스트 메세지 출력
        Button no_button = findViewById(R.id.no_button);
        no_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                YesOrNo[seat_number]=0;
                finish();
            }
        });

    }



    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


}