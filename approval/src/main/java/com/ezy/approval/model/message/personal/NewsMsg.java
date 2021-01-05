package com.ezy.approval.model.message.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName NewsMsg.java
 * @Description 图文消息
 * @createTime 2020年06月19日 16:04:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsMsg extends BaseMsg {


    /**
     * news : {"articles":[{"title":"中秋节礼品领取","description":"今年中秋节公司有豪礼相送","url":"URL","picurl":"http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png"}]}
     * enable_id_trans : 0
     */

    /**
     * 图文内容
     */
    private NewsBean news;

    /**
     * 表示是否开启id转译，0表示否，1表示是，默认0
     */
    private int enable_id_trans;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NewsBean {


        private List<ArticlesBean> articles;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ArticlesBean {
            /**
             * title : 中秋节礼品领取
             * description : 今年中秋节公司有豪礼相送
             * url : URL
             * picurl : http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png
             */

            private String title;
            private String description;
            private String url;
            private String picurl;

        }
    }
}
