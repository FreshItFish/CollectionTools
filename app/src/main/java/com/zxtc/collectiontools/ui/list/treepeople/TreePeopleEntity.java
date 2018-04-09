package com.zxtc.collectiontools.ui.list.treepeople;

/**
 * 作者：KY
 * 创建时间：2018/4/3 11:17
 * 描述: 人员组织结构树 实体
 */

public class TreePeopleEntity {
    private int isPeople;   //区分人员与部门 0-部门  1-人员
    private String id;      //当前id
    private String pid;     //上层节点id，顶层节点为""
    private String name;    //名称
    private String departcode;  //部门id

    public int getIsPeople() {
        return isPeople;
    }

    public void setIsPeople(int isPeople) {
        this.isPeople = isPeople;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartcode() {
        return departcode;
    }

    public void setDepartcode(String departcode) {
        this.departcode = departcode;
    }
}
