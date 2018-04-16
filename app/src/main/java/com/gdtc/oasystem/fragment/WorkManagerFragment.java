package com.gdtc.oasystem.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangjiawei on 2018-4-16.
 */

public class WorkManagerFragment extends BaseFragment {

    private Unbinder mUnbinder;
    @BindView(R.id.mGridView)
    GridView mGridView;

    private SimpleAdapter simpleAdapter;

    private int[] icon = {R.mipmap.icon_huiyi, R.mipmap.icon03_06, R.mipmap.icon03_07,
            R.mipmap.icon03_08, R.mipmap.icon03_09, R.mipmap.icon03_10,R.mipmap.icon03_13,
            R.mipmap.icon03_07, R.mipmap.icon03_15, R.mipmap.icon03_16};

    private String[] iconName = {"会议通知", "行政待批", "发文待批", "收文待批", "已批工作", "待批工作", "收文办理", "发文办理", "政务申请","内网网站"};
    private List<Map<String, Object>> data_list;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_work_manager;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);

        data_list = new ArrayList<Map<String, Object>>();

        getData();
        simpleAdapter = new SimpleAdapter(getActivity(),
                data_list,
                R.layout.item_work_manager,
                new String[]{"ItemImage", "ItemText"},
                new int[]{R.id.image, R.id.text});

        mGridView.setAdapter(simpleAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initLoadData() {
    }

    @Override
    protected void lazyFetchData() {

    }

    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", icon[i]);
            map.put("ItemText", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

}
