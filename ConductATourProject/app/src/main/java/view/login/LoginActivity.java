package view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.conductatour.pack.R;

import butterknife.BindView;
import butterknife.OnClick;
import presenter.LoginPresenter;
import view.baseview.BaseActivity;
import view.homepage.HomePageActivity;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @BindView(R.id.verification_code)
    TextView mVerificationCode;

    @BindView(R.id.phone_number)
    TextView mPhoneNumber;

    /**
     * 初始化view
     *
     * @return 返回view的id
     */
    @Override
    protected int initView() {
        return R.layout.activity_login;
    }

    /**
     * 初始化的数据
     */
    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void showData(String showStr) {
        startActivity(HomePageActivity.newInstance(this));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick(R.id.login)
    public void setmGetVerficationCode() {
        String phoneNum = mPhoneNumber.getText().toString().trim();
        String verificationCode = mVerificationCode.getText().toString().trim();
        mPresenter.userLogin(phoneNum, verificationCode);
    }

}
