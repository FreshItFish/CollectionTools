package com.zxtc.collectiontools.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *
 */

public class PostImageBean implements Parcelable{
    //@SerializedName("") 可以解决前后天之间字段名不匹配的问题
    @SerializedName("userId")
    private int userId;
    @SerializedName("userName")
    private String userName;
    @SerializedName("remark")
    private String remark;
    @SerializedName("userPhotPath")
    private String userPhotPath;

    public int getUserId(){
        return this.userId;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getRemark(){
        return this.remark;
    }

    public String getUserPhotPath(){
        return this.userPhotPath;
    }

    protected PostImageBean(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        remark = in.readString();
        userPhotPath = in.readString();
    }

    public static final Creator<PostImageBean> CREATOR = new Creator<PostImageBean>() {
        @Override
        public PostImageBean createFromParcel(Parcel in) {
            return new PostImageBean(in);
        }

        @Override
        public PostImageBean[] newArray(int size) {
            return new PostImageBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(remark);
        dest.writeString(userPhotPath);
    }

}
