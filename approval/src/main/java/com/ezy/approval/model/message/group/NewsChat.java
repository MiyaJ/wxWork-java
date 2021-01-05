package com.ezy.approval.model.message.group;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName NewsChat.java
 * @Description TODO
 * @createTime 2020年06月28日 17:00:00
 */
public class NewsChat extends BaseChat {


    /**
     * news : {"articles":[{"title":"中秋节礼品领取","description":"今年中秋节公司有豪礼相送","url":"https://work.weixin.qq.com/","picurl":"http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png"}]}
     */

    private NewsBean news;

    public NewsBean getNews() {
        return news;
    }

    public void setNews(NewsBean news) {
        this.news = news;
    }

    public static class NewsBean {
        private List<ArticlesBean> articles;

        public List<ArticlesBean> getArticles() {
            return articles;
        }

        public void setArticles(List<ArticlesBean> articles) {
            this.articles = articles;
        }

        public static class ArticlesBean {
            /**
             * title : 中秋节礼品领取
             * description : 今年中秋节公司有豪礼相送
             * url : https://work.weixin.qq.com/
             * picurl : http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png
             */

            private String title;
            private String description;
            private String url;
            private String picurl;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPicurl() {
                return picurl;
            }

            public void setPicurl(String picurl) {
                this.picurl = picurl;
            }
        }
    }
}
