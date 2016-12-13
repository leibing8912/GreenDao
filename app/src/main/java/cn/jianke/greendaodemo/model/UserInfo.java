package cn.jianke.greendaodemo.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @className: UserInfo
 * @classDescription: 用户信息
 * @author: leibing
 * @createTime: 2016/12/12
 */

@Entity
public class UserInfo {
   // 自增id
   @Id(autoincrement = true)
   private long id;
   // 名称
   @NotNull
   private String name;
   // 年龄
   private int age;
   // 自我评价
   private String selfAssessment;
   @Generated(hash = 1783263508)
   public UserInfo(long id, @NotNull String name, int age, String selfAssessment) {
       this.id = id;
       this.name = name;
       this.age = age;
       this.selfAssessment = selfAssessment;
   }
   @Generated(hash = 1279772520)
   public UserInfo() {
   }
   public long getId() {
       return this.id;
   }
   public void setId(long id) {
       this.id = id;
   }
   public String getName() {
       return this.name;
   }
   public void setName(String name) {
       this.name = name;
   }
   public int getAge() {
       return this.age;
   }
   public void setAge(int age) {
       this.age = age;
   }
   public String getSelfAssessment() {
       return this.selfAssessment;
   }
   public void setSelfAssessment(String selfAssessment) {
       this.selfAssessment = selfAssessment;
   }
}
