package com.project.mgjandroid.ui.activity.pintuan;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupUserComments;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.Entity;
import com.project.mgjandroid.ui.activity.PhotoActivity;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by User_Cjh on 2016/8/12.
 */
public class PersonEvaluateAdapter extends BaseListAdapter<GroupUserComments> {

    public PersonEvaluateAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, GroupUserComments bean, int position, View convertView, ViewGroup parent) {
        RoundImageView avatar = holder.getView(R.id.evaluate_avatar);
        TextView username = holder.getView(R.id.evaluate_username);
        TextView date = holder.getView(R.id.evaluate_date);
        RatingBar score = holder.getView(R.id.evaluate_score);
        TextView scoreShow = holder.getView(R.id.evaluate_score_show);
        TextView content = holder.getView(R.id.evaluate_content);
        LinearLayout photoLayout = holder.getView(R.id.evaluate_photo_layout);
        LinearLayout photoLayout2 = holder.getView(R.id.evaluate_photo_layout_2);
        if (CheckUtils.isNoEmptyStr(bean.getUserInfo().getHeaderImg())) {
            ImageUtils.loadBitmap(mActivity, bean.getUserInfo().getHeaderImg(), avatar, R.drawable.default_group_user_avatar, Constants.getEndThumbnail(34, 34));
        }
        String name = bean.getUserInfo().getName();
//        if(name.length() > 1){
//            name = name.substring(0,1)+"***"+name.substring(name.length()-1,name.length());
//        }else {
//            name = "***"+name;
//        }
        username.setText(name);
        date.setText(bean.getCreateTime().split(" ")[0]);
        score.setRating(bean.getCompositeScore().floatValue());
        scoreShow.setText(bean.getCompositeScore() + "分");
        content.setText(bean.getGroupBuyScoreComments());
        final String imgs = bean.getImgs();
        if (CheckUtils.isNoEmptyStr(bean.getImgs())) {
            photoLayout.setVisibility(View.VISIBLE);
            String[] photos = bean.getImgs().split(";");
            if (photos.length > 3) {
                photoLayout2.setVisibility(View.VISIBLE);
                for (int i = 0; i < photos.length; i++) {
                    if (i >= 3) {
                        LinearLayout fm = (LinearLayout) photoLayout2.getChildAt(i - 3);
                        CornerImageView photo = (CornerImageView) fm.getChildAt(0);
                        if (CheckUtils.isNoEmptyStr(photos[i])) {
                            ImageUtils.loadBitmap(mActivity, photos[i], photo, R.drawable.group_zhanwei, Constants.getEndThumbnail(80, 80));
                        }
                        fm.setTag(i);
                        fm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mActivity, PhotoActivity.class);
                                intent.putExtra("position", (Integer) v.getTag());
                                intent.putExtra("imgs", imgs);
                                mActivity.startActivity(intent);
                            }
                        });
                    } else {
                        LinearLayout fm = (LinearLayout) photoLayout.getChildAt(i);
                        CornerImageView photo = (CornerImageView) fm.getChildAt(0);
                        if (CheckUtils.isNoEmptyStr(photos[i])) {
                            ImageUtils.loadBitmap(mActivity, photos[i], photo, R.drawable.group_zhanwei, Constants.getEndThumbnail(80, 80));
                        }
                        fm.setTag(i);
                        fm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mActivity, PhotoActivity.class);
                                intent.putExtra("position", (Integer) v.getTag());
                                intent.putExtra("imgs", imgs);
                                mActivity.startActivity(intent);
                            }
                        });
                    }
                }
            } else {
                photoLayout2.setVisibility(View.GONE);
                int count = photoLayout.getChildCount() > photos.length ? photos.length : photoLayout.getChildCount();
                for (int i = 0; i < count; i++) {
                    LinearLayout fm = (LinearLayout) photoLayout.getChildAt(i);
                    CornerImageView photo = (CornerImageView) fm.getChildAt(0);
                    if (CheckUtils.isNoEmptyStr(photos[i])) {
                        ImageUtils.loadBitmap(mActivity, photos[i], photo, R.drawable.group_zhanwei, Constants.getEndThumbnail(80, 80));
                    }
                    fm.setTag(i);
                    fm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, PhotoActivity.class);
                            intent.putExtra("position", (Integer) v.getTag());
                            intent.putExtra("imgs", imgs);
                            mActivity.startActivity(intent);
                        }
                    });
                }
            }
        } else {
            photoLayout.setVisibility(View.GONE);
        }
    }
}
