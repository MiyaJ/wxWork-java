package com.ezy.approval.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName UploadVO.java
 * @Description 上传临时素材vo
 * @createTime 2020年07月28日 13:31:00
 */
@Data
public class UploadVO implements Serializable {

    /**
     * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file)
     */
    private String type;

    /**
     * 媒体文件上传后获取的唯一标识，3天内有效
     */
    private String mediaId;

    /**
     * 媒体文件上传时间戳
     */
    private String createdAt;

}
