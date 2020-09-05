package view.homepage.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.conductatour.pack.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import common.CommonUtils;
import common.LogUtils;
import common.ToastUtils;
import common.customview.BaseDialog;
import common.customview.FullyLinearLayoutManager;
import common.customview.IOSScrollView;
import model.ScenicSpotModel;
import model.TourGuideModel;
import presenter.ArtificialInterpretationPresenter;
import view.baseview.BaseFragment;
import view.homepage.adapter.ScenicSpotAdapter;

import static android.view.ViewGroup.FOCUS_BEFORE_DESCENDANTS;

/**
 * 人工讲解
 * 游客界面功能
 */
public class ArtificialInterpretationFragment extends BaseFragment<ArtificialInterpretationPresenter> implements ArtificialInterpretationView, BaseDialog.DialogClickListener {
    /**
     * View
     */
    @BindView(R.id.artificial_interpretation_mapview)
    MapView mArtificialMapview;

    @BindView(R.id.scenic_spot_lists)
    RecyclerView mScenicSpotLists;

    @BindView(R.id.ll_call_tour_guide)
    LinearLayout mLlCallTourGuide;

    @BindView(R.id.include_interpretation_bid)
    View mIncludeInterpretationBid;

    @BindView(R.id.include_tour_guide_infor)
    View mIncludeTourGuideInfor;

    // 呼叫导游界面景点
    @BindView(R.id.recycle_calltourist_attractions)
    RecyclerView mRecycleCalltouristAttractions;

    @BindView(R.id.speaking_progress)
    CheckBox mSpeakingProgress;

    @BindView(R.id.speak_progress)
    CheckBox mSpeakProgress;

    @BindView(R.id.tourists_number_low)
    CheckBox mTouristsNumberLow;

    @BindView(R.id.tourists_number_medium)
    CheckBox mTouristsNumberMedium;

    @BindView(R.id.tourists_number_hight)
    CheckBox mTouristsNumberHight;


    /**
     * 变量
     */
    private Bundle savedInstanceState;
    private BaiduMap mBaiduMap;
    private ScenicSpotAdapter scenicSpotAdapter;
    private BaseDialog cancelOrderDialog;


    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        return inflater.inflate(R.layout.fragment_artificial_interpretation, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initData() {
        // recycleview初始化动作
        FullyLinearLayoutManager callBeforManager = new FullyLinearLayoutManager(getContext());
        callBeforManager.setOrientation(RecyclerView.VERTICAL);
        mScenicSpotLists.setLayoutManager(callBeforManager);
        FullyLinearLayoutManager callEndManager = new FullyLinearLayoutManager(getContext());
        callEndManager.setOrientation(RecyclerView.VERTICAL);
        mRecycleCalltouristAttractions.setLayoutManager(callEndManager);

    }

    @Override
    public void onResume() {
        super.onResume();
        mArtificialMapview.onResume();
        mBaiduMap = mArtificialMapview.getMap();
        mBaiduMap.setIndoorEnable(true);
        mBaiduMap.setMyLocationEnabled(true);

        // 定位到当前位置
        mPresenter.locationPosition(getContext());

        // 获取接单导游信息
        mPresenter.getOrderTourGuideInfo();

        // 获取景区景点信息
        mPresenter.getScenicSpotDatas();
    }

    @Override
    protected ArtificialInterpretationPresenter createPresenter() {
        return new ArtificialInterpretationPresenter(getContext(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLocationPosition(BDLocation location) {
        LogUtils.d("showLocationPosition-----定位信息");
        if (location == null || mBaiduMap == null) {
            return;
        }

        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    public void showScenicSpotDatas(List<ScenicSpotModel> scenicSpotModels) {
        scenicSpotAdapter = new ScenicSpotAdapter();
        mScenicSpotLists.setAdapter(scenicSpotAdapter);
        mRecycleCalltouristAttractions.setAdapter(scenicSpotAdapter);
        mPresenter.setInitAdapter(scenicSpotAdapter);
        scenicSpotAdapter.flushDatas(scenicSpotModels);
    }

    /**
     * 景区接单导游地图上显示
     */
    @Override
    public void showOrderTourGuideInfo(List<LatLng> overlayOptions) {
        View parentView = null;
        BitmapDescriptor bdGround = null;
        MarkerOptions markerOptions = null;
        for (LatLng options : overlayOptions) {
            parentView = LayoutInflater.from(getContext()).inflate(R.layout.view_order_tour_guide_infor, null);
            markerOptions = new MarkerOptions();
            markerOptions.position(options);
            bdGround = BitmapDescriptorFactory.fromBitmap(CommonUtils.getViewBitmap(parentView));
            markerOptions.icon(bdGround);
            mBaiduMap.addOverlay(markerOptions);
        }
    }


    /**
     * 所有的点击事件
     */
    @OnClick({R.id.call_tour_guide, R.id.relocation, R.id.button_cancel_call, R.id.callup_tour_guide, R.id.send_message_tour_guide, R.id.reposition_tour_guide})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.call_tour_guide:
                // 呼叫导游
                mIncludeTourGuideInfor.setVisibility(View.VISIBLE);
                mIncludeInterpretationBid.setVisibility(View.GONE);
                break;
            case R.id.relocation:
                // 重新定位
                ToastUtils.centerToas(getActivity(), "重新定位");
                mPresenter.locationPosition(getContext());
                break;
            case R.id.button_cancel_call:
                // 取消订单
                cancelOrderDialog = new BaseDialog(getActivity());
                cancelOrderDialog.showDialog("确认取消订单吗？", "cancelOrderDialog");
                cancelOrderDialog.setDialogClickListener(this);
                break;
            case R.id.callup_tour_guide:
                // 给导游打电话
                ToastUtils.centerToas(getContext(), "给导游打电话");
                break;
            case R.id.send_message_tour_guide:
                ToastUtils.centerToas(getContext(), "给导发消息");
                break;
            case R.id.reposition_tour_guide:
                ToastUtils.centerToas(getActivity(), "重新定位游客位置");
                mPresenter.locationPosition(getContext());
                break;
            default:
                break;
        }
    }

    /**
     * checkbox选择事件
     */
    @OnCheckedChanged({R.id.speaking_progress, R.id.speak_progress, R.id.tourists_number_low, R.id.tourists_number_medium, R.id.tourists_number_hight})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.speaking_progress:
                // 加入正在讲解的团
                if (isChecked) {
                    ToastUtils.centerToas(getContext(), "选择正在讲解的团");
                    mSpeakProgress.setChecked(false);
                    mSpeakingProgress.setChecked(true);
                } else {
                    mSpeakProgress.setChecked(true);
                    mSpeakingProgress.setChecked(false);
                }
                break;
            case R.id.speak_progress:
                // 加入未讲解的团
                if (isChecked) {
                    ToastUtils.centerToas(getContext(), "选择未讲解的团");
                    mSpeakingProgress.setChecked(false);
                    mSpeakProgress.setChecked(true);
                } else {
                    mSpeakingProgress.setChecked(true);
                    mSpeakProgress.setChecked(false);
                }
                break;
            case R.id.tourists_number_low:
                // 团队人数低
                if (isChecked) {
                    mTouristsNumberLow.setChecked(true);
                    mTouristsNumberMedium.setChecked(false);
                    mTouristsNumberHight.setChecked(false);
                } else {
                    mTouristsNumberLow.setChecked(false);
                }
                break;
            case R.id.tourists_number_medium:
                // 团队人数中等
                if (isChecked) {
                    mTouristsNumberLow.setChecked(false);
                    mTouristsNumberMedium.setChecked(true);
                    mTouristsNumberHight.setChecked(false);
                } else {
                    mTouristsNumberMedium.setChecked(false);
                }
                break;
            case R.id.tourists_number_hight:
                // 团队人数高
                if (isChecked) {
                    mTouristsNumberLow.setChecked(false);
                    mTouristsNumberMedium.setChecked(false);
                    mTouristsNumberHight.setChecked(true);
                } else {
                    mTouristsNumberHight.setChecked(false);
                }
                break;
        }
    }

    @Override
    public void onDialogClickListener(View view, String tagStr) {
        switch (tagStr) {
            case "cancelOrderDialog":
                mIncludeTourGuideInfor.setVisibility(View.GONE);
                mIncludeInterpretationBid.setVisibility(View.VISIBLE);
                mPresenter.getScenicSpotDatas();
                break;
            default:
                break;
        }
    }
}
