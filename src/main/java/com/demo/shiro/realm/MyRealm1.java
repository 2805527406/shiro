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

	//返回一个唯一的Realm名字
	public String getName() {
		return "myrealm1";
	}

	//判断此Realm是否支持此Token
	public boolean supports(AuthenticationToken token) {
		//仅支持 UsernamePasswordToken 类型的 Token
		return token instanceof UsernamePasswordToken;
	}
	
	//根据Token获取认证信息
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();//得到用户名
		String password = new String((char[])token.getCredentials());//得到密码
		System.out.println("realm:"+getName());
		System.out.println("账号："+username);
		System.out.println("密码："+password);
		System.out.println("我没有进行账号验证哦~");
		if(!"zhang".equals(username)) {
            throw new UnknownAccountException(); //如果用户名错误
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //如果密码错误
        }
		//如果身份认证验证成功，返回一个 AuthenticationInfo 实现；
		return new SimpleAuthenticationInfo(username,password,getName());
	}

}
