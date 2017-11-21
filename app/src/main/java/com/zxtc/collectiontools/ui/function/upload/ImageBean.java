package com.zxtc.collectiontools.ui.function.upload;

import java.util.List;

/**
 *
 */

public class ImageBean {

    private int responseCode;

    private List<Data> data;

    public int getResponseCode(){
        return this.responseCode;
    }

    public List<Data> getData(){
        return this.data;
    }

    public class Data
    {
        private int userId;

        private String userName;

        private String remark;

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
    }
}
