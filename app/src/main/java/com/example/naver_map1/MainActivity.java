package com.example.naver_map1;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        // ...35.945543, 126.682142
        UiSettings uiSettings = naverMap.getUiSettings();
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        Context context = getApplicationContext();

        uiSettings.setLocationButtonEnabled(true);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        naverMap.addOnLocationChangeListener(location ->
                Toast.makeText(this,
                        location.getLatitude() + ", " + location.getLongitude(),
                        Toast.LENGTH_SHORT).show());

        // LatLng는 하나의 위경도 좌표를 나타내는 클래스
        // latitude가 위도를 longitude가 경도를 나타냄
        // LatLng의 모든 속성은 final 이므로 각 속성은 생성자로만 지정할 수 있고, 한 번 생성된 객체의 속성은 변경할 수 없다.
//        LatLng coord = new LatLng(35.945543, 126.682142);
//
//        Toast.makeText(context,
//                "위도: " + coord.latitude + ", 경도: " + coord.longitude,
//                Toast.LENGTH_SHORT).show();

        // MBR : 최소 경계 사각형(MBR : Minimun Bounding Rectangle) -> 하나의 영역을 나타내는 가장 간단한 방법
        // 영역을 다루는 API에서 자주 사용
        // LoatLngBounds는 위경도 좌표로 이루어진 하나의 MBR을 나타내는 클래스

//        LatLng southWest = new LatLng(31.43, 122.37);
//        LatLng northEast = new LatLng(44.35, 132);
//        LatLngBounds bounds = new LatLngBounds(southWest, northEast);
        // LatLngBounds.Builder를 사용하면 여러 좌표를 포함하는 MBR을 만들 수 있다.
//        LatLngBounds bounds = new LatLngBounds.Bulder()
//                .include(new LatLng(37.5640984, 126.9712268))
//                .include(new LatLng(37.5651279, 126.9767904))
//                .include(new LatLng(37.5625365, 126.9832241))
//                .include(new LatLng(37.5585305, 126.9809297))
//                .include(new LatLng(37.5590777, 126.974617))
//                .build();
        // mapType 속성을 지정하면 지도의 유형 변경 가능.
        // Basic, Navi, Satellite, Hybrid, Terrain, None
        naverMap.setMapType(NaverMap.MapType.Hybrid);
//        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);
//        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, false);
//
//
//        naverMap.setBuildingHeight(0.5f);
//
//        CameraPosition cameraPosition = new CameraPosition(
//                new LatLng(37.5666102, 126.9783881), // 대상 지점
//                16, // 줌 레벨
//                20, // 기울임 각도
//                180 // 베어링 각도
//        );
//
//        Toast.makeText(context,
//                "대상 지점 위도: " + cameraPosition.target.latitude + ", " +
//                        "대상 지점 경도: " + cameraPosition.target.longitude + ", " +
//                        "줌 레벨: " + cameraPosition.zoom + ", " +
//                        "기울임 각도: " + cameraPosition.tilt + ", " +
//                        "베어링 각도: " + cameraPosition.bearing,
//                Toast.LENGTH_SHORT).show();
    }

}