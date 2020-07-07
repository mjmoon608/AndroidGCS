package com.example.naver_map1;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class NaverMapView extends FragmentActivity implements OnMapReadyCallback {
    private MapView mapView;
    private FusedLocationSource locationSource;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    Button go_main_activity;

    InfoWindow infoWindow = new InfoWindow();
    Marker kunsan_Uni = new Marker();

    LocationManager mLocMan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_mapview);
        go_main_activity = findViewById(R.id.go_main_activity);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        mLocMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);


        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_view, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        Intent go_main_intnet = new Intent(this, MainActivity.class);

        go_main_activity.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(go_main_intnet);
            }
        });


    }
//    @UiThread
//    @Override
//    public void onMapReady(@NonNull NaverMap naverMap) {
//
//        if (!mLocMan.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            //GPS 꺼져있을때 DIALOG로 위치 켤건지 끌껀지 확인 및 설정이동
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("GPS 연결 실패");
//            builder.setMessage("설정에서 GPS를 켜시겠습니까?\n아니오 선택시 군산대학교로 위치 이동");
//            //예 선택시 GPS 설정으로 이동
//            builder.setPositiveButton("예",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
////                            Toast.makeText(getApplicationContext(), "예를 선택했다", Toast.LENGTH_SHORT).show();
//                            Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                            startActivity(gpsOptionsIntent);
//                        }
//                    });
//            //아니오 선택시 기본 위치인 군산대학교로 카메라 이동.
//            builder.setNegativeButton("아니오",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(getApplicationContext(), "기본 위치인 군산대학교로 이동", Toast.LENGTH_LONG).show();
//                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.945280, 126.682140));
//                            naverMap.moveCamera(cameraUpdate);
//                        }
//                    });
//            builder.show();
////            Toast.makeText(getApplicationContext(), "GPS 실패", Toast.LENGTH_SHORT).show();
//        }
//
//        naverMap.setLocationSource(locationSource);
//        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
//
//
//        naverMap.setMapType(NaverMap.MapType.Satellite);
//
//
//        kunsan_Uni.setTag("군산대학교 정보창");
//        kunsan_Uni.setOnClickListener(overlay -> {
//            if (kunsan_Uni.getInfoWindow() == null) {
//                infoWindow.open(kunsan_Uni);
//            } else {
//                infoWindow.close();
//            }
//            return true;
//        });
//
//        kunsan_Uni.setPosition(new LatLng(35.945347, 126.682148));
//        kunsan_Uni.setCaptionText("군산대학교");
//
//        kunsan_Uni.setMap(naverMap);
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

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

        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);


        naverMap.setMapType(NaverMap.MapType.Satellite);


        kunsan_Uni.setTag("군산대학교 정보창");
        kunsan_Uni.setOnClickListener(overlay -> {
            if (kunsan_Uni.getInfoWindow() == null) {
                infoWindow.open(kunsan_Uni);
            } else {
                infoWindow.close();
            }
            return true;
        });

        kunsan_Uni.setPosition(new LatLng(35.945347, 126.682148));
        kunsan_Uni.setCaptionText("군산대학교");

        kunsan_Uni.setMap(naverMap);


    }
}