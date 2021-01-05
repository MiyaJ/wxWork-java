# wxWork-java
对接企业微信的Java微服务，采用spring cloud alibaba 方案。

## approval 审批
- 对接企业微信审批应用，包括审批模板管理，审批单管理。

## callback 回调
- 企业微信回调通知处理。
- 采用rabbit mq 搭建消息中心，解析回调报文，将内容通过mq 推送到业务服务。
- 后面会采用rocket mq 来替代rabbit mq

## common 公共通用模块，包含model Java类，工具类等