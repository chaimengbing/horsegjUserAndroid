package com.project.mgjandroid.ui.activity.pintuan;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.CreateGroup;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GetQiNiuTokenModel;
import com.project.mgjandroid.model.MorePrimaryCategoryModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/8/15.
 */
public class PublishGroupPurchaseNextActivity extends BaseActivity {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.commit_to_review)
    private TextView tvCommit;
    @InjectView(R.id.create_group_name)
    private EditText etName;
    @InjectView(R.id.create_group_sex_man)
    private LinearLayout llMan;
    @InjectView(R.id.create_group_sex_male)
    private LinearLayout llMale;
    @InjectView(R.id.create_group_status_man)
    private ImageView ivMan;
    @InjectView(R.id.create_group_status_male)
    private ImageView ivMale;
    @InjectView(R.id.create_group_phone)
    private EditText etPhone;
    @InjectView(R.id.create_group_flied)
    private TextView tvFlied;
    @InjectView(R.id.create_group_flied_help)
    private ImageView ivHelp;
    @InjectView(R.id.create_group_address)
    private EditText etAddress;
    @InjectView(R.id.create_group_id_card)
    private EditText etIdCard;
    @InjectView(R.id.create_group_oneself_intro)
    private EditText etOwnIntro;

    private boolean isMan = true;
    private CreateGroup group;
    private List<CreateGroup> userGroup;
    private MLoadingDialog mMLoadingDialog;
    private MaterialDialog mMaterialDialog;
    private ArrayList<String> imageUrls;
    private ArrayList<String> mSelectFiles;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_group_purchase_next);
        Injector.get(this).inject();
        initView();
        setListener();
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
        llMan.setOnClickListener(this);
        llMale.setOnClickListener(this);
        ivHelp.setOnClickListener(this);
        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 60) {
                    ToastUtils.displayMsg("联系地址不得多于个60字符", mActivity);
                    etAddress.setText(s.subSequence(0, 60));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        tvTitle.setText("发起拼团");
//        group = (CreateGroup) getIntent().getSerializableExtra("group");
//        tvFlied.setText(group.getFlied());
        String info = PreferenceUtils.getStringPreference(PublishGroupPurchaseActivity.CREATE_GROUP, "", mActivity);
        userGroup = JSON.parseArray(info, CreateGroup.class);
        if (CheckUtils.isEmptyList(userGroup)) {
            finish();
            return;
        }
        for (CreateGroup mGroup : userGroup) {
            if (mGroup.getUserId() == App.getUserInfo().getId()) {
                group = mGroup;
            }
        }
        if (group == null) {
            finish();
            return;
        }
        setGroupInfo();
        mMLoadingDialog = new MLoadingDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_group_sex_man:
                if (isMan)
                    break;
                isMan = true;
                ivMan.setImageResource(R.drawable.service_status_checked);
                ivMale.setImageResource(R.drawable.service_status_unchecked);
                break;
            case R.id.create_group_sex_male:
                if (!isMan)
                    break;
                isMan = false;
                ivMale.setImageResource(R.drawable.service_status_checked);
                ivMan.setImageResource(R.drawable.service_status_unchecked);
                break;
            case R.id.create_group_flied_help:
                ToastUtils.displayMsg("若需修改，请返回首页定位到您要开展的业务范围", mActivity);
                break;
            case R.id.commit_to_review:
                if (canCommit()) {
                    getQiniuUploadToken();
                }
                break;
            case R.id.common_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        saveGroupInfo();
        this.finish();
        overridePendingTransition(R.anim.unhold, R.anim.unfade);
    }

    private void setGroupInfo() {
        etName.setText(group.getName());
        etName.setSelection(group.getName().length());
        etPhone.setText(group.getPhone());
        etAddress.setText(group.getAddress());
        etIdCard.setText(group.getIdCard());
        tvFlied.setText(group.getFlied());
        etOwnIntro.setText(group.getOneselfIntro());
        if ("0".equals(group.getSex())) {
            isMan = false;
            ivMale.setImageResource(R.drawable.service_status_checked);
            ivMan.setImageResource(R.drawable.service_status_unchecked);
        } else {
            isMan = true;
            ivMan.setImageResource(R.drawable.service_status_checked);
            ivMale.setImageResource(R.drawable.service_status_unchecked);
        }
    }

    private void saveGroupInfo() {
        group.setName(etName.getText().toString().trim());
        group.setPhone(etPhone.getText().toString().trim());
        group.setAddress(etAddress.getText().toString().trim());
        group.setIdCard(etIdCard.getText().toString().trim());
        group.setOneselfIntro(etOwnIntro.getText().toString().trim());
        if (isMan) {
            group.setSex("1");
        } else {
            group.setSex("0");
        }
        String createGroup = JSON.toJSONString(userGroup);
        PreferenceUtils.saveStringPreference(PublishGroupPurchaseActivity.CREATE_GROUP, createGroup, mActivity);
        MLog.d(createGroup);
    }

    private void commit() {
        mMLoadingDialog.show(getFragmentManager(), "");
        VolleyOperater<MorePrimaryCategoryModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        map.put("goodsName", group.getGoodsName());
        map.put("minNum", Integer.parseInt(group.getMinMember()));
        map.put("maxNum", Integer.parseInt(group.getMaxMember()));
        map.put("price", new BigDecimal(group.getGroupPrice()));
        map.put("originalPrice", new BigDecimal(group.getMarketPrice()));
        map.put("days", Integer.parseInt(group.getDays()));
        map.put("deliveryDays", Integer.parseInt(group.getSendDays()));
        if (!group.getServices().isEmpty()) {
            map.put("service", group.getServices());
        }
        map.put("description", group.getGoodsIntro());
        map.put("imgs", appendImage());
        map.put("name", group.getName());
        map.put("mobile", group.getPhone());
        map.put("sex", Integer.parseInt(group.getSex()));
        map.put("idCardNo", group.getIdCard());
        map.put("intro", group.getOneselfIntro());
        map.put("address", group.getAddress());
        operater.doRequest(Constants.URL_CREATE_GROUP, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    group.setGoodsName("");
                    group.setMinMember("");
                    group.setMaxMember("");
                    group.setGroupPrice("");
                    group.setMarketPrice("");
                    group.setGoodsIntro("");
                    group.setDays("");
                    group.setSendDays("");
                    group.setServices("");
                    group.setFlied("");
                    group.setGoodsPath("");
                    String createGroup = JSON.toJSONString(userGroup);
                    PreferenceUtils.saveStringPreference(PublishGroupPurchaseActivity.CREATE_GROUP, createGroup, mActivity);
                    PublishGroupPurchaseNextActivity.this.finish();
                    PublishGroupPurchaseActivity.instance.createSuccess();
                }
            }
        }, MorePrimaryCategoryModel.class);
    }

    private boolean canCommit() {
        if (etName.getText().toString().trim().length() == 0) {
            ToastUtils.displayMsg("请填写姓名", mActivity);
            return false;
        }
        if (etPhone.getText().toString().trim().length() < 5) {
            ToastUtils.displayMsg("请填写正确的联系方式", mActivity);
            return false;
        }
        if (etAddress.getText().toString().trim().length() == 0) {
            ToastUtils.displayMsg("请填写联系地址", mActivity);
            return false;
        }
        if (etIdCard.getText().toString().trim().length() == 0 || (etIdCard.getText().toString().trim().length() != 18
                && etIdCard.getText().toString().trim().length() != 15)) {
            ToastUtils.displayMsg("请填写正确格式的身份证号", mActivity);
            return false;
        }
        if (etOwnIntro.getText().toString().trim().length() == 0) {
            ToastUtils.displayMsg("请填写个人介绍", mActivity);
            return false;
        }
        saveGroupInfo();
        return true;
    }

    protected void getQiniuUploadToken() {
        showUploadDialog();
        VolleyOperater<GetQiNiuTokenModel> operater = new VolleyOperater<GetQiNiuTokenModel>(this);
        operater.doRequest(Constants.URL_GET_QINIU_TOKEN, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    GetQiNiuTokenModel getQiNiuTokenModel = (GetQiNiuTokenModel) obj;
                    uploadPicture(getQiNiuTokenModel.getValue().getToken(), getQiNiuTokenModel.getValue().getPath());
                } else {
                    mMaterialDialog.dismiss();
                }
            }
        }, GetQiNiuTokenModel.class);
    }

    protected void uploadPicture(String token, String path) {
        UploadOptions uploadOptions = new UploadOptions(null, null, false, new UpProgressHandler() {
            public void progress(String key, double percent) {
                MLog.i("qiniu:" + key + ": " + percent);
            }
        }, null);
        mPosition = 0;
        imageUrls = new ArrayList<>();
        mSelectFiles = ChoosePhotoModel.getInstance().getmCurrentPhotoList();
        startUpload(token, mSelectFiles.get(mPosition), uploadOptions, path);
    }

    protected void startUpload(final String token, final String picturePath, final UploadOptions uploadOptions, final String path) {
        new UploadManager().put(picturePath, getImgUUID(picturePath), token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                mPosition++;
                if (!info.isOK()) {
                    ToastUtils.displayMsg("上传失败", mActivity);
                    mMaterialDialog.dismiss();
                    return;
                } else {
                    try {
                        if (mPosition == mSelectFiles.size()) {
                            mMaterialDialog.dismiss();
                            imageUrls.add(path + response.getString("key"));
                            commit();
                            return;
                        } else {
                            imageUrls.add(path + response.getString("key"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (mPosition < mSelectFiles.size()) {
                        startUpload(token, mSelectFiles.get(mPosition), uploadOptions, path);
                    }
                }
            }
        }, uploadOptions);
    }

    private String appendImage() {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < imageUrls.size(); i++) {
            if (i == imageUrls.size() - 1) {
                sb.append(imageUrls.get(i));
            } else {
                sb.append(imageUrls.get(i) + ";");
            }
        }
        group.setGoodsImgs(sb.toString());
        return sb.toString();
    }

    private String getImgUUID(String picturePath) {
        String picType;
        String substring = picturePath.substring(picturePath.lastIndexOf("."));
        if (!TextUtils.isEmpty(substring)) {
            picType = substring;
        } else {
            picType = ".jpg";
        }
        return CommonUtils.generateImgID() + picType;
    }

    private void showUploadDialog() {
        if (mMaterialDialog == null) {
            mMaterialDialog = new MaterialDialog(mActivity);
            mMaterialDialog.setCanceledOnTouchOutside(true);
        }
        mMaterialDialog.setMessage("正在提交...");
        mMaterialDialog.show();
    }
}
