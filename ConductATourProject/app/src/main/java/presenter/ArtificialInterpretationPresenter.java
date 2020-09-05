package presenter;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.conductatour.pack.R;

import java.util.ArrayList;
import java.util.List;

import common.LogUtils;
import common.ToastUtils;
import model.ScenicSpotModel;
import model.TourGuideModel;
import presenter.basepresenter.BasePresenter;
import view.homepage.adapter.ScenicSpotAdapter;
import view.homepage.fragment.ArtificialInterpretationView;

/**
 * 人工讲解
 */
public class ArtificialInterpretationPresenter extends BasePresenter<ArtificialInterpretationView> implements ScenicSpotAdapter.OnItemClickListener {
    private Context mContext;
    private ArtificialInterpretationView artificialView;
    private LocationClient mLocationClient;
    private ScenicSpotAdapter mScenicSpotAdapter;

    public ArtificialInterpretationPresenter(Context context, ArtificialInterpretationView artificialView) {
        this.mContext = context;
        this.artificialView = artificialView;
    }

    /**
     * 定位当前的位置
     */
    public void locationPosition(Context context) {
        mLocationClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd0911");
        option.setScanSpan(1000);

        mLocationClient.setLocOption(option);

        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);

        mLocationClient.start();
        LogUtils.e("showLocationPosition----start()");
    }

    /**
     * 获取景区中进店列表信息
     */
    public void getScenicSpotDatas() {
        ScenicSpotModel scenicSpotModel = null;
        List<ScenicSpotModel> scenicSpotModels = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            scenicSpotModel = new ScenicSpotModel();
            scenicSpotModel.setScenicName("武侯祠祠堂" + i);
            scenicSpotModel.setScenicDes("诸葛亮祠堂----");
            scenicSpotModels.add(scenicSpotModel);
        }
        artificialView.showScenicSpotDatas(scenicSpotModels);
    }

    public void setInitAdapter(ScenicSpotAdapter scenicSpotAdapter) {
        mScenicSpotAdapter = scenicSpotAdapter;
        mScenicSpotAdapter.setOnItemClickListener(this);
    }

    @Override
    public void attachView(ArtificialInterpretationView artificialInterpretationView) {

    }

    /**
     * 景点列表的点击事件
     */
    @Override
    public void onItemClickListener(ScenicSpotModel scenicSpotModel, int position) {
        ToastUtils.centerToas(mContext, scenicSpotModel.getScenicName());
    }

    /**
     * 获取接单导游信息
     */
    public void getOrderTourGuideInfo() {
        List<LatLng> tourGuideModels = new ArrayList<>();
        LatLng latLng = null;
        for (int i = 0; i < 1; i++) {
            latLng = new LatLng(30.67, 104.07);
            tourGuideModels.add(latLng);
        }
        artificialView.showOrderTourGuideInfo(tourGuideModels);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                artificialView.showLocationPosition(location);

                List<LatLng> tourGuideModels = new ArrayList<>();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                tourGuideModels.add(latLng);
                artificialView.showOrderTourGuideInfo(tourGuideModels);
                mLocationClient.stop();
            }
        }
    }
}
