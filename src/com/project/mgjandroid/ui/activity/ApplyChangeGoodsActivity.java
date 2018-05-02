package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.BaseRecyclerAdapter;
import com.project.mgjandroid.bean.AddImage;
import com.project.mgjandroid.bean.ChangeOrReturnGoods;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChangeOrReturnModel;
import com.project.mgjandroid.model.GetQiNiuTokenModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.pintuan.AddGoodsRecyclerAdapter;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.FileUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by User_Cjh on 2016/9/21.
 */
public class ApplyChangeGoodsActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener {
    @InjectView(R.id.apply_change_back)
    private ImageView back;
    @InjectView(R.id.apply_change_goods_name)
    private TextView goodsName;
    @InjectView(R.id.apply_change_goods_count)
    private TextView goodsCount;
    @InjectView(R.id.replace_confirm)
    private TextView confirm;
    @InjectView(R.id.goods_back)
    private TextView typeBack;
    @InjectView(R.id.hide_keyboard_layout)
    private LinearLayout hideKeyBoardLayout;
    @InjectView(R.id.goods_replace)
    private TextView typeReplace;
    @InjectView(R.id.rechange_goods_minus)
    private ImageView minus;
    @InjectView(R.id.rechange_goods_add)
    private ImageView add;
    @InjectView(R.id.rechange_goods_count)
    private TextView count;
    @InjectView(R.id.apply_change_describe)
    private EditText describe;
    @InjectView(R.id.back_or_replace_goods_imgs)
    private RecyclerView imgsRecycler;

