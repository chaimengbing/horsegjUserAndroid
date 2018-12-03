package com.project.mgjandroid.ui.activity.invitingfriends;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.InvitingModeModel;
import com.project.mgjandroid.model.information.InformationLawModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.EncodingUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.io.ByteArrayOutputStream;

/**
 * Created by SunXueLiang on 2017-07-18.
 */

public class InvitingModeFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.iv_circle_of_friends)
    private ImageView ivCircleOfFriends;
    @InjectView(R.id.iv_qq)
    private ImageView ivQQ;
    @InjectView(R.id.iv_weixin)
    private ImageView ivWeiXin;
    @InjectView(R.id.textView3)
    private TextView tv3;
    @InjectView(R.id.pic_code)
    private CornerImageView picCode;

    private View view;
    private String url;
    private ShareUtil.BaseUiListener uiListener;
    private SparseArray<byte[]> byteArray = new SparseArray<>();
    private int imgWidth;
    private MLoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_inviting_mode, container, false);
        InjectorFragment.get(this).inject(view);
        initView();
        getData();
        return view;
    }

    private void initView() {
        ivCircleOfFriends.setOnClickListener(this);
        ivQQ.setOnClickListener(this);
        ivWeiXin.setOnClickListener(this);
        tv3.setMovementMethod(ScrollingMovementMethod.getInstance());
        loadingDialog = new MLoadingDialog();
    }


    private void getData() {
        loadingDialog.show(mActivity.getFragmentManager(), "");
        VolleyOperater<InvitingModeModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INVITER_CODE_URL, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    InvitingModeModel model = (InvitingModeModel) obj;
                    url = model.getValue();
                    if (byteArray.get(model.getCode()) == null) {
                        MLog.e("---------->EncodingUtils.createQRCode");
                        imgWidth = DipToPx.dip2px(mActivity, 150);
                        Bitmap decodeResource = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_launcher);
                        Bitmap bitmap = EncodingUtils.createQRCode(url, imgWidth, imgWidth, decodeResource);
                        if (bitmap != null) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            try {
                                bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
                                byte[] bytes = baos.toByteArray();
                                MLog.e("---------->bytes.length" + bytes.length);
                                Glide.with(mActivity).load(bytes).into(picCode);
                                byteArray.put(model.getCode(), bytes);
                                baos.close();
                                baos = null;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            bitmap.recycle();
                            bitmap = null;
                        }
                    } else {
                        Glide.with(mActivity).load(byteArray.get(model.getCode())).into(picCode);
                    }
                }
            }
        }, InvitingModeModel.class);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_circle_of_friends:
                ShareUtil.shareToWechat(mActivity, 1, "邀请好友天天分钱", "邀请你的朋友扫码下载马管家。消费一次，分利一次；分享一次，分利一生。", url, null);
                break;
            case R.id.iv_qq:
                uiListener = new ShareUtil.BaseUiListener(mActivity);
                ShareUtil.shareToQQ(mActivity, uiListener, "邀请好友天天分钱", "邀请你的朋友扫码下载马管家。消费一次，分利一次；分享一次，分利一生。", url, null);
                break;
            case R.id.iv_weixin:
                ShareUtil.shareToWechat(mActivity, 0, "邀请好友天天分钱", "邀请你的朋友扫码下载马管家。消费一次，分利一次；分享一次，分利一生。", url, null);
                break;
        }
    }


}
