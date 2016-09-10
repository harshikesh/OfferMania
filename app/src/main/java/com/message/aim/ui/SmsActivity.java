package com.message.aim.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.message.aim.R;
import com.message.aim.adapter.CategoryGridAdapter;
import com.message.aim.application.AppController;
import com.message.aim.model.SmsObject;
import com.message.aim.network.DataRequester;
import com.message.aim.utils.Constants;
import com.message.aim.utils.Utilities;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SmsActivity extends AppCompatActivity {

  private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
  private static final String TAG = "SmsActivity";
  ArrayList<String> smsMessagesList = new ArrayList<String>();
  private RecyclerView mRecyclerView;
  private GridLayoutManager glm;
  private CategoryGridAdapter mAdapter;

  private HashMap<String, ArrayList<SmsObject>> smsObjectHash;
  private AppController mDataMan;
  private SharedPreferences mPref;

  @TargetApi(Build.VERSION_CODES.M) @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sms);
    mDataMan = ((AppController) getApplication());
    mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    int hasLocationPermission = checkSelfPermission(Manifest.permission.READ_SMS);
    List<String> permissions = new ArrayList<String>();
    if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(SmsActivity.this,
          new String[] { "android.permission.READ_SMS" }, REQUEST_CODE_ASK_PERMISSIONS);
    }

    smsObjectHash = new HashMap<>();
    mRecyclerView = (RecyclerView) findViewById(R.id.companyList);
    glm = new GridLayoutManager(this, 3);
    glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        switch (position) {
          case 6:
            return 3;
          default:
            return 1;
        }
      }
    });
    glm.setOrientation(GridLayoutManager.VERTICAL);

    mRecyclerView.setLayoutManager(glm);
    // specify an adapter
    mAdapter = new CategoryGridAdapter(this);
    mRecyclerView.setAdapter(mAdapter);
    if (hasLocationPermission == PackageManager.PERMISSION_GRANTED) {
      mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      if (!mPref.getBoolean(Constants.FIRST_TIME, false)) {
        Log.d(TAG, "first time");
        refreshSmsInbox();
      }
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    switch (requestCode) {
      case REQUEST_CODE_ASK_PERMISSIONS:
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
          if (!mPref.getBoolean(Constants.FIRST_TIME, false)) {
            Log.d(TAG, "first time");
            refreshSmsInbox();
          }
        } else {
          finish();
        }
        break;
      default:
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  public void refreshSmsInbox() {
   /* MESSAGE_TYPE_ALL    = 0;
    MESSAGE_TYPE_INBOX  = 1;
    MESSAGE_TYPE_SENT   = 2;
    MESSAGE_TYPE_DRAFT  = 3;
    MESSAGE_TYPE_OUTBOX = 4;*/


    JSONObject superObj = new JSONObject();



    JSONObject js = new JSONObject();
    for (Map.Entry<String, ArrayList<SmsObject>> entry : smsObjectHash.entrySet()) {
      JSONArray jsarray = new JSONArray();
      ArrayList<SmsObject> obj = entry.getValue();
      for (SmsObject sms : obj) {
        JSONObject jsobj = new JSONObject();
        try {
          jsobj.put(Constants.Subject, sms.getSubject());
          jsobj.put(Constants.Body, sms.getMsg());
          jsobj.put(Constants.Date, sms.getDate());
          jsobj.put(Constants.Seen, sms.getSeen());
          jsarray.put(jsobj);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      try {
        js.put(entry.getKey(), jsarray);
      } catch (JSONException e) {
        e.printStackTrace();
      }
      // System.out.println(entry.getKey() + "/" + entry.getValue());
    }
    try {
      superObj.put("sms", js);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  //  postData(superObj);
  }

  private void postData(JSONObject superObj) {

    mDataMan.getDataManager().postUserData(new WeakReference<DataRequester>(new DataRequester() {
      @Override public void onFailure(Throwable error) {
        Log.d(TAG, "Data:" + "Failure");
      }

      @Override public void onSuccess(Object respObj) {
        Log.d(TAG, "Data:" + "Success");
      }
    }), superObj, TAG);
  }
}