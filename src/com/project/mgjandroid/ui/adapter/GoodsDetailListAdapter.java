package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GoodsEvaluateModel;
import com.project.mgjandroid.model.NewGoodsEvaluateModel;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

/**
 * Created by ning on 2016/3/11.
 */
public class GoodsDetailListAdapter extends BaseListAdapter<NewGoodsEvaluateModel.ValueBean.ListBean> {

    public GoodsDetailListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, NewGoodsEvaluateModel.ValueBean.ListBean bean, int position, View convertView, ViewGroup parent) {
        CornerImageView evaluateAvatar = holder.getView(R.id.user_avatar);
        TextView evaluateUsername = holder.getView(R.id.user_name);
        TextView tvScore =  holder.getView(R.id.tv_score);
        TextView evaluateTime = holder.getView(R.id.tv_time);
        TextView evaluateContent = holder.getView(R.id.tv_content);
        TextView tvReply= holder.getView(R.id.tv_merchant_reply);
        NoScrollGridView gridView= holder.getView(R.id.grid_view);
        if(bean.getGoodsScore().intValue()>=3){
            tvScore.setText("赞了该商品");
            Drawable drawable = mActivity.getResources().getDrawable(R.drawable.ic_little_praise_checked);
            drawable.setBounds(0,0,drawable.getMinimumHeight(),drawable.getMinimumWidth());
            tvScore.setCompoundDrawables(drawable,null,null,null);
        }else {
            Drawable drawable = mActivity.getResources().getDrawable(R.drawable.ic_little_trample_checked);
            drawable.setBounds(0,0,drawable.getMinimumHeight(),drawable.getMinimumWidth());
            tvScore.setCompoundDrawables(drawable,null,null,null);
            tvScore.setText("踩了该商品");
        }
        evaluateTime.setText(bean.getCreateTime().split(" ")[0]);
        if (TextUtils.isEmpty(bean.getGoodsScoreComments())) {
            evaluateContent.setText("该用户没有做具体的评价哦！");
            evaluateContent.setTextColor(mActivity.getResources().getColor(R.color.gray_4));
        } else {
            evaluateContent.setText(bean.getGoodsScoreComments());
            evaluateContent.setTextColor(mActivity.getResources().getColor(R.color.gray_1));
        }
        String name = bean.getAppUser().getName();
        if (name == null || name.length() == 0) {
            name = bean.getAppUser().getMobile();
        }
        if (name == null || name.length() == 0) {
            name = "***";
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
        if (CheckUtils.isNoEmptyStr(bean.getReplyContent())) {
            tvReply.setVisibility(View.VISIBLE);
            tvReply.setText("商家回复：" + bean.getReplyContent());
        } else {
            tvReply.setVisibility(View.GONE);
        }

        if(CheckUtils.isNoEmptyStr(bean.getImgUrl())){
            gridView.setVisibility(View.VISIBLE);
            MerchantEvaluationGridImageAdapter adapter = new MerchantEvaluationGridImageAdapter(mActivity);
            gridView.setAdapter(adapter);
            adapter.setUrls(bean.getImgUrl(),";");
        }else {
            gridView.setVisibility(View.GONE);
        }
    }
}
