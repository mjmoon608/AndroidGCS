package com.example.naver_map1;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    LocationManager mLocMan;

    Button map_btn1;
    Button map_btn2;
    Button map_btn3;
    ToggleButton map_toggle1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map_btn1 = findViewById(R.id.map_btn1);
        map_btn2 = findViewById(R.id.map_btn2);
        map_btn3 = findViewById(R.id.map_btn3);
        map_toggle1 = findViewById(R.id.map_toggle1);

        mLocMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


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


        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
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

        if (!mLocMan.isProviderEnabled(LocationManager.GPS_PROVIDER)){
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
                            Toast.makeText(getApplicationContext(),"기본 위치인 군산대학교로 이동",Toast.LENGTH_LONG).show();
                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.945280, 126.682140));
                            naverMap.moveCamera(cameraUpdate);
                        }
                    });
            builder.show();
//            Toast.makeText(getApplicationContext(), "GPS 실패", Toast.LENGTH_SHORT).show();
        }else{
            //GPS 켜져 있을때 해당 정보 받아와서 map update
            naverMap.setLocationSource(locationSource);
            naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        }


        naverMap.setMapType(NaverMap.MapType.Satellite);
        UiSettings uiSettings = naverMap.getUiSettings();

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

}