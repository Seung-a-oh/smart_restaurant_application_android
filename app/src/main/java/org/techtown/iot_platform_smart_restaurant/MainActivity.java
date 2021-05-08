package org.techtown.iot_platform_smart_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button seat_button = (Button)findViewById(R.id.seat_button);
        seat_button.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, realtime_seats.class);
               startActivity(intent);
           }
        });

       Button cooking_button = (Button)findViewById(R.id.cooking_button);
       cooking_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, cooking.class);
                startActivity(intent);
            }
       });

//        만약 테이블을 선택하면
//                팝업창: 좌석을 예약하시겠습니까?
//                    예 -> 시간 - +
    }
}