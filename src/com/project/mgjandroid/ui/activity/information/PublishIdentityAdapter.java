package com.project.mgjandroid.ui.activity.information;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Agent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/11/22.
 */

public class PublishIdentityAdapter extends RecyclerView.Adapter {


    private Context mContext;

    private LayoutInflater inflater;

    private String[] data = new String[]{"商家", "个人"};

    private PublishAreaAdapter.OnClickListener listener;

    public PublishIdentityAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setList(String[] data) {
        if (data == null) {
            this.data = new String[]{};
        } else {
            this.data = data;
        }
        notifyDataSetChanged();
    }

    public void setListener(PublishAreaAdapter.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PublishIdentityAdapter.PublishAreaViewHolder(inflater.inflate(R.layout.property_choose_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String identity = "";
        if (data[position] != null) identity = data[position];
        ((PublishIdentityAdapter.PublishAreaViewHolder) holder).tv.setText(identity);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    private class PublishAreaViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        PublishAreaViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_property);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnClickListener {
        void onClick(int positon);
    }
}

