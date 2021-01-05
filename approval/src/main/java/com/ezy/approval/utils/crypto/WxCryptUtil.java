package com.ezy.approval.utils.crypto;

import org.apache.tomcat.util.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Caixiaowei
 * @ClassName WxCryptUtil.java
 * @Description 微信报文工具类
 * @createTime 2020年07月01日 10:58:00
 */
public class WxCryptUtil {

    private static final Base64 BASE64 = new Base64();
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final ThreadLocal<DocumentBuilder> BUILDER_LOCAL = new ThreadLocal<DocumentBuilder>() {
        @Override
        protected DocumentBuilder initialValue() {
            try {
                final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setExpandEntityReferences(false);
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                return factory.newDocumentBuilder();
            } catch (ParserConfigurationException exc) {
                throw new IllegalArgumentException(exc);
            }
        }
    };

    protected byte[] aesKey;
    protected String token;
    protected String corpid;

    public WxCryptUtil() {
    }

    /**
     * 有参构造
     *
     * @param token          开发者设置的token
     * @param encodingAesKey 开发者设置的EncodingAESKey
     * @param corpid         企业ID
     * @return
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/1 11:03
     */
    public WxCryptUtil(String token, String encodingAesKey, String corpid) {
        this.token = token;
        this.corpid = corpid;
        this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
    }

    /**
     * 报文解密
     *
     * @param msgSignature
     * @param timeStamp
     * @param nonce
     * @param encryptedXml
     * @return String 解密后的明文
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/1 11:08
     */
    public String decrypt(String msgSignature, String timeStamp, String nonce, String encryptedXml) {
        // 密钥，公众账号的app corpSecret
        // 提取密文
        String cipherText = extractEncryptPart(encryptedXml);

        // 验证安全签名
        String signature = SHA1.gen(this.token, timeStamp, nonce, cipherText);
        if (!signature.equals(msgSignature)) {
            throw new RuntimeException("加密消息签名校验失败");
        }

        // 解密
        return decrypt(cipherText);
    }

    private static String extractEncryptPart(String xml) {
        try {
            DocumentBuilder db = BUILDER_LOCAL.get();
            Document document = db.parse(new InputSource(new StringReader(xml)));

            Element root = document.getDocumentElement();
            return root.getElementsByTagName("Encrypt").item(0).getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对密文进行解密
     *
     * @param cipherText 密文
     * @return String 解密后的明文
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/1 11:11
     */
    public String decrypt(String cipherText) {
        byte[] original;
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(this.aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(this.aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(cipherText);

            // 解密
            original = cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String xmlContent;
        String fromAppid;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);

            // 分离16位随机字符串,网络字节序和AppId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

            int xmlLength = bytesNetworkOrder2Number(networkOrder);

            xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
            fromAppid = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // appid不相同的情况 暂时忽略这段判断
//    if (!fromAppid.equals(this.appidOrCorpid)) {
//      throw new RuntimeException("AppID不正确，请核实！");
//    }

        return xmlContent;

    }

    /**
     * 消息加密和安全签名
     *
     * @param
     * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce, encrypt的xml格式的字符串
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/1 11:21
     */
    public String encrypt(String plainText) {
        // 加密
        String encryptedXml = encrypt(genRandomStr(), plainText);

        // 生成安全签名
        String timeStamp = Long.toString(System.currentTimeMillis() / 1000L);
        String nonce = genRandomStr();

        String signature = SHA1.gen(this.token, timeStamp, nonce, encryptedXml);
        return generateXml(encryptedXml, signature, timeStamp, nonce);
    }

    /**
     * 生成xml 消息
     *
     * @param encrypt   加密后的消息密文
     * @param signature 安全签名
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @return 生成的xml字符串
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/1 11:20
     */
    private static String generateXml(String encrypt, String signature, String timestamp, String nonce) {
        String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
                + "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
                + "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n"
                + "</xml>";
        return String.format(format, encrypt, signature, timestamp, nonce);
    }

    /**
     * 报文加密
     *
     * @param randomStr 随机字符串
     * @param plainText 需要加密的明文
     * @return String 加密后base64编码的字符串
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/1 11:17
     */
    protected String encrypt(String randomStr, String plainText) {
        ByteGroup byteCollector = new ByteGroup();
        byte[] randomStringBytes = randomStr.getBytes(CHARSET);
        byte[] plainTextBytes = plainText.getBytes(CHARSET);
        byte[] bytesOfSizeInNetworkOrder = number2BytesInNetworkOrder(plainTextBytes.length);
        byte[] appIdBytes = this.corpid.getBytes(CHARSET);

        // randomStr + networkBytesOrder + text + appid
        byteCollector.addBytes(randomStringBytes);
        byteCollector.addBytes(bytesOfSizeInNetworkOrder);
        byteCollector.addBytes(plainTextBytes);
        byteCollector.addBytes(appIdBytes);

        // ... + pad: 使用自定义的填充方式对明文进行补位填充
        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);

        // 获得最终的字节流, 未加密
        byte[] unencrypted = byteCollector.toBytes();

        try {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(this.aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(this.aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            // 加密
            byte[] encrypted = cipher.doFinal(unencrypted);

            // 使用BASE64对加密后的字符串进行编码
            return BASE64.encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成随机字符串
     *
     * @param
     * @return String 随机字符串
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/1 11:19
     */
    private static String genRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 将一个数字转换成生成4个字节的网络字节序bytes数组.
     *
     * @param number 待转换的数字
     * @return byte[] 4个字节的网络字节序bytes数组.
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/1 11:16
     */
    private static byte[] number2BytesInNetworkOrder(int number) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (number & 0xFF);
        orderBytes[2] = (byte) (number >> 8 & 0xFF);
        orderBytes[1] = (byte) (number >> 16 & 0xFF);
        orderBytes[0] = (byte) (number >> 24 & 0xFF);
        return orderBytes;
    }

    /**
     * 4个字节的网络字节序bytes数组还原成一个数字
     *
     * @param bytesInNetworkOrder 4个字节的网络字节序bytes数组
     * @return int 还原后的数字
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/1 11:12
     */
    private static int bytesNetworkOrder2Number(byte[] bytesInNetworkOrder) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++) {
            sourceNumber <<= 8;
            sourceNumber |= bytesInNetworkOrder[i] & 0xff;
        }
        return sourceNumber;
    }
}
