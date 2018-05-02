package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.LegworkStatusModel;

import java.util.List;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/3/26 17:45
 */

public class LegworkStatusAdapter extends RecyclerView.Adapter<LegworkStatusAdapter.MyViewHolder> {

    List<LegworkStatusModel> list;//存放数据
    Context context;

    public LegworkStatusAdapter(List<LegworkStatusModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_legwork_ststus_layout, parent, false));
        return holder;
    }

    //在这里可以获得每个子项里面的控件的实例，比如这里的TextView,子项本身的实例是itemView，
    //在这里对获取对象进行操作
    //holder.itemView是子项视图的实例，holder.textView是子项内控件的实例
    //position是点击位置
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //设置textView显示内容为list里的对应项
        holder.tvName.setText(list.get(position).getName());
        holder.tvTime.setText(list.get(position).getTime());

        if (list.size() == 1) {
            holder.line1.setVisibility(View.INVISIBLE);
            holder.line2.setVisibility(View.INVISIBLE);
            holder.ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.legwork_order_status_2));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.legwork_status));
            //设置加粗
            holder.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.tvTime.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            if (position == 0) {
                //第一个
                holder.line1.setVisibility(View.INVISIBLE);
                holder.line2.setVisibility(View.VISIBLE);
                holder.ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.legwork_order_status_1));
                holder.tvName.setTextColor(context.getResources().getColor(R.color.color_3));
                //设置加粗
                holder.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.tvTime.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            } else if (position == list.size() - 1) {
                //最后一个
                holder.line1.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.INVISIBLE);
                holder.ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.legwork_order_status_2));
                holder.tvName.setTextColor(context.getResources().getColor(R.color.legwork_status));
                //设置加粗
                holder.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                holder.tvTime.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                holder.line1.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);
                holder.ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.legwork_order_status_1));
                holder.tvName.setTextColor(context.getResources().getColor(R.color.color_3));
                //设置加粗
                holder.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.tvTime.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        }
    }

    //要显示的子项数量
    @Override
    public int getItemCount() {
        return list.size();
    }

    //这里定义的是子项的类，不要在这里直接对获取对象进行操作
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvTime;
        View line1;
        View line2;
        ImageView ivStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_status_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_status_time);
            line1 = itemView.findViewById(R.id.view_line1);
            line2 = itemView.findViewById(R.id.view_line2);
            ivStatus = (ImageView) itemView.findViewById(R.id.iv_status);
        }
    }

}
