package com.zxtc.collectiontools.ui.list.simplerecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxtc.collectiontools.R;

import java.util.List;

/**
 *
 */

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.MyViewHolder> {


    private List<String> mList;
    private Context mContext;

    public SecondAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_recycler, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position % 2 == 0){
            holder.tv.setBackgroundResource(R.color.white);
        }else {
            holder.tv.setBackgroundResource(R.color.lightgray);
        }
        holder.tv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_item);
        }
    }
}
