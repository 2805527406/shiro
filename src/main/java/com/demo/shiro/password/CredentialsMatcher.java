package com.demo.shiro.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

public interface CredentialsMatcher {
	
	/**
	 * //ƥ���û������ token ��ƾ֤��δ���ܣ���ϵͳ�ṩ��ƾ֤���Ѽ��ܣ�
	 * @param token
	 * @param info
	 * @return
	 */
	boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info);
}
