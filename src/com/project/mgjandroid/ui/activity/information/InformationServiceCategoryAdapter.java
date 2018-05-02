package com.project.mgjandroid.ui.activity.information;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationServiceCategory;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.FulltimeTagGridAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.NoScrollGridView;

/**
 * Created by yuandi on 2016/11/19.
 */

public class InformationServiceCategoryAdapter extends BaseListAdapter<InformationServiceCategory> {

    private View.OnClickListener mListener;

    public InformationServiceCategoryAdapter(int layoutId, Activity mActivity, View.OnClickListener listener) {
        super(layoutId, mActivity);
        mListener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, InformationServiceCategory bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.tag_name);
        tvName.setText(bean.getName());

        NoScrollGridView noScrollGridView = holder.getView(R.id.tag_grid_view);
        InformationCategoryAdapter tagGridAdapter = new InformationCategoryAdapter(R.layout.item_tag_grid_view, mActivity, mListener, position);
        tagGridAdapter.setData(bean.getInformationCategoryList());
        noScrollGridView.setAdapter(tagGridAdapter);
    }
}
