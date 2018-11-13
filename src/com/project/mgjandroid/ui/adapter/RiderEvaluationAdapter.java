package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.Entity;
import com.project.mgjandroid.model.groupbuying.GroupBuyingDeliverymanImpress;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.utils.CheckUtils;

import java.util.ArrayList;
import java.util.List;

public class RiderEvaluationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GroupBuyingDeliverymanImpress> strList;

    public RiderEvaluationAdapter(Context context) {
        this.context = context;
        strList = new ArrayList<>();
    }

    public ArrayList<GroupBuyingDeliverymanImpress> getList() {
        return strList;
    }

    public void setList(ArrayList<GroupBuyingDeliverymanImpress> strList) {
        this.strList = strList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (CheckUtils.isEmptyList(strList)) {
            return 0;
        }
        return strList.size();
    }

    @Override
    public Object getItem(int i) {
        return strList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.rider_evaluation_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (CheckUtils.isNoEmptyList(strList)) {
            holder.textView.setText(strList.get(i).getImpress());
        }

        holder.textView.setSelected(strList.get(i).isChecked());

        if (strList.get(i).isChecked()) {
            holder.textView.setTextColor(context.getResources().getColor(R.color.bg_festival));
        } else {
            holder.textView.setTextColor(context.getResources().getColor(R.color.color_6));
        }


        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
