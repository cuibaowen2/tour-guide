package view.homepage.fragment;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

import model.ScenicSpotModel;
import view.baseview.IBaseView;

public interface ArtificialInterpretationView extends IBaseView {
    void showLocationPosition(BDLocation location);

    void showScenicSpotDatas(List<ScenicSpotModel> scenicSpotModels);

    void showOrderTourGuideInfo(List<LatLng> tourGuideModels);
}
