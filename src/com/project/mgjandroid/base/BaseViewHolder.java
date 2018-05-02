package com.project.mgjandroid.base;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by User_Cjh on 2016/5/23.
 */
public class BaseViewHolder extends ViewHolder {

    private View view;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T get(int id) {
        View childView = view.findViewById(id);
        return (T) childView;
    }

    public void setText(int id, String text) {
        TextView textView = get(id);
        textView.setText(text);
    }

    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = get(viewId);
        iv.setImageBitmap(bitmap);
    }

    public void setImageResource(int viewId, int resId) {
        ImageView iv = get(viewId);
        iv.setImageResource(resId);
    }

    public void setBackgroundColor(int viewId, int color) {
        View v = get(viewId);
        v.setBackgroundColor(color);
    }

    public void setVisibility(int viewId, int visibility) {
        View v = get(viewId);
        v.setVisibility(visibility);
    }
}
