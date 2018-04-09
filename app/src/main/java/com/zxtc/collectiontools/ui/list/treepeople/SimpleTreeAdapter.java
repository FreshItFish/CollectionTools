package com.zxtc.collectiontools.ui.list.treepeople;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zxtc.collectiontools.R;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * 作者：KY
 * 创建时间：2018/4/2 11:26
 * 描述: 人员树 适配器
 */

public class SimpleTreeAdapter extends TreeListViewAdapter {
    private Context context;

    public SimpleTreeAdapter(ListView mTree, Context context, List<Node> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand) {
        super(mTree, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
        this.context = context;
    }

    @Override
    public View getConvertView(final Node node, int position, View convertView, ViewGroup parent) {
        View view;
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_organization_tree_select, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) view.findViewById(R.id.tv_name);
            holder.tvCount = (TextView) view.findViewById(R.id.tv_count);
            holder.cb = (CheckBox) view.findViewById(R.id.check_box);
            holder.iv = (ImageView) view.findViewById(R.id.iv_right);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(node, holder.cb.isChecked());
            }
        });

        if (node.isChecked()){
            holder.cb.setChecked(true);
        }else {
            holder.cb.setChecked(false);
        }

        if (node.getIcon() == -1) {
            holder.iv.setVisibility(View.INVISIBLE);
        } else {
            holder.iv.setVisibility(View.VISIBLE);
            holder.iv.setImageResource(node.getIcon());
        }

        holder.tvName.setText(node.getName());

        //不是学生，都显示当前分类下有多少人
//        if (node.getIsPeople() != 1) {
//            holder.tvCount.setVisibility(View.VISIBLE);
//            holder.tvCount.setText("(" + node.getCount() + ")");
//        } else {
//            holder.tvCount.setVisibility(View.GONE);
//        }

        return view;
    }

    class ViewHolder {
        TextView tvName, tvCount;
        CheckBox cb;
        ImageView iv;
    }
}
