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

public class PublishAreaAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private LayoutInflater inflater;

    private List<Agent> agents = new ArrayList<>();

    private PublishAreaAdapter.OnClickListener listener;

    public PublishAreaAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setList(List<Agent> agents) {
        if (agents == null) {
            this.agents = new ArrayList<>();
        } else {
            this.agents = agents;
        }
        notifyDataSetChanged();
    }

    public void setListener(PublishAreaAdapter.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PublishAreaAdapter.PublishAreaViewHolder(inflater.inflate(R.layout.property_choose_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String area = "";
        if (agents.get(position).getProvinceName() != null)
            area = agents.get(position).getProvinceName();
        if (agents.get(position).getCityName() != null && !area.equals(agents.get(position).getCityName()))
            area += agents.get(position).getCityName();
        if (agents.get(position).getAgentType() == 1 && agents.get(position).getDistrictName() != null)
            area += agents.get(position).getDistrictName();
        ((PublishAreaAdapter.PublishAreaViewHolder) holder).tv.setText(area);
    }

    @Override
    public int getItemCount() {
        return agents.size();
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

