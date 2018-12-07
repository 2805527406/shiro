package com.demo1;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 身份验证
 * @author Administrator
 *
 */
public class DemoTest1 {
	
	/**
	 * 认证流程：
	 * 	1.调用Subject.login(token)进行登录，其会自动委托给SecurityManager，调用之前必须通过SecurityUtils.setSecurityManager()设置
	 *  2.SecurityManager负责身份验证逻辑：它会委托给Authenticator进行身份验证
	 *  3.Authenticator才是真正的验证这，Shiro API中核心的身份认证入口点，此处可以自定义插入自己的实现
	 *  4.Authenticator可能会委托给相应的AuthenticationStrategy进行多Realm身份验证，默认ModularRealmAuthenticator会调用AuthenticationStragegy进行多Realm身份验证
	 *  5.Authenticator会把相应的token传入Realm，从Realm获取身份验证信息，如果没有返回/抛出异常表示身份验证失败了。此处可以配置多个Realm，将按照相应的顺序及策略进行访问
	 */
	@Test
	public void testHelloWorld() {
		//1.获取SecurityManager工厂，用ini配置文件初始化SecurityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		//2.得到SecurutyManager实例，并绑定给Securityutils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//3.得到Subject及创建用户名、密码身份验证Token
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		try {
			//4.登录
			subject.login(token);
		} catch (Exception e) {
			//AuthenticationException 身份验证失败异常-子类：
				//--DisabledAccountException 禁用的账号
				//--LockedAccountException 锁定的账号
				//--UnknownAccountException 错误的账号
				//--ExcessiveAttemptsException 登录失败次数过多
				//--IncorrectCredentialsException 错误的凭证/密码错误
				//--ExpiredCredentialsException 过期的凭证
			e.printStackTrace();
		}
		System.err.println("账号是否登录："+subject.isAuthenticated());
		//5.退出
		subject.logout();
		System.err.println("账号是否退出："+subject.isAuthenticated());
	}
	
	/**
	 * 自定义realm--单realm配置
	 */
	@Test
	public void testCustomRealm() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("123213","123");
		subject.login(token);
	}
	/**
	 * 自定义realm--多realm配置
	 */
	@Test
	public void testMultiRealm() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-multi-realm.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("123213","123");
		subject.login(token);
	}
	
	/**
	 * 自定义realm--jdbc配置
	 */
	@Test
	public void testJDBCRealm() {
		Factory<SecurityManager> factory = 
				new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		subject.login(token);
		System.out.println(subject.isAuthenticated());
	}
	
	/**
	 * 自定义验证策略
	 */
	public void login(String configFile) {
		Factory<SecurityManager> factory = 
				new IniSecurityManagerFactory(configFile);
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		subject.login(token);
	}

	//测试AllSuccessfulStrategy成功
	@Test
	public void testAllSuccessfulStrategyWithSuccess() {
		login("classpath:shiro-authenticator-all-success.ini");
		Subject subject = SecurityUtils.getSubject();
		//得到一个身份集合，其包含了Realm验证成功的身份信息
		PrincipalCollection principalCollection = subject.getPrincipals();
		List list = principalCollection.asList();
		System.out.println(list);
	}
	
	
	
	
	
	
}
