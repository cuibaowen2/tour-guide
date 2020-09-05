package view.homepage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidkun.xtablayout.XTabLayout;
import com.conductatour.pack.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import common.LogUtils;
import common.customview.CustomScrollViewPager;
import presenter.CulturalAudioPlayPresenter;
import view.baseview.BaseFragment;
import view.homepage.adapter.CulturalExplaionAdapter;

/**
 * 文化语音播放界面
 */
public class CulturalAudioPlayFragment extends BaseFragment<CulturalAudioPlayPresenter> implements CulturalAudioPlayView {
    public static CulturalAudioPlayFragment newInstance(String param1) {
        CulturalAudioPlayFragment fragment = new CulturalAudioPlayFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * View
     */
    @BindView(R.id.titles_table_layout)
    XTabLayout mTitleTableLayouot;

    @BindView(R.id.fragments_viewpage)
    CustomScrollViewPager mFragmentsViewPage;

    /**
     * 变量
     */
    private Bundle savedInstanceState;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    /**
     * 适配器
     */
    private CulturalExplaionAdapter culturalExplaionAdapter;

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        return inflater.inflate(R.layout.fragment_cultural_audio_play, container, false);
    }

    @Override
    protected void initData() {
        LogUtils.d("CulturalAudioPlayFragment-----initData");
        mFragmentsViewPage.setScrollable(false);
        culturalExplaionAdapter = new CulturalExplaionAdapter(getChildFragmentManager());
        if (fragments.size() == 0) {
            fragments.add(new IntelligentInterpretationFragment());
            fragments.add(new ArtificialInterpretationFragment());

            titles.add("智能讲解");
            titles.add("人工讲解");
        }
        culturalExplaionAdapter.setFragments(fragments);
        culturalExplaionAdapter.setTitles(titles);
        mFragmentsViewPage.setAdapter(culturalExplaionAdapter);
        mTitleTableLayouot.setupWithViewPager(mFragmentsViewPage);
        mTitleTableLayouot.setxTabDisplayNum(2);
        // 初始化获取当前用户角色的
        mPresenter.initUserRole();
    }

    @Override
    protected CulturalAudioPlayPresenter createPresenter() {
        return new CulturalAudioPlayPresenter(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 用户角色信息：根据用户角色来初始化用户讲解界面的展示
     *
     * @param userRole 用户角色
     */
    @Override
    public void showUserRole(String userRole) {

    }
}
