package view.login;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.conductatour.pack.R;

import butterknife.BindView;
import butterknife.OnClick;
import presenter.VerificationCodePresenter;
import view.baseview.BaseActivity;

public class VerificationCodeActivity extends BaseActivity<VerificationCodePresenter> implements VerificationCodeView {
    @BindView(R.id.phone_number)
    TextView mPhoneNumber;

    @Override
    protected int initView() {
        return R.layout.activity_verification_code;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected VerificationCodePresenter createPresenter() {
        return new VerificationCodePresenter(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick(R.id.get_verification_code)
    public void setmGetVerficationCode() {
        String phoneNumber = mPhoneNumber.getText().toString().trim();
        startActivity(LoginActivity.newInstance(this));
    }
}
