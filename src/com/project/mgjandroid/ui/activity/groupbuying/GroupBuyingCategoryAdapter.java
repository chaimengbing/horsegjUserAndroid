package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCategory;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2017/3/10.
 */

public class GroupBuyingCategoryAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<GroupPurchaseCategory> data;

    public GroupBuyingCategoryAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    public List<GroupPurchaseCategory> getData() {
        return data;
    }

    public void setData(List<GroupPurchaseCategory> data) {
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
            convertView = mInflater.inflate(R.layout.group_buying_category_list_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.check = (ImageView) convertView.findViewById(R.id.check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        showItem(convertView, position, holder);
        return convertView;
    }

    private void showItem(View convertView, int position, ViewHolder holder) {
        final GroupPurchaseCategory category = data.get(position);
        if (category.isSelected()) {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.title_bar_bg));
            holder.check.setVisibility(View.VISIBLE);
        } else {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.color_6));
            holder.check.setVisibility(View.INVISIBLE);
        }
        holder.name.setText(category.getName());
    }

    static class ViewHolder {
        TextView name;
        ImageView check;
    }
}
