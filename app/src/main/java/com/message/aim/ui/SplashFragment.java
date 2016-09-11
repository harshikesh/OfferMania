package com.message.aim.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.message.aim.R;
import java.util.ArrayList;

/**
 * Created by harshikesh.kumar on 11/09/16.
 */
public class SplashFragment extends Fragment {

  public static SplashFragment newInstance() {
    SplashFragment fragment = new SplashFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_splash, container, false);
    return rootView;
  }

  @Override public void onResume() {
    super.onResume();
  }
}
