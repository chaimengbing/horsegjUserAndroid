package com.project.mgjandroid.ui.activity.pintuan;

import android.app.Activity;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupInfo;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.GroupTimeTextView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User_Cjh on 2016/8/11.
 */
public class PintuanListAdapter extends BaseListAdapter<GroupInfo> {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private View.OnClickListener listener;
    private boolean isRunning;

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public PintuanListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void isRunningGroup(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    protected void getRealView(ViewHolder holder, GroupInfo bean, int position, View convertView, ViewGroup parent) {
        if (bean == null)
            return;
        ImageView image = holder.getView(R.id.pintuan_list_image);
        if (CheckUtils.isNoEmptyStr(bean.getImgs())) {
//            ImageUtils.loadBitmap(mActivity, bean.getImgs().split(";")[0], image, R.drawable.home_default, Constants.getEndThumbnail(320, 192));
            ImageUtils.loadBitmap(mActivity, bean.getImgs().split(";")[0], image, R.drawable.horsegj_default, "");
        }
        holder.setText(R.id.pintuan_list_title, bean.getGoodsName());
        ProgressBar bar = holder.getView(R.id.current_progress);
        if (bean.getMinNum() == 0) {
            bean.setMinNum(1);
        }
        int curProgress = bean.getTotalNum() * 100 / bean.getMinNum();
        if (curProgress >= 100) {
            bar.setProgress(100);
        } else {
            bar.setProgress(curProgress);
        }
        TextView progressShow = holder.getView(R.id.current_progress_show);
        progressShow.setText(curProgress + "%");
        int width = DipToPx.dip2px(mActivity, 35);
        int height = DipToPx.dip2px(mActivity, 12);
        int barWidth = (int) DeviceParameter.getScreenWidth() - DipToPx.dip2px(mActivity, 123);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        int leftMargin = curProgress * barWidth / 100 - width / 2;

        if (leftMargin < 0)
            leftMargin = 0;
        else if (leftMargin > barWidth - width)
            leftMargin = barWidth - width;
        lp.setMargins(leftMargin, 0, 0, 0);
        progressShow.setLayoutParams(lp);

        holder.setText(R.id.group_join_count, bean.getTotalNum() + "");
        holder.setText(R.id.group_total_count, bean.getMinNum() + "");
        final GroupTimeTextView residualTime = holder.getView(R.id.residual_time);
        TextView textView = holder.getView(R.id.origin_price);
        textView.setText("¥" + bean.getOriginalPrice());
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.setText(R.id.current_price, bean.getPrice() + "");
        TextView joinGroup = holder.getView(R.id.join_group);
        if (getTimeBetween(new Date(), bean.getDefaultEndTime()) > 0 && (bean.getStatus() == 3 || (bean.getStatus() == 4 && bean.getTotalNum() < bean.getMaxNum()))) {
            joinGroup.setText("参与拼团");
            joinGroup.setTextColor(mActivity.getResources().getColor(R.color.white));
            joinGroup.setBackgroundResource(R.drawable.shape_corner_pintuan);
            residualTime.setTimes(getTimeBetween(new Date(), bean.getDefaultEndTime()));
            joinGroup.setTag(position);
            joinGroup.setOnClickListener(listener);
        } else {
            bean.setIsOver(true);
            joinGroup.setText("已结束");
            joinGroup.setTextColor(mActivity.getResources().getColor(R.color.mine_divide_grey));
            joinGroup.setBackgroundResource(R.drawable.shape_corner_pintuan_over);
            joinGroup.setClickable(false);
            residualTime.setTimes(0);
        }
        if (isRunning) {
            residualTime.setTag(bean.isOver());
            residualTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if ("剩余 00:00:00".equals(s.toString())) {
                        boolean b = (boolean) residualTime.getTag();
                        if (!b) {
                            notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private long getTimeBetween(Date curTime, String endTime) {
        if (CheckUtils.isEmptyStr(endTime))
            return 0;
        try {
            long time1 = curTime.getTime();
            Date date2 = sdf.parse(endTime);
            long time2 = date2.getTime();
            return time2 - time1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
