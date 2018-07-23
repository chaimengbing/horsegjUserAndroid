package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.view.CornerImageView;

public class RefundReasonListAdapter extends BaseAdapter{

    private String[] reason;
    private LayoutInflater layoutInflater;

    public RefundReasonListAdapter(Context context, String[] str) {
        this.reason = str;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return reason.length;
    }

    @Override
    public Object getItem(int i) {
        return reason[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.reason_item, null);
            holder.tvWord = (CheckBox) view.findViewById(R.id.tv_textview);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvWord.setText(reason[i]);

        return view;
    }

    static class ViewHolder {
        CheckBox tvWord;
    }
}
