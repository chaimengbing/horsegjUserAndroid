package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2016/9/22.
 */
public class BroadcastNew extends Entity {


    /**
     * id : 2
     * createTime : 2017-09-06 17:43:19
     * modifyTime : 2017-09-06 17:43:19
     * agentId : 3
     * hotTitle0 : 热门1
     * concernTitle0 : 关注1
     * hotTitle1 : 热门2
     * concernTitle1 : 关注2
     * hotTitle2 : 热门3
     * concernTitle2 : 关注3
     * titleJson : [{"titleType":1,"hotTitle":"热门1"},{"titleType":2,"concernTitle":"关注1"},{"titleType":1,"hotTitle":"热门2"},{"titleType":2,"concernTitle":"关注2"},{"titleType":1,"hotTitle":"热门3"},{"titleType":2,"concernTitle":"关注3"}]
     * newsUrl : www.baidu.com
     * hasDel : 0
     * creator : 1
     * modifiedByAgentId : 1
     * modifiedByAgent : null
     * agent : null
     */

    private int id;
    private String hotTitle0;
    private String concernTitle0;
    private String hotTitle1;
    private String concernTitle1;
    private String hotTitle2;
    private String concernTitle2;
    private String titleJson;
    private String newsUrl;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getHotTitle0() {
        return hotTitle0;
    }

    public void setHotTitle0(String hotTitle0) {
        this.hotTitle0 = hotTitle0;
    }

    public String getConcernTitle0() {
        return concernTitle0;
    }

    public void setConcernTitle0(String concernTitle0) {
        this.concernTitle0 = concernTitle0;
    }

    public String getHotTitle1() {
        return hotTitle1;
    }

    public void setHotTitle1(String hotTitle1) {
        this.hotTitle1 = hotTitle1;
    }

    public String getConcernTitle1() {
        return concernTitle1;
    }

    public void setConcernTitle1(String concernTitle1) {
        this.concernTitle1 = concernTitle1;
    }

    public String getHotTitle2() {
        return hotTitle2;
    }

    public void setHotTitle2(String hotTitle2) {
        this.hotTitle2 = hotTitle2;
    }

    public String getConcernTitle2() {
        return concernTitle2;
    }

    public void setConcernTitle2(String concernTitle2) {
        this.concernTitle2 = concernTitle2;
    }

    public String getTitleJson() {
        return titleJson;
    }

    public void setTitleJson(String titleJson) {
        this.titleJson = titleJson;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

}
