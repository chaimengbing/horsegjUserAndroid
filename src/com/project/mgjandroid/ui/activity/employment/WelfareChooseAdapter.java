package com.project.mgjandroid.ui.activity.employment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.information.PropertyArrayModel.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/11/15.
 */

public class WelfareChooseAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mInflater;

    private List<Property> welfares = new ArrayList<>();

    public WelfareChooseAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<Property> welfares) {
        if (welfares != null) {
            this.welfares = welfares;
        } else {
            this.welfares = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public List<Property> getList() {
        return welfares;
    }

    @Override
    public int getCount() {
        return welfares.size();
    }

    @Override
    public Object getItem(int position) {
        return welfares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_welfare_choose, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cb.setText(welfares.get(position).getName());
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    welfares.get(position).setChecked(false);
                } else {
                    welfares.get(position).setChecked(true);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {

        CheckBox cb;

        public ViewHolder(View view) {
            cb = (CheckBox) view.findViewById(R.id.cb);
        }
    }
}
