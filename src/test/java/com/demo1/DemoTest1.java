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
 * �����֤
 * @author Administrator
 *
 */
public class DemoTest1 {
	
	/**
	 * ��֤���̣�
	 * 	1.����Subject.login(token)���е�¼������Զ�ί�и�SecurityManager������֮ǰ����ͨ��SecurityUtils.setSecurityManager()����
	 *  2.SecurityManager���������֤�߼�������ί�и�Authenticator���������֤
	 *  3.Authenticator������������֤�⣬Shiro API�к��ĵ������֤��ڵ㣬�˴������Զ�������Լ���ʵ��
	 *  4.Authenticator���ܻ�ί�и���Ӧ��AuthenticationStrategy���ж�Realm�����֤��Ĭ��ModularRealmAuthenticator�����AuthenticationStragegy���ж�Realm�����֤
	 *  5.Authenticator�����Ӧ��token����Realm����Realm��ȡ�����֤��Ϣ�����û�з���/�׳��쳣��ʾ�����֤ʧ���ˡ��˴��������ö��Realm����������Ӧ��˳�򼰲��Խ��з���
	 */
	@Test
	public void testHelloWorld() {
		//1.��ȡSecurityManager��������ini�����ļ���ʼ��SecurityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		//2.�õ�SecurutyManagerʵ�������󶨸�Securityutils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//3.�õ�Subject�������û��������������֤Token
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		try {
			//4.��¼
			subject.login(token);
		} catch (Exception e) {
			//AuthenticationException �����֤ʧ���쳣-���ࣺ
				//--DisabledAccountException ���õ��˺�
				//--LockedAccountException �������˺�
				//--UnknownAccountException ������˺�
				//--ExcessiveAttemptsException ��¼ʧ�ܴ�������
				//--IncorrectCredentialsException �����ƾ֤/�������
				//--ExpiredCredentialsException ���ڵ�ƾ֤
			e.printStackTrace();
		}
		System.err.println("�˺��Ƿ��¼��"+subject.isAuthenticated());
		//5.�˳�
		subject.logout();
		System.err.println("�˺��Ƿ��˳���"+subject.isAuthenticated());
	}
	
	/**
	 * �Զ���realm--��realm����
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
	 * �Զ���realm--��realm����
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
	 * �Զ���realm--jdbc����
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
	 * �Զ�����֤����
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

	//����AllSuccessfulStrategy�ɹ�
	@Test
	public void testAllSuccessfulStrategyWithSuccess() {
		login("classpath:shiro-authenticator-all-success.ini");
		Subject subject = SecurityUtils.getSubject();
		//�õ�һ����ݼ��ϣ��������Realm��֤�ɹ��������Ϣ
		PrincipalCollection principalCollection = subject.getPrincipals();
		List list = principalCollection.asList();
		System.out.println(list);
	}
	
	
	
	
	
	
}
