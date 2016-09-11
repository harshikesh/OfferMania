package com.message.aim.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.message.aim.R;
import com.message.aim.adapter.CategoryGridAdapter;
import com.message.aim.application.AppController;
import com.message.aim.model.SmsObject;
import com.message.aim.network.DataRequester;
import com.message.aim.utils.Constants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by harshikesh.kumar on 11/09/16.
 */
public class SMSActivity1 extends Fragment {

  private RecyclerView mRecyclerView;
  private GridLayoutManager glm;
  private CategoryGridAdapter mAdapter;

  private HashMap<String, ArrayList<SmsObject>> smsObjectHash;
  private AppController mDataMan;
  private SharedPreferences mPref;
  private Context mContext;

  @Override public void onStart() {
    super.onStart();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
  }

  public static SMSActivity1 newInstance(HashMap<String, ArrayList<SmsObject>> sms) {
    //smsObjectHash = sms;
    SMSActivity1 fragment = new SMSActivity1();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.activity_sms, container, false);

    smsObjectHash = new HashMap<>();
    mRecyclerView = (RecyclerView) rootView.findViewById(R.id.companyList);
    glm = new GridLayoutManager(mContext, 3);
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
    mAdapter = new CategoryGridAdapter(mContext);
    mRecyclerView.setAdapter(mAdapter);

    return rootView;
  }

  @Override public void onResume() {
    super.onResume();
  }
}
