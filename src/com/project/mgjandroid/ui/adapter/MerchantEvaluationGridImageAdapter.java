package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.pictureviewer.PictureViewActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public class MerchantEvaluationGridImageAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<String> urls = new ArrayList<>();

    public MerchantEvaluationGridImageAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        if (urls == null) {
            urls = new ArrayList<>();
        }
        this.urls = urls;
        notifyDataSetChanged();
    }

    public void setUrls(String urls, String split) {
        String[] strings = urls.split(split);
        this.urls = Arrays.asList(strings);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.merchant_evaluation_grid_image_item, null);
            holder.img = (CornerImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageUtils.loadBitmap(context, urls.get(position), holder.img, R.drawable.horsegj_default, Constants.getEndThumbnail(100, 100));

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureViewActivity.toViewPicture(context, JSONArray.toJSONString(urls), position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        CornerImageView img;
    }
}
