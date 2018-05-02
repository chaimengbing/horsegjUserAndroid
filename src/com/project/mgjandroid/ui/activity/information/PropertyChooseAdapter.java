package com.project.mgjandroid.ui.activity.information;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/11/15.
 */

public class PropertyChooseAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private LayoutInflater inflater;

    private List<String> properties = new ArrayList<>();

    private PropertyClickListener listener;

    public PropertyChooseAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setList(List<String> properties) {
        if (properties == null) {
            this.properties = new ArrayList<>();
        } else {
            this.properties = properties;
        }
        notifyDataSetChanged();
    }

    public void setListener(PropertyClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PropertyViewHolder(inflater.inflate(R.layout.property_choose_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PropertyViewHolder) holder).tv.setText(properties.get(position));
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    private class PropertyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        PropertyViewHolder(View view) {
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

    public interface PropertyClickListener {
        void onClick(int positon);
    }
}
