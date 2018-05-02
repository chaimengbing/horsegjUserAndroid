package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchantService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2017/3/11.
 */

public class GroupBuyingMerchantServiceMenuAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<GroupPurchaseMerchantService> data;

    public GroupBuyingMerchantServiceMenuAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    public List<GroupPurchaseMerchantService> getData() {
        return data;
    }

    public void setData(List<GroupPurchaseMerchantService> data) {
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
    public GroupPurchaseMerchantService getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.group_buying_merchant_service_menu_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        showItem(convertView, position, holder);

        return convertView;
    }

    private void showItem(View convertView, int position, ViewHolder holder) {
        GroupPurchaseMerchantService service = data.get(position);
        if (service.isSelected()) {
            holder.name.setSelected(true);
        } else {
            holder.name.setSelected(false);
        }
        holder.name.setText(service.getName());
    }

    static class ViewHolder {
        TextView name;
    }
}