package com.message.aim.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.bumptech.glide.util.Util;
import com.message.aim.R;
import com.message.aim.utils.Utilities;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

  private TextView textView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    String id = getIntent().getStringExtra(Utilities.ID);
    textView = (TextView) findViewById(R.id.text);
    textView.setText(id);
  }
}
