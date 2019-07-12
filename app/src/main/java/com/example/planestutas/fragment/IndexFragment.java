package com.example.planestutas.fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planestutas.AdInfoActivity;
import com.example.planestutas.R;
import com.example.planestutas.WeatherInfoActivity;
import com.example.planestutas.myviews.MyGridView;
import com.example.planestutas.pojo.Ad;
import com.example.planestutas.pojo.Tipt;
import com.example.planestutas.pojo.WeatherData;
import com.example.planestutas.services.MyWeatherService;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment {
    private List<Ad> list;
    private MyGridView gridView;
    private MyAdapter myAdapter;
    private Intent intent;
    private  MyWeatherReceiver receiver;
    private TextView textView_current_temp;
    private TextView textView_pm25;
    private TextView textView_temp;
    private ImageView imageView_weather;
    private RelativeLayout weather_line;
    ArrayList<WeatherData> weatherDatas;
    ArrayList<Tipt> tipts;

    public IndexFragment() {}

    //LayoutInflater布局引入器
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        gridView = (MyGridView) view.findViewById(R.id.fragment_index_gridview);
        textView_current_temp = (TextView) view.findViewById(R.id.fragment_index_textview_current_temp);
        textView_pm25 = (TextView) view.findViewById(R.id.fragment_index_textview_pm25);
        textView_temp = (TextView) view.findViewById(R.id.fragment_index_textview_maxmintemp);
        imageView_weather = (ImageView) view.findViewById(R.id.fragment_index_textview_current_img);
        weather_line = (RelativeLayout) view.findViewById(R.id.fragment_index_weather_line);
        intent = new Intent(getContext(), MyWeatherService.class);

        //注册广播接收器
        receiver = new MyWeatherReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.iss.tiptweather");
        filter.addAction("com.iss.weather");
        getContext().registerReceiver(receiver,filter);


        getContext().startService(intent);
        setData();
        myAdapter = new MyAdapter();
        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(),"title="+list.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AdInfoActivity.class);
                intent.putExtra("title",list.get(position).getTitle());
                startActivity(intent);
            }
        });
        weather_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WeatherInfoActivity.class);
                intent.putParcelableArrayListExtra("weatherDatas",weatherDatas);
                intent.putParcelableArrayListExtra("tipts",tipts);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(receiver);
    }

    public void setData() {
        list = new ArrayList<>();
        list.add(new Ad(R.mipmap.ads1,"免费乘机泊车","深航旅客专享"));
        list.add(new Ad(R.mipmap.ads2,"免费升舱及积分礼包","青岛航空新会员专享"));
    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_index_gridview_item,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.fragment_index_gridview_item_imageview);
            TextView textView1 = (TextView) view.findViewById(R.id.fragment_index_gridview_item_textview1);
            TextView textView2 = (TextView) view.findViewById(R.id.fragment_index_gridview_item_textview2);
            imageView.setImageResource(list.get(position).getImageId());
            textView1.setText(list.get(position).getTitle());
            textView2.setText(list.get(position).getSubTitle());
            return view;
        }
    }
    //自定义一个广播接收器
    public class MyWeatherReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //Log.e("iss","action="+action);
            if("com.iss.tiptweather".equals(action)){
                String message = intent.getStringExtra("message");
                int error = intent.getIntExtra("error",-1);
                if(error == 0){
                    String pm25 = intent.getStringExtra("pm25");
                    textView_pm25.setText("pm2.5:"+pm25);
                    tipts = intent.getParcelableArrayListExtra("tipts");
                    weatherDatas = intent.getParcelableArrayListExtra("weatherDatas");
                    String t = weatherDatas.get(0).getDate();
                    int index = t.indexOf("：");
                    String current_temp = t.substring(index+1,t.length()-1);
                    textView_current_temp.setText(current_temp);
                }else{
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                }
            }else if("com.iss.weather".equals(action)){
                weatherDatas = intent.getParcelableArrayListExtra("weatherDatas");
                if(weatherDatas.get(0).getDayPicPath()!=null){
                    imageView_weather.setImageURI(Uri.parse(weatherDatas.get(0).getDayPicPath()));
                }
            }
        }
    }
}
