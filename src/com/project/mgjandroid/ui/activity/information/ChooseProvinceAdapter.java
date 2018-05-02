package com.project.mgjandroid.ui.activity.information;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.ProvinceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/11/22.
 */

public class ChooseProvinceAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private LayoutInflater inflater;

    private List<ProvinceBean> mProvinces = new ArrayList<>();

    private OnClickListener listener;

    public ChooseProvinceAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setList(List<ProvinceBean> mProvinces) {
        if (mProvinces == null) {
            this.mProvinces = new ArrayList<>();
        } else {
            this.mProvinces = mProvinces;
        }
        notifyDataSetChanged();
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PublishAreaViewHolder(inflater.inflate(R.layout.property_choose_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String area = "";
        if (mProvinces.get(position).getName() != null) area = mProvinces.get(position).getName();
        ((PublishAreaViewHolder) holder).tv.setText(area);
    }

    @Override
    public int getItemCount() {
        return mProvinces.size();
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

