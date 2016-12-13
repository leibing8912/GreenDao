package cn.jianke.greendaodemo.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.jianke.greendaodemo.R;
import cn.jianke.greendaodemo.common.StringUtil;

/**
 * @className: AddUserInfoActivity
 * @classDescription: 添加更新用户信息
 * @author: leibing
 * @createTime: 2016/12/12
 */
public class AddUserInfoActivity extends AppCompatActivity implements View.OnClickListener{
    // 姓名标识
    public final static String NAME = "name";
    // 年龄标识
    public final static String AGE = "age";
    // 自我评价
    public final static String SELF_ASSESSMENT = "selfAssessment";
    // id标识
    public final static String ID = "id";
    // 姓名
    private EditText nameEdt;
    // 年龄
    private EditText ageEdt;
    // 自我评价
    private EditText selfAssessmentEdt;
    // 更新数据id
    private long updateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_info);
        // findView
        // 姓名
        nameEdt = (EditText) findViewById(R.id.edt_name);
        // 年龄
        ageEdt = (EditText) findViewById(R.id.edt_age);
        // 自我评价
        selfAssessmentEdt = (EditText) findViewById(R.id.edt_selfAssessment);
        // onClick
        // 提交（添加or更新）
        findViewById(R.id.btn_commit).setOnClickListener(this);
        // 根据意图更新UI
        updateUiByIntent();
    }

    /**
     * 根据意图更新UI
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param
     * @return
     */
    private void updateUiByIntent() {
        Intent intent = getIntent();
        // 若意图为空则返回
        if (intent == null)
            return;
        Bundle bundle = intent.getExtras();
        // 若bundle数据为空则返回
        if (bundle == null)
            return;
        String name = bundle.getString(AddUserInfoActivity.NAME);
        int age = bundle.getInt(AddUserInfoActivity.AGE);
        String selfAssessment = bundle.getString(AddUserInfoActivity.SELF_ASSESSMENT);
        // 设置id
        updateId = bundle.getLong(AddUserInfoActivity.ID);
        // 更新名称显示
        if (StringUtil.isNotEmpty(name) && nameEdt != null){
            nameEdt.setText(name.trim());
        }
        // 更新年龄显示
        if (age != 0 && ageEdt != null){
            ageEdt.setText(age + "");
        }
        // 更新自我评价显示
        if (StringUtil.isNotEmpty(selfAssessment) && selfAssessmentEdt != null){
            selfAssessmentEdt.setText(selfAssessment.trim());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_commit:
                commit();
                break;
            default:
                break;
        }
    }

    /**
     * 提交（添加or更新）
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param
     * @return
     */
    private void commit() {
        if (nameEdt == null || StringUtil.isEmpty(nameEdt.getText().toString())) {
            Toast.makeText(AddUserInfoActivity.this, "姓名不能空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ageEdt == null
                || StringUtil.isEmpty(ageEdt.getText().toString())
                || !StringUtil.strIsNum(ageEdt.getText().toString())) {
            Toast.makeText(AddUserInfoActivity.this, "年龄不能为空或非法", Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putLong(ID, updateId);
        bundle.putString(NAME, nameEdt.getText().toString());
        bundle.putInt(AGE, Integer.valueOf(ageEdt.getText().toString()));
        bundle.putString(SELF_ASSESSMENT, selfAssessmentEdt.getText().toString());
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
