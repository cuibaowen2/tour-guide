package presenter;

import presenter.basepresenter.BasePresenter;
import view.login.VerificationCodeView;

public class VerificationCodePresenter extends BasePresenter<VerificationCodeView> {
    private VerificationCodeView verificationCodeView;

    public VerificationCodePresenter(VerificationCodeView view) {
        verificationCodeView = view;
    }
}