    private int type = -1;
    public static final int TYPE_BACK = 0;
    public static final int TYPE_REPLACE = 1;
    private int currentCount = 0;
    private int maxCount;
    private ChangeOrReturnModel.ValueEntity orderItem;
    private AddGoodsRecyclerAdapter adapter;
    private ArrayList<String> mSelectFiles;
    private PopupWindow mPopupWindow;
    private File file;
    private static final int MY_TAKE_PICTURE = 200;
    private MaterialDialog mMaterialDialog;
    private ArrayList<String> imageUrls;
    private int mPosition;
    private ChangeOrReturnGoods goods;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_apply_change_goods);
        Injector.get(this).inject();
        initView();
        setListener();
    }

    private void setListener() {
        back.setOnClickListener(this);
        confirm.setOnClickListener(this);
        typeBack.setOnClickListener(this);
        typeReplace.setOnClickListener(this);
        minus.setOnClickListener(this);
        add.setOnClickListener(this);
        hideKeyBoardLayout.setOnClickListener(this);
    }

    private void initView() {
        if (getIntent().hasExtra("orderItem")) {
            orderItem = (ChangeOrReturnModel.ValueEntity) getIntent().getSerializableExtra("orderItem");
        }
        if (getIntent().hasExtra("updateGoods")) {
            goods = (ChangeOrReturnGoods) getIntent().getSerializableExtra("updateGoods");
        }
        if (orderItem == null) {
            finish();
            return;
        }
        setData();
        imgsRecycler.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        adapter = new AddGoodsRecyclerAdapter(mActivity, R.layout.item_group_add_pic);
        imgsRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        ChoosePhotoModel.getInstance().setMaxCount(3);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());
        if (goods != null) {
            updateView();
        }
    }

    private void updateView() {
        currentCount = goods.getAmt();
        goodsCount.setText("×" + goods.getAmt());
        count.setText(currentCount + "");
        minus.setImageResource(R.drawable.min_group_goods);
        if (goods.getType() == TYPE_BACK) {
            type = TYPE_BACK;
            typeBack.setBackgroundResource(R.drawable.shap_back_goods_bg);
            typeBack.setTextColor(mResource.getColor(R.color.white));
            typeReplace.setBackgroundResource(R.drawable.shap_gray_range_bg);
            typeReplace.setTextColor(mResource.getColor(R.color.color_3));
        } else {
            type = TYPE_REPLACE;
            typeBack.setBackgroundResource(R.drawable.shap_gray_range_bg);
            typeBack.setTextColor(mResource.getColor(R.color.color_3));
            typeReplace.setBackgroundResource(R.drawable.shap_back_goods_bg);
            typeReplace.setTextColor(mResource.getColor(R.color.white));
        }
        describe.setText(goods.getReason());
        mSelectFiles = ChoosePhotoModel.getInstance().getmCurrentPhotoList();
        String[] pictures = goods.getPaths().split(";");
        for (int i = 0; i < pictures.length; i++) {
            mSelectFiles.add(pictures[i]);
        }
    }

    private void setData() {
        goodsName.setText(orderItem.getOrderItem().getName());
        maxCount = orderItem.getOrderItem().getQuantity();
        goodsCount.setText("×" + orderItem.getOrderItem().getQuantity());
        if (orderItem.isCanChange()) {
            typeReplace.setVisibility(View.VISIBLE);
        } else {
            typeReplace.setVisibility(View.GONE);
        }
        if (orderItem.isCanReturn()) {
            typeBack.setVisibility(View.VISIBLE);
            type = TYPE_BACK;
        } else {
            typeBack.setVisibility(View.GONE);
            type = TYPE_REPLACE;
            typeReplace.setBackgroundResource(R.drawable.shap_back_goods_bg);
            typeReplace.setTextColor(mResource.getColor(R.color.white));
        }
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
        if (data.size() < 3) {
            data.add(new AddImage());
        }
        adapter.setData(data);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.apply_change_back:
                CommonUtils.hideKeyBoard2(v);
                onBackPressed();
                break;
            case R.id.hide_keyboard_layout:
                CommonUtils.hideKeyBoard2(v);
                break;
            case R.id.replace_confirm://确认按钮
                if (currentCount == 0) {
                    ToastUtils.displayMsg("退换商品数量不能为0", mActivity);
                    break;
                }
                if (CheckUtils.isEmptyList(mSelectFiles)) {
                    ToastUtils.displayMsg("请至少选择一张图片", mActivity);
                    break;
                }
                if (CheckUtils.isEmptyStr(describe.getText().toString().trim()) || describe.getText().length() < 10) {
                    ToastUtils.displayMsg("请至少输入10个字以上的问题描述", mActivity);
                    break;
                }
                CommonUtils.hideKeyBoard2(v);
                getQiniuUploadToken();
                break;
            case R.id.goods_back:
                CommonUtils.hideKeyBoard2(v);
                if (type == TYPE_BACK)
                    return;
                type = TYPE_BACK;
                typeBack.setBackgroundResource(R.drawable.shap_back_goods_bg);
                typeBack.setTextColor(mResource.getColor(R.color.white));
                typeReplace.setBackgroundResource(R.drawable.shap_gray_range_bg);
                typeReplace.setTextColor(mResource.getColor(R.color.color_3));
                break;
            case R.id.goods_replace:
                CommonUtils.hideKeyBoard2(v);
                if (type == TYPE_REPLACE)
                    break;
                type = TYPE_REPLACE;
                typeBack.setBackgroundResource(R.drawable.shap_gray_range_bg);
                typeBack.setTextColor(mResource.getColor(R.color.color_3));
                typeReplace.setBackgroundResource(R.drawable.shap_back_goods_bg);
                typeReplace.setTextColor(mResource.getColor(R.color.white));
                break;
            case R.id.rechange_goods_add:
                CommonUtils.hideKeyBoard2(v);
                if (currentCount >= maxCount) {
                    break;
                }
                minus.setImageResource(R.drawable.min_group_goods);
                currentCount++;
                count.setText(currentCount + "");
                break;
            case R.id.rechange_goods_minus:
                CommonUtils.hideKeyBoard2(v);
                if (currentCount == 0) {
                    break;
                }
                currentCount--;
                if (currentCount == 0) {
                    minus.setImageResource(R.drawable.min_group_goods_gray);
                }
                count.setText(currentCount + "");
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
                            commitComplete();
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

    private void commitComplete() {
        ChangeOrReturnGoods goods = new ChangeOrReturnGoods();
        goods.setOrderId(orderItem.getOrderId());
        goods.setOrderItemId(orderItem.getOrderItem().getId());
        goods.setPosition(orderItem.getPosition());
        goods.setType(type);
        goods.setAmt(currentCount);
        goods.setReason(describe.getText().toString().trim());
        goods.setImgs(appendImage());
        goods.setPaths(getPath());
        Intent intent = new Intent();
        intent.putExtra("goods", goods);
        setResult(100, intent);
        finish();
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
        return sb.toString();
    }

    private String getPath() {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mSelectFiles.size(); i++) {
            if (i == mSelectFiles.size() - 1) {
                sb.append(mSelectFiles.get(i));
            } else {
                sb.append(mSelectFiles.get(i) + ";");
            }
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChoosePhotoModel.getInstance().clear();
    }
}
