package com.message.aim.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.message.aim.R;
import com.message.aim.database.DatabaseHandler;
import com.message.aim.model.PreSmsData;
import com.message.aim.utils.Utilities;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private TextView textView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    String id = getIntent().getStringExtra(Utilities.ID);
    textView = (TextView) findViewById(R.id.text);
    textView.setText(id);

    DatabaseHandler db = new DatabaseHandler(this);

    Log.d("Insert: ", "Inserting ..");
    db.addSms(new PreSmsData("Ravi", "9100000000"));
    db.addSms(new PreSmsData("Srinivas", "9199999999"));
    db.addSms(new PreSmsData("Tommy", "9522222222"));
    db.addSms(new PreSmsData("Karthik", "9533333333"));

    Log.d("Reading: ", "Reading all contacts..");
    List<PreSmsData> contacts = db.getAllSms();

    for (PreSmsData cn : contacts) {
      String log = "Id: " + cn.getId() + " ,Name: " + cn.getName() + " ,code: " + cn.getCode();
      Log.d("Name: ", log);
    }
  }
}
