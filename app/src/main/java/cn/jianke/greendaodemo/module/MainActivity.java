package cn.jianke.greendaodemo.module;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.jk.greendao.gen.UserInfoDao;
import org.greenrobot.greendao.query.Query;
import java.util.ArrayList;
import java.util.List;
import cn.jianke.greendaodemo.R;
import cn.jianke.greendaodemo.adapter.UserInfoAdapter;
import cn.jianke.greendaodemo.common.GreenDaoManager;
import cn.jianke.greendaodemo.model.UserInfo;
import cn.jianke.greendaodemo.widget.CustomDialog;

/**
 * @className: MainActivity
 * @classDescription: 测试GreenDao
 * @author: leibing
 * @createTime: 2016/12/12
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    // 添加数据标识
    public final static int ADD_DATA_REQUEST_CODE = 0;
    // 更新数据标识
    public final static int UPDATE_DATA_REQUEST_CODE = 1;
    // 用户信息Dao
    private UserInfoDao mUserInfoDao;
    // 数据列表
    private ListView greenDaoLv;
    // 适配器
    private UserInfoAdapter mUserInfoAdapter;
    // 数据源
    private List<UserInfo> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init UserInfoDao
        mUserInfoDao = GreenDaoManager.getInstance().getDaoSession().getUserInfoDao();
        // findView
        greenDaoLv = (ListView) findViewById(R.id.lv_green_dao);
        // 初始化数据源
        mData = new ArrayList<>();
        // 初始化适配器
        mUserInfoAdapter = new UserInfoAdapter(this, mData);
        // 设置适配器
        greenDaoLv.setAdapter(mUserInfoAdapter);
        // onItemClick
        greenDaoLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                CustomDialog.getInstance().show("", "", MainActivity.this,
                        new CustomDialog.CustomDialogListener() {
                    @Override
                    public void onClick(int target) {
                        switch (target){
                            case CustomDialog.UPDATE:
                                // 更新
                                updateData(i);
                                break;
                            case CustomDialog.DELETE:
                                // 删除
                                deleteData(i);
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });
        // onClick
        // 查询
        findViewById(R.id.btn_green_dao_query).setOnClickListener(this);
        // 添加
        findViewById(R.id.btn_green_dao_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_green_dao_query:
                // 查询
                queryData();
                break;
            case R.id.btn_green_dao_add:
                // 添加
                addData();
                break;
            default:
                break;
        }
    }

    /**
     * 查询数据
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param
     * @return
     */
    private void queryData() {
        Query mQuery = mUserInfoDao.queryBuilder().build();
        List<UserInfo> userInfoList = mQuery.list();
        if (mUserInfoAdapter != null
                && userInfoList != null
                && userInfoList.size() != 0){
            if (mData != null) {
                mData.clear();
                mData.addAll(userInfoList);
            }
            mUserInfoAdapter.setData(userInfoList);
        }
    }

    /**
     * 添加数据
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param
     * @return
     */
    private void addData() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, AddUserInfoActivity.class);
        startActivityForResult(intent, ADD_DATA_REQUEST_CODE);
    }

    /**
     * 更新数据
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param i 数据索引
     * @return
     */
    private void updateData(int i) {
        // 若索引不合法则返回
        if (mData == null
                || mData.size() == 0
                || i>=mData.size())
            return;
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, AddUserInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(AddUserInfoActivity.ID, mData.get(i).getId());
        bundle.putString(AddUserInfoActivity.NAME, mData.get(i).getName());
        bundle.putInt(AddUserInfoActivity.AGE, mData.get(i).getAge());
        bundle.putString(AddUserInfoActivity.SELF_ASSESSMENT, mData.get(i).getSelfAssessment());
        intent.putExtras(bundle);
        startActivityForResult(intent, UPDATE_DATA_REQUEST_CODE);
    }

    /**
     * 删除数据
     * @author leibing
     * @createTime 2016/12/13
     * @lastModify 2016/12/13
     * @param i 数据索引
     * @return
     */
    private void deleteData(int i) {
        try {
            // 若索引不合法则返回
            if (mData == null
                    || mData.size() == 0
                    || i>=mData.size())
                return;
            UserInfo userInfo = mData.get(i);
            if (mUserInfoDao != null && userInfo != null)
                mUserInfoDao.deleteInTx(userInfo);
            mData.remove(i);
            if (mUserInfoAdapter != null) {
                mUserInfoAdapter.setData(mData);
            }
        }catch (Exception ex){
            System.out.println("dddddddd delete data ex = " + ex.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ADD_DATA_REQUEST_CODE:
                // 添加数据
                addDataFromIntent(data);
                break;
            case UPDATE_DATA_REQUEST_CODE:
                // 更新数据
                updateDataFromIntent(data);
                break;
            default:
                break;
        }
    }

    /**
     * 更新数据
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param data
     * @return
     */
    private void updateDataFromIntent(Intent data) {
        try {
            // 若数据为空则返回
            if (data == null)
                return;
            Bundle bundle = data.getExtras();
            // 若bundle数据为空则返回
            if (bundle == null)
                return;
            // 姓名
            long id = bundle.getLong(AddUserInfoActivity.ID);
            String name = bundle.getString(AddUserInfoActivity.NAME);
            int age = bundle.getInt(AddUserInfoActivity.AGE);
            String selfAssessment = bundle.getString(AddUserInfoActivity.SELF_ASSESSMENT);
            // 更新数据
            if (mUserInfoDao != null){
                UserInfo mUserInfo = mUserInfoDao.queryBuilder().where(
                        UserInfoDao.Properties.Id.eq(id)).build().unique();
                if (mUserInfo != null){
                    mUserInfo.setName(name);
                    mUserInfo.setAge(age);
                    mUserInfo.setSelfAssessment(selfAssessment);
                    mUserInfoDao.update(mUserInfo);
                }
            }
            // 查询数据
            queryData();
        }catch (Exception ex){
            System.out.println("dddddddd update data ex = " + ex.getMessage());
        }
    }

    /**
     * 添加数据
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param data
     * @return
     */
    private void addDataFromIntent(Intent data) {
        try {
            // 若数据为空则返回
            if (data == null)
                return;
            Bundle bundle = data.getExtras();
            // 若bundle数据为空则返回
            if (bundle == null)
                return;
            // 姓名
            String name = bundle.getString(AddUserInfoActivity.NAME);
            int age = bundle.getInt(AddUserInfoActivity.AGE);
            String selfAssessment = bundle.getString(AddUserInfoActivity.SELF_ASSESSMENT);
            // 组装数据
            UserInfo mUserInfo = new UserInfo();
            System.out.println("dddddddd queryMaxId = " + queryMaxId());
            mUserInfo.setId(queryMaxId());
            mUserInfo.setName(name);
            mUserInfo.setAge(age);
            mUserInfo.setSelfAssessment(selfAssessment);
            // 添加数据
            if (mUserInfoDao != null) {
                mUserInfoDao.insert(mUserInfo);
                if (mData != null)
                    mData.add(mUserInfo);
                if (mUserInfoAdapter != null)
                    mUserInfoAdapter.setData(mData);
            }
        }catch (Exception ex){
            System.out.println("dddddddd add data ex = " + ex.getMessage());
        }
    }

    /**
     * 查询数据最大id
     * @author leibing
     * @createTime 2016/12/13
     * @lastModify 2016/12/13
     * @param
     * @return
     */
    public long queryMaxId(){
        if (mUserInfoDao == null)
            return 0;
        Query mQuery = mUserInfoDao.queryBuilder().build();
        List<UserInfo> userInfoList = mQuery.list();
        if (userInfoList != null && userInfoList.size() != 0){
            UserInfo userInfo = userInfoList.get(userInfoList.size() -1);
            return (userInfo.getId() + 1);
        }

        return 0;
    }
}
