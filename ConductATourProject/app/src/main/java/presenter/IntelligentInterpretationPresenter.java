package presenter;

import presenter.basepresenter.BasePresenter;
import view.homepage.fragment.IntelligentInterpretationView;

/**
 * 智能讲解
 */
public class IntelligentInterpretationPresenter extends BasePresenter<IntelligentInterpretationView> {
    private IntelligentInterpretationView interpretationView;

    public IntelligentInterpretationPresenter(IntelligentInterpretationView interpretationView) {
        this.interpretationView = interpretationView;
    }
}
