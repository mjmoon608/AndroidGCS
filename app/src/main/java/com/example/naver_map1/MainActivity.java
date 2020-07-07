package com.example.naver_map1;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PolygonOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;



    private boolean isDeleteMode = false;

    private final String clientId = "4eg2x03t7o";
    private final String clientSecret = "2mvLSCRXfYtzuVkpM6vsNhOXyU3NnNb6fBWAIApw";

    LocationManager mLocMan;

    Button map_btn1;
    Button map_btn2;
    Button map_btn3;
    ToggleButton map_toggle1;
    ToggleButton marker_delete_toggle;
    Button poly_delete;
    Button webview_intent_btn;

    Marker kunsan_Uni = new Marker();
    Marker kunsan_cityHall = new Marker();
    Marker kunsan_myHome = new Marker();

    InfoWindow infoWindow = new InfoWindow();

    Marker touch_marker = new Marker();
    int marker_index = 0;

    PolygonOverlay polygonOverlay = new PolygonOverlay();
    ArrayList<LatLng> polyCoords = new ArrayList<>();
    ArrayList<Marker> markers = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent go_webview_activity = new Intent(this, NaverMapView.class);




        map_btn1 = findViewById(R.id.map_btn1);
        map_btn2 = findViewById(R.id.map_btn2);
        map_btn3 = findViewById(R.id.map_btn3);
        map_toggle1 = findViewById(R.id.map_toggle1);
        marker_delete_toggle = findViewById(R.id.marker_delete);
        poly_delete = findViewById(R.id.poly_delete_btn);
        webview_intent_btn = findViewById(R.id.mapview_intent);




        mLocMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        map_btn3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Text: ", map_btn3.getText().toString());
                if (map_btn3.getText().toString().equals("SATELITE")) {
                    map_btn1.setText("BASIC");
                    map_btn2.setText("HYBRID");
                    map_btn1.setVisibility(v.VISIBLE);
                    map_btn2.setVisibility(v.VISIBLE);
                } else if (map_btn3.getText().toString().equals("BASIC")) {
                    map_btn1.setText("HYBRID");
                    map_btn2.setText("SATELITE");
                    map_btn1.setVisibility(v.VISIBLE);
                    map_btn2.setVisibility(v.VISIBLE);
                } else if (map_btn3.getText().toString().equals("HYBRID")) {
                    map_btn1.setText("BASIC");
                    map_btn2.setText("SATELITE");
                    map_btn1.setVisibility(v.VISIBLE);
                    map_btn2.setVisibility(v.VISIBLE);
                }
            }
        });

        map_btn1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map_btn1.getText().toString().equals("BASIC")) {
                    map_btn1.setText("HYBRID");
                    map_btn2.setText("SATELITE");
                    map_btn3.setText("BASIC");
                    map_btn1.setVisibility(View.INVISIBLE);
                    map_btn2.setVisibility(View.INVISIBLE);
                    naverMap.setMapType(NaverMap.MapType.Basic);
                } else if (map_btn1.getText().toString().equals("HYBRID")) {
                    map_btn1.setText("BASIC");
                    map_btn2.setText("SATELITE");
                    map_btn3.setText("HYBRID");
                    map_btn1.setVisibility(View.INVISIBLE);
                    map_btn2.setVisibility(View.INVISIBLE);
                    naverMap.setMapType(NaverMap.MapType.Hybrid);
                }
            }
        });
        map_btn2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map_btn2.getText().toString().equals("SATELITE")) {
                    map_btn1.setText("BASIC");
                    map_btn2.setText("HYBRID");
                    map_btn3.setText("SATELITE");
                    map_btn1.setVisibility(View.INVISIBLE);
                    map_btn2.setVisibility(View.INVISIBLE);
                    naverMap.setMapType(NaverMap.MapType.Satellite);
                } else if (map_btn2.getText().toString().equals("HYBRID")) {
                    map_btn1.setText("BASIC");
                    map_btn2.setText("SATELITE");
                    map_btn3.setText("HYBRID");
                    map_btn1.setVisibility(View.INVISIBLE);
                    map_btn2.setVisibility(View.INVISIBLE);
                    naverMap.setMapType(NaverMap.MapType.Hybrid);
                }
            }
        });

        poly_delete.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                polyCoords.clear();
                polygonOverlay.setMap(null);
                markers.clear();
            }
        });

        webview_intent_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(go_webview_activity);
            }
        });


        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);


        // 마커 정보창 생성
        kunsan_Uni.setTag("군산대학교 정보창");
        kunsan_Uni.setOnClickListener(overlay -> {
            if (kunsan_Uni.getInfoWindow() == null) {
                infoWindow.open(kunsan_Uni);
            } else {
                infoWindow.close();
            }
            return true;
        });

        kunsan_cityHall.setTag("군산시청 정보창");
        kunsan_cityHall.setOnClickListener(overlay -> {
            if (kunsan_cityHall.getInfoWindow() == null) {
                infoWindow.open(kunsan_cityHall);
            } else {
                infoWindow.close();
            }
            return true;
        });

        kunsan_myHome.setTag("우리 집 정보창");
        kunsan_myHome.setOnClickListener(overlay -> {
            if (kunsan_myHome.getInfoWindow() == null) {
                infoWindow.open(kunsan_myHome);
            } else {
                infoWindow.close();
            }
            return true;
        });


        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getApplicationContext()) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return (CharSequence) infoWindow.getMarker().getTag();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            } else {
                return;
            }
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        if (!mLocMan.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //GPS 꺼져있을때 DIALOG로 위치 켤건지 끌껀지 확인 및 설정이동

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS 연결 실패");
            builder.setMessage("설정에서 GPS를 켜시겠습니까?\n아니오 선택시 군산대학교로 위치 이동");
            //예 선택시 GPS 설정으로 이동
            builder.setPositiveButton("예",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(getApplicationContext(), "예를 선택했다", Toast.LENGTH_SHORT).show();
                            Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(gpsOptionsIntent);
                        }
                    });
            //아니오 선택시 기본 위치인 군산대학교로 카메라 이동.
            builder.setNegativeButton("아니오",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "기본 위치인 군산대학교로 이동", Toast.LENGTH_LONG).show();
                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.945280, 126.682140));
                            naverMap.moveCamera(cameraUpdate);
                        }
                    });
            builder.show();
