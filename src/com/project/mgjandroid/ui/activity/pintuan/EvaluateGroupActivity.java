package com.project.mgjandroid.ui.activity.pintuan;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.BaseRecyclerAdapter;
import com.project.mgjandroid.bean.AddImage;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GetQiNiuTokenModel;
import com.project.mgjandroid.model.ServiceTypeModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.PhotoActivity;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by User_Cjh on 2016/8/16.
 */
public class EvaluateGroupActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener, TextWatcher {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.next_setup)
    private TextView tvSubmit;
    @InjectView(R.id.evaluate_group_photo)
    private RecyclerView rvGoodsImgs;
    @InjectView(R.id.group_evaluate_content)
    private EditText etContent;
    @InjectView(R.id.group_evaluate_content_indicator)
    private TextView tvIndicator;
    @InjectView(R.id.total_evaluate)
    private RatingBar rbTotal;
    @InjectView(R.id.goods_evaluate)
    private RatingBar rbGoods;
    @InjectView(R.id.service_evaluate)
    private RatingBar rbService;

    private static final int MY_TAKE_PICTURE = 200;
    private AddGoodsRecyclerAdapter adapter;
    private ArrayList<String> mSelectFiles;
    private String groupBuyOrderId;
    private String groupBuyId;
    private File file;
    private PopupWindow mPopupWindow;
    private MaterialDialog mMaterialDialog;
    private int mPosition;
    private ArrayList<String> imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_group);
        Injector.get(this).inject();

        initView();
        setListener();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
            case R.id.next_setup:
                CommonUtils.hideKeyBoard2(v);
                if (etContent.getText().toString().trim().length() < 15) {
                    ToastUtils.displayMsg("评价最少为15字哦", mActivity);
                    break;
                }
                if (mSelectFiles == null || mSelectFiles.size() == 0) {
                    submitEvaluate();
                } else {
                    getQiniuUploadToken();
                }
                break;
        }
    }

    private void submitEvaluate() {
        VolleyOperater<ServiceTypeModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("groupBuyId", groupBuyId);
        map.put("groupBuyOrderId", groupBuyOrderId);
        map.put("groupBuyOrderId", groupBuyOrderId);
        map.put("compositeScore", rbTotal.getRating());//总体
        map.put("goodsScore", rbGoods.getRating());//商品
        map.put("serviceScore", rbService.getRating());//服务
        map.put("groupBuyScoreComments", etContent.getText().toString().trim());//内容

        if (CheckUtils.isNoEmptyList(mSelectFiles)) {
            map.put("imgs", appendImage());
        }

        operater.doRequest(Constants.URL_CREATE_GROUP_COMMENT, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast("获取数据失败");
                        return;
                    }
                    finish();
                }
            }
        }, ServiceTypeModel.class);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        etContent.addTextChangedListener(this);
        rbTotal.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 1) {
                    ratingBar.setRating(1);
                }
            }
        });
        rbGoods.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 1) {
                    ratingBar.setRating(1);
                }
            }
        });
        rbService.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 1) {
                    ratingBar.setRating(1);
                }
            }
        });
    }

    private void initView() {
        tvTitle.setText("评价晒单");
        if (getIntent().hasExtra("groupBuyOrderId") && getIntent().hasExtra("groupBuyId")) {
            groupBuyOrderId = getIntent().getStringExtra("groupBuyOrderId");
            groupBuyId = getIntent().getStringExtra("groupBuyId");

            setView();
        } else {
            finish();
            return;
        }
    }

    private void setView() {
        rvGoodsImgs.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        adapter = new AddGoodsRecyclerAdapter(mActivity, R.layout.item_group_add_pic);
        rvGoodsImgs.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        ChoosePhotoModel.getInstance().setMaxCount(6);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());

        rbTotal.setRating(5);
        rbGoods.setRating(5);
        rbService.setRating(5);
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
        if (data.size() < 6) {
            data.add(new AddImage());
        }
        adapter.setData(data);
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            tvIndicator.setText(s.length() + "/500字");
        } else {
            tvIndicator.setText("0/500字");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

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
                            submitEvaluate();
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
        StringBuffer sbPath = new StringBuffer("");
        for (int i = 0; i < imageUrls.size(); i++) {
            if (i == imageUrls.size() - 1) {
                sb.append(imageUrls.get(i));
            } else {
                sb.append(imageUrls.get(i) + ";");
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
}
