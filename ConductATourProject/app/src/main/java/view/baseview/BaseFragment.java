package view.baseview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import presenter.basepresenter.BasePresenter;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    private Unbinder unbinder;
    private View parentView;
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = initView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, parentView);
        mPresenter = createPresenter();
        initData();
        return parentView;
    }

    protected abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
