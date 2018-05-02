package com.project.mgjandroid.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GetQiNiuTokenModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.FileUtils;
import com.project.mgjandroid.utils.MLog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/11/11.
 */

public class ImageUploadActivity extends BaseActivity implements View.OnClickListener {

    private static final int MY_TAKE_PICTURE = 6601;
    private static final int MY_PICK_PICTURE = 6602;
    private static final int MY_CROP_PICTURE = 6603;

    protected Dialog avatarDialog;
    protected File file;
    protected File mFile;
    protected UploadManager uploadManager;
    protected Bitmap bitmap;
    protected String avatarUrl = "";
    protected CornerImageView ivAvatar;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_pick_photo) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, MY_PICK_PICTURE);
            avatarDialog.dismiss();
        } else if (v.getId() == R.id.btn_take_photo) {
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            FileUtils.createDirFile(Constants.IMG_PATH);
            String path = Constants.IMG_PATH + CommonUtils.generateUUID() + ".jpg";
            file = FileUtils.createNewFile(path);
            if (file == null) {
                return;
            }
            Uri cameraUri = Uri.fromFile(file);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
            startActivityForResult(openCameraIntent, MY_TAKE_PICTURE);
            avatarDialog.dismiss();
        } else if (v.getId() == R.id.iv_avatar || v.getId() == R.id.tv_take_photo) {
            initAvatarDialog();
        }
    }

    /**
     * 选择从相册还是拍照获取头像
     */
    private void initAvatarDialog() {
        avatarDialog = new Dialog(this, R.style.fullDialog);
        RelativeLayout contentView = (RelativeLayout) View.inflate(this, R.layout.pick_or_take_photo_dialog, null);
        Button dialog_bt_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
        Button dialog_bt_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
        Button dialog_bt_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
        dialog_bt_pick_photo.setOnClickListener(this);
        dialog_bt_take_photo.setOnClickListener(this);
        dialog_bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarDialog.dismiss();
            }
        });
        avatarDialog.setContentView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        avatarDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MY_TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri uri = Uri.fromFile(file);
                    startImageAction(uri, DipToPx.dip2px(mActivity, 100), DipToPx.dip2px(mActivity, 100), MY_CROP_PICTURE, true);
                }
                break;
            case MY_PICK_PICTURE:
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        toast("SD不可用");
                        return;
                    }
                    Uri uri = data.getData();
                    startImageAction(uri, DipToPx.dip2px(mActivity, 100), DipToPx.dip2px(mActivity, 100), MY_CROP_PICTURE, true);
                } else {
                    toast("照片获取失败");
                }
                break;
            case MY_CROP_PICTURE:
                if (data == null) {
                    return;
                } else {
                    saveCropAvator(data);
                }
                break;
            default:
                break;
        }
    }

    private void startImageAction(Uri uri, int outputX, int outputY, int requestCode, boolean isCrop) {
        Intent intent;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        String path = Constants.IMG_PATH + CommonUtils.generateImgID() + ".jpg";
        mFile = FileUtils.createNewFile(path);
        if (mFile == null) {
            return;
        }
        Uri cameraUri = Uri.fromFile(mFile);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, requestCode);
    }

    private void saveCropAvator(Intent data) {
        Bundle extras = data.getExtras();
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(mFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            //在这上传照片
            ivAvatar.setImageBitmap(bitmap);
            getQiniuUploadToken();
        }
        /*if (extras != null) {
            try {
                bitmap = extras.getParcelable("data");
            }catch (Exception e){
                MLog.d("exception");
            }
            if (bitmap != null){
                //在这上传照片
                MLog.d("not null");
                ivAvatar.setImageBitmap(bitmap);
                getQiniuUploadToken();
            }else{
                MLog.d("null");
            }
            if (bitmap != null && bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }else{
            MLog.d("ex null");
        }*/
    }

    protected void getQiniuUploadToken() {
        Map<String, Object> map = new HashMap<>();
        VolleyOperater<GetQiNiuTokenModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_GET_QINIU_TOKEN, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    GetQiNiuTokenModel getQiNiuTokenModel = (GetQiNiuTokenModel) obj;
                    uploadPicture(getQiNiuTokenModel.getValue().getToken(), getQiNiuTokenModel.getValue().getPath());
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

        startUpload(token, mFile, uploadOptions, path);
    }

    protected void startUpload(String token, final File file, UploadOptions uploadOptions, final String path) {
        if (uploadManager == null) {
            Configuration config = new Configuration.Builder()
                    .connectTimeout(15) // 链接超时。默认 10秒
                    .responseTimeout(60) // 服务器响应超时。默认 60秒
                    .build();
            uploadManager = new UploadManager(config);
        }
        uploadManager.put(BitmapUtil.Bitmap2Bytes(bitmap), getImgUUID(file.getAbsolutePath()), token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                MLog.i("qiniu:" + info.toString());
                if (info.isOK()) {
                    avatarUrl = path + key;
                    MLog.i("qiniu:" + info.toString());
                } else {
                    toast("头像上传失败");
                }
            }
        }, uploadOptions);
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

}
