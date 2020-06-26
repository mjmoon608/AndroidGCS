package com.example.naver_map1;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ReverseGeocording extends AsyncTask<String, String, String> {
    private static final String clientId = "4eg2x03t7o";
    private static final String clientSecret = "2mvLSCRXfYtzuVkpM6vsNhOXyU3NnNb6fBWAIApw";
//    private double latitude;
//    private double longitude;
//    public ReverseGeocording(double latitude, double longitude) {
//        try {
//            String text = URLEncoder.encode(String.format("%f,%f",latitude,longitude), "UTF-8");
//            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords=" + text;
//            URL url = new URL(apiURL);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("X-Naver-Client-Id", clientId);
//            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
//            // response 수신
//            int responseCode = con.getResponseCode();
////                    System.out.println("responseCode=" + responseCode);
//            Log.d("HTTP 응답 코드: ", ""+responseCode);
//            BufferedReader br;
//            if (responseCode == 200) {
//                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            } else {
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//            }
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//            while ((inputLine = br.readLine()) != null) {
//                response.append(inputLine);
//            }
//            br.close();
////                    System.out.println(response.toString());
//            Log.d("HTTP body: ", response.toString());
//        } catch (Exception e) {
//            System.out.println(e);
//            //Log.d()
//        }
//    }

    @Override
    protected String doInBackground(String ... coords) {
        String text = coords[0];

        
        String result = null;
        StringBuilder urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords=" + text + "&sourcecrs=epsg:4326&output=json&orders=addr");
        try {
//            String text = URLEncoder.encode(String.format("%f,%f",latitude,longitude), "UTF-8");
//            String text = URLEncoder.encode(url_coords, "UTF-8");
//            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords=" + text + "&sourcecrs=epsg:4326&output=json&orders=addr";

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type", "application/json");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            // response 수신
//            Log.d("testLog", "test: " + con.getResponseCode());
//            System.out.println("responseCode=" + responseCode);
            int responseCode = con.getResponseCode();
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
            result = response.toString();
        } catch (Exception e) {
//            System.out.println(e);
            Log.d("error: ", ""+e);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //result -> status -> name : "invalid request" 일 땐 Toast 메세지로 다른 좌표 찍으라고 알리기

        //result -> status -> name: "ok" 가 들어왔을 땐 해당 좌표 마커 찍기

        Log.d("HTTP Result : ", result);
    }
}
