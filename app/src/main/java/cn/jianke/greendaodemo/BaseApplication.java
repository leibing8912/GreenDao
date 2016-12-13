package cn.jianke.greendaodemo;

import android.app.Application;

/**
 * @className: BaseApplication
 * @classDescription: 应用实例
 * @author: leibing
 * @createTime: 2016/12/12
 */
public class BaseApplication extends Application{
    // sington
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        // init instance
        instance = this;
    }

    /**
     * sington
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param
     * @return
     */
    public static BaseApplication getInstance(){
        if (instance == null){
            synchronized (BaseApplication.class){
                if (instance == null){
                    instance = new BaseApplication();
                }
            }
        }

        return instance;
    }
}
