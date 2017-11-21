package com.zxtc.collectiontools.ui.list.CoordinatorLayoutdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.application.MyConstant;
import com.zxtc.collectiontools.entity.PostImageBean;

import java.util.List;

/**
 * 卡片布局适配器
 */
public class MyCardViewAdapter extends RecyclerView.Adapter<MyCardViewAdapter.MyViewHolder> {


    private Context context;
//    private List<ImageBean.Data> bean;
    private List<PostImageBean> bean;

    public MyCardViewAdapter(Context context, List<PostImageBean> bean) {
        this.context = context;
        this.bean = bean;
//        this.bean = bean.getData();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cardview, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(bean.get(position).getRemark());
        holder.tv2.setText(bean.get(position).getUserName());
        String userPhotPath = bean.get(position).getUserPhotPath().replaceAll("\\\\", "/");
//        String userPhotPath = "upload/temp7498176818381015528edit.png";
        Glide.with(context)
                .load(MyConstant.net_address + userPhotPath)
                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return (bean == null || bean.size() == 0) ? 0 : bean.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private TextView tv2;
        private ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.cardview_tv);
            tv2 = (TextView) itemView.findViewById(R.id.cardview_tv2);
            iv = (ImageView) itemView.findViewById(R.id.cardview_iv);
        }
    }

}
