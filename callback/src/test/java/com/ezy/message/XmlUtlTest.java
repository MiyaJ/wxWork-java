package com.ezy.message;

import com.alibaba.fastjson.JSONObject;
import com.ezy.message.model.callback.approval.*;
import com.ezy.message.model.callback.contact.Contact;
import com.ezy.message.model.callback.contact.ExtAttr;
import com.ezy.message.model.callback.contact.Item;
import com.ezy.message.model.callback.contact.ItemText;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * @author Caixiaowei
 * @ClassName XmlUtlTest.java
 * @Description TODO
 * @createTime 2020年08月03日 14:46:00
 */
@Test
@Slf4j
public class XmlUtlTest {

    String create_user = "<xml>\n" +
            "    <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
            "    <FromUserName><![CDATA[sys]]></FromUserName> \n" +
            "    <CreateTime>1403610513</CreateTime>\n" +
            "    <MsgType><![CDATA[event]]></MsgType>\n" +
            "    <Event><![CDATA[change_contact]]></Event>\n" +
            "    <ChangeType>create_user</ChangeType>\n" +
            "    <UserID><![CDATA[zhangsan]]></UserID>\n" +
            "    <Name><![CDATA[张三]]></Name>\n" +
            "    <Department><![CDATA[1,2,3]]></Department>\n" +
            "    <IsLeaderInDept><![CDATA[1,0,0]]></IsLeaderInDept>\n" +
            "    <Position><![CDATA[产品经理]]></Position>\n" +
            "    <Mobile>13800000000</Mobile>\n" +
            "    <Gender>1</Gender>\n" +
            "    <Email><![CDATA[zhangsan@gzdev.com]]></Email>\n" +
            "    <Status>1</Status>\n" +
            "    <Avatar><![CDATA[http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0]]></Avatar>\n" +
            "    <Alias><![CDATA[zhangsan]]></Alias>\n" +
            "    <Telephone><![CDATA[020-123456]]></Telephone>\n" +
            "    <Address><![CDATA[广州市]]></Address>\n" +
            "    <ExtAttr>\n" +
            "        <Item>\n" +
            "        <Name><![CDATA[爱好]]></Name>\n" +
            "        <Type>0</Type>\n" +
            "        <Text>\n" +
            "            <Value><![CDATA[旅游]]></Value>\n" +
            "        </Text>\n" +
            "        </Item>\n" +
            "        <Item>\n" +
            "        <Name><![CDATA[卡号]]></Name>\n" +
            "        <Type>1</Type>\n" +
            "        <Web>\n" +
            "            <Title><![CDATA[企业微信]]></Title>\n" +
            "            <Url><![CDATA[https://work.weixin.qq.com]]></Url>\n" +
            "        </Web>\n" +
            "        </Item>\n" +
            "    </ExtAttr>\n" +
            "</xml>\n";

    private String update_user = "<xml>\n" +
            "    <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
            "    <FromUserName><![CDATA[sys]]></FromUserName> \n" +
            "    <CreateTime>1403610513</CreateTime>\n" +
            "    <MsgType><![CDATA[event]]></MsgType>\n" +
            "    <Event><![CDATA[change_contact]]></Event>\n" +
            "    <ChangeType>update_user</ChangeType>\n" +
            "    <UserID><![CDATA[zhangsan]]></UserID>\n" +
            "    <NewUserID><![CDATA[zhangsan001]]></NewUserID>\n" +
            "    <Name><![CDATA[张三]]></Name>\n" +
            "    <Department><![CDATA[1,2,3]]></Department>\n" +
            "    <IsLeaderInDept><![CDATA[1,0,0]]></IsLeaderInDept>\n" +
            "    <Position><![CDATA[产品经理]]></Position>\n" +
            "    <Mobile>13800000000</Mobile>\n" +
            "    <Gender>1</Gender>\n" +
            "    <Email><![CDATA[zhangsan@gzdev.com]]></Email>\n" +
            "    <Status>1</Status>\n" +
            "    <Avatar><![CDATA[http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0]]></Avatar>\n" +
            "    <Alias><![CDATA[zhangsan]]></Alias>\n" +
            "    <Telephone><![CDATA[020-123456]]></Telephone>\n" +
            "    <Address><![CDATA[广州市]]></Address>\n" +
            "    <ExtAttr>\n" +
            "        <Item>\n" +
            "        <Name><![CDATA[爱好]]></Name>\n" +
            "        <Type>0</Type>\n" +
            "        <Text>\n" +
            "            <Value><![CDATA[旅游]]></Value>\n" +
            "        </Text>\n" +
            "        </Item>\n" +
            "        <Item>\n" +
            "        <Name><![CDATA[卡号]]></Name>\n" +
            "        <Type>1</Type>\n" +
            "        <Web>\n" +
            "            <Title><![CDATA[企业微信]]></Title>\n" +
            "            <Url><![CDATA[https://work.weixin.qq.com]]></Url>\n" +
            "        </Web>\n" +
            "        </Item>\n" +
            "    </ExtAttr>\n" +
            "</xml>\n";

    private String delete_user = "<xml>\n" +
            "    <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
            "    <FromUserName><![CDATA[sys]]></FromUserName> \n" +
            "    <CreateTime>1403610513</CreateTime>\n" +
            "    <MsgType><![CDATA[event]]></MsgType>\n" +
            "    <Event><![CDATA[change_contact]]></Event>\n" +
            "    <ChangeType>delete_user</ChangeType>\n" +
            "    <UserID><![CDATA[zhangsan]]></UserID>\n" +
            "</xml>\n";
    /**
     * 新增成员
     */
    public void test_createUser() {
        XStream xstream = new XStream();
        xstream.processAnnotations(new Class[]{Contact.class, ExtAttr.class, Item.class, ItemText.class});
        Contact contact = (Contact) xstream.fromXML(this.create_user);
        log.info("create_user: {}", JSONObject.toJSONString(contact));
    }

    /**
     * 更新成员
     */
    public void test_upadteUser() {
        XStream xstream = new XStream();
        xstream.processAnnotations(new Class[]{Contact.class, ExtAttr.class, Item.class, ItemText.class});
        Contact contact = (Contact) xstream.fromXML(this.update_user);
        log.info("update_user: {}", JSONObject.toJSONString(contact));
    }

    /**
     * 删除成员
     */
    public void test_deleteUser() {
        XStream xstream = new XStream();
        xstream.processAnnotations(new Class[]{Contact.class, ExtAttr.class, Item.class, ItemText.class});
        Contact contact = (Contact) xstream.fromXML(this.delete_user);
        log.info("delete_user: {}", JSONObject.toJSONString(contact));
    }

    private String create_party = "<xml>\n" +
            "    <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
            "    <FromUserName><![CDATA[sys]]></FromUserName> \n" +
            "    <CreateTime>1403610513</CreateTime>\n" +
            "    <MsgType><![CDATA[event]]></MsgType>\n" +
            "    <Event><![CDATA[change_contact]]></Event>\n" +
            "    <ChangeType>create_party</ChangeType>\n" +
            "    <Id>2</Id>\n" +
            "    <Name><![CDATA[张三]]></Name>\n" +
            "    <ParentId><![CDATA[1]]></ParentId>\n" +
            "    <Order>1</Order>\n" +
            "</xml>\n";

    private String update_party = "<xml>\n" +
            "    <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
            "    <FromUserName><![CDATA[sys]]></FromUserName> \n" +
            "    <CreateTime>1403610513</CreateTime>\n" +
            "    <MsgType><![CDATA[event]]></MsgType>\n" +
            "    <Event><![CDATA[change_contact]]></Event>\n" +
            "    <ChangeType>update_party</ChangeType>\n" +
            "    <Id>2</Id>\n" +
            "    <Name><![CDATA[张三]]></Name>\n" +
            "    <ParentId><![CDATA[1]]></ParentId>\n" +
            "</xml>\n";

    private String delete_party = "<xml>\n" +
            "    <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
            "    <FromUserName><![CDATA[sys]]></FromUserName> \n" +
            "    <CreateTime>1403610513</CreateTime>\n" +
            "    <MsgType><![CDATA[event]]></MsgType>\n" +
            "    <Event><![CDATA[change_contact]]></Event>\n" +
            "    <ChangeType>delete_party</ChangeType>\n" +
            "    <Id>2</Id>\n" +
            "</xml>\n";

    /**
     * 新增部门
     */
    public void test_createParty() {
        XStream xstream = new XStream();
        xstream.processAnnotations(new Class[]{Contact.class, ExtAttr.class, Item.class, ItemText.class});
        Contact contact = (Contact) xstream.fromXML(this.create_party);
        log.info("create_party: {}", JSONObject.toJSONString(contact));
    }

    /**
     * 更新部门
     */
    public void test_upadteParty() {
        XStream xstream = new XStream();
        xstream.processAnnotations(new Class[]{Contact.class, ExtAttr.class, Item.class, ItemText.class});
        Contact contact = (Contact) xstream.fromXML(this.update_party);
        log.info("update_party: {}", JSONObject.toJSONString(contact));
    }

    /**
     * 删除部门
     */
    public void test_deleteParty() {
        XStream xstream = new XStream();
        xstream.processAnnotations(new Class[]{Contact.class, ExtAttr.class, Item.class, ItemText.class});
        Contact contact = (Contact) xstream.fromXML(this.delete_party);
        log.info("delete_party: {}", JSONObject.toJSONString(contact));
    }

    private String update_tag = "<xml>\n" +
            "    <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
            "    <FromUserName><![CDATA[sys]]></FromUserName> \n" +
            "    <CreateTime>1403610513</CreateTime>\n" +
            "    <MsgType><![CDATA[event]]></MsgType>\n" +
            "    <Event><![CDATA[change_contact]]></Event>\n" +
            "    <ChangeType><![CDATA[update_tag]]></ChangeType>\n" +
            "    <TagId>1</TagId>\n" +
            "    <AddUserItems><![CDATA[zhangsan,lisi]]></AddUserItems>\n" +
            "    <DelUserItems><![CDATA[zhangsan1,lisi1]]></DelUserItems>\n" +
            "    <AddPartyItems><![CDATA[1,2]]></AddPartyItems>\n" +
            "    <DelPartyItems><![CDATA[3,4]]></DelPartyItems>\n" +
            "</xml>\n";

    public void test_updateTag() {
        XStream xstream = new XStream();
        xstream.processAnnotations(new Class[]{Contact.class, ExtAttr.class, Item.class, ItemText.class});
        Contact contact = (Contact) xstream.fromXML(this.update_tag);
        log.info("update_tag: {}", JSONObject.toJSONString(contact));
    }

    private String approvalMsg = "<xml><ToUserName><![CDATA[wwc5057e76805fa8ac]]></ToUserName><FromUserName><![CDATA[sys]]></FromUserName><CreateTime>1599189240</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[sys_approval_change]]></Event><AgentID>3010040</AgentID><ApprovalInfo><SpNo>202009040005</SpNo><SpName><![CDATA[测试]]></SpName><SpStatus>1</SpStatus><TemplateId><![CDATA[Bs7wRUBt1HhUXNvP3hVCmxBUqZrEyo2XnLakE3XFJ]]></TemplateId><ApplyTime>1599189240</ApplyTime><Applyer><UserId><![CDATA[cxw0615]]></UserId><Party><![CDATA[2]]></Party></Applyer><SpRecord><SpStatus>1</SpStatus><ApproverAttr>1</ApproverAttr><Details><Approver><UserId><![CDATA[TianQin]]></UserId></Approver><Speech><![CDATA[]]></Speech><SpStatus>1</SpStatus><SpTime>0</SpTime></Details></SpRecord><SpRecord><SpStatus>1</SpStatus><ApproverAttr>1</ApproverAttr><Details><Approver><UserId><![CDATA[zcl0615]]></UserId></Approver><Speech><![CDATA[]]></Speech><SpStatus>1</SpStatus><SpTime>0</SpTime></Details></SpRecord><SpRecord><SpStatus>1</SpStatus><ApproverAttr>2</ApproverAttr><Details><Approver><UserId><![CDATA[TianQin]]></UserId></Approver><Speech><![CDATA[]]></Speech><SpStatus>1</SpStatus><SpTime>0</SpTime></Details><Details><Approver><UserId><![CDATA[zcl0615]]></UserId></Approver><Speech><![CDATA[]]></Speech><SpStatus>1</SpStatus><SpTime>0</SpTime></Details></SpRecord><SpRecord><SpStatus>1</SpStatus><ApproverAttr>1</ApproverAttr><Details><Approver><UserId><![CDATA[TianQin]]></UserId></Approver><Speech><![CDATA[]]></Speech><SpStatus>1</SpStatus><SpTime>0</SpTime></Details></SpRecord><Notifyer><UserId><![CDATA[zcl0615]]></UserId></Notifyer><Notifyer><UserId><![CDATA[TianQin]]></UserId></Notifyer><Notifyer><UserId><![CDATA[zcl0615]]></UserId></Notifyer><StatuChangeEvent>1</StatuChangeEvent></ApprovalInfo></xml>";
    public void test_approval() {
        XStream xstream = new XStream();
        xstream.ignoreUnknownElements();
        xstream.autodetectAnnotations(true);
        //使用注解修改对象名称
        xstream.processAnnotations(new Class[]{Applyer.class, ApprovalInfo.class, Comments.class,
                CommentUserInfo.class, Details.class, Notifyer.class, SpRecord.class,
                ApprovalStatuChangeEvent.class});
        ApprovalStatuChangeEvent callbackMessage = (ApprovalStatuChangeEvent) xstream.fromXML(approvalMsg);

        log.info("testXml: {}", JSONObject.toJSONString(callbackMessage));
    }
}
