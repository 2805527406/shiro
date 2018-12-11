package com.demo.shiro.permission;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm{
	/**
	 * 表示根据用户身份
			获取授权信息。
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		 authorizationInfo.addRole("role1");
		 authorizationInfo.addRole("role2");
		 authorizationInfo.addObjectPermission(new BitPermission("+user1+10"));
		 authorizationInfo.addObjectPermission(new WildcardPermission("user1:*")); 
		 authorizationInfo.addStringPermission("+user2+10");
		 authorizationInfo.addStringPermission("user2:*");
		 return authorizationInfo; 
	}

	/**
	 * 表示获取身份验证信
		息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		   String username = (String)token.getPrincipal();  //得到用户名
	        String password = new String((char[])token.getCredentials()); //得到密码
	        if(!"zhang".equals(username)) {
	            throw new UnknownAccountException(); //如果用户名错误
	        }
	        if(!"123".equals(password)) {
	            throw new IncorrectCredentialsException(); //如果密码错误
	        }
	        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
	        return new SimpleAuthenticationInfo(username, password, getName());
	}

}
