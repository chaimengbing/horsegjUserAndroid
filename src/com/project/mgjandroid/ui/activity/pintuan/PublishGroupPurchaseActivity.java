package com.project.mgjandroid.ui.activity.pintuan;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.base.BaseRecyclerAdapter;
import com.project.mgjandroid.bean.AddImage;
import com.project.mgjandroid.bean.CreateGroup;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ServiceTypeModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.PhotoActivity;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.FileUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by User_Cjh on 2016/8/15.
 */
public class PublishGroupPurchaseActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener, AdapterView.OnItemClickListener {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.next_setup)
    private TextView tvNext;
    @InjectView(R.id.create_group_goods_name)
    private EditText etGoodsName;
    @InjectView(R.id.create_group_min_member)
    private EditText etMinMember;
    @InjectView(R.id.create_group_max_member)
    private EditText etMaxMember;
    @InjectView(R.id.create_group_group_price)
    private EditText etGroupPrice;
    @InjectView(R.id.create_group_market_price)
    private EditText etMarketPrice;
    @InjectView(R.id.create_group_goods_intro)
    private EditText etGoodsIntro;
    @InjectView(R.id.create_group_goods_imgs)
    private RecyclerView rvGoodsImgs;
    @InjectView(R.id.create_group_days)
    private EditText etDays;
    @InjectView(R.id.create_group_send_days)
    private EditText etSendDays;
    @InjectView(R.id.create_group_service_grid)
    private NoScrollGridView gvService;

    private AddGoodsRecyclerAdapter adapter;
    private PopupWindow mPopupWindow;
    private ArrayList<String> mSelectFiles;
    private File file;
    private static final int MY_TAKE_PICTURE = 200;
    public static final String CREATE_GROUP = "create_group";
    private String address = "";
    private GroupServiceAdapter serviceAdapter;
    private CreateGroup group;
    private List<CreateGroup> userGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_group_purchase);
        Injector.get(this).inject();
        instance = this;
        initView();
        setLinstener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.next_setup:
                if (checkCanDoNext()) {
                    saveGroupInfo();
                    Intent intent = new Intent(mActivity, PublishGroupPurchaseNextActivity.class);
//                    intent.putExtra("group", group);
                    startActivity(intent);
                }
                break;
            case R.id.take_photo:
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                FileUtils.createDirFile(Constants.IMG_PATH);
                String path = Constants.IMG_PATH + generateUUID() + ".jpg";
                file = FileUtils.createNewFile(path);
                if (file == null) {
                    return;
                }
                Uri cameraUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                startActivityForResult(openCameraIntent, MY_TAKE_PICTURE);
                dismissPopupWindow();
                break;
            case R.id.select_photo:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_GET_IMAGE_LIST);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                dismissPopupWindow();
                break;
            case R.id.feed_back_cancel:
                dismissPopupWindow();
                break;
        }
    }

    private boolean checkCanDoNext() {
        if (etGoodsName.getText().toString().trim().length() == 0) {
            ToastUtils.displayMsg("请填写商品名称", mActivity);
            return false;
        }
        if (etMinMember.getText().toString().trim().length() == 0) {
            ToastUtils.displayMsg("请填写成团最小数量", mActivity);
            return false;
        }
        if (etMaxMember.getText().toString().trim().length() == 0) {
            ToastUtils.displayMsg("请填写成团最大数量", mActivity);
            return false;
        }
        if (Integer.parseInt(etMinMember.getText().toString()) > Integer.parseInt(etMaxMember.getText().toString())) {
            ToastUtils.displayMsg("成团最小数量不能大于成团最大数量", mActivity);
            return false;
        }
        if (etGroupPrice.getText().toString().trim().length() == 0) {
            ToastUtils.displayMsg("请填写拼团价格", mActivity);
            return false;
        }
        if (etGroupPrice.getText().toString().trim().endsWith(".") || etGroupPrice.getText().toString().trim().startsWith("00")) {
            ToastUtils.displayMsg("请填写正确的拼团价格", mActivity);
            return false;
        }
        if (etMarketPrice.getText().toString().trim().length() == 0) {
            ToastUtils.displayMsg("请填写市场价格", mActivity);
            return false;
        }
        if (etMarketPrice.getText().toString().trim().endsWith(".") || etMarketPrice.getText().toString().trim().startsWith("00")) {
            ToastUtils.displayMsg("请填写正确的拼团价格", mActivity);
            return false;
        }
        if (etGoodsIntro.getText().toString().trim().length() == 0) {
            ToastUtils.displayMsg("请填写商品介绍", mActivity);
            return false;
        }
        if (ChoosePhotoModel.getInstance().getmCurrentPhotoList() == null || ChoosePhotoModel.getInstance().getmCurrentPhotoList().size() == 0) {
            ToastUtils.displayMsg("请至少选择一张图片", mActivity);
            return false;
        }
        if (etDays.getText().toString().trim().length() == 0 || etDays.getText().toString().startsWith("0")) {
            ToastUtils.displayMsg("请填写开团天数", mActivity);
            return false;
        }
        if (etSendDays.getText().toString().trim().length() == 0 || etSendDays.getText().toString().startsWith("0")) {
            ToastUtils.displayMsg("请填写发货时间", mActivity);
            return false;
        }
        return true;
    }

    private void setLinstener() {
        ivBack.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        etGroupPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int flag = s.toString().indexOf(".");
                if (s.length() > 0 && !s.toString().endsWith(".")) {
//                    BigDecimal max = new BigDecimal("99999.99");
//                    BigDecimal current = new BigDecimal(s.toString());
//                    if (current.compareTo(max) > 0) {
//                        etGroupPrice.setText("99999.99");
//                        etGroupPrice.setSelection("99999.99".length());
//                    }
                    if (s.length() > 5 && flag < s.length() - 3) {
                        String cur = s.toString().substring(0, s.length() - 1);
                        etGroupPrice.setText(cur);
                        etGroupPrice.setSelection(cur.length());
                    }
                }
                if (flag != -1 && flag < s.length() - 3) {
                    String cur = s.toString().substring(0, s.toString().indexOf(".") + 3);
                    etGroupPrice.setText(cur);
                    etGroupPrice.setSelection(cur.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etMarketPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int flag = s.toString().indexOf(".");
                if (s.length() > 0 && !s.toString().endsWith(".")) {
//                    BigDecimal max = new BigDecimal("99999.99");
//                    BigDecimal current = new BigDecimal(s.toString());
//                    if (current.compareTo(max) > 0) {
//                        etMarketPrice.setText("99999.99");
//                        etMarketPrice.setSelection("99999.99".length());
//                    }
                    if (s.length() > 5 && flag < s.length() - 3) {
                        String cur = s.toString().substring(0, s.length() - 1);
                        etMarketPrice.setText(cur);
                        etMarketPrice.setSelection(cur.length());
                    }
                }
                if (flag != -1 && flag < s.length() - 3) {
                    String cur = s.toString().substring(0, s.toString().indexOf(".") + 3);
                    etMarketPrice.setText(cur);
                    etMarketPrice.setSelection(cur.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        tvTitle.setText("发起拼团");
        address = getIntent().getStringExtra("address");
        rvGoodsImgs.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        adapter = new AddGoodsRecyclerAdapter(mActivity, R.layout.item_group_add_pic);
        rvGoodsImgs.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        ChoosePhotoModel.getInstance().setMaxCount(5);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());
        serviceAdapter = new GroupServiceAdapter(R.layout.item_group_service, mActivity);
        gvService.setAdapter(serviceAdapter);
        gvService.setOnItemClickListener(this);

        setGroupInfo();
        getServiceType();
    }

    private void getServiceType() {
        VolleyOperater<ServiceTypeModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_SERVICE_TYPE, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast("获取数据失败");
                        return;
                    }
                    ServiceTypeModel model = (ServiceTypeModel) obj;
                    if (group != null) {
                        String service = group.getServices();
                        if (CheckUtils.isNoEmptyStr(service)) {
                            String[] services = service.split(",");
                            for (int i = 0; i < services.length; i++) {
                                for (ServiceTypeModel.ValueEntity entity : model.getValue()) {
                                    if (services[i].equals(entity.getId())) {
                                        entity.setIsSelected(true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    serviceAdapter.setData(model.getValue());
                }
            }
        }, ServiceTypeModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSelectFiles = ChoosePhotoModel.getInstance().getmCurrentPhotoList();
        if (mSelectFiles == null || mSelectFiles.size() == 0) {
            List<AddImage> list = new ArrayList<>();
            list.add(new AddImage());
            adapter.setData(list);
            return;
        }
        List<AddImage> data = new ArrayList<>();
        for (String str : mSelectFiles) {
            AddImage img = new AddImage();
            img.setImageUrl(str);
            data.add(img);
        }
        if (data.size() < 5) {
            data.add(new AddImage());
        }
        adapter.setData(data);
    }

    @Override
    public void onBackPressed() {
        saveGroupInfo();
        this.finish();
        overridePendingTransition(R.anim.unhold, R.anim.unfade);
    }

    private void setGroupInfo() {
        String createGroup = PreferenceUtils.getStringPreference(CREATE_GROUP, "", mActivity);
        if (CheckUtils.isEmptyStr(createGroup)) {
            return;
        }
        userGroup = JSON.parseArray(createGroup, CreateGroup.class);
        for (CreateGroup mGroup : userGroup) {
            if (mGroup.getUserId() == App.getUserInfo().getId()) {
                group = mGroup;
            }
        }
        if (group == null) {
            return;
        }
        etGoodsName.setText(group.getGoodsName());
        etGoodsName.setSelection(group.getGoodsName().length());
        etMinMember.setText(group.getMinMember());
        etMaxMember.setText(group.getMaxMember());
        etGroupPrice.setText(group.getGroupPrice());
        etMarketPrice.setText(group.getMarketPrice());
        etGoodsIntro.setText(group.getGoodsIntro());
        etDays.setText(group.getDays());
        etSendDays.setText(group.getSendDays());
        String path = group.getGoodsPath();
        mSelectFiles = ChoosePhotoModel.getInstance().getmCurrentPhotoList();
        if (CheckUtils.isNoEmptyStr(path)) {
            String[] paths = path.split(";");
            for (int i = 0; i < paths.length; i++) {
                if (FileUtils.isFileExist(paths[i]))
                    mSelectFiles.add(paths[i]);
            }
        }
    }

    private void saveGroupInfo() {
        String info = PreferenceUtils.getStringPreference(CREATE_GROUP, "", mActivity);
        if (CheckUtils.isNoEmptyStr(info)) {
            userGroup = JSON.parseArray(info, CreateGroup.class);
        }
        if (CheckUtils.isEmptyList(userGroup)) {
            userGroup = new ArrayList<>();
        } else {
            for (CreateGroup mGroup : userGroup) {
                if (mGroup.getUserId() == App.getUserInfo().getId()) {
                    group = mGroup;
                }
            }
        }
        if (group == null) {
            group = new CreateGroup();
            group.setUserId(App.getUserInfo().getId());
            userGroup.add(group);
        }
        group.setGoodsName(etGoodsName.getText().toString().trim());
        group.setMinMember(etMinMember.getText().toString().trim());
        group.setMaxMember(etMaxMember.getText().toString().trim());
        group.setGroupPrice(etGroupPrice.getText().toString().trim());
        group.setMarketPrice(etMarketPrice.getText().toString().trim());
        group.setGoodsIntro(etGoodsIntro.getText().toString().trim());
        group.setDays(etDays.getText().toString().trim());
        group.setSendDays(etSendDays.getText().toString().trim());
        StringBuffer sb = new StringBuffer("");
        for (ServiceTypeModel.ValueEntity entity : serviceAdapter.getData()) {
            if (entity.isSelected())
                sb.append(entity.getId() + ",");
        }
        String service = sb.toString();
        if (service.length() > 0) {
            service = service.substring(0, service.length() - 1);
        }
        group.setServices(service);
        group.setFlied(address);
        StringBuffer sbPath = new StringBuffer("");
        for (int i = 0; i < mSelectFiles.size(); i++) {
            if (i == mSelectFiles.size() - 1) {
                sbPath.append(mSelectFiles.get(i));
            } else {
                sbPath.append(mSelectFiles.get(i) + ";");
            }
        }
        group.setGoodsPath(sbPath.toString());
        String createGroup = JSON.toJSONString(userGroup);
        PreferenceUtils.saveStringPreference(CREATE_GROUP, createGroup, mActivity);
    }

    @Override
    public void onItemClick(View view, int position) {
        CommonUtils.hideKeyBoard2(view);
        List<AddImage> list = adapter.getData();
        if (list.get(position).isHasImage()) {
            //跳转页面
            Intent intent = new Intent(mActivity, PhotoActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        } else {
            //添加图片
            showPopupWindow();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MY_TAKE_PICTURE:
                mSelectFiles = ChoosePhotoModel.getInstance().getmCurrentPhotoList();
                AddImage model = new AddImage();
                model.setImageUrl(file.getAbsolutePath());
                mSelectFiles.add(file.getAbsolutePath());
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void initPopupWindow() {
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_select_photo, null);
        TextView tvTakePhoto = (TextView) linearLayout.findViewById(R.id.take_photo);
        TextView tvSelectPhoto = (TextView) linearLayout.findViewById(R.id.select_photo);
        TextView tvCancel = (TextView) linearLayout.findViewById(R.id.feed_back_cancel);
        tvTakePhoto.setOnClickListener(this);
        tvSelectPhoto.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        mPopupWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
    }

    private void showPopupWindow() {
        if (mPopupWindow == null) {
            initPopupWindow();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.8f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(lp);
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else if (!mPopupWindow.isShowing()) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.8f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(lp);
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成图片随机id
     *
     * @return
     */
    public static String generateImgID() {
        return new StringBuilder().
                append(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())).
                append(generateThreeRandomNum()).toString();
    }


    /**
     * 生成三位随机数
     *
     * @return
     */
    public static int generateThreeRandomNum() {
        Random random = new Random();
        int num = (int) (random.nextDouble() * (1000 - 100) + 100);
        return num;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChoosePhotoModel.getInstance().clear();
    }

    public static PublishGroupPurchaseActivity instance;

    public void createSuccess() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<ServiceTypeModel.ValueEntity> data = serviceAdapter.getData();
        if (data.get(position).isSelected()) {
            data.get(position).setIsSelected(false);
        } else {
            data.get(position).setIsSelected(true);
        }
        serviceAdapter.setData(data);
    }
}
