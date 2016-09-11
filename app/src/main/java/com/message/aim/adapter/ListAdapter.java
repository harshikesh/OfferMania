package com.message.aim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.message.aim.R;
import com.message.aim.model.SmsObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harshikesh.kumar on 11/09/16.
 */
public class ListAdapter extends BaseAdapter {
  private final Context mContext;
  List<SmsObject> mSmsObjects;

  public ListAdapter(Context context, List<SmsObject> sms) {
    mContext = context;
    mSmsObjects = sms;
  }

  @Override public int getCount() {
    return mSmsObjects.size();
  }

  @Override public Object getItem(int position) {
    return null;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    View row = convertView;
    CardViewHolder viewHolder;
    if (row == null) {
      LayoutInflater inflater =
          (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      row = inflater.inflate(R.layout.list_item_card, parent, false);
      viewHolder = new CardViewHolder();
      viewHolder.mTextView = (TextView) row.findViewById(R.id.sms);
      row.setTag(viewHolder);
    } else {
      viewHolder = (CardViewHolder) row.getTag();
    }
    if(true) {
      viewHolder.mTextView.setText("" + mSmsObjects.get(position).getMsg());
    }
    return row;
  }

  class CardViewHolder {

    TextView mTextView;
  }
}
