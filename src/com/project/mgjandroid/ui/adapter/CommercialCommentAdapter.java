package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.LeafComment;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.MerchantEvaluateModel;
import com.project.mgjandroid.model.NewMerchantEvaluateModel;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.RatingBarView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class CommercialCommentAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<NewMerchantEvaluateModel.ValueBean.ListBean> list;

    public CommercialCommentAdapter(Context context) {
        this.context = context;
        list = new ArrayList<NewMerchantEvaluateModel.ValueBean.ListBean>();
        this.inflater = LayoutInflater.from(context);

    }

    public List<NewMerchantEvaluateModel.ValueBean.ListBean> getList() {
        return list;
    }

    public void setList(List<NewMerchantEvaluateModel.ValueBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.commercial_comment_item, null);
            holder.score = (RatingBar) convertView.findViewById(R.id.merchant_score);
            holder.userAvatar = (CornerImageView) convertView.findViewById(R.id.user_avatar);
            holder.tvName = (TextView) convertView.findViewById(R.id.user_name);
            holder.tvScore = (TextView) convertView.findViewById(R.id.tv_score);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvContent= (TextView) convertView.findViewById(R.id.tv_content);
            holder.tvReply= (TextView) convertView.findViewById(R.id.tv_merchant_reply);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (CheckUtils.isNoEmptyList(list) && list.size() > position) {
            NewMerchantEvaluateModel.ValueBean.ListBean comment = list.get(position);
            if (comment != null) {
                showItem(holder, comment);
            }
        }
        return convertView;
    }

    private void showItem(final ViewHolder holder, final NewMerchantEvaluateModel.ValueBean.ListBean comment) {
        holder.score.setRating(comment.getMerchantScore());
        int RatingScore = Math.round(comment.getMerchantScore());
        if (RatingScore == 1) {
            holder.tvScore.setText("极差");
        } else if (RatingScore == 2) {
            holder.tvScore.setText("失望");
        } else if (RatingScore == 3) {
            holder.tvScore.setText("一般");
        } else if (RatingScore == 4) {
            holder.tvScore.setText("满意");
        } else if (RatingScore == 5) {
            holder.tvScore.setText("超赞");
        }
        holder.tvDate.setText(comment.getCreateTime());
        String des = comment.getMerchantComments();
        if (CheckUtils.isNoEmptyStr(des)) {
            holder.tvContent.setVisibility(View.VISIBLE);
            holder.tvContent.setText(des);
            holder.tvContent.setTextColor(context.getResources().getColor(R.color.black));
        } else {
//			holder.tvContent.setVisibility(View.GONE);
            holder.tvContent.setText("该用户没有做具体评价哦！");
            holder.score.setRating(5);
            holder.tvScore.setText("超赞");
            holder.tvContent.setTextColor(context.getResources().getColor(R.color.gray_4));
        }
        if (comment.getAppUser() != null) {
            String name = comment.getAppUser().getName();
            if (name == null || name.length() == 0) {
                name = comment.getAppUser().getMobile();
            }
            if (name == null || name.length() == 0) {
                name = "***";
            }
            if (name.length() <= 1) {
                name = name.substring(0, 1) + "***";
            } else if (name.length() > 1) {
                name = name.substring(0, 1) + "***" + name.substring(name.length() - 1, name.length());
            }
            holder.tvName.setText(name);
            ImageUtils.loadBitmap(context, comment.getAppUser().getHeaderImg(), holder.userAvatar, R.drawable.comment_defaut_head, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL);
        }
        if (CheckUtils.isNoEmptyStr(comment.getReplyContent())) {
            holder.tvReply.setVisibility(View.VISIBLE);
            holder.tvReply.setText("商家回复：" + comment.getReplyContent());
        } else {
            holder.tvReply.setVisibility(View.GONE);
        }

    }

    static class ViewHolder {
        RatingBar score;
        TextView tvContent,tvName,tvScore,tvDate,tvReply;
        CornerImageView userAvatar;
    }

}
