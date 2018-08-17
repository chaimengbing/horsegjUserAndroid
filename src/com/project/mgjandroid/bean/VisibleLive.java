package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;

/**
 * Created by yuandi on 2016/7/5.
 */
public class VisibleLive extends Entity {

    private static final long serialVersionUID = 1L;

    /**
     * 代理商Id
     */
    private Long agentId;

    /**
     * 商家Id
     */
    private Long merchantId;

    /**
     * 视频位置名称
     */
    private String positionName;

    /**
     * 填写视频地址
     */
    private String videoUrl;
    /**
     * 填写视频封面
     */
    private String videoPic;

    /**
     * 视频播放地址
     */
    private String videoSrc;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoSrc() {
        return videoSrc;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public String getVideoPic() {
        return videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }
}
