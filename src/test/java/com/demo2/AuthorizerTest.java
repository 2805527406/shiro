package com.demo2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class AuthorizerTest{
	/**
	 * ��¼��������
	 * @param configFile
	 */
	public void login(String configFile,String user,String password) {
		Factory<SecurityManager> factory = 
				new IniSecurityManagerFactory(configFile);
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user,password);
		subject.login(token);
	}
	public void login(String configFile) {
		login(configFile,"zhang","123");
	}
	
	@Test
	public void testIsPermitted() {
		login("classpath:role/shiro-authorizer.ini", "zhang", "123"); 
		Subject subject = SecurityUtils.getSubject();
		Assert.assertTrue(subject.isPermitted("user1:update"));
		 Assert.assertTrue(subject.isPermitted("user2:update"));
		 //ͨ��������λ�ķ�ʽ��ʾȨ��
		 Assert.assertTrue(subject.isPermitted("+user1+2"));//����Ȩ��
		 Assert.assertTrue(subject.isPermitted("+user1+8"));//�鿴Ȩ��
		 Assert.assertTrue(subject.isPermitted("+user2+10"));//�������鿴
		 Assert.assertFalse(subject.isPermitted("+user1+4"));//û��ɾ��Ȩ��
		 Assert.assertTrue(subject.isPermitted("menu:view"));//ͨ ��MyRolePermissionResolver �����õ���Ȩ��

	}
}
