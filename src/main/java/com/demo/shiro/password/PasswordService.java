package com.demo.shiro.password;

public interface PasswordService {
	
	/**
	 * //输入明文密码得到密文密码
	 * @param plaintextPassword 明文密码
	 * @return
	 */
	String encryptPassword(Object plaintextPassword);
}
