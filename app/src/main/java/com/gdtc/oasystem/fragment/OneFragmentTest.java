package com.gdtc.oasystem.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.gdtc.oasystem.R;
import com.gdtc.oasystem.base.TestBaseFragment;


/**
 * Created by fan on 2016/7/12.
 */
public class OneFragmentTest extends TestBaseFragment {

    @Override
    protected View bindLayout(LayoutInflater inflater) {
        return View.inflate(getContext(), R.layout.fragment_one,null);
    }

}
