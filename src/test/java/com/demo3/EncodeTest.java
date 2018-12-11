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
 * ����/����
 * @author Administrator
 *
 */
public class EncodeTest {
	//------------------------����/����----------------------------------------------------------------------
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
	 * 	Ϊ�˷���ʹ�ã�Shiro �ṩ�� HashService��Ĭ���ṩ�� DefaultHashService ʵ��
	 * 1�����ȴ���һ�� DefaultHashService��Ĭ��ʹ�� SHA-512 �㷨��
		2������ͨ�� hashAlgorithmName �����޸��㷨��
		3������ͨ�� privateSalt ����һ��˽�Σ�����ɢ��ʱ�Զ����û�����Ĺ��λ�ϲ���һ������
		4������ͨ�� generatePublicSalt �������û�û�д��빫�ε�������Ƿ����ɹ��Σ�
		5���������� randomNumberGenerator �������ɹ��Σ�
		6���������� hashIterations �������޸�Ĭ�ϼ��ܵ���������
		7����Ҫ����һ�� HashRequest�������㷨�����ݡ����Ρ�����������
	 */
	@Test
	public void defaultShiroHash() {
		DefaultHashService hashService = new DefaultHashService(); //Ĭ���㷨 SHA-512 
		hashService.setHashAlgorithmName("SHA-512");
		hashService.setPrivateSalt(new SimpleByteSource("123")); //˽�Σ�Ĭ����
		hashService.setGeneratePublicSalt(true);//�Ƿ����ɹ��Σ�Ĭ�� false
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//�������ɹ��Ρ�Ĭ�Ͼ����
		hashService.setHashIterations(1); //���� Hash ֵ�ĵ�������
		HashRequest request = new HashRequest.Builder()
		 .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
		 .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
		String hex = hashService.computeHash(request).toHex();
	}
	
	//------------------����/����----------------------------------------------------------------------------
	@Test
	public void testAes() {
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(128); //���� key ����
		//���� key
		Key key = aesCipherService.generateNewKey();
		String text = "hello";
		//����
		String encrptText =
		aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
		//����
		String text2 =
		new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
		System.out.println(encrptText+","+text2);
	}
}
