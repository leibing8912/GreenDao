package cn.jianke.greendaodemo.common;

import com.jk.greendao.gen.DaoMaster;
import com.jk.greendao.gen.DaoSession;
import com.jk.greendao.gen.MySQLiteOpenHelper;
import cn.jianke.greendaodemo.BaseApplication;

/**
 * @className: GreenDaoManager
 * @classDescription: GreenDao封装
 * @author: leibing
 * @createTime: 2016/12/12
 */

public class GreenDaoManager {
    // greendao database name
    public final static String GREEN_DAO_DATABASE_NAME = "green_dao_db";
    // sington
    private static GreenDaoManager instance;
    // DaoSession
    private DaoSession mDaoSession;

    /**
     * constructor
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param
     * @return
     */
    private GreenDaoManager(){
        MySQLiteOpenHelper devOpenHelper = new MySQLiteOpenHelper(BaseApplication.getInstance(),
                GREEN_DAO_DATABASE_NAME, null);
        DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * sington
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param
     * @return
     */
    public static GreenDaoManager getInstance(){
        if (instance == null){
            synchronized (GreenDaoManager.class){
                if (instance == null){
                    instance = new GreenDaoManager();
                }
            }
        }
        return instance;
    }

    /**
     * get DaoSession
     * @author leibing
     * @createTime 2016/12/13
     * @lastModify 2016/12/13
     * @param
     * @return
     */
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
