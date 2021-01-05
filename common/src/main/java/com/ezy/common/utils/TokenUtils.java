package com.ezy.common.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.ezy.common.model.TokenVerifyResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @Author: Kevin Liu
 * @CreateDate: 2020/7/14 17:32
 * @Desc JWT工具类
 * @Version: 1.0
 */
public class TokenUtils {

	/**
	 * 加密后的key，数据存放在的配置中心
	 */
	private static String secretKey;

	/**
	 * 超时时间，数据存放在的配置中心
	 */
	private static Integer timeout;

	/**
	 * 刷新token的预警时间，数据存放在的配置中心
	 */
	private static Integer refreshMillis;

	public static TokenUtils newInstance(Integer timeout, Integer refreshMillis, String secretKey) {
		TokenUtils.secretKey = secretKey;
		TokenUtils.timeout = timeout;
		TokenUtils.refreshMillis = refreshMillis;
		return new TokenUtils();
	}

	public String createToken(String json) {
		//指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		//生成JWT的时间
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
		Map<String, Object> claims = new HashMap<String, Object>(4);
		claims.put("timeout", timeout);
		claims.put("needFreshMillis", refreshMillis);

		//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，
		// 切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
		// 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
		SecretKey key = generalKey(secretKey);
		//下面就是在为payload添加各种标准声明和私有声明了
		//这里其实就是new一个JwtBuilder，设置jwt的body
		//如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
		JwtBuilder builder = Jwts.builder()
				.setClaims(claims)
				//设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
				.setId(UUID.randomUUID().toString())
				//iat: jwt的签发时间
				.setIssuedAt(now)
				//sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
				.setSubject(json)
				//设置签名使用的签名算法和签名使用的秘钥
				.signWith(signatureAlgorithm, key);
		return builder.compact();
	}

	/**
	 * 解密jwt
	 *
	 * @param jwt
	 * @return
	 */
	public Claims parseToken(String jwt) {
		//签名秘钥，和生成的签名的秘钥一模一样
		SecretKey key = generalKey(secretKey);
		//得到DefaultJwtParser
		return Jwts.parser()
				//设置签名的秘钥
				.setSigningKey(key)
				//设置需要解析的jwt
				.parseClaimsJws(jwt).getBody();
	}

	/**
	 * 由字符串生成加密key
	 *
	 * @return
	 */
	private SecretKey generalKey(String secretKey) {
		//本地的密码解码
		byte[] encodedKey = Base64.decodeBase64(secretKey);
		// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。
		return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	}

	public TokenVerifyResult verifyJWT(String jwt) {
		Claims claims;
		try {
			claims = parseToken(jwt);
		} catch (Exception ex) {
			return new TokenVerifyResult(false, "解析失败");
		}
		Date startDate = claims.getIssuedAt();
		Date now = Calendar.getInstance().getTime();
		long between = DateUtil.between(startDate, now, DateUnit.MS);
		if (between > timeout) {
			return new TokenVerifyResult(false, "操作超时, 请重新登陆!", null);
		}

		long endMillis = startDate.getTime() + timeout - now.getTime();
		if (endMillis < refreshMillis) {
			return new TokenVerifyResult(false, "Token已临近刷新时间点, 重新刷新Token!", createToken(claims.getSubject()));
		}
		return new TokenVerifyResult(claims.getSubject());

	}

}
