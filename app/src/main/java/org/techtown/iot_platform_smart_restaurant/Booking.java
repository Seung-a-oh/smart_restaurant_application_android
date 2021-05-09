package org.techtown.iot_platform_smart_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Booking extends Realtime_seats {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.booking);

        //"예" 클릭시 창 닫히고, 토스트 메세지 출력
        Button yes_button = findViewById(R.id.yes_button);
        yes_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
                showToast(String.format("%d번 좌석이 예약되었습니다.", seat_number));
                //서버에 seat_number의 예약 정보 전송하는 코드 작성 필요
            }
        });

        //"아니오" 클릭시 창 닫히고, 토스트 메세지 출력
        Button no_button = findViewById(R.id.no_button);
        no_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
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