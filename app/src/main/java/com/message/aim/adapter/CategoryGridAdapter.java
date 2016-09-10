package com.message.aim.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.message.aim.R;
import com.message.aim.ui.MainActivity;
import com.message.aim.utils.Utilities;

import static com.message.aim.utils.Utilities.categoryList;

/**
 * Created by harshikesh.kumar on 10/09/16.
 */
public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.ListViewHolder> {

  private static final String CATEGORY = "category";

  private final Context mContext;
  private SharedPreferences mPref;

  public class ListViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener, View.OnLongClickListener {

    private final TextView mTopicName;
    private ImageView mImage;
    private LinearLayout mContainer;
    private LinearLayout mTextLayout;
    private String id;

    public ListViewHolder(View itemView) {
      super(itemView);
      this.mTopicName = (TextView) itemView.findViewById(R.id.title_text_view);
      this.mImage = (ImageView) itemView.findViewById(R.id.img_grid);
      this.mContainer = (LinearLayout) itemView.findViewById(R.id.container);
      this.mTextLayout = (LinearLayout) itemView.findViewById(R.id.textLayout);
      this.id = "";
      itemView.setOnClickListener(this);
      itemView.setOnLongClickListener(this);
    }

    @Override public void onClick(View v) {
      int position = getAdapterPosition();
      String id = ((ListViewHolder) v.getTag()).id;
      Log.d("TAG : Adapter", id);
      Intent intent = new Intent(mContext, MainActivity.class);
      intent.putExtra(Utilities.ID,id);
      mContext.startActivity(intent);
    }

    @Override public boolean onLongClick(View v) {
      return false;
    }
  }

  public CategoryGridAdapter(Context context) {
    mContext = context;
    mPref = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
  }

  @Override
  public CategoryGridAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);

    return new ListViewHolder(view);
  }

  @Override public void onBindViewHolder(ListViewHolder viewHolder, int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    viewHolder.mTopicName.setText(categoryList[position]);
    if (mPref.getBoolean(categoryList[position], false)) {
      viewHolder.mTextLayout.setBackgroundColor(
          mContext.getResources().getColor(R.color.greentranslucent));
    }
    viewHolder.mContainer.setTag(viewHolder);
    viewHolder.id = "" + position;
    Glide.with(mContext).load("").into(viewHolder.mImage);
  }

  @Override public int getItemCount() {
    return categoryList.length;
  }
}

