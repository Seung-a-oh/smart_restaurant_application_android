package org.techtown.iot_platform_smart_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Booking extends Realtime_seats {
    int t = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.booking);

        // Create AE and Get AEID
        GetAEInfo();

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
                //onem2m의 파일에서 데이터 받아오기
//                String br =((onem2m_post_cin)onem2m_post_cin.mContext).test(seat_number);
                //String booked_resource = Integer.toString(br);

//                String seat = Integer.toString(seat_number);
                String br = ((Booked_Table)Booked_Table.mContext).set_table(seat_number);

                ControlRequest req = new ControlRequest(br);
                req.start();

                finish();
                showToast(String.format("%d번 좌석이 예약되었습니다.", seat_number));

//                postReservedTable rsv = new postReservedTable();
//                rsv.setTable(seat_number);

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


    final String Mobius_Address ="203.253.128.177";
   // private EditText EditText_Address ="203.253.128.177";
    private static CSEBase csebase = new CSEBase();
    private static AE ae = new AE();
    public Handler handler;
    private static String TAG = "Booking";
    private String ServiceAEName = "IP-team06";

    public interface IReceived {
        void getResponseBody(String msg);
    }

    public Booking() {
        handler = new Handler();
    }

public void GetAEInfo(){
    //Mobius_Address = EditText_Address.getText().toString();
    csebase.setInfo("203.253.128.177","7579","Mobius","1883");

    ae.setAppName("ncube");
    aeCreateRequest aeCreate = new aeCreateRequest();
    aeCreate.setReceiver(new IReceived() {
        public void getResponseBody(final String msg) {
            handler.post(new Runnable() {
                public void run() {
                    Log.d(TAG, "** AE Create ResponseCode[" + msg +"]");
                    if( Integer.parseInt(msg) == 201 ){
                        ;
                    }
                    else { // If AE is Exist , GET AEID
                        aeRetrieveRequest aeRetrive = new aeRetrieveRequest();
                        aeRetrive.setReceiver(new IReceived() {
                            public void getResponseBody(final String resmsg) {
                                handler.post(new Runnable() {
                                    public void run() {
                                        Log.d(TAG, "** AE Retrive ResponseCode[" + resmsg +"]");
                                    }
                                });
                            }
                        });
                        aeRetrive.start();
                    }
                }
            });
        }
    });
    aeCreate.start();
}

    // post ae
    class aeCreateRequest extends Thread {
        private final Logger LOG = Logger.getLogger(aeCreateRequest.class.getName());
        String TAG = aeCreateRequest.class.getName();
        private IReceived receiver;
        int responseCode=0;
        public ApplicationEntityObject applicationEntity;
        public void setReceiver(IReceived hanlder) { this.receiver = hanlder; }
        public aeCreateRequest(){
            applicationEntity = new ApplicationEntityObject();
            applicationEntity.setResourceName(ae.getappName());
            Log.d(TAG, ae.getappName() + "JJjj");
        }
        @Override
        public void run() {
            try {

                String sb = csebase.getServiceUrl();
                URL mUrl = new URL(sb);

                HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setInstanceFollowRedirects(false);

                conn.setRequestProperty("Content-Type", "application/vnd.onem2m-res+xml;ty=2");
                conn.setRequestProperty("Accept", "application/xml");
                conn.setRequestProperty("locale", "ko");
                conn.setRequestProperty("X-M2M-Origin", "S"+ae.getappName());
                conn.setRequestProperty("X-M2M-RI", "12345");
                conn.setRequestProperty("X-M2M-NM", ae.getappName() );

                String reqXml = applicationEntity.makeXML();
                conn.setRequestProperty("Content-Length", String.valueOf(reqXml.length()));

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.write(reqXml.getBytes());
                dos.flush();
                dos.close();

                responseCode = conn.getResponseCode();

                BufferedReader in = null;
                String aei = "";
                if (responseCode == 201) {
                    // Get AEID from Response Data
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String resp = "";
                    String strLine;
                    while ((strLine = in.readLine()) != null) {
                        resp += strLine;
                    }

                    ParseElementXml pxml = new ParseElementXml();
                    aei = pxml.GetElementXml(resp, "aei");
                    ae.setAEid( aei );
                    Log.d(TAG, "Create Get AEID[" + aei + "]");
                    in.close();
                }
                if (responseCode != 0) {
                    receiver.getResponseBody( Integer.toString(responseCode) );
                }
                conn.disconnect();
            } catch (Exception exp) {
                LOG.log(Level.SEVERE, exp.getMessage());
            }

        }
    }

    // get ae
    class aeRetrieveRequest extends Thread {
        private final Logger LOG = Logger.getLogger(aeCreateRequest.class.getName());
        private IReceived receiver;
        int responseCode=0;

        public aeRetrieveRequest() {
        }
        public void setReceiver(IReceived hanlder) {
            this.receiver = hanlder;
        }

        @Override
        public void run() {
            try {
                String sb = csebase.getServiceUrl()+"/"+ ae.getappName();
                URL mUrl = new URL(sb);

                HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(false);

                conn.setRequestProperty("Accept", "application/xml");
                conn.setRequestProperty("X-M2M-RI", "12345");
                conn.setRequestProperty("X-M2M-Origin", "Sandoroid");
                conn.setRequestProperty("nmtype", "short");
                conn.connect();

                responseCode = conn.getResponseCode();

                BufferedReader in = null;
                String aei = "";
                if (responseCode == 200) {
                    // Get AEID from Response Data
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String resp = "";
                    String strLine;
                    while ((strLine = in.readLine()) != null) {
                        resp += strLine;
                    }

                    ParseElementXml pxml = new ParseElementXml();
                    aei = pxml.GetElementXml(resp, "aei");
                    ae.setAEid( aei );
                    //Log.d(TAG, "Retrieve Get AEID[" + aei + "]");
                    in.close();
                }
                if (responseCode != 0) {
                    receiver.getResponseBody( Integer.toString(responseCode) );
                }
                conn.disconnect();
            } catch (Exception exp) {
                LOG.log(Level.SEVERE, exp.getMessage());
            }
        }
    }

    //요청 보내기
    class ControlRequest extends Thread {
        private final Logger LOG = Logger.getLogger(ControlRequest.class.getName());
        private IReceived receiver;
        //        private String container_name = "cnt-led";
        private String container_name = "usedTable";


        public ContentInstanceObject contentinstance;
        public ControlRequest(String comm) {
            contentinstance = new ContentInstanceObject();
            contentinstance.setContent(comm);
        }

        public void setReceiver(IReceived hanlder) { this.receiver = hanlder; }

        @Override
        public void run() {
            try {
                String sb = csebase.getServiceUrl() + "/" + ServiceAEName + "/" + container_name;

                URL mUrl = new URL(sb);

                HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setInstanceFollowRedirects(false);

                conn.setRequestProperty("Accept", "application/xml");
                conn.setRequestProperty("Content-Type", "application/vnd.onem2m-res+xml;ty=4");
                conn.setRequestProperty("locale", "ko");
                conn.setRequestProperty("X-M2M-RI", "12345");
                conn.setRequestProperty("X-M2M-Origin", ae.getAEid() );

                String reqContent = contentinstance.makeXML();
                conn.setRequestProperty("Content-Length", String.valueOf(reqContent.length()));

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.write(reqContent.getBytes());
                dos.flush();
                dos.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String resp = "";
                String strLine="";
                while ((strLine = in.readLine()) != null) {
                    resp += strLine;
                }
                if (resp != "") {
                    receiver.getResponseBody(resp);
                }
                conn.disconnect();
            }
            catch (Exception exp) {
                LOG.log(Level.SEVERE, exp.getMessage());
            }
        }
    }

    private String getContainerName(String msg) {
        String cnt = "";
        try {
            JSONObject jsonObject = new JSONObject(msg);
            cnt = jsonObject.getJSONObject("pc").
                    getJSONObject("m2m:sgn").getString("sur");
            // Log.d(TAG, "Content is " + cnt);
        } catch (JSONException e) {
            Log.e(TAG, "JSONObject error!");
        }
        return cnt;
    }

    private String getContainerContentJSON(String msg) {
        String con = "";
        try {
            JSONObject jsonObject = new JSONObject(msg);
            con = jsonObject.getJSONObject("pc").
                    getJSONObject("m2m:sgn").
                    getJSONObject("nev").
                    getJSONObject("rep").
                    getJSONObject("m2m:cin").
                    getString("con");
//            Log.d(TAG, "Content is " + con);
        } catch (JSONException e) {
            Log.e(TAG, "JSONObject error!");
        }
        return con;
    }

    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

}