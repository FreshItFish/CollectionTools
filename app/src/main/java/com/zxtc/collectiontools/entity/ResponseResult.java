package com.zxtc.collectiontools.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 基础的网络请求返回值的Bean类型
 * 2017年4月8日16:02:25
 */
public class ResponseResult<T> implements Parcelable{
    /**
     * 返回码，标示了响应的具体状态，类似http请求中的404,201
     */
    @SerializedName("responseCode")
    public Integer code;
    /**
     * 附加的消息，以文字解释{@see #responseCode}响应状态码的含义
     * 但是要注意，当前字段是附加的解释消息，仅供参考，不保证必然是存在、正确、有意义的，以{@see #responseCode}为准。
     */
    @SerializedName("message")
    public String msg;
    /**
     * 返回数据
     */
    @SerializedName("data")
    public T data;

    /**
     * 响应的数据的类型，即{@see data}的类型
     * 此字段仅备用
     */
    public String response_data_type;

    protected ResponseResult(Parcel in) {
        msg = in.readString();
        response_data_type = in.readString();
    }

    public static final Creator<ResponseResult> CREATOR = new Creator<ResponseResult>() {
        @Override
        public ResponseResult createFromParcel(Parcel in) {
            return new ResponseResult(in);
        }

        @Override
        public ResponseResult[] newArray(int size) {
            return new ResponseResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
        dest.writeString(response_data_type);
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", response_data_type='" + response_data_type + '\'' +
                '}';
    }
}
