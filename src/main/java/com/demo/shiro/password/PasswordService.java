package com.demo.shiro.password;

public interface PasswordService {
	
	/**
	 * //������������õ���������
	 * @param plaintextPassword ��������
	 * @return
	 */
	String encryptPassword(Object plaintextPassword);
}
