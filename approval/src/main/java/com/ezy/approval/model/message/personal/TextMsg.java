package com.ezy.approval.model.message.personal;

import lombok.Data;

/**
 * @author Caixiaowei
 * @ClassName SendDTO.java
 * @Description 文本消息dto
 * @createTime 2020年06月19日 11:03:00
 */
@Data
public class TextMsg extends BaseMsg {
    /**
     * touser : UserID1|UserID2|UserID3
     * toparty : PartyID1|PartyID2
     * totag : TagID1 | TagID2
     * msgtype : text
     * agentid : 1
     * text : {"content":"你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况<\/a>，聪明避开排队。"}
     * safe : 0
     * enable_id_trans : 0
     * enable_duplicate_check : 0
     * duplicate_check_interval : 1800
     */

    public TextMsg() {
    }

    /**
     * 文本信息内容 bean
     */
    private TextBean text;

    /**
     * 表示是否开启id转译，0表示否，1表示是，默认0
     */
    private int enable_id_trans;


    public static class TextBean {

        public TextBean() {
        }

        public TextBean(String content) {
            this.content = content;
        }

        /**
         * content : 你的快递已到，请携带工卡前往邮件中心领取。
         * 出发前可查看<a href="http://work.weixin.qq.com">邮件中心视频实况</a>，聪明避开排队。
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
