package com.project.mgjandroid.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.BaiduGeocoderModel;
import com.project.mgjandroid.model.CheckAddressModel;
import com.project.mgjandroid.model.DeleteAddressModel;
import com.project.mgjandroid.model.EditAddressModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.DialogUtil;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {
    public static final String KEY_POI_INFO = "POI_INFO";
    public static final int SET_POI_INFO_REQUEST = 0;
    @InjectView(R.id.add_address_act_back)
    private ImageView ivBack;
    @InjectView(R.id.add_address_act_tv_title)
    private TextView tvTitle;
    @InjectView(R.id.add_address_act_delete)
    private ImageView ivDelete;
    @InjectView(R.id.add_address_edt_name)
    private EditText etName;
    @InjectView(R.id.add_address_name_iv_delete)
    private ImageView ivNameDel;
    @InjectView(R.id.add_address_cb_sir)
    private CheckBox cbMale;
    @InjectView(R.id.add_address_cb_miss)
    private CheckBox cbFemale;
    @InjectView(R.id.add_address_edt_mobile)
    private EditText etMobile;
    @InjectView(R.id.add_address_mobile_iv_delete)
    private ImageView ivMobileDel;
    @InjectView(R.id.add_address_phone_option_iv_add)
    private ImageView ivAddPhoneOption;
    @InjectView(R.id.add_address_edt_phone_option_layout)
    private LinearLayout llPhoneOption;
    @InjectView(R.id.add_address_edt_phone_option)
    private EditText etPhoneOption;
    @InjectView(R.id.add_address_phone_option_iv_delete)
    private ImageView ivPhoneOptionDel;
    @InjectView(R.id.location_address)
    private TextView tvLocation;
    @InjectView(R.id.add_address_edt_house)
    private EditText etHouseNum;
    @InjectView(R.id.add_address_house_iv_delete)
    private ImageView ivHouseNumDel;
    @InjectView(R.id.add_address_act_tv_save)
    private TextView tvSaveAddress;

    private UserAddress userAddress;
    private long merchantId;
    private Double latitude;
    private Double longitude;
    private PopupWindow phoneWindow;
    private MLoadingDialog loadingDialog;
    private long agentId;
    private String editUserAddress = "";

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.add_address_act);
        Injector.get(this).inject();
        userAddress = (UserAddress) getIntent().getSerializableExtra("USER_ADDRESS");
        merchantId = getIntent().getLongExtra("MERCHANT_ID", -1);
        agentId = getIntent().getIntExtra("agentId", -1);
        initView();
    }

    private void initView() {
        if (userAddress != null) {
            tvTitle.setText(getString(R.string.modify_address));
            ivDelete.setVisibility(View.VISIBLE);
            etName.setText(userAddress.getName());
            etMobile.setText(userAddress.getMobile());
            if (!TextUtils.isEmpty(userAddress.getHouseNumber())) {
                etHouseNum.setText(userAddress.getHouseNumber());
            }
            tvLocation.setText(userAddress.getAddress());
            latitude = userAddress.getLatitude();
            longitude = userAddress.getLongitude();
            if (userAddress.getGender().equals(getString(R.string.sir))) {
                cbMale.setChecked(true);
            } else if (userAddress.getGender().equals(getString(R.string.miss))) {
                cbFemale.setChecked(true);
            }
            if (!TextUtils.isEmpty(userAddress.getBackupMobile())) {
                ivAddPhoneOption.setVisibility(View.GONE);
                llPhoneOption.setVisibility(View.VISIBLE);
                etPhoneOption.setText(userAddress.getBackupMobile());
            }
        } else {
            tvTitle.setText(getString(R.string.add_address));
        }
        loadingDialog = new MLoadingDialog();
        ivBack.setOnClickListener(this);
        tvSaveAddress.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        ivNameDel.setOnClickListener(this);
        ivMobileDel.setOnClickListener(this);
        ivHouseNumDel.setOnClickListener(this);
        ivAddPhoneOption.setOnClickListener(this);
        ivPhoneOptionDel.setOnClickListener(this);
        cbMale.setOnClickListener(this);
        cbFemale.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        etName.setOnFocusChangeListener(this);
        etMobile.setOnFocusChangeListener(this);
        etHouseNum.setOnFocusChangeListener(this);
        etPhoneOption.setOnFocusChangeListener(this);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivNameDel.setVisibility(View.VISIBLE);
                } else {
                    ivNameDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivMobileDel.setVisibility(View.VISIBLE);
                    String mobile = App.getUserInfo().getMobile();
                    if (mobile != null) {
                        if (mobile.startsWith(s.toString()) && s.length() < 11) {
                            if (phoneWindow == null) {
                                showPhonePop(etMobile);
                            } else if (!phoneWindow.isShowing()) {
                                phoneWindow.showAsDropDown(etMobile, 0, DipToPx.dip2px(AddAddressActivity.this, 10));
                            }
                        } else {
                            if (phoneWindow != null && phoneWindow.isShowing()) {
                                phoneWindow.dismiss();
                            }
                        }
                    }
                } else {
                    ivMobileDel.setVisibility(View.GONE);
                    if (phoneWindow == null) {
                        showPhonePop(etMobile);
                    } else if (!phoneWindow.isShowing()) {
                        phoneWindow.showAsDropDown(etMobile, 0, DipToPx.dip2px(AddAddressActivity.this, 10));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etHouseNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivHouseNumDel.setVisibility(View.VISIBLE);
                } else {
                    ivHouseNumDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPhoneOption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivPhoneOptionDel.setVisibility(View.VISIBLE);
                } else {
                    ivPhoneOptionDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_address_act_back:
                back();
                break;
            case R.id.add_address_act_tv_save:
                MLog.d("merchantId = " + merchantId + ",agentId = " + agentId);
                if (merchantId > 0 || agentId > 0) {
                    CheckUpdate();
                    break;
                }
                saveUserAddress(true);
                break;
            case R.id.add_address_act_delete://删除地址
                doDeleteAddress();
                break;
            case R.id.add_address_name_iv_delete:
                etName.setText("");
                break;
            case R.id.add_address_mobile_iv_delete:
                etMobile.setText("");
                break;
            case R.id.add_address_house_iv_delete:
                etHouseNum.setText("");
                break;
            case R.id.add_address_cb_sir:
                if (cbMale.isChecked()) {
                    cbFemale.setChecked(false);
                } else {
                    cbMale.setChecked(true);
                }
                break;
            case R.id.add_address_cb_miss:
                if (cbFemale.isChecked()) {
                    cbMale.setChecked(false);
                } else {
                    cbFemale.setChecked(true);
                }
                break;
            case R.id.location_address:
                Intent intent = new Intent(AddAddressActivity.this, SetAddressActivity.class);
                intent.putExtra("has_data", "1");
                startActivityForResult(intent, SET_POI_INFO_REQUEST);
                break;
            case R.id.add_address_phone_option_iv_add:
                llPhoneOption.setVisibility(View.VISIBLE);
                ivAddPhoneOption.setVisibility(View.GONE);
                etPhoneOption.requestFocus();
                break;
            case R.id.add_address_phone_option_iv_delete:
                etPhoneOption.setText("");
                break;
            default:
                break;
        }
    }

    private void CheckUpdate() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        if (merchantId != -1)
            map.put("merchantId", merchantId);
        if (agentId != -1)
            map.put("agentId", agentId);
        VolleyOperater<CheckAddressModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_CHECK_ADDRESS, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                    }
                    CheckAddressModel model = (CheckAddressModel) obj;
                    if (model.isValue()) {
                        saveUserAddress(true);
                    } else {
                        showDialog();
                    }
                } else {
                    ToastUtils.displayMsg(R.string.save_fail, AddAddressActivity.this);
                }
            }
        }, CheckAddressModel.class);
    }

    private void doDeleteAddress() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", userAddress.getId());
        VolleyOperater<DeleteAddressModel> operater = new VolleyOperater<>(AddAddressActivity.this);
        operater.doRequest(Constants.URL_DELETE_ADDRESS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    DeleteAddressModel deleteAddressModel = (DeleteAddressModel) obj;
                    if (deleteAddressModel.getCode() == 0) {
//                        setResult(AddressManagerListAdapter.EDITED_ADDRESS,new Intent());
                        finish();
                    }
                }
                loadingDialog.dismiss();
            }
        }, DeleteAddressModel.class);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.add_address_edt_name:
                if (hasFocus && etName.getText().toString().length() > 0) {
                    ivNameDel.setVisibility(View.VISIBLE);
                } else {
                    ivNameDel.setVisibility(View.GONE);
                }
                break;
            case R.id.add_address_edt_mobile:
                if (hasFocus && etMobile.getText().toString().length() > 0) {
                    ivMobileDel.setVisibility(View.VISIBLE);
                    ivMobileDel.setVisibility(View.VISIBLE);
                    if (App.getUserInfo().getMobile().startsWith(etMobile.getText().toString()) && etMobile.getText().toString().length() < 11) {
                        if (phoneWindow == null) {
                            showPhonePop(etMobile);
                        } else if (!phoneWindow.isShowing()) {
                            phoneWindow.showAsDropDown(etMobile, 0, DipToPx.dip2px(AddAddressActivity.this, 10));
                        }
                    } else {
                        if (phoneWindow != null && phoneWindow.isShowing()) {
                            phoneWindow.dismiss();
                        }
                    }
                } else if (hasFocus) {
                    ivMobileDel.setVisibility(View.GONE);
                    if (phoneWindow == null) {
                        showPhonePop(etMobile);
                    } else if (!phoneWindow.isShowing()) {
                        phoneWindow.showAsDropDown(etMobile, 0, DipToPx.dip2px(this, 10));
                    }
                } else {
                    ivMobileDel.setVisibility(View.GONE);
                    if (phoneWindow != null && phoneWindow.isShowing()) {
                        phoneWindow.dismiss();
                    }
                }
                break;
            case R.id.add_address_edt_house:
                if (hasFocus && etHouseNum.getText().toString().length() > 0) {
                    ivHouseNumDel.setVisibility(View.VISIBLE);
                } else {
                    ivHouseNumDel.setVisibility(View.GONE);
                }
                break;
            case R.id.add_address_edt_phone_option:
                if (hasFocus && etPhoneOption.getText().toString().length() > 0) {
                    ivPhoneOptionDel.setVisibility(View.VISIBLE);
                } else {
                    ivPhoneOptionDel.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SET_POI_INFO_REQUEST) {
            if (resultCode == RESULT_OK) {
                BaiduGeocoderModel.ResultBean.PoisBean poiInfo = (BaiduGeocoderModel.ResultBean.PoisBean) data.getSerializableExtra(KEY_POI_INFO);
                tvLocation.setText(poiInfo.getAddr() + poiInfo.getName());
//                etHouseNum.setText(poiInfo.getAddr());
                Log.i("userAddress", "poiInfo.getAddr() " + poiInfo.getAddr() + ",poiInfo.getName():" + poiInfo.getName());
                editUserAddress = poiInfo.getName();
                etHouseNum.requestFocus();
//                Editable spannable = etHouseNum.getText();
//                Selection.setSelection(spannable, spannable.length());
                longitude = poiInfo.getPoint().getX();
                latitude = poiInfo.getPoint().getY();
            } else if (resultCode == SetAddressActivity.SUGGESTION_RESULT) {
                SuggestionResult.SuggestionInfo poiInfo = data.getParcelableExtra(KEY_POI_INFO);
                Log.i("userAddress", "city" + poiInfo.city + ",district:" + poiInfo.district + ",key:" + poiInfo.key);
                editUserAddress = poiInfo.key;
                tvLocation.setText(poiInfo.city + poiInfo.district + poiInfo.key);
//                etHouseNum.setText(poiInfo.key);
                etHouseNum.requestFocus();
//                Editable spannable = etHouseNum.getText();
//                Selection.setSelection(spannable, spannable.length());
                longitude = poiInfo.pt.longitude;
                latitude = poiInfo.pt.latitude;
            }
        }
    }

    private void saveUserAddress(boolean isInspectAddress) {
        Map<String, Object> map = new HashMap<>();
        if (userAddress != null) {
            map.put("id", userAddress.getId());
        }
        map.put("detailedAddress", editUserAddress);
        if (!TextUtils.isEmpty(etName.getText().toString())) {
            if (!checkUsername(etName.getText().toString().trim())) {
                ToastUtils.displayMsg("输入内容不合法", this);
//                loadingDialog.dismiss();
                return;
            }
            map.put("name", etName.getText().toString());
        } else {
            ToastUtils.displayMsg(R.string.set_your_name, this);
//            loadingDialog.dismiss();
            return;
        }
        if (!TextUtils.isEmpty(etMobile.getText().toString()) && etMobile.getText().length() == 11) {
            map.put("mobile", etMobile.getText().toString());
        } else {
            ToastUtils.displayMsg("请输入11位手机号码", this);
//            loadingDialog.dismiss();
            return;
        }
        if (!TextUtils.isEmpty(etPhoneOption.getText().toString())) {
            map.put("backupMobile", etPhoneOption.getText().toString());
        }
        if (cbMale.isChecked()) {
            map.put("gender", getString(R.string.sir));
        } else if (cbFemale.isChecked()) {
            map.put("gender", getString(R.string.miss));
        } else {
            ToastUtils.displayMsg(R.string.choose_gender, this);
//            loadingDialog.dismiss();
            return;
        }
        if (!TextUtils.isEmpty(tvLocation.getText())) {
            map.put("address", tvLocation.getText());
        } else {
            ToastUtils.displayMsg(R.string.set_your_address, this);
//            loadingDialog.dismiss();
            return;
        }
        if (!TextUtils.isEmpty(etHouseNum.getText().toString())) {
//            if (etHouseNum.getText().length() < 6) {
//                ToastUtils.displayMsg("详细地址至少填写6个字符", this);
////                loadingDialog.dismiss();
//                return;
//            }
            map.put("houseNumber", etHouseNum.getText().toString());
        } else {
            if (isInspectAddress) {
                DialogUtil dialogUtil = new DialogUtil(mActivity, true, true, "您未填写详细地址的信息，可能会影响到配送哦", "我再改改", "就这样了", new DialogUtil.SureInterfance() {
                    @Override
                    public void sureTodo() {
                        saveUserAddress(false);
                    }
                });
                dialogUtil.showCustomDialog();
                return;
            } else {
                map.put("houseNumber", "");
            }
        }
        loadingDialog.show(getFragmentManager(), "");
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        if (merchantId != -1)
            map.put("merchantId", merchantId);
        if (agentId > 0)
            map.put("agentId", agentId);
        VolleyOperater<EditAddressModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_EDIT_ADDRESS, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
//                    ToastUtils.displayMsg(R.string.save_success, AddAddressActivity.this);
                    Log.e("UserAddress", ((EditAddressModel) obj).getValue().toString());
                    back();
                } else {
                    ToastUtils.displayMsg(R.string.save_fail, AddAddressActivity.this);
                }
                loadingDialog.dismiss();
            }
        }, EditAddressModel.class);
    }

    private void showPhonePop(View mView) {
        TextView phoneNumber = (TextView) getLayoutInflater().inflate(R.layout.mobile_textview, null);
        phoneNumber.setText(App.getUserInfo().getMobile());

        phoneWindow = new PopupWindow(phoneNumber, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        phoneWindow.setOutsideTouchable(true);
        phoneWindow.showAsDropDown(mView, 0, DipToPx.dip2px(this, 10));

        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneWindow.dismiss();
                etMobile.setText(App.getUserInfo().getMobile());
                Editable spannable = etMobile.getText();
                Selection.setSelection(spannable, spannable.length());
            }
        });
    }

    /**
     * 验证用户名
     *
     * @param username 用户名
     * @return boolean
     */
    public static boolean checkUsername(String username) {
        String regex = "([a-zA-Z0-9\\u4e00-\\u9fa5]{1,10})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.chooseAddressDialog);
        LinearLayout contentView = (LinearLayout) getLayoutInflater().inflate(R.layout.unreach_address_pick_dialog, null);
        TextView tvAddressChange = (TextView) contentView.findViewById(R.id.dialod_address_change);
        TextView btnConfirm = (TextView) contentView.findViewById(R.id.dialod_address_confirm);
        TextView btnCancel = (TextView) contentView.findViewById(R.id.dialod_address_cancel);

        String tip = "商品将无法配送到此地址，是否仍然保存?";
        tvAddressChange.setText(tip);
        btnConfirm.setText("仍然保存");
        btnCancel.setText("取消");

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AddressManageActivity.instance != null) {
                    AddressManageActivity.instance.clearAddress();
                }
                dialog.dismiss();
                saveUserAddress(true);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(contentView, new LinearLayout.LayoutParams(DeviceParameter.getIntScreenWidth() - DipToPx.dip2px(this, 40), LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
