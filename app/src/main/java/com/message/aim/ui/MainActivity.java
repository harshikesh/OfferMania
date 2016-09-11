package com.message.aim.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.message.aim.R;
import com.message.aim.adapter.ListAdapter;
import com.message.aim.model.SmsObject;
import com.message.aim.utils.Utilities;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private TextView textView;
  private int position;
  private ListView list;
  private String comp;
  List<SmsObject> listSms;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    position = getIntent().getIntExtra(Utilities.ID, 0);
    comp = getIntent().getStringExtra(Utilities.COMP);
    getSupportActionBar().setTitle(comp);
    list = (ListView) findViewById(R.id.list);
    List<List<SmsObject>> l = new ArrayList<List<SmsObject>>(SplashActivity.compList.values());
    listSms = new ArrayList<>();
    for (SmsObject s : l.get(position)) {
      if (s.getMsg().toLowerCase().contains("off") || s.getMsg()
          .toLowerCase()
          .contains("cashback")) {
        listSms.add(s);
      }
    }
    if(listSms.size() ==0){
      Toast.makeText(getApplicationContext(),"Sorry no offers",Toast.LENGTH_SHORT).show();
      finish();
    }
    ListAdapter listAdapter = new ListAdapter(getApplicationContext(), listSms);
    list.setAdapter(listAdapter);
  }
}
