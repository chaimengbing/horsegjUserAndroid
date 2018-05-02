package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseEvaluate;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by yuandi on 2017/3/6.
 */

public class GroupBuyingEvaluationAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<GroupPurchaseEvaluate> data;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public GroupBuyingEvaluationAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    public ArrayList<GroupPurchaseEvaluate> getData() {
        return data;
    }

    public void setData(ArrayList<GroupPurchaseEvaluate> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.group_buying_evaluation_item, null);
            holder.rootView = convertView.findViewById(R.id.view_root);
            holder.avatar = (CornerImageView) convertView.findViewById(R.id.user_avatar);
            holder.userName = (TextView) convertView.findViewById(R.id.user_name);
            holder.tvDate = (TextView) convertView.findViewById(R.id.time);
            holder.tvAveragePrice = (TextView) convertView.findViewById(R.id.average_price);
            holder.tvEvaluation = (TextView) convertView.findViewById(R.id.evaluation);
            holder.rbScore = (RatingBar) convertView.findViewById(R.id.rb_score);
            holder.gridView = (NoScrollGridView) convertView.findViewById(R.id.grid_view);
            holder.gridViewDivider = convertView.findViewById(R.id.grid_view_divider);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        showItem(convertView, position, holder);

        return convertView;
    }

    private void showItem(View convertView, int position, ViewHolder holder) {
        final GroupPurchaseEvaluate evaluation = data.get(position);

        ImageUtils.loadBitmap(context, evaluation.getAppUser().getHeaderImg(), holder.avatar, R.drawable.user_avatar, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_EVALUATE);
        holder.userName.setText(evaluation.getAppUser().getName());
        holder.tvDate.setText(format.format(evaluation.getCreateTime()));
        holder.rbScore.setRating(evaluation.getTotalScore().floatValue());
        if (evaluation.getPerCapitaConsumptionAmt() != null && evaluation.getPerCapitaConsumptionAmt().compareTo(BigDecimal.ZERO) > 0) {
            holder.tvAveragePrice.setText("¥" + StringUtils.BigDecimal2Str(evaluation.getPerCapitaConsumptionAmt()) + "/人");
        } else {
            holder.tvAveragePrice.setText("");
        }
        if (CheckUtils.isNoEmptyStr(evaluation.getContent())) {
            holder.tvEvaluation.setText(evaluation.getContent());
        } else {
            holder.tvEvaluation.setText("客户未做出具体评价");
        }
        if (CheckUtils.isNoEmptyStr(evaluation.getImages())) {
            holder.gridView.setVisibility(View.VISIBLE);
            holder.gridViewDivider.setVisibility(View.VISIBLE);
            GroupBuyingGridImageAdapter adapter = new GroupBuyingGridImageAdapter(context, true);
            holder.gridView.setAdapter(adapter);
            adapter.setUrls(evaluation.getImages(), ";");
        } else {
            holder.gridView.setVisibility(View.GONE);
            holder.gridViewDivider.setVisibility(View.GONE);
        }
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupBuyingEvaluationDetailActivity.toGroupBuyingEvaluationDetailActivity(context, evaluation);
            }
        });
    }

    static class ViewHolder {
        View rootView, gridViewDivider;
        CornerImageView avatar;
        TextView userName, tvDate, tvAveragePrice, tvEvaluation;
        RatingBar rbScore;
        NoScrollGridView gridView;
    }
}
