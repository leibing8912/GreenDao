package cn.jianke.greendaodemo.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import cn.jianke.greendaodemo.R;

/**
 * @className: CustomDialog
 * @classDescription: 自定义弹窗（当消息权限被禁后处理）
 * @author: leibing
 * @createTime: 2016/12/6
 */
public class CustomDialog {
    // 更新
    public final static int UPDATE = 0;
    // 删除
    public final static int DELETE = 1;
    // 窗体管理者
    private WindowManager wm;
    private WindowManager.LayoutParams mParams;
    private View mView;
    // 单例
    public static CustomDialog instance;

    /**
     * 获取单例
     * @author leibing
     * @createTime 2016/12/6
     * @lastModify 2016/12/6
     * @param
     * @return
     */
    public static CustomDialog getInstance(){
        if (instance == null){
            synchronized (CustomDialog.class){
                if (instance == null)
                    instance = new CustomDialog();
            }
        }

        return instance;
    }

    /**
     * 显示自定义Toast
     * @author leibing
     * @createTime 2016/12/6
     * @lastModify 2016/12/6
     * @param title 标题
     * @param content 内容
     * @param context 上下文
     * @param listener 监听
     * @return
     */
    public void show(String title , String content,
                     Context context, final CustomDialogListener listener) {
        try {
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mView = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
            mParams = new WindowManager.LayoutParams();
            // 标题
            TextView titleTv = (TextView) mView.findViewById(R.id.tv_title);
            // 内容
            TextView contentTv = (TextView) mView.findViewById(R.id.tv_content);
            // onClick
            if (listener != null){
                // 更新
                mView.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hide();
                        listener.onClick(UPDATE);
                    }
                });
                // 删除
                mView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hide();
                        listener.onClick(DELETE);
                    }
                });
            }
            // 设置标题
            if (title != null && !title.equals(""))
                titleTv.setText(title.trim());
            // 设置内容
            if (content != null && !content.equals(""))
                contentTv.setText(content.trim());
            WindowManager.LayoutParams params = mParams;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            wm.addView(mView ,params);
        }catch (Exception ex){
        }
    }

    /**
     * 隐藏自定义Toast
     * @author leibing
     * @createTime 2016/12/6
     * @lastModify 2016/12/6
     * @param
     * @return
     */
    private void hide() {
        if (wm != null) {
            if (mView != null) {
                wm.removeView(mView);
                mView = null;
            }
            if (mView == null) {
                wm = null;
            }
        }
    }

    /**
     * @interfaceName: CustomDialogListener
     * @interfaceDescription: 回调监听
     * @author: leibing
     * @createTime: 2016/12/6
     */
    public interface CustomDialogListener{
        void onClick(int target);
    }
}
