package presenter.basepresenter;

import rx.Observable;
import rx.Subscriber;
import view.baseview.IView;

/**
 * create by libo
 * create on 2018/11/13
 * description
 */
public interface Ipresenter<V extends IView> {
    /**
     * 关联P与V
     * @param v
     */
    void attachView(V v);

    /**
     * 取消关联P与V
     */
    void detachView();

    /**
     * Rx订阅
     */
    void subscribe(Observable observable, Subscriber subscriber);

    /**
     * Rx取消订阅
     */
    void unSubscribe();
}
