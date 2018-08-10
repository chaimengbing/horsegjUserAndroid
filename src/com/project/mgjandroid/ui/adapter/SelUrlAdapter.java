package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;

public class SelUrlAdapter extends BaseAdapter {

    Context context;
    String[] urls;

    public SelUrlAdapter(Context context, String[] urls) {
        super();
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        if (urls != null) {
            return urls.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return urls[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(context);
        textView.setText(urls[i]);
        int paddLeft = (int) context.getResources().getDimension(R.dimen.x10);
        int paddTop = (int) context.getResources().getDimension(R.dimen.x15);
        textView.setPadding(paddLeft, paddTop, paddLeft, paddTop);
        return textView;
    }
}
