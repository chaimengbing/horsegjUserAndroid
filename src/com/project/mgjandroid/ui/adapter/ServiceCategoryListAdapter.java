package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.PrimaryCategory;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.MorePrimaryCategoryModel.ServiceCategory;
import com.project.mgjandroid.ui.activity.PrimaryCategoryActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingCategoryActivity;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.utils.ToastUtils;

/**
 * Created by yuandi on 2016/6/22.
 */
public class ServiceCategoryListAdapter extends BaseListAdapter<ServiceCategory> {

    private NoticeDialog noticeDialog;

    public ServiceCategoryListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, ServiceCategory bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.service_primary_name);
        tvName.setText(bean.getName());
        TextView tvDesc = holder.getView(R.id.service_primary_description);
        tvDesc.setText(bean.getDescription());

        final NoScrollGridView noScrollGridView = holder.getView(R.id.grid_view);
        PrimaryCategoryListAdapter tagGridAdapter = new PrimaryCategoryListAdapter(R.layout.primary_category_grid_view_item, mActivity);
        tagGridAdapter.setData(bean.getPrimaryCategoryList());
        noScrollGridView.setAdapter(tagGridAdapter);
        noScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > ((PrimaryCategoryListAdapter) parent.getAdapter()).getDataCount() - 1)
                    return;
                PrimaryCategory primaryCategory = (PrimaryCategory) noScrollGridView.getAdapter().getItem(position);
                if (primaryCategory.getGraySwitch() == 0) {
                    if (primaryCategory.getGotoType() == 2) {
                        Intent intent = new Intent(mActivity, PrimaryCategoryActivity.class);
                        intent.putExtra("tagCategoryId", primaryCategory.getTagCategoryId());
                        intent.putExtra("tagCategoryType", primaryCategory.getTagCategoryType());
                        intent.putExtra("categoryName", primaryCategory.getName());
                        mActivity.startActivity(intent);
                    } else if (primaryCategory.getGotoType() == 1) {
                        if (primaryCategory.getGotoUrl().startsWith("maguanjia://")) {
                            if (primaryCategory.getGotoUrl().replace("maguanjia://", "").startsWith("http")) {
                                Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, primaryCategory.getGotoUrl().replace("maguanjia://", ""));
                                mActivity.startActivity(intent);
                            } else {
                                Routers.open(mActivity, ActivitySchemeManager.SCHEME + primaryCategory.getGotoUrl().replace("maguanjia://", ""), new RouterCallback() {
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
                        } else if (primaryCategory.getGotoUrl().startsWith("http")) {
//                            Intent intent = new Intent(mActivity, Banner2WebActivity.class);
//                            intent.putExtra(Banner2WebActivity.URL, primaryCategory.getGotoUrl());
//                            mActivity.startActivity(intent);
                            Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                            intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, primaryCategory.getGotoUrl());
                            mActivity.startActivity(intent);
                        }
                    } else if (primaryCategory.getGotoType() == 4) {
                        GroupBuyingCategoryActivity.toGroupBuyingCategoryActivity(mActivity, primaryCategory.getName(), primaryCategory.getGroupPurchaseCategoryId(), primaryCategory.getChildGroupPurchaseCategoryId());
                    }
                } else {
                    ToastUtils.displayMsg("尚未开通，敬请期待", mActivity);
                }
            }
        });
    }

    private void showNoticeDialog() {
        if (noticeDialog == null) {
            noticeDialog = new NoticeDialog(mActivity, new NoticeDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    noticeDialog.dismiss();
                }
            }, "", "您当前版本过低，暂无法使用该功能。请更新到最新版本马管家。");
        }
        noticeDialog.show();
    }
}
