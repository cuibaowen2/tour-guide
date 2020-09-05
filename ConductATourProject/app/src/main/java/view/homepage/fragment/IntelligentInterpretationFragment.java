package view.homepage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.maps.MapView;
import com.conductatour.pack.R;

import butterknife.BindView;
import presenter.IntelligentInterpretationPresenter;
import view.baseview.BaseFragment;

/**
 * 智能讲解
 */
public class IntelligentInterpretationFragment extends BaseFragment<IntelligentInterpretationPresenter> implements IntelligentInterpretationView {
    /**
     * View
     */
    @BindView(R.id.intelligent_interpretation_mapview)
    MapView mIntelligentMapview;

    /**
     * 变量
     */
    private Bundle savedInstanceState;

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        return inflater.inflate(R.layout.fragment_intelligent_interpretation, container, false);
    }

    @Override
    protected void initData() {
        mIntelligentMapview.onCreate(savedInstanceState);
    }

    @Override
    protected IntelligentInterpretationPresenter createPresenter() {
        return new IntelligentInterpretationPresenter(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
