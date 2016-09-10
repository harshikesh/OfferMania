package com.message.aim.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.message.aim.R;
import com.message.aim.utils.Constants;
import com.message.aim.utils.Utilities;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONObject;

/*
 * Created by harshikesh.kumar on 10/09/16.
*/

public class DataManager {

  private static final String TAG = DataManager.class.getSimpleName();
  public static final String BASE_URL = "http://192.168.43.167:8989";
  private static DataManager mInstance;
  private Context mContext;
  private RequestQueue mRequestQueue;
  private SharedPreferences mPref;

  private DataManager(Context context) {
    mContext = context;
  }

  public static synchronized DataManager getInstance(Context context) {
    if (mInstance == null) {
      Log.v(TAG, "Creating data manager instance");
      mInstance = new DataManager(context.getApplicationContext());
    }
    return mInstance;
  }

  public void init() {
    mRequestQueue = getRequestQueue();
  }

  private RequestQueue getRequestQueue() {
    if (mRequestQueue == null) {
      mRequestQueue = Volley.newRequestQueue(mContext);
    }
    return mRequestQueue;
  }

  public <T> void addToRequestQueue(Request<T> request, String tag) {
    // set the default tag if tag is empty
    request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
    mRequestQueue.add(request);
  }

  public <T> void addToRequestQueue(Request<T> request) {
    request.setTag(TAG);
    mRequestQueue.add(request);
  }

  /**
   * Cancel any pending volley request associated with the {param requestTag}
   */

  public void cancelPendingRequests(String requestTag) {
    if (mRequestQueue != null) {
      mRequestQueue.cancelAll(requestTag);
    }
  }

  /**
   * Cleanup & save anything that needs saving as app is going away.
   */

  public void terminate() {
    mRequestQueue.stop();
  }

  public void addToRequestQueueInDM(Request request, String tag) {
    addToRequestQueue(request, tag);
  }

  public void postUserData(final WeakReference<DataRequester> wRequester, JSONObject obj,
      String tag) {
    Log.v(TAG, "Api call : post user data");
    Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
      @Override public void onResponse(JSONObject jsonObject) {
        Log.v(TAG, "Success : post user data returned a response");
        try {
          Log.v(TAG, "Success : obtained userId : ");
        } catch (Exception e) {
          Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "First time success");
        mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = mPref.edit();
        if (!mPref.getBoolean(Constants.FIRST_TIME, false)) {
          editor.putBoolean(Constants.FIRST_TIME, true);
        }
        editor.commit();
      }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override public void onErrorResponse(VolleyError volleyError) {
        Log.v(TAG, "Error : post user data failed");
        DataRequester req = null;
        if (wRequester != null) {
          req = wRequester.get();
        }
        if (req != null) {
          req.onFailure(volleyError);
        }
      }
    };

    CustomJsonObjectRequest request =
        new CustomJsonObjectRequest(Request.Method.POST, BASE_URL + "/messages", obj,
            responseListener, errorListener);

    addToRequestQueue(request, tag);
  }
  //
  //public void getAttemptedTopicList(final WeakReference<DataRequester> wRequester, String tag) {
  //  Log.v(TAG, "Api call : get attempted topic list");
  //  JSONObject obj = new JSONObject();
  //
  //  Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
  //    @Override public void onResponse(JSONObject jsonObject) {
  //      Log.v(TAG, "Success : get attempted topic list returned a response");
  //      DataRequester req = null;
  //      if (wRequester != null) {
  //        req = wRequester.get();
  //      }
  //      TopicLists allContactResponse = null;
  //      if (jsonObject != null && !TextUtils.isEmpty(jsonObject.toString())) {
  //        Log.v(TAG, "Success : converting Json to Java Object via Gson");
  //        allContactResponse = new Gson().fromJson(jsonObject.toString(), TopicLists.class);
  //      }
  //
  //      if (req != null) {
  //        if (allContactResponse != null) {
  //          req.onSuccess(allContactResponse);
  //        }
  //      }
  //    }
  //  };
  //
  //  Response.ErrorListener errorListener = new Response.ErrorListener() {
  //    @Override public void onErrorResponse(VolleyError volleyError) {
  //      Log.v(TAG, "Error : get attempted topic list failed");
  //      DataRequester req = null;
  //      if (wRequester != null) {
  //        req = wRequester.get();
  //      }
  //      if (req != null) {
  //        req.onFailure(volleyError);
  //      }
  //    }
  //  };
  //
  //  // http://172.20.172.49:8989/users/5711e13ed4c6f0df5adf8a17/attempted_topics
  //  mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
  //  String userId = mPref.getString(mContext.getString(R.string.user_id_key),
  //      mContext.getString(R.string.user_id_default_value));
  //  Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
  //  builder.appendPath("users").appendPath(userId).appendPath("attempted_topics");
  //
  //  String url = builder.build().toString();
  //  CustomJsonObjectRequest request =
  //      new CustomJsonObjectRequest(Request.Method.GET, url, obj, responseListener, errorListener);
  //  addToRequestQueue(request, tag);
  //}
}

