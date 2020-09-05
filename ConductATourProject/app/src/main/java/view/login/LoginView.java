package view.login;

import view.baseview.IBaseView;

/**
 * 定义业务逻辑中需要回传给view的功能定义
 */
public interface LoginView extends IBaseView {
    void showData(String showStr);
}
