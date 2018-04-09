package com.zxtc.collectiontools.base;

/**
 * 作者：KY
 * 创建时间：2018/4/3 16:52
 * 描述:
 */

public class TreePeopleEvent {
    public String flag;
    public String id;
    public String name;    //名称
    public int size;   //选择个数

    public TreePeopleEvent(String id, String name, int size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public TreePeopleEvent(String flag, String id, String name, int size) {
        this.flag = flag;
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
