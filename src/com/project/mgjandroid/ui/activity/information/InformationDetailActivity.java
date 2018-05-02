package com.project.mgjandroid.ui.activity.information;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.PositionInformation;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.employment_simple.RecruitSimpleDetailFragment;
import com.project.mgjandroid.ui.activity.employment_simple.ResumeSimpleDetailFragment;
import com.project.mgjandroid.ui.activity.healthy.HealthDetailsFragment;
import com.project.mgjandroid.ui.activity.education.EducationDetailsFragment;
import com.project.mgjandroid.ui.activity.education.FamilyEducationDetailsFragment;
import com.project.mgjandroid.ui.activity.employment.RecruitmentDetailFragment;
import com.project.mgjandroid.ui.activity.employment.ResumeDetailFragment;
import com.project.mgjandroid.ui.activity.geomancy.FengShuiDetailsFragment;
import com.project.mgjandroid.ui.activity.homemaking.HomeMakingDetailsFragment;
import com.project.mgjandroid.ui.activity.laws.LawsDetailsFragment;
import com.project.mgjandroid.ui.activity.renthouse.LeaseDetailFragment;
import com.project.mgjandroid.ui.activity.renthouse.RentDetailFragment;
import com.project.mgjandroid.ui.activity.repair.RepairDetailsFragment;
import com.project.mgjandroid.ui.activity.secondhand.BuyDetailsFragment;
import com.project.mgjandroid.ui.activity.secondhand.SecondHandDetailsFragment;
import com.project.mgjandroid.ui.activity.waste.WasteDetailsFragment;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by yuandi on 2016/11/14.
 */
@Router(value = "information/:informationType/:informationId", intParams = "informationType", longParams = "informationId")
public class InformationDetailActivity extends BaseActivity {

    public static final int REQUEST_CODE_PAY = 2016;

    @InjectView(R.id.common_back)
    private ImageView mBack;
    @InjectView(R.id.common_title)
    private TextView mTitle;
    @InjectView(R.id.com_share)
    private ImageView mShare;

    private BaseFragment fragment;
    private long informationId;
    private int informationType;
    private ShareUtil shareUtil;
    private CallPhoneDialog dialog;

    public static void toInformationDetail(Context context, long informationId, int informationType) {
        Intent intent = new Intent(context, InformationDetailActivity.class);
        intent.putExtra("informationId", informationId);
        intent.putExtra("informationType", informationType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.employment_info_detail_activity);
        Injector.get(this).inject();
        informationId = getIntent().getLongExtra("informationId", 0);
        informationType = getIntent().getIntExtra("informationType", 0);
        initView();
    }

    private void initView() {
        mTitle.setText("详情");
        mBack.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mShare.setVisibility(View.VISIBLE);
        getFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_layout, fragment).commit();
    }

    private void getFragment() {
        switch (informationType) {
            case 10:
                fragment = new ResumeDetailFragment();
                break;
            case 1:
                fragment = new RecruitmentDetailFragment();
                break;
            case 7:
                fragment = new FengShuiDetailsFragment();
                break;
            case 9:
                fragment = new HealthDetailsFragment();
                break;
            case 8:
                fragment = new LawsDetailsFragment();
                break;
            case 11:
                fragment = new WasteDetailsFragment();
                break;
            case 5:
                fragment = new HomeMakingDetailsFragment();
                break;
            case 3:
                fragment = new RepairDetailsFragment();
                break;
            case 2:
                fragment = new LeaseDetailFragment();
                break;
            case 14:
                fragment = new RentDetailFragment();
                break;
            case 4:
                fragment = new EducationDetailsFragment();
                break;
            case 13:
                fragment = new FamilyEducationDetailsFragment();
                break;
            case 6:
                fragment = new SecondHandDetailsFragment();
                break;
            case 12:
                fragment = new BuyDetailsFragment();
                break;
            case 16:
                fragment = new ResumeSimpleDetailFragment();
                break;
            case 15:
                fragment = new RecruitSimpleDetailFragment();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                back();
                break;
            case R.id.com_share:
                if (fragment.getInformation() != null) {
                    if (shareUtil == null) {
                        shareUtil = new ShareUtil(mActivity, fragment.getInformation().getTitle(), fragment.getInformation().getDescription(), fragment.getInformation().getShareUrl(), getString());
                    }
                    shareUtil.showPopupWindow();
                }
                break;
        }
    }

    public void canShare(boolean canShare) {
        if (canShare) {
            mShare.setVisibility(View.VISIBLE);
        } else {
            mShare.setVisibility(View.GONE);
        }
    }

    private String getString() {
        String imgs = fragment.getInformation().getImgs();
        if (informationType == InformationType.Position.getValue()) {
            imgs = ((PositionInformation) fragment.getInformation()).getHeadImg();
            if (TextUtils.isEmpty(imgs)) imgs = fragment.getInformation().getImgs();
        }
        return imgs;
    }

    public long getInformationId() {
        return informationId;
    }

    public int getInformationType() {
        return informationType;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAY && resultCode == RESULT_OK && data != null) {
            showDialog(fragment.getInformation().getMobile());
        }
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showDialog(final String mobile) {
        if (dialog != null) {
            dialog.show();
            return;
        }
        dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse(String.format(getString(R.string.withdraw_phone), mobile)));
                mActivity.startActivity(intent);
                dialog.dismiss();
            }

            @Override
            public void onExit() {
                dialog.dismiss();
            }
        }, "", mobile);

        dialog.show();
    }
}
