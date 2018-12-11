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
	 * 登录基本步骤
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
		 //通过二进制位的方式表示权限
		 Assert.assertTrue(subject.isPermitted("+user1+2"));//新增权限
		 Assert.assertTrue(subject.isPermitted("+user1+8"));//查看权限
		 Assert.assertTrue(subject.isPermitted("+user2+10"));//新增及查看
		 Assert.assertFalse(subject.isPermitted("+user1+4"));//没有删除权限
		 Assert.assertTrue(subject.isPermitted("menu:view"));//通 过MyRolePermissionResolver 解析得到的权限

	}
}
