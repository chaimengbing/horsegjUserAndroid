package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.LegworkEntityModel;
import com.project.mgjandroid.ui.activity.legwork.LegworkWriteOrderActivity;

import java.util.List;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/3/26 17:45
 */

public class LegworkTabAdapter extends RecyclerView.Adapter<LegworkTabAdapter.MyViewHolder> {

    List<LegworkEntityModel.ValueBean.LegWorkGoodsCategoryListBean> list;//存放数据
    Context context;
    EditText et;
    boolean isBusiness = false;

    public LegworkTabAdapter(List<LegworkEntityModel.ValueBean.LegWorkGoodsCategoryListBean> list, Context context, EditText et, boolean isBusiness) {
        this.list = list;
        this.context = context;
        this.et = et;
        this.isBusiness = isBusiness;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_legwork_tab_layout, parent, false));
        return holder;
    }

    //在这里可以获得每个子项里面的控件的实例，比如这里的TextView,子项本身的实例是itemView，
    //在这里对获取对象进行操作
    //holder.itemView是子项视图的实例，holder.textView是子项内控件的实例
    //position是点击位置
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //设置textView显示内容为list里的对应项
        holder.textView.setText(list.get(position).getName());
        holder.textView.setEnabled(isBusiness);
        if (isBusiness) {
            holder.textView.setBackgroundColor(Color.parseColor("#fdf2d5"));
            holder.textView.setTextColor(Color.parseColor("#66421b"));
        } else {
            holder.textView.setBackgroundColor(Color.parseColor("#f0f0f0"));
            holder.textView.setTextColor(Color.parseColor("#999999"));
        }
        //子项的点击事件监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LegworkWriteOrderActivity.class);
                if (et != null) {
                    intent.putExtra("desc", et.getText().toString().trim());
                }
                intent.putExtra("parentId", "" + list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    //要显示的子项数量
    @Override
    public int getItemCount() {
        return list.size();
    }

    //这里定义的是子项的类，不要在这里直接对获取对象进行操作
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_tab_item);
        }
    }

//    //在指定位置插入，原位置的向后移动一格
//    public boolean addItem(int position, String msg) {
//        if (position < list.size() && position >= 0) {
//            list.add(position, msg);
//            notifyItemInserted(position);
//            return true;
//        }
//        return false;
//    }
//
//    //去除指定位置的子项
//    public boolean removeItem(int position) {
//        if (position < list.size() && position >= 0) {
//            list.remove(position);
//            notifyItemRemoved(position);
//            return true;
//        }
//        return false;
//    }
//
//    //清空显示数据
//    public void clearAll() {
//        list.clear();
//        notifyDataSetChanged();
//    }
}
