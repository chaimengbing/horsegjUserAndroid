package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;

import java.util.ArrayList;

/**
 * Created by yuandi on 2017/3/7.
 */

public class GroupBuyingMerchantServiceAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private int[] iconRes = {
            R.drawable.group_buying_new_service_1, R.drawable.group_buying_new_service_2, R.drawable.group_buying_new_service_3,
            R.drawable.group_buying_new_service_4, R.drawable.group_buying_new_service_5, R.drawable.group_buying_new_service_6,
            R.drawable.group_buying_new_service_7
    };
    private String[] names = {
            "无线", "刷卡", "包厢", "停车", "景观位", "露天位", "无烟区"
    };
    private ArrayList<Integer> data;

    public GroupBuyingMerchantServiceAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public void setData(ArrayList<Integer> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.group_buying_merchant_service_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        showItem(convertView, position, holder);

        return convertView;
    }

    private void showItem(View convertView, int position, ViewHolder holder) {
        switch (data.get(position)) {
            case 0:
                holder.imageView.setImageResource(iconRes[0]);
                holder.name.setText(names[0]);
                break;
            case 1:
                holder.imageView.setImageResource(iconRes[1]);
                holder.name.setText(names[1]);
                break;
            case 2:
                holder.imageView.setImageResource(iconRes[2]);
                holder.name.setText(names[2]);
                break;
            case 3:
                holder.imageView.setImageResource(iconRes[3]);
                holder.name.setText(names[3]);
                break;
            case 4:
                holder.imageView.setImageResource(iconRes[4]);
                holder.name.setText(names[4]);
                break;
            case 5:
                holder.imageView.setImageResource(iconRes[5]);
                holder.name.setText(names[5]);
                break;
            case 6:
                holder.imageView.setImageResource(iconRes[6]);
                holder.name.setText(names[6]);
                break;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        TextView name;
    }
}
