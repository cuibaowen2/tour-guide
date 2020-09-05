package view.homepage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.conductatour.pack.R;

import butterknife.BindView;
import common.LogUtils;
import common.permissionmanager.PermissionListener;
import common.permissionmanager.PermissionsUtil;
import presenter.basepresenter.BasePresenter;
import view.baseview.BaseActivity;
import view.homepage.fragment.CulturalAudioPlayFragment;
import view.homepage.fragment.MeFragment;
import view.homepage.fragment.SpotShortVideoFragment;

public class HomePageActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        return intent;
    }

    /**
     * View
     */
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    /**
     * 变量
     */
    private int lastSelectedPosition = 0;
    private CulturalAudioPlayFragment mCulturalAudioPlayFragment;
    private SpotShortVideoFragment mSpotShortVideoFragment;
    private MeFragment mMeFragment;

    @Override
    protected int initView() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_location_on_white_24dp, "文化讲解").setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.ic_find_replace_white_24dp, "短视频").setActiveColor(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_favorite_white_24dp, "我").setActiveColor(R.color.green))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(this);
        initFragments();
        changeFragments(lastSelectedPosition);

        initLocationPermission();
    }

    private void initFragments() {
        if (mCulturalAudioPlayFragment == null) {
            mCulturalAudioPlayFragment = CulturalAudioPlayFragment.newInstance("文化语音");
        }
        if (mSpotShortVideoFragment == null) {
            mSpotShortVideoFragment = SpotShortVideoFragment.newInstance("短视频");
        }
        if (mMeFragment == null) {
            mMeFragment = MeFragment.newInstance("我");
        }
    }

    private void initLocationPermission() {
        PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                Toast.makeText(HomePageActivity.this, "访问定位权限", Toast.LENGTH_LONG).show();
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                Toast.makeText(HomePageActivity.this, "用户拒绝了访问定位权限", Toast.LENGTH_LONG).show();
            }
        }, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onTabSelected(int position) {
        changeFragments(position);
    }

    private void changeFragments(int position) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:

                transaction.replace(R.id.fragment_position, mCulturalAudioPlayFragment, "mCulturalAudioPlayFragment");
                break;
            case 1:

                transaction.replace(R.id.fragment_position, mSpotShortVideoFragment, "mSpotShortVideoFragment");
                break;
            case 2:

                transaction.replace(R.id.fragment_position, mMeFragment, "mMeFragment");
                break;
            default:
                break;

        }
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
