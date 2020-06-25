package com.example.naver_map1;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ReverseGeocording extends AsyncTask<Double, Double, String> {
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
    protected String doInBackground(Double ... doubles) {
        double latitude = doubles[0];
        double longitude = doubles[1];
        
        String result = null;

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
            result = response.toString();
        } catch (Exception e) {
            System.out.println(e);
            //Log.d()
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d("HTTP Result : ", result);
    }
}
