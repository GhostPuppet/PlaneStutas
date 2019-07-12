package com.example.planestutas.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.planestutas.pojo.Tipt;
import com.example.planestutas.pojo.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MyWeatherService extends IntentService {
    String message = "success";
    ArrayList<WeatherData> weatherDatas = new ArrayList<>();

    public MyWeatherService() {
        super("MyWeatherService");
    }

    //当执行onHandleIntent时，会自动开启一个worker线程
    //并且当onHandleIntent方法的所有代码执行完毕，线程会自动消亡，服务也自动销毁
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                String city = URLEncoder.encode("天津","UTF-8");
                String url = "http://api.map.baidu.com/telematics/v3/weather?location="+ city +"&mcode=6F:3E:27:E3:FB:CF:2F:70:EA:54:5D:A4:A7:C9:42:B7:81:67:AA:0A;com.example.administrator.myapp23_weatherforcast&output=json&ak=5Lsh7GHRpGvEbYafVjMqtG0RRmWUFOgq";
                RequestParams params = new RequestParams(url);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        parseJson(result);
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e("iss","error");
                        message = "网络错误，请重试！";
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {}
                    @Override
                    public void onFinished() {
                        Log.e("iss","finished");
                    }
                });
            } catch (UnsupportedEncodingException e) {

            }
        }
    }
    //解析Json数据
    public void parseJson(String result){
        try {
            JSONObject root = new JSONObject(result);
            int error = root.getInt("error");
            if(error != 0){
                message = "数据访问参数错误!";
            }else{
                JSONArray results = root.getJSONArray("results");
                JSONObject info = results.getJSONObject(0);
                String pm25 = info.getString("pm25");
                //Log.e("iss","pm25="+pm25);

                JSONArray index = info.getJSONArray("index");
                ArrayList<Tipt> tipts = new ArrayList<>();
                for(int i=0;i<index.length();i++){
                    Tipt tipt = new Tipt();
                    JSONObject o = index.getJSONObject(i);
                    tipt.setDes(o.getString("des"));
                    tipt.setTipt(o.getString("tipt"));
                    tipt.setTitle(o.getString("title"));
                    tipt.setZs(o.getString("zs"));
                    tipts.add(tipt);
                }


                JSONArray weather_data = info.getJSONArray("weather_data");
                for(int i=0;i<weather_data.length();i++){
                    WeatherData data = new WeatherData();
                    JSONObject o = weather_data.getJSONObject(i);
                    data.setDate(o.getString("date"));
                    data.setDayPictureUrl(o.getString("dayPictureUrl"));
                    downLoadPicture(o.getString("dayPictureUrl"),i,"day");
                    data.setNightPictureUrl(o.getString("nightPictureUrl"));
                    downLoadPicture(o.getString("nightPictureUrl"),i,"night");
                    data.setWeather(o.getString("weather"));
                    data.setWind(o.getString("wind"));
                    data.setTemperature(o.getString("temperature"));
                    weatherDatas.add(data);
                }
                //发送广播

                //Log.e("a", "parseJson: "+weatherDatas );
                Intent intent = new Intent("com.iss.tiptweather");
                intent.putExtra("pm25",pm25);
                intent.putExtra("message",message);
                intent.putExtra("error",error);
                intent.putParcelableArrayListExtra("tipts",tipts);
                intent.putParcelableArrayListExtra("weatherDatas",weatherDatas);

                sendBroadcast(intent);

            }
        } catch (JSONException e) {

        }
    }
    public void downLoadPicture(String url,final int index,final String type) {
        url = url + "?random" + Math.random();
        //Log.e("iss", "url=" + url);
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //Log.e("iss","p="+result);
                if(type.equals("day")){
                    weatherDatas.get(index).setDayPicPath(result.getPath());
                }else if(type.equals("night")){
                    weatherDatas.get(index).setNightPicPath(result.getPath());
                }
                //发送广播
                Intent intent = new Intent("com.iss.weather");
                intent.putParcelableArrayListExtra("weatherDatas",weatherDatas);
                sendBroadcast(intent);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.e("iss","finished");
            }
        });
    }
}

