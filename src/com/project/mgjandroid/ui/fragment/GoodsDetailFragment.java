package com.project.mgjandroid.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GoodsEvaluateModel;
import com.project.mgjandroid.model.NewGoodsEvaluateModel;
import com.project.mgjandroid.model.NewMerchantEvaluateModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.EvaluateListActivity;
import com.project.mgjandroid.ui.adapter.GoodsDetailListAdapter;
import com.project.mgjandroid.ui.view.HeaderViewPagerFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsDetailFragment extends HeaderViewPagerFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    private ListView mListView;
    private GoodsDetailListAdapter mListAdapter;
    private int currentSection = 0;
    private int maxResults = 3;
    private ArrayList<NewGoodsEvaluateModel.ValueBean.ListBean> data;
    private Goods mGoods;
    private boolean hasMoreData = false;
    private int queryType = 0;
    private int isHaveContent = 1;
    private TextView tvCount;
    private RadioButton tvAll;
    private RadioButton tvSatisfy;
    private RadioButton tvYawp;
    private RadioButton tvHavePicturess;
    private TextView tvUnEmpty;

    public GoodsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods_detail, container, false);
        mListView = (ListView) view.findViewById(R.id.goods_detail_list_view);
        mListAdapter = new GoodsDetailListAdapter(R.layout.item_goods_detail_list_view, getActivity());
        View headerView = inflater.inflate(R.layout.header_view_goods_detail_list_view, null);
        TextView tvDes = (TextView) headerView.findViewById(R.id.goods_detail_content);
        TextView tvEva = (TextView) headerView.findViewById(R.id.goods_detail_evaluate);
        tvCount = (TextView) headerView.findViewById(R.id.goods_evalute_count);
        RelativeLayout layoutEva = (RelativeLayout)headerView.findViewById(R.id.evaluate_layout);
        layoutEva.setOnClickListener(this);

        RadioGroup rgLabel = (RadioGroup) headerView.findViewById(R.id.select_bar);
        rgLabel.setOnCheckedChangeListener(this);
        tvAll = (RadioButton) headerView.findViewById(R.id.evaluate_fragment_all);
        tvAll.setChecked(true);
        tvSatisfy = (RadioButton) headerView.findViewById(R.id.evaluate_fragment_satisfy);
        tvSatisfy.setTextColor(Color.parseColor("#ffdc550f"));
        tvYawp = (RadioButton) headerView.findViewById(R.id.evaluate_fragment_yawp);
        tvYawp.setTextColor(Color.parseColor("#ffBFBFBF"));
        tvHavePicturess = (RadioButton) headerView.findViewById(R.id.evaluate_fragment_have_pictures);
        tvHavePicturess.setTextColor(Color.parseColor("#ffdc550f"));
        tvUnEmpty = (TextView) headerView.findViewById(R.id.evaluate_fragment_show_un_empty);
        tvUnEmpty.setOnClickListener(this);
        tvUnEmpty.setSelected(true);
        mListView.addHeaderView(headerView);
        data = new ArrayList<>();
        mListView.setAdapter(mListAdapter);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("goods")) {
            mGoods = (Goods) arguments.getSerializable("goods");
            tvDes.setText(mGoods.getDescription());
//            tvEva.setText(getString(R.string.gool_detail_header_good_evaluate) + "  (" + mGoods.getCommentNum() + ")");
            int number = (int) (mGoods.getCommentScore().floatValue() * 100 / 5.0);
            if (mGoods.getCommentNum() != 0) {
                tvEva.setText(getString(R.string.gool_detail_header_good_evaluate) + "  (" + " 好评率" + number + "%)");
            } else {
                tvEva.setText(getString(R.string.gool_detail_header_good_evaluate));
            }
            getGoodsEvaluate();
        }

        return view;
    }

    @Override
    public View getScrollableView() {
        return mListView;
    }

    private void getGoodsEvaluate() {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", mGoods.getId());
        map.put("start", currentSection);
        map.put("size", maxResults);
        map.put("queryType", queryType);
        map.put("isHaveContent", isHaveContent);
        VolleyOperater<NewGoodsEvaluateModel> operater = new VolleyOperater<>(getActivity());
        operater.doRequest(Constants.URL_QUERY_GOODS_EVALUATE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    NewGoodsEvaluateModel goodsEvaluateModel = (NewGoodsEvaluateModel) obj;
                    List<NewGoodsEvaluateModel.ValueBean.ListBean> list = goodsEvaluateModel.getValue().getList();
                    data.addAll(list);
                    mListAdapter.setData(data);
                    tvCount.setText(goodsEvaluateModel.getValue().getAllCount()+"条评价");
                    setRadioGroup(goodsEvaluateModel);
                }
            }
        }, NewGoodsEvaluateModel.class);
    }

    private void setRadioGroup(NewGoodsEvaluateModel model) {
        if (model != null) {
            tvAll.setText("全部 " + model.getValue().getAllCount());
            tvSatisfy.setText("好评 " + model.getValue().getGoodCount());
            tvYawp.setText("差评 " + model.getValue().getPoorCount());
            tvHavePicturess.setText("有图 " + model.getValue().getImgCount());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluate_fragment_show_un_empty:
                if (tvUnEmpty.isSelected()) {
                    isHaveContent = 0;
                } else {
                    isHaveContent = 1;
                }
                tvUnEmpty.setSelected(!tvUnEmpty.isSelected());
                getGoodsEvaluate();
                //TODO 刷新列表
                break;
            case R.id.evaluate_layout:
                Intent intent = new Intent(getActivity(), EvaluateListActivity.class);
                intent.putExtra("goodsId",mGoods.getId());
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.evaluate_fragment_all:
                queryType = 0;
                changeTextColor(tvAll, tvSatisfy, tvYawp, tvHavePicturess);
                tvYawp.setTextColor(Color.parseColor("#ffBFBFBF"));
                if (!tvAll.isSelected()) {
                    currentSection = 0;
                    data.clear();
                    getGoodsEvaluate();
                }
                break;
            case R.id.evaluate_fragment_satisfy:
                queryType = 1;
                changeTextColor(tvSatisfy, tvAll, tvYawp, tvHavePicturess);
                tvYawp.setTextColor(Color.parseColor("#ffBFBFBF"));
                if (!tvSatisfy.isSelected()) {
                    currentSection = 0;
                    data.clear();
                    getGoodsEvaluate();
                }
                break;
            case R.id.evaluate_fragment_yawp:
                queryType = 2;
                changeTextColor(tvYawp, tvSatisfy, tvAll, tvHavePicturess);
                if (!tvYawp.isSelected()) {
                    currentSection = 0;
                    data.clear();
                    getGoodsEvaluate();
                }
                break;
            case R.id.evaluate_fragment_have_pictures:
                queryType = 3;
                changeTextColor(tvHavePicturess, tvSatisfy, tvYawp, tvAll);
                tvYawp.setTextColor(Color.parseColor("#ffBFBFBF"));
                if (!tvHavePicturess.isSelected()) {
                    currentSection = 0;
                    data.clear();
                    getGoodsEvaluate();
                }
                break;
        }
    }

    private void changeTextColor(RadioButton tvAll, RadioButton tvSatisfy, RadioButton tvYawp, RadioButton tvHavePicturess) {
        tvAll.setTextColor(getResources().getColor(R.color.white));
        tvSatisfy.setTextColor(Color.parseColor("#ffdc550f"));
        tvYawp.setTextColor(Color.parseColor("#ffdc550f"));
        tvHavePicturess.setTextColor(Color.parseColor("#ffdc550f"));
    }
}
