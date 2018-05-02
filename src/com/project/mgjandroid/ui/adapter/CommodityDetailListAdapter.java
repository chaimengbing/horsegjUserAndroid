package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CommodityEvaluateModel;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ning on 2016/3/11.
 */
public class CommodityDetailListAdapter extends BaseAdapter {
    private List<CommodityEvaluateModel.CommentList> bean;
    private Activity mActivity;
    private int layoutId;

    public CommodityDetailListAdapter(List<CommodityEvaluateModel.CommentList> bean, Activity mActivity, int layoutId) {
        this.bean = bean;
        this.mActivity = mActivity;
        this.layoutId = layoutId;
    }

    public CommodityDetailListAdapter(Activity mActivity, int layoutId) {
        bean = new ArrayList<>();
        this.mActivity = mActivity;
        this.layoutId = layoutId;
    }

    public void refreshData(List<CommodityEvaluateModel.CommentList> newBean) {
        bean.clear();
        bean.addAll(newBean);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(position, convertView, parent, layoutId);
        getRealView(holder, bean.get(position), position, convertView, parent);
        return holder.getConvertView();
    }

    protected void getRealView(ViewHolder holder, CommodityEvaluateModel.CommentList bean, int position, View convertView, ViewGroup parent) {
        RatingBar ratingBar = holder.getView(R.id.commodity_detail_rat_score);
        TextView evaluateTime = holder.getView(R.id.tv_evaluate_time);
        TextView evaluateNum = holder.getView(R.id.evaluate_num);
        TextView evaluateContent = holder.getView(R.id.tv_evaluate_content);
        RoundImageView evaluateAvatar = holder.getView(R.id.commodity_item_evaluate_avatar);
        TextView evaluateUsername = holder.getView(R.id.tv_user_name);
        ratingBar.setRating(bean.getGoodsScore().floatValue());
        evaluateNum.setText(bean.getGoodsScore().toString());
        evaluateTime.setText(bean.getCreateTime().split(" ")[0]);
        if (TextUtils.isEmpty(bean.getGoodsScoreComments())) {
            evaluateContent.setText("该用户没有做具体的评价哦！");
            evaluateContent.setTextColor(mActivity.getResources().getColor(R.color.gray_4));
        } else {
            evaluateContent.setText(bean.getGoodsScoreComments());
            evaluateContent.setTextColor(mActivity.getResources().getColor(R.color.gray_1));
        }
        String name = bean.getAppUser().getName();
        if (name == null) {
            name = bean.getAppUser().getMobile();
        }
        if (name.length() <= 1) {
            name = name.substring(0, 1) + "***";
        } else if (name.length() > 1) {
            name = name.substring(0, 1) + "***" + name.substring(name.length() - 1, name.length());
        }
        evaluateUsername.setText(name);
        String headerImg = bean.getAppUser().getHeaderImg();
        if (!TextUtils.isEmpty(headerImg)) {
            ImageUtils.loadBitmap(mActivity, headerImg + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_EVALUATE, evaluateAvatar, R.drawable.comment_defaut_head, "");
        }
    }


}
