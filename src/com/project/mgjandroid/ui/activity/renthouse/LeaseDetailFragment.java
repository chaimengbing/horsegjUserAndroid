package com.project.mgjandroid.ui.activity.renthouse;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.HouseLeaseInformation;
import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.RentHouseDetailModel;
import com.project.mgjandroid.model.information.InformationHealthModel;
import com.project.mgjandroid.model.information.InformationLeaseRentModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.information.InformationDetailActivity;
import com.project.mgjandroid.ui.activity.information.ReportActivity;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.pictureviewer.PictureViewActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaseDetailFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @InjectView(R.id.rent_house_view_pager)
    private ViewPager mViewPager;
    @InjectView(R.id.rent_house_name)
    private TextView tvName;
    @InjectView(R.id.rent_house_tag)
    private TextView tvTag;
    @InjectView(R.id.rent_house_price)
    private TextView tvPrice;
    @InjectView(R.id.rent_house_detail_time)
    private TextView tvTime;
    @InjectView(R.id.rent_house_jubao)
    private TextView tvReport;
    @InjectView(R.id.rent_house_detail_content)
    private TextView tvContent;
    @InjectView(R.id.rent_house_detail_contact)
    private TextView tvContact;
    @InjectView(R.id.rent_house_page_count)
    private TextView tvPageCount;
    @InjectView(R.id.top_banner)
    private RelativeLayout rlTop;
    @InjectView(R.id.rent_house_type)
    private TextView tvType;
    @InjectView(R.id.rent_house_location)
    private TextView tvLocation;
    @InjectView(R.id.rent_house_area_tip)
    private TextView tvAreaTip;
    @InjectView(R.id.rent_house_area)
    private TextView tvArea;

    private List<String> mImageUrls;
    private PhotoPagerAdapter mAdapter;
    private int mLength;
    private HouseLeaseInformation information;
    private MLoadingDialog mMLoadingDialog;

    public LeaseDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lease_detail, container, false);
        InjectorFragment.get(this).inject(view);
        initView();
        return view;
    }

    private void initView() {
        int secondHandType = ChooseCityModel.getInstance().getSecondHandType();
        if (secondHandType == 1) {
            rlTop.setVisibility(View.GONE);
            tvAreaTip.setVisibility(View.GONE);
            tvArea.setVisibility(View.GONE);
        }

        mImageUrls = new ArrayList<>();
        mAdapter = new PhotoPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

        mMLoadingDialog = new MLoadingDialog();
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("informationId", ((InformationDetailActivity) mActivity).getInformationId());
        map.put("informationType", ((InformationDetailActivity) mActivity).getInformationType());
        VolleyOperater<InformationLeaseRentModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    InformationLeaseRentModel model = (InformationLeaseRentModel) obj;
                    if (model.getCode() == 0) {
                        information = model.getValue();
                        information.setServiceTime(model.getServertime());
                        showData();
                    }
                }
            }
        }, InformationLeaseRentModel.class);
    }

    private void showData() {
        String imgs = information.getImgs();
        if (!TextUtils.isEmpty(imgs)) {
            String[] split = imgs.split(";");
            mLength = split.length;
            tvPageCount.setText("1/" + mLength);
            for (int i = 0; i < split.length; i++) {
                mImageUrls.add(split[i]);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            rlTop.setVisibility(View.GONE);
        }
        tvName.setText(information.getTitle());
        tvTag.setText(information.getCategoryName());
        tvPrice.setText(StringUtils.getPrice(mActivity, StringUtils.money2Str(information.getAmt(), "元/月")));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        tvTime.setText("发布时间：" + format.format(information.getCreateTime()));
        tvTime.setText("发布时间：" + DateUtils.format(information.getModifyTime(), information.getServiceTime()));
        tvContent.setText(information.getDescription());
        tvReport.setOnClickListener(this);
        tvContact.setOnClickListener(this);

        tvType.setText(information.getHouseType());
        tvLocation.setText(information.getSectorArea());
        String houseArea = information.getHouseArea() + "";
        houseArea = StringUtils.BigDecimal2Str(BigDecimal.valueOf(Double.parseDouble(houseArea)));
        tvArea.setText(houseArea + "㎡");
        if (information.getIsExpire() != 1 && information.getStatus() == 2) {
            ((InformationDetailActivity) getActivity()).canShare(true);
        } else {
            ((InformationDetailActivity) getActivity()).canShare(false);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvPageCount.setText((position + 1) + "/" + mLength);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class PhotoPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageUtils.loadBitmap(mActivity, mImageUrls.get(position), imageView, R.drawable.house_lease_default, "");
            imageView.setTag(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PictureViewActivity.toViewPicture(mActivity, JSONArray.toJSONString(mImageUrls), (int) (v.getTag()));
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rent_house_jubao:
                if (App.isLogin()) {
                    ReportActivity.toReport(mActivity, ((InformationDetailActivity) mActivity).getInformationId(), ((InformationDetailActivity) mActivity).getInformationType());
                    break;
                } else {
                    startActivity(new Intent(mActivity, SmsLoginActivity.class));
                }
                break;
            case R.id.rent_house_detail_contact:
                if (information != null)
                    findInformationFreeStandardList(information.getUserId(), information.getId(), information.getAgentId(), InformationType.Lease.getValue(), information.getCategoryId(), 3);
                break;
        }
    }

    @Override
    public InformationBaseProperty getInformation() {
        return information;
    }
}
