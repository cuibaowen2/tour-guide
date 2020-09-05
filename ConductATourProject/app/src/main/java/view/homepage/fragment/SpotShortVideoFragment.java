package view.homepage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.conductatour.pack.R;

import presenter.basepresenter.BasePresenter;
import view.baseview.BaseFragment;

public class SpotShortVideoFragment extends BaseFragment {

    public static SpotShortVideoFragment newInstance(String param1) {
        SpotShortVideoFragment fragment = new SpotShortVideoFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spot_short_vido, container, false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
