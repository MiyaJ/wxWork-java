package com.ezy.approval.model.message.group;

/**
 * @author Caixiaowei
 * @ClassName ImageChat.java
 * @Description 图片群聊
 * @createTime 2020年06月28日 16:58:00
 */
public class ImageChat extends BaseChat {


    /**
     * image : {"media_id":"MEDIAID"}
     */

    private ImageBean image;

    public ImageBean getImage() {
        return image;
    }

    public void setImage(ImageBean image) {
        this.image = image;
    }

    public static class ImageBean {
        /**
         * media_id : MEDIAID
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
