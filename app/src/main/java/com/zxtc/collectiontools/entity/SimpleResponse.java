package com.zxtc.collectiontools.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 简单的 response 请求
 */
public class SimpleResponse implements Parcelable {

    private static final long serialVersionUID = -1477609349345966116L;
    private transient Parcel inn;

    public int code;
    public String msg;

    protected SimpleResponse(Parcel in) {
        inn = in;
        code = in.readInt();
        msg = in.readString();
    }

    public static final Creator<SimpleResponse> CREATOR = new Creator<SimpleResponse>() {
        @Override
        public SimpleResponse createFromParcel(Parcel in) {
            return new SimpleResponse(in);
        }

        @Override
        public SimpleResponse[] newArray(int size) {
            return new SimpleResponse[size];
        }
    };

    public ResponseResult toMyResponse() {
        ResponseResult myResponse = new ResponseResult(inn);
        myResponse.code = code;
        myResponse.msg = msg;
        return myResponse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
    }
}