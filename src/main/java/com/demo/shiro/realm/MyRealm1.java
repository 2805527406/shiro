package com.demo.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class MyRealm1 implements Realm{

	//����һ��Ψһ��Realm����
	public String getName() {
		return "myrealm1";
	}

	//�жϴ�Realm�Ƿ�֧�ִ�Token
	public boolean supports(AuthenticationToken token) {
		//��֧�� UsernamePasswordToken ���͵� Token
		return token instanceof UsernamePasswordToken;
	}
	
	//����Token��ȡ��֤��Ϣ
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();//�õ��û���
		String password = new String((char[])token.getCredentials());//�õ�����
		System.out.println("realm:"+getName());
		System.out.println("�˺ţ�"+username);
		System.out.println("���룺"+password);
		System.out.println("��û�н����˺���֤Ŷ~");
		if(!"zhang".equals(username)) {
            throw new UnknownAccountException(); //����û�������
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //����������
        }
		//��������֤��֤�ɹ�������һ�� AuthenticationInfo ʵ�֣�
		return new SimpleAuthenticationInfo(username,password,getName());
	}

}
