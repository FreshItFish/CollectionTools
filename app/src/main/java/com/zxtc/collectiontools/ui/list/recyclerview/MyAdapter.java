package com.zxtc.collectiontools.ui.list.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxtc.collectiontools.R;

/**
 * 上滑加载更多recycler适配器
 */

public class MyAdapter extends BaseAdapter<String> {
    private Context mContext;

    public MyAdapter(Context context) {
        mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position % 2 == 0){
            holder.itemView.setBackgroundResource(R.color.white);
        }else {
            holder.itemView.setBackgroundResource(R.color.lightgray);
        }
        ((MyViewHolder) holder).bind(getDataSet().get(position));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.tv_item);
        }

        public void bind(CharSequence content) {
            mTextView.setText(content);
        }
    }
}
