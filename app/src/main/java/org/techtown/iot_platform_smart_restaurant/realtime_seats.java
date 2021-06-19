package org.techtown.iot_platform_smart_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import fr.arnaudguyon.xmltojsonlib. XmlToJson;

public class Realtime_seats extends AppCompatActivity {
    public static int seat_number=0;
    public static int[] YesOrNo = new int[7];
    public static String Using_seat = "";
    public static int[] t = {0,10,10,10,10,10,10};
    public static String reservedTable = "000000000000";

    public void showToast(String data){
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realtime_seats);

        GetAEInfo();

        final ImageView refresh = (ImageView)findViewById(R.id.refresh);

        final Button button1 = findViewById(R.id.button1);
        final Button button2 = (Button)findViewById(R.id.button2);
        final Button button3 = (Button)findViewById(R.id.button3);
        final Button button4 = (Button)findViewById(R.id.button4);
        final Button button5 = (Button)findViewById(R.id.button5);
        final Button button6 = (Button)findViewById(R.id.button6);


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrieveRequest req = new RetrieveRequest("usedTable");
                req.setReceiver(new IReceived() {
                    public void getResponseBody(final String msg) {
                        handler.post(new Runnable() {
                            public void run() {

                                Log.d(TAG, msg);
                                reservedTable = getContainerContentXML(msg);
                                Using_seat = getContainerContentXML(msg);

                                //사용중, 혹은 예약된 좌석을 골라서 비활성화 처리
                                if(Using_seat.substring(0,1).equals("1") || Using_seat.substring(1,2).equals("1")){
                                    button1.setEnabled(false);
                                }
                                if(Using_seat.substring(2,3).equals("1") || Using_seat.substring(3,4).equals("1")){
                                    button2.setEnabled(false);
                                }
                                if(Using_seat.substring(4,5).equals("1") || Using_seat.substring(5,6).equals("1")){
                                    button3.setEnabled(false);
                                }
                                if(Using_seat.substring(6,7).equals("1") || Using_seat.substring(7,8).equals("1")){
                                    button4.setEnabled(false);
                                }
                                if(Using_seat.substring(8,9).equals("1") || Using_seat.substring(9,10).equals("1")){
                                    button5.setEnabled(false);
                                }
                                if(Using_seat.substring(10,11).equals("1") || Using_seat.substring(11,12).equals("1")){
                                    button6.setEnabled(false);
                                }

                                if(Using_seat.substring(0,2).equals("00")){
                                    button1.setEnabled(true);
                                    YesOrNo[1] = 0;
                                }
                                if(Using_seat.substring(2,4).equals("00") ){
                                    button2.setEnabled(true);
                                    YesOrNo[2] = 0;
                                }
                                if(Using_seat.substring(4,6).equals("00") ){
                                    button3.setEnabled(true);
                                    YesOrNo[3] = 0;
                                }
                                if(Using_seat.substring(6,8).equals("00") ){
                                    button4.setEnabled(true);
                                    YesOrNo[4] = 0;
                                }
                                if(Using_seat.substring(8,10).equals("00") ){
                                    button5.setEnabled(true);
                                    YesOrNo[5] = 0;
                                }
                                if(Using_seat.substring(10,12).equals("00") ){
                                    button6.setEnabled(true);
                                    YesOrNo[6] = 0;
                                }


                            }
                        });
                    }
                });
                req.start();
            }
        });


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
                getSeat();
                startActivity(intent);
            }
        });

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
                getSeat();
                startActivity(intent);
            }
        });

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
                getSeat();
                startActivity(intent);
            }
        });

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
                getSeat();
                startActivity(intent);

            }
        });

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
                getSeat();
                startActivity(intent);
            }
        });

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
                getSeat();
                startActivity(intent);
            }
        });
    }

    public int getSeat(){
        return seat_number;
    }

    final String Mobius_Address ="203.253.128.177";
    // private EditText EditText_Address ="203.253.128.177";
    private static CSEBase csebase = new CSEBase();
    private static AE ae = new AE();
    public Handler handler;
    private static String TAG = "Seats";
    private String ServiceAEName = "IP-team06";

    public Realtime_seats(){
        handler = new Handler();
    }



    public void GetAEInfo(){
        csebase.setInfo(Mobius_Address,"7579","Mobius","1883");

        ae.setAppName("smart_restaurant");
        Realtime_seats.aeCreateRequest aeCreate = new Realtime_seats.aeCreateRequest();
        aeCreate.setReceiver(new Booking.IReceived() {
            public void getResponseBody(final String msg) {
                handler.post(new Runnable() {
                    public void run() {
                        Log.d(TAG, "** AE Create ResponseCode[" + msg +"]");
                        if( Integer.parseInt(msg) == 201 ){
                        }
                        else { // If AE is Exist , GET AEID
                            Realtime_seats.aeRetrieveRequest aeRetrive = new Realtime_seats.aeRetrieveRequest();
                            aeRetrive.setReceiver(new Booking.IReceived() {
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
        private final Logger LOG = Logger.getLogger(Booking.aeCreateRequest.class.getName());
        String TAG = Booking.aeCreateRequest.class.getName();
        private Booking.IReceived receiver;
        int responseCode=0;
        public ApplicationEntityObject applicationEntity;
        public void setReceiver(Booking.IReceived hanlder) { this.receiver = hanlder; }
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

    public interface IReceived {
        void getResponseBody(String msg);
    }

    class RetrieveRequest extends Thread {
        private final Logger LOG = Logger.getLogger(RetrieveRequest.class.getName());
        private IReceived receiver;
        //        private String ContainerName = "cnt-co2";
        private String ContainerName = "";


        public RetrieveRequest(String containerName) {
            this.ContainerName = containerName;
        }
        public RetrieveRequest() {}
        public void setReceiver(IReceived hanlder) { this.receiver = hanlder; }

        @Override
        public void run() {
            try {
                String sb = csebase.getServiceUrl() + "/" + ServiceAEName + "/" + ContainerName + "/" + "latest";
//                String sb = "http://203.253.128.177:7579/Mobius/IP-team06/usedTable/latest";

//                csebase.getServiceUrl() + "/" + ServiceAEName + "/" + ContainerName + "/" + "latest";

                URL mUrl = new URL(sb);

                HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(false);

                conn.setRequestProperty("Accept", "application/xml");
                conn.setRequestProperty("X-M2M-RI", "12345");
                conn.setRequestProperty("X-M2M-Origin", ae.getAEid() );
                conn.setRequestProperty("nmtype", "long");
                conn.connect();

                String strResp = "";
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String strLine= "";
                while ((strLine = in.readLine()) != null) {
                    strResp += strLine;
                }

                if ( strResp != "" ) {
                    receiver.getResponseBody(strResp);
                }
                conn.disconnect();

            } catch (Exception exp) {
                LOG.log(Level.WARNING, exp.getMessage());
            }
        }
    }

    class SubscribeResource extends Thread {
        private final Logger LOG = Logger.getLogger(SubscribeResource.class.getName());
        private IReceived receiver;
        //        private String container_name = "cnt-co2"; //change to control container name
        private String container_name; //change to control container name

        public ContentSubscribeObject subscribeInstance;
        public SubscribeResource(String containerName) {
            subscribeInstance = new ContentSubscribeObject();
            subscribeInstance.setUrl(csebase.getHost());
            subscribeInstance.setResourceName(ae.getAEid()+"_rn");
            subscribeInstance.setPath(ae.getAEid()+"_sub");
            subscribeInstance.setOrigin_id(ae.getAEid());

            // added by J. Yun, SCH Univ.
            this.container_name = containerName;
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
                conn.setRequestProperty("Content-Type", "application/vnd.onem2m-res+xml; ty=23");
                conn.setRequestProperty("locale", "ko");
                conn.setRequestProperty("X-M2M-RI", "12345");
                conn.setRequestProperty("X-M2M-Origin", ae.getAEid());

                String reqmqttContent = subscribeInstance.makeXML();
                conn.setRequestProperty("Content-Length", String.valueOf(reqmqttContent.length()));

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.write(reqmqttContent.getBytes());
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

            } catch (Exception exp) {
                LOG.log(Level.SEVERE, exp.getMessage());
            }
        }
    }

    private String getContainerContentXML(String msg) {
        String con = "";
        try {
            XmlToJson xmlToJson = new XmlToJson.Builder(msg).build();
            JSONObject jsonObject = xmlToJson.toJson();
            con = jsonObject.getJSONObject("m2m:cin").getString("con");
//            Log.d(TAG, "Content is " + con);
        } catch (JSONException e) {
            Log.e(TAG, "JSONObject error!");
        }
        return con;
    }

    // get ae
    class aeRetrieveRequest extends Thread {
        private final Logger LOG = Logger.getLogger(Booking.aeCreateRequest.class.getName());
        private Booking.IReceived receiver;
        int responseCode=0;

        public aeRetrieveRequest() {
        }
        public void setReceiver(Booking.IReceived hanlder) {
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
        private final Logger LOG = Logger.getLogger(Booking.ControlRequest.class.getName());
        private Booking.IReceived receiver;
        //        private String container_name = "cnt-led";
        private String container_name = "usedTable";


        public ContentInstanceObject contentinstance;
        public ControlRequest(String comm) {
            contentinstance = new ContentInstanceObject();
            contentinstance.setContent(comm);
        }

        public void setReceiver(Booking.IReceived hanlder) { this.receiver = hanlder; }

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
}