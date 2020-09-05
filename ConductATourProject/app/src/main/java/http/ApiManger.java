package http;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static common.Constants.HTTP_CACHE;
import static common.Constants.isDebug;

public class ApiManger {

    private static ApiManger mApiManager;
    private OkHttpClient mOkHttpClient;
    private static ApiService mApiService;
    private final int TIMEOUT = 10;

    private ApiManger() {
//        initOkhttp();
//        initRetrofit();
    }

    public static synchronized ApiService getApiService() {
        if (mApiService == null) {
            mApiManager = new ApiManger();
        }
        return mApiService;
    }

    private void initOkhttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)  //连接超时设置
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)  //写入缓存超时10s
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)  //读取缓存超时10s
                .retryOnConnectionFailure(true)  //失败重连
                .addInterceptor(new HeaderInterceptor())  //添加header
                .addInterceptor(new NetCacheInterceptor());  //添加网络缓存

        addLogIntercepter(builder);  //日志拦截器
        setCacheFile(builder);  //网络缓存

        mOkHttpClient = builder.build();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.SERVER_ADDRESS)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    /**
     * 设置缓存文件路径
     */
    private void setCacheFile(OkHttpClient.Builder builder) {
        //设置缓存文件
        File cacheFile = new File(HTTP_CACHE);
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheFile,cacheSize);
        builder.cache(cache);
    }

    /**
     * 调试模式下加入日志拦截器
     * @param builder
     */
    private void addLogIntercepter(OkHttpClient.Builder builder) {
        if (isDebug) {
            builder.addInterceptor(new LoggingInterceptor());
        }
    }
}
