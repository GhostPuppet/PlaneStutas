package com.example.planestutas.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.planestutas.CalendarActivity;
import com.example.planestutas.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusFragment extends Fragment {
    private TextView textView_choose1,textView_choose2;
    private LinearLayout choose_line1,choose_line2;
    private TextView textView_date;
    private MyStatusRecevier recevier;

    public StatusFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        textView_choose1 = (TextView) view.findViewById(R.id.fragment_status_choose_textview1);
        textView_choose2 = (TextView) view.findViewById(R.id.fragment_status_choose_textview2);
        textView_date = (TextView) view.findViewById(R.id.fragment_status_textview_date);

        textView_date.setText(new SimpleDateFormat("yyyy-MM-dd  EEEE").format(new Date()));
        textView_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CalendarActivity.class);
                getContext().startActivity(intent);
            }
        });

        recevier = new MyStatusRecevier();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.iss.dateSelected");
        getContext().registerReceiver(recevier,filter);
        choose_line1 = (LinearLayout) view.findViewById(R.id.fragment_status_choose_line1);
        choose_line2 = (LinearLayout) view.findViewById(R.id.fragment_status_choose_line2);

        textView_choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_line1.setVisibility(View.VISIBLE);
                choose_line2.setVisibility(View.GONE);
            }
        });
        textView_choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_line1.setVisibility(View.GONE);
                choose_line2.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
    public class MyStatusRecevier extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if("com.iss.dateSelected".equals(intent.getAction())){
                long time = intent.getLongExtra("time",0);
                textView_date.setText(new SimpleDateFormat("yyyy-MM-dd  EEEE").format(time));
            }
        }
    }

}
