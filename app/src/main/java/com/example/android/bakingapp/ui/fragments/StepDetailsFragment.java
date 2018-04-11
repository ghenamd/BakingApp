package com.example.android.bakingapp.ui.fragments;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.StepDetailsFragmentBinding;

public class StepDetailsFragment extends Fragment {
    private StepDetailsFragmentBinding mBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater,R.layout.step_details_fragment,container,false);
        View view = mBinding.getRoot();
        return view;
    }
}
