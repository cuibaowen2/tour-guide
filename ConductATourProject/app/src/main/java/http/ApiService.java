package http;

import model.UserModel;
import model.basemodel.BaseModel;
import retrofit2.http.GET;
import rx.Observable;

import static common.Constants.isDebug;

public interface ApiService {

    /** *******************************************  IP配置  ******************************************** */

    /** 正式服务器地址 */
    String SERVER_ADDRESS_RELEASE = "";

    /** 测试服务器地址 */
    String SERVER_ADDRESS_DEBUG = "";

    /** 服务器域名 */
    String SERVER_ADDRESS = isDebug ? SERVER_ADDRESS_DEBUG : SERVER_ADDRESS_RELEASE;

    @GET("")
    Observable<BaseModel<UserModel>> getUserInfo();
}
