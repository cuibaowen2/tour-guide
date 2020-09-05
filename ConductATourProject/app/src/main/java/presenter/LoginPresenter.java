package presenter;

import http.ApiCallBack;
import model.basemodel.BaseModel;
import presenter.basepresenter.BasePresenter;
import view.login.LoginView;

public class LoginPresenter extends BasePresenter<LoginView> {
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        attachView(loginView);
        this.loginView = loginView;
        loginView.showData("cuibaoqanfj");
    }

    public void getUserInfo() {
        subscribe(apiService.getUserInfo(), new ApiCallBack() {
            @Override
            public void onSuccess(BaseModel baseModel) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    public void userLogin(String phoneNum, String verificationCode) {
        loginView.showData("");
    }
}
