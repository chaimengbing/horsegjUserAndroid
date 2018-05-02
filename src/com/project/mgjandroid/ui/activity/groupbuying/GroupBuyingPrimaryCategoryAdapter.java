package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchasePrimaryCategory;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2017/3/14.
 */

public class GroupBuyingPrimaryCategoryAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<GroupPurchasePrimaryCategory> data;
    private NoticeDialog noticeDialog;

    public GroupBuyingPrimaryCategoryAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    public List<GroupPurchasePrimaryCategory> getData() {
        return data;
    }

    public void setData(List<GroupPurchasePrimaryCategory> data) {
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
            convertView = mInflater.inflate(R.layout.group_buying_category_item, null);
            holder.rootView = (LinearLayout) convertView.findViewById(R.id.root_view);
            holder.icon = (ImageView) convertView.findViewById(R.id.image_view);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        showItem(convertView, position, holder);

        return convertView;
    }

    private void showItem(View convertView, int position, ViewHolder holder) {
        final GroupPurchasePrimaryCategory category = data.get(position);
        if (category.getGraySwitch() == 0) {
            ImageUtils.loadBitmap(context, category.getPicUrl() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL, holder.icon, R.drawable.category_default, "");
        } else {
            ImageUtils.loadBitmap(context, category.getGrayUrl() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL, holder.icon, R.drawable.category_default, "");
        }
        holder.name.setText(category.getName());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category.getGraySwitch() == 0) {
                    switch (category.getGotoType()) {
                        case 1: //链接地址
                            if (category.getGotoUrl().startsWith("maguanjia://")) {
                                if (category.getGotoUrl().replace("maguanjia://", "").startsWith("http")) {
                                    Intent intent = new Intent(context, YLBWebViewActivity.class);
                                    intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, category.getGotoUrl().replace("maguanjia://", ""));
                                    context.startActivity(intent);
                                } else {
                                    Routers.open(context, ActivitySchemeManager.SCHEME + category.getGotoUrl().replace("maguanjia://", ""), new RouterCallback() {
                                        @Override
                                        public void notFound(Context context, Uri uri) {
                                            showNoticeDialog();
                                        }

                                        @Override
                                        public void beforeOpen(Context context, Uri uri) {

                                        }

                                        @Override
                                        public void afterOpen(Context context, Uri uri) {

                                        }

                                        @Override
                                        public void error(Context context, Uri uri, Throwable e) {

                                        }
                                    });
                                }
                            } else if (category.getGotoUrl().startsWith("http")) {
//                                Intent intent = new Intent(context, Banner2WebActivity.class);
//                                intent.putExtra(Banner2WebActivity.URL, category.getGotoUrl() + "?longitude=" + PreferenceUtils.getLocation(context)[1] + "&latitude=" + PreferenceUtils.getLocation(context)[0]);
//                                intent.putExtra("name", category.getName());
//                                context.startActivity(intent);
                                Intent intent = new Intent(context, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, category.getGotoUrl() + "?longitude=" + PreferenceUtils.getLocation(context)[1] + "&latitude=" + PreferenceUtils.getLocation(context)[0]);
                                context.startActivity(intent);
                            }
                            break;
                        case 2: //团购分类
                            GroupBuyingCategoryActivity.toGroupBuyingCategoryActivity(context, category.getName(), category.getGroupPurchaseCategoryId(), category.getChildGroupPurchaseCategoryId());
                            break;
                    }
                }
            }
        });
    }

    static class ViewHolder {
        LinearLayout rootView;
        ImageView icon;
        TextView name;
    }

    private void showNoticeDialog() {
        if (noticeDialog == null) {
            noticeDialog = new NoticeDialog(context, new NoticeDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    noticeDialog.dismiss();
                }
            }, "", "您当前版本过低，暂无法使用该功能。请更新到最新版本马管家。");
        }
        noticeDialog.show();
    }
}
