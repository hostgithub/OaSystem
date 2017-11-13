package com.gdtc.oasystem.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.TestBaseFragment;

/**
 * Created by fan on 2016/7/12.
 */
public class TwoFragmentTest extends TestBaseFragment {

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return View.inflate(getContext(), R.layout.fragment_two,null);
//    }

    @Override
    protected View bindLayout(LayoutInflater inflater) {
        return View.inflate(getContext(), R.layout.fragment_two,null);
    }
}
