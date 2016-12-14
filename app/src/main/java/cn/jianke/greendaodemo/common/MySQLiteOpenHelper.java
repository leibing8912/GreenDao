package cn.jianke.greendaodemo.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.jk.greendao.gen.DaoMaster;
import com.jk.greendao.gen.UserInfoDao;

/**
 * @className: MySQLiteOpenHelper
 * @classDescription: 自定义SQLiteOpenHelper，用于升级
 * @author: leibing
 * @createTime: 2016/12/14
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级处理
        MigrationHelper.migrate(db,UserInfoDao.class);
    }
}