//            Toast.makeText(getApplicationContext(), "GPS 실패", Toast.LENGTH_SHORT).show();
        }
//        else{
//            //GPS 켜져 있을때 해당 정보 받아와서 map update
//            naverMap.setLocationSource(locationSource);
//            naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
//
//        }
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);


        naverMap.setMapType(NaverMap.MapType.Satellite);
        UiSettings uiSettings = naverMap.getUiSettings();

        kunsan_Uni.setPosition(new LatLng(35.945347, 126.682148));
        kunsan_Uni.setCaptionText("군산대학교");

        kunsan_cityHall.setPosition(new LatLng(35.967640, 126.736849));
        kunsan_cityHall.setCaptionText("군산시청");

        kunsan_myHome.setPosition(new LatLng(35.944474, 126.686798));
        kunsan_myHome.setCaptionText("우리 집");

        kunsan_Uni.setMap(naverMap);
        kunsan_cityHall.setMap(naverMap);
        kunsan_myHome.setMap(naverMap);


//        // map click evnet로 infoWindow 꺼지도록 설정.
//        naverMap.setOnMapClickListener((coord, point) -> {
//            infoWindow.close();
//        });

        // 맵 터치 이벤트로 해당 좌표에 마커 생성
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
//                Marker touch_marker = new Marker();











//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                String coords = Double.toString(latitude) + ","+Double.toString(longitude);


                Toast.makeText(getApplicationContext(), String.format("%f, %f", latitude, longitude), Toast.LENGTH_SHORT).show();
                touch_marker.setPosition(new LatLng(latitude, longitude));
                touch_marker.setTag(String.format("%f, %f", latitude, longitude));
                markers.add(touch_marker);
                touch_marker.setMap(naverMap);

                // reverse geocording
                ReverseGeocording reverseGeocording = new ReverseGeocording(MainActivity.this);
//                reverseGeocording.doInBackground(coords);
                reverseGeocording.execute(coords);




                polyCoords.add(new LatLng(latitude,longitude));

//                polyCoords add 테스트용
//                Log.d("test polyCoords: ", polyCoords.size()+"");
//                for (int i = 0; i < polyCoords.size(); i++){
//                    Log.d("testPolyCoords: ", polyCoords.get(i)+"");
//                }

                if(polyCoords.size() >= 3){
                    polygonOverlay.setCoords(polyCoords);
                    polygonOverlay.setOutlineWidth(5);
                    polygonOverlay.setOutlineColor(Color.RED);
                    polygonOverlay.setMap(naverMap);
                }




                //coords 파라미터 값으로 지역을 알 수 없을 땐 Toast메세지로 다른 좌표 찍어달라고 하기

                //네이버 API 요청 성공적으로 받았을 때만 해당 위치에 좌표 찍기.

/*
                try {
                    String text = URLEncoder.encode(String.format("%f,%f",latitude,longitude), "UTF-8");
                    String apiURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords=" + text;
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                    // response 수신
                    int responseCode = con.getResponseCode();
//                    System.out.println("responseCode=" + responseCode);
                    Log.d("HTTP 응답 코드: ", ""+responseCode);
                    BufferedReader br;
                    if (responseCode == 200) {
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    } else {
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    br.close();
//                    System.out.println(response.toString());
                    Log.d("HTTP body: ", response.toString());
                } catch (Exception e) {
                    System.out.println(e);
                    //Log.d()
                }
*/
                touch_marker.setOnClickListener(overlay -> {
                    if (!isDeleteMode) {
                        if (touch_marker.getInfoWindow() == null) {
                            infoWindow.open(touch_marker);
                        } else {
                            infoWindow.close();
                        }
                    } else {
                        touch_marker.setMap(null);
                    }
                    return true;
                });
                marker_index++;
            }
        });

        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setZoomControlEnabled(false);
        uiSettings.setLocationButtonEnabled(true);
    }

    public void onToggleClick(View v) {
        boolean isOn = ((ToggleButton) v).isChecked();

        if (isOn) {
            naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_CADASTRAL, true);
        } else {
            naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_CADASTRAL, false);
        }
    }

    public void onDeleteToggleClick(View view) {
        boolean isOn = ((ToggleButton) view).isChecked();

        if (isOn) {
            isDeleteMode = true;
        } else {
            isDeleteMode = false;
        }
    }

//    public void onLoad(String addr){
//        String[] recvCoords = addr.split(",");
//        float latitude = Float.parseFloat(recvCoords[1]);
//        float longitude = Float.parseFloat(recvCoords[2]);
//        Marker touch_marker = new Marker();
//        touch_marker.setPosition(new LatLng(latitude, longitude));
//        touch_marker.setTag(latitude+"," +longitude);
//        touch_marker.setMap(this.naverMap);
//
//        touch_marker.setOnClickListener(overlay -> {
//            if (!isDeleteMode) {
//                if (touch_marker.getInfoWindow() == null) {
//                    infoWindow.open(touch_marker);
//                } else {
//                    infoWindow.close();
//                }
//            } else {
//                touch_marker.setMap(null);
//            }
//            return true;
//        });
//
//    }
}