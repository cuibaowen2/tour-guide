package view.baseview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import presenter.basepresenter.BasePresenter;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    private Unbinder unbinder;
    protected P mPresenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int viewId = initView();
        setContentView(viewId);
        unbinder = ButterKnife.bind(this);
        mPresenter = createPresenter();
        setNavigationBarColor();
        initData(savedInstanceState);
    }

    protected abstract int initView();

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != unbinder) {
            unbinder.unbind();
        }

        //页面销毁时取消presenter绑定
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setNavigationBarColor() {
        //添加变色标志
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //导航栏颜色
        getWindow().setNavigationBarColor(Color.parseColor("#fcfcfc"));
        //状态栏颜色
        getWindow().setStatusBarColor(Color.parseColor("#fcfcfc"));

    }

}
