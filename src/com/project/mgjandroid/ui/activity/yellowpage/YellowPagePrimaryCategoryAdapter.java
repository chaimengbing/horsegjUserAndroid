package com.project.mgjandroid.ui.activity.yellowpage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.yellowpage.YellowPageCategory;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2017/3/14.
 */

public class YellowPagePrimaryCategoryAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<YellowPageCategory> data;

    public YellowPagePrimaryCategoryAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    public List<YellowPageCategory> getData() {
        return data;
    }

    public void setData(List<YellowPageCategory> data) {
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
            convertView = mInflater.inflate(R.layout.group_buying_category_item, null);
            holder.rootView = (LinearLayout) convertView.findViewById(R.id.root_view);
            holder.icon = (ImageView) convertView.findViewById(R.id.image_view);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        showItem(convertView, position, holder);

        return convertView;
    }

    private void showItem(View convertView, int position, ViewHolder holder) {
        final YellowPageCategory category = data.get(position);
        ImageUtils.loadBitmap(context, category.getPicUrl() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL, holder.icon, R.drawable.category_default, "");
        holder.name.setText(category.getName());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YellowPageListActivity.class);
                intent.putExtra("type", category.getValue());
                intent.putExtra("name", category.getName());
                context.startActivity(intent);
            }
        });
    }

    static class ViewHolder {
        LinearLayout rootView;
        ImageView icon;
        TextView name;
    }
}
