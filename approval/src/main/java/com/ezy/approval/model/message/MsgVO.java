package com.ezy.approval.model.message;

import lombok.Data;

/**
 * @author Caixiaowei
 * @ClassName MsgVO.java
 * @Description 消息返回体
 * @createTime 2020年06月19日 13:56:00
 */
@Data
public class MsgVO {


    /**
     * errcode : 0
     * errmsg : ok
     * invaliduser : userid1|userid2
     * invalidparty : partyid1|partyid2
     * invalidtag : tagid1|tagid2
     */

    private int errcode;
    private String errmsg;
    private String invaliduser;
    private String invalidparty;
    private String invalidtag;

}
