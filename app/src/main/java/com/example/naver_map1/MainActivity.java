package com.example.naver_map1;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
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

    Button map_btn1;
    Button map_btn2;
    Button map_btn3;

    ArrayList<String> btnTextList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTextList.add("Basic");
        btnTextList.add("Terrain");
        btnTextList.add("Hybrid");


        map_btn1 = findViewById(R.id.map_btn1);
//        map_btn1.setText(btnTextList.get(0));
        map_btn1.setText("Basic");
        map_btn2 = findViewById(R.id.map_btn2);
//        map_btn2.setText(btnTextList.get(1));
        map_btn1.setText("Terrain");
        map_btn3 = findViewById(R.id.map_btn3);
//        map_btn3.setText(btnTextList.get(2));
        map_btn1.setText("Hybrid");

        map_btn3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (map_btn3.getText() == "Hybrid"){
                    map_btn1.setText("Basic");
                    map_btn2.setText("Terrain");
                    map_btn1.setVisibility(v.VISIBLE);
                    map_btn2.setVisibility(v.VISIBLE);
                }
                else if(map_btn3.getText() == "Basic"){
                    map_btn1.setText("Terrain");
                    map_btn2.setText("Hybrid");
                    map_btn1.setVisibility(v.VISIBLE);
                    map_btn2.setVisibility(v.VISIBLE);
                }
                else if(map_btn3.getText() == "Terrain"){
                    map_btn1.setText("Basic");
                    map_btn2.setText("Hybrid");
                    map_btn1.setVisibility(v.VISIBLE);
                    map_btn2.setVisibility(v.VISIBLE);
                }
            }
        });

        // map_btn1, 2 온 클릭 리스너 만들기




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
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        naverMap.setMapType(NaverMap.MapType.Hybrid);

    }

}