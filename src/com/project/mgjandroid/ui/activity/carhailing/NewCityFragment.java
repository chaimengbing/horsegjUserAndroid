package com.project.mgjandroid.ui.activity.carhailing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.inter_face.AreaClick;
import com.project.mgjandroid.ui.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCityFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private static NewCityFragment fragment;
    private AreaClick mListener;
    private TripCityListAdapter mAdapter;

    public NewCityFragment() {
        // Required empty public constructor
    }


    public static NewCityFragment newInstance() {
        if (fragment == null) {
            fragment = new NewCityFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.city_list_view);
        mAdapter = new TripCityListAdapter(R.layout.item_common_choose_area, mActivity);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onAreaClick(1, position);
    }

    public void setListener(AreaClick listener) {
        mListener = listener;
    }

    public void notifyList() {
        if (mAdapter != null) {
            mAdapter.setData(ChooseAreaActivity.mCitys);
        }
    }
}
