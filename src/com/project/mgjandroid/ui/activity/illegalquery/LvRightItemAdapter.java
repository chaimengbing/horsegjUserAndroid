package com.project.mgjandroid.ui.activity.illegalquery;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-05-09.
 */

public class LvRightItemAdapter extends BaseAdapter {
    private List<String> letter;

    private Activity context;
    private LayoutInflater layoutInflater;


    public LvRightItemAdapter(Activity context, List<String> strings) {
        this.context = context;
        this.letter = strings;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return letter.size();
    }

    @Override
    public Object getItem(int position) {
        return letter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.lv_right_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvLvRightItem = (TextView) convertView.findViewById(R.id.tv_lv_right_item);

            convertView.setTag(viewHolder);
        }
        initializeViews(letter.get(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Object entity, ViewHolder holder) {
        //TODO implement
        holder.tvLvRightItem.setText(String.valueOf(entity));
    }

    protected class ViewHolder {
        private TextView tvLvRightItem;
    }
}
