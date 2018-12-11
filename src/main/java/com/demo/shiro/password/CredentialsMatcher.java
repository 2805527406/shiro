package com.demo.shiro.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

public interface CredentialsMatcher {
	
	/**
	 * //匹配用户输入的 token 的凭证（未加密）与系统提供的凭证（已加密）
	 * @param token
	 * @param info
	 * @return
	 */
	boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info);
}
