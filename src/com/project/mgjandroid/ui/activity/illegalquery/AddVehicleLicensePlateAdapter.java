package com.project.mgjandroid.ui.activity.illegalquery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;

/**
 * Created by SunXueLiang on 2017-05-12.
 */

public class AddVehicleLicensePlateAdapter extends BaseAdapter {

    private String[] province;
    private LayoutInflater layoutInflater;

    public AddVehicleLicensePlateAdapter(Context context, String[] str) {
        this.province = str;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return province.length;
    }

    @Override
    public Object getItem(int i) {
        return province[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.girdview_item, null);
            TextView tvWord = (TextView) view.findViewById(R.id.tv_word);
            tvWord.setText(province[i]);
        }
        return view;
    }
}
