package com.demo3;

import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;

/**
 * 编码/解码
 * @author Administrator
 *
 */
public class EncodeTest {
	//------------------------编码/解码----------------------------------------------------------------------
	@Test
	public void testBase64Encode() {
		String string = "zhangx";
		String base64Str = Base64.encodeToString(string.getBytes());
		String str2 = Base64.decodeToString(base64Str);
		System.out.println(base64Str+","+str2);
	}
	
	@Test
	public void testMd5Encode() {
		String string="zhangx";
		String salt = "123";
		String sha1 = new Md5Hash(string,salt).toString();
		System.out.println(sha1);
	}

	/**
	 * 	为了方便使用，Shiro 提供了 HashService，默认提供了 DefaultHashService 实现
	 * 1、首先创建一个 DefaultHashService，默认使用 SHA-512 算法；
		2、可以通过 hashAlgorithmName 属性修改算法；
		3、可以通过 privateSalt 设置一个私盐，其在散列时自动与用户传入的公盐混合产生一个新盐
		4、可以通过 generatePublicSalt 属性在用户没有传入公盐的情况下是否生成公盐；
		5、可以设置 randomNumberGenerator 用于生成公盐；
		6、可以设置 hashIterations 属性来修改默认加密迭代次数；
		7、需要构建一个 HashRequest，传入算法、数据、公盐、迭代次数。
	 */
	@Test
	public void defaultShiroHash() {
		DefaultHashService hashService = new DefaultHashService(); //默认算法 SHA-512 
		hashService.setHashAlgorithmName("SHA-512");
		hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
		hashService.setGeneratePublicSalt(true);//是否生成公盐，默认 false
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
		hashService.setHashIterations(1); //生成 Hash 值的迭代次数
		HashRequest request = new HashRequest.Builder()
		 .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
		 .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
		String hex = hashService.computeHash(request).toHex();
	}
	
	//------------------加密/解密----------------------------------------------------------------------------
	@Test
	public void testAes() {
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(128); //设置 key 长度
		//生成 key
		Key key = aesCipherService.generateNewKey();
		String text = "hello";
		//加密
		String encrptText =
		aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
		//解密
		String text2 =
		new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
		System.out.println(encrptText+","+text2);
	}
}
