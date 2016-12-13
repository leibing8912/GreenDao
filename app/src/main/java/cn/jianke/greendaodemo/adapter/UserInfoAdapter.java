package cn.jianke.greendaodemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.lang.ref.SoftReference;
import java.util.List;
import cn.jianke.greendaodemo.R;
import cn.jianke.greendaodemo.common.StringUtil;
import cn.jianke.greendaodemo.model.UserInfo;

/**
 * @className: UserInfoAdapter
 * @classDescription: 用户信息适配器
 * @author: leibing
 * @createTime: 2016/12/12
 */

public class UserInfoAdapter extends BaseAdapter{
    // 数据源
    private List<UserInfo> mData;
    // 布局引用
    private SoftReference<LayoutInflater> mLayoutInflaterSoftRef;

    /**
     *
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param mContext 上下文
     * @param mData 数据源
     * @return
     */
    public UserInfoAdapter(Context mContext, List<UserInfo> mData){
        mLayoutInflaterSoftRef = new SoftReference<LayoutInflater>(LayoutInflater.from(mContext));
        this.mData = mData;
    }

    /**
     * 设置数据源
     * @author leibing
     * @createTime 2016/12/12
     * @lastModify 2016/12/12
     * @param mData 数据源
     * @return
     */
    public void setData(List<UserInfo> mData){
        this.mData = mData;
        UserInfoAdapter.this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size(): 0;
    }

    @Override
    public Object getItem(int i) {
        return mData != null ? mData.get(i): null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null
                && mLayoutInflaterSoftRef != null
                && mLayoutInflaterSoftRef.get() != null){
            view = mLayoutInflaterSoftRef.get().inflate(R.layout.layout_user_info_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 更新UI
        if (mData != null
                && mData.size() != 0
                && i < mData.size()){
            viewHolder.updateUI(mData.get(i));
        }

        return view;
    }

    /**
     * @className: ViewHolder
     * @classDescription: 视图容器
     * @author: leibing
     * @createTime: 2016/12/12
     */
    static class ViewHolder{
        // 名称
        private TextView nameTv;
        // 年龄
        private TextView ageTv;
        // 自我评价
        private TextView selfAssessmentTv;

        /**
         * constructor
         * @author leibing
         * @createTime 2016/12/12
         * @lastModify 2016/12/12
         * @param view 视图
         * @return
         */
        public ViewHolder(View view){
            // 名称
            nameTv = (TextView) view.findViewById(R.id.tv_name);
            // 年龄
            ageTv = (TextView) view.findViewById(R.id.tv_age);
            // 自我评价
            selfAssessmentTv = (TextView) view.findViewById(R.id.tv_self_assessment);
        }

        /**
         * 更新UI
         * @author leibing
         * @createTime 2016/12/12
         * @lastModify 2016/12/12
         * @param mUserInfo 数据实体
         * @return
         */
        public void updateUI(UserInfo mUserInfo){
            // 若数据为空则返回
            if (mUserInfo == null)
                return;
            // 更新名称
            if (nameTv != null
                    && StringUtil.isNotEmpty(mUserInfo.getName())){
                nameTv.setText(mUserInfo.getName().trim());
            }
            // 更新年龄
            if (ageTv != null && mUserInfo.getAge() != 0){
                ageTv.setText(mUserInfo.getAge() + "");
            }
            // 更新自我评价
            if (selfAssessmentTv != null
                    && StringUtil.isNotEmpty(mUserInfo.getSelfAssessment())){
                selfAssessmentTv.setText(mUserInfo.getSelfAssessment().trim());
            }
        }
    }
}
