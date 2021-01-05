package com.ezy.approval.model.message.personal;

import lombok.Data;

/**
 * @author Caixiaowei
 * @ClassName ImageMsg.java
 * @Description 图片消息
 * @createTime 2020年06月19日 13:32:00
 */
@Data
public class ImageMsg extends BaseMsg {


    /**
     * image : {"media_id":"MEDIA_ID"}
     */

    private ImageBean image;

    public static class ImageBean {

        public ImageBean() {

        }

        public ImageBean(String media_id) {
            this.media_id = media_id;
        }
        /**
         * media_id : MEDIA_ID
         */

        /**
         * 图片媒体文件id，可以调用上传临时素材接口获取
         */
        private String media_id;

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }
    }
}
