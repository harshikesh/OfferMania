package com.message.aim.ui;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.message.aim.model.SmsObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by harshikesh.kumar on 10/09/16.
 */
public class SplashActivity extends AppCompatActivity {

  private static final String TAG = "SMS";
  private HashMap<String, ArrayList<SmsObject>> smsObjectHash;
  static HashMap<String, String> prehash = new HashMap<>();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    smsObjectHash = new HashMap<>();

    init();
    getAllMessages();
    processSms();
  }

  private void init() {
    String code[] = {
        "FLPKRT", "FCHRGE", "METROS", "DOMINO", "OLACAB", "GoIBIB", "FPANDA", "Vpaytm", "GROFRS",
        "ADIDAS", "JABONG"
    };
    String name[] = {
        "Flipkart", "FreeCharge", "Metro", "Dominos", "OLA", "GoIbibo", "Food Pand", "Paytm",
        "Grofers", "ADIDAS", "JABONG"
    };
    for (int i = 0; i < code.length; i++) {
      prehash.put(code[i], name[i]);
    }
  }

  private void getAllMessages() {
    ContentResolver contentResolver = getContentResolver();
    Cursor smsInboxCursor =
        contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

    int indexBody = smsInboxCursor.getColumnIndex("body");
    int indexAddress = smsInboxCursor.getColumnIndex("address");
    int threadId = smsInboxCursor.getColumnIndex("thread_id");
    int seen = smsInboxCursor.getColumnIndex("seen");
    int type = smsInboxCursor.getColumnIndex("type");
    int subject = smsInboxCursor.getColumnIndex("subject");
    int person = smsInboxCursor.getColumnIndex("person");
    int date = smsInboxCursor.getColumnIndex("date");
    if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
    do {
      String sdate = smsInboxCursor.getString(date);
      String saddress = smsInboxCursor.getString(indexAddress);
      String sseen = smsInboxCursor.getString(seen);
      String ssubject = smsInboxCursor.getString(subject);
      String sbody = smsInboxCursor.getString(indexBody);

      SmsObject smsobj = new SmsObject(ssubject, sbody, sdate, sseen);
      if (smsObjectHash.containsKey(saddress)) {
        ArrayList<SmsObject> obj = smsObjectHash.get(saddress);
        obj.add(smsobj);
      } else {
        ArrayList<SmsObject> obj = new ArrayList<>();
        obj.add(smsobj);
        smsObjectHash.put(saddress, obj);
      }
    } while (smsInboxCursor.moveToNext());
    smsInboxCursor.close();
  }

  private void processSms() {
    Log.d(TAG, "Processing messages ");
    for (Map.Entry<String, ArrayList<SmsObject>> entry : smsObjectHash.entrySet()) {
      Log.d(TAG, "Sender  : " + entry.getKey());
      String sender = entry.getKey();

      int index = (sender.indexOf("-") == -1) ? 0 : (sender.indexOf("-") + 1);
      String temp = sender.substring(index);

      if (prehash.containsKey(temp)) {
        for (SmsObject sms : smsObjectHash.get(sender)) {
          parseSms(sms);
        }
      } else {
        continue;
      }
    }
  }

  private void parseSms(SmsObject sms) {

  }
}

