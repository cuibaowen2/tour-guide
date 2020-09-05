package presenter;

import presenter.basepresenter.BasePresenter;
import view.homepage.fragment.CulturalAudioPlayView;

public class CulturalAudioPlayPresenter extends BasePresenter<CulturalAudioPlayView> {
    private CulturalAudioPlayView culturalAudioPlayView;

    public CulturalAudioPlayPresenter(CulturalAudioPlayView culturalAudioPlayView) {
        this.culturalAudioPlayView = culturalAudioPlayView;
    }

    /**
     * 初始化用户角色接口
     */
    public void initUserRole() {
        culturalAudioPlayView.showUserRole("游客");
    }
}
