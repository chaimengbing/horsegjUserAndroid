package com.project.mgjandroid.ui.activity.groupbuying;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.Entity;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.utils.CheckUtils;

import java.util.ArrayList;
import java.util.List;

public class RiderImpressionAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<String> strList;

    public RiderImpressionAdapter(Context context) {
        this.context = context;
        strList = new ArrayList<>();
    }

    public ArrayList<String> getList() {
        return strList;
    }

    public void setList(ArrayList<String> strList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.rider_impression_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(strList.get(i));

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }

}
