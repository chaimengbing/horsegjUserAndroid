package com.project.mgjandroid.ui.activity.yellowpage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.yellowpage.YellowPage;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;

import java.text.DecimalFormat;

/**
 * Created by User_Cjh on 2017/6/13.
 */

public class YellowPageListAdapter extends BaseListAdapter<YellowPage> {

    private DecimalFormat df = new DecimalFormat("0.0");
    private CallPhoneDialog callPhoneDialog;
    private boolean isSearch = false;
    private String searchText;

    public YellowPageListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void isSearch(boolean isSearch) {
        this.isSearch = isSearch;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    protected void getRealView(ViewHolder holder, YellowPage bean, int position, View convertView, ViewGroup parent) {
        CornerImageView logo = holder.getView(R.id.yellow_page_image);
        ImageUtils.loadBitmap(mActivity, bean.getLogoPath(), logo, R.drawable.horsegj_default, Constants.getEndThumbnail(55, 55));
        TextView tvName = holder.getView(R.id.yellow_page_title);
        if (isSearch) {
            String name = bean.getMerchantName();
            if (CheckUtils.isNoEmptyStr(name))
                name = name.replaceAll(searchText, "<font color='#ff9900'>" + searchText + "</font>");
            tvName.setText(Html.fromHtml(name));
        } else {
            tvName.setText(bean.getMerchantName());
        }
        if (bean.getDistance() != null) {
            if (bean.getDistance() > 1000) {
                Double d = bean.getDistance() / 1000;
                holder.setText(R.id.yellow_page_distance, df.format(d) + "km");
            } else {
                holder.setText(R.id.yellow_page_distance, bean.getDistance().intValue() + "m");
            }
        }
        String mobile = bean.getMobiles();
        if (CheckUtils.isEmptyStr(mobile)) {
            holder.setText(R.id.yellow_page_desc, "");
            return;
        }
        if (mobile.contains(";")) {
            mobile = mobile.split(";")[0];
        }
        if (mobile.contains(":")) {
            mobile = mobile.substring(mobile.indexOf(":") + 1, mobile.length());
        }
        holder.setText(R.id.yellow_page_desc, mobile);
        ImageView ivPhone = holder.getView(R.id.yellow_page_phone);
        if (ivPhone != null) {
            ivPhone.setTag(mobile);
            ivPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String phone = (String) v.getTag();
                    callPhoneDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                        @Override
                        public void onSure() {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.DIAL");
                            intent.setData(Uri.parse("tel:" + phone));
                            mActivity.startActivity(intent);
                            callPhoneDialog.dismiss();
                        }

                        @Override
                        public void onExit() {
                            callPhoneDialog.dismiss();
                        }
                    }, "", phone);
                    callPhoneDialog.show();
                }
            });
        }
    }
}
