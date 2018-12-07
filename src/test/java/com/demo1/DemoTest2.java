package com.demo1;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * Ȩ��
 * @author Administrator
 *
 */
public class DemoTest2 {

	/**
	 * shiro ֧�����ַ�ʽ����Ȩ
	 * 1.���ʽ��
	 * 		Subject subject = SecurityUtils.getSubject();
	 * 		if(subjec.hasRole("admin")){
	 * 			//��Ȩ��
	 * 		}else{
	 * 			//��Ȩ��
	 * 		}
	 * 
	 * 2.ע��ʽ��
	 * 		@RequiresRoles("admin")
	 * 		public void hello(){//��Ȩ��}
	 * 
	 * 3.JSP��ǩ
	 * 		<shiro:hasRole name="admin"><!--��Ȩ��--></shiro:hasRole>
	 */
	/**
	 * ��¼��������
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
	
	/**
	 * �ж��û���ɫ�������׳��쳣��
	 */
	@Test
	public void testHasRole() {
		login("classpath:role/shiro-role.ini");
		Subject subject = SecurityUtils.getSubject();
		System.out.println("�Ƿ�ӵ��role1:"+subject.hasRole("role1"));
		System.out.println("�Ƿ�ӵ��role2:"+subject.hasAllRoles(Arrays.asList("role1","role3")));
		System.out.println("�Ƿ�ӵ��role3:"+subject.hasRoles(Arrays.asList("role1","role3")));
	}
	
	/**
	 * �ж��û���ɫ(û�н�ɫ �׳��쳣)
	 */
	@Test
	public void testCheckRole() {
		login("classpath:role/shiro-role.ini");
		Subject subject = SecurityUtils.getSubject();
		subject.checkRole("role1");
		subject.checkRoles("role1","role3");
	}
	
	/**
	 * �ж��û���ɫ�Ƿ�ӵ��Ȩ��--���׳��쳣
	 * isPermitted--����boolean���� ���׳��쳣
	 * checkPermission--û�з���ֵ �׳��쳣
	 * 
	 */
	@Test
	public void testIsPermitted() {
		login("classpath:role/shiro-permission.ini","zhang","123");
		Subject subject = SecurityUtils.getSubject();
		System.out.println("�˺��Ƿ���Ȩ��1��"+subject.isPermitted("user:create"));
		System.out.println("�˺��Ƿ���Ȩ��2��"+subject.isPermittedAll("user:update","user:delete"));
		System.out.println("�˺��Ƿ���Ȩ��3��"+subject.isPermittedAll("user:delete"));
		subject.checkPermission("user:create"); 
	}
	
	/**
	 * �ַ���ͨ���Ȩ��
	 */
	@Test
	public void testPermission() {
		/**
		 * ��:����ʾ��Դ/����/ʵ���ķָ��,����ʾ�����ķָ��*����ʾ������Դ/����/ʵ����
		 * 1.������ԴȨ��
		 * 		subject().checkPermissions("system:user:update"); 
		 * 2.������Դ��Ȩ��
		 * 		ini�ļ�����
		 * 		role41=system:user:update,system:user:delete
		 * 		�����ж�
		 * 		subject().checkPermissions("system:user:update", "system:user:delete"); 
		 * 			
		 * 		��д��
		 * 		role42="system:user:update,delete"
		 * 		subject().checkPermissions("system:user:update,delete"); 
		 * 3.ȫ����ԴȨ��
		 * 		role52=system:user:* �� role53=system:user 
		 * 		subject().checkPermissions("system:user:*");
		 *		subject().checkPermissions("system:user"); 
		 * 4.������ԴȨ�ޣ�
		 * 		role61=*:view 
		 * 		subject().checkPermissions("user:view"); 
		 * 		�û�ӵ��������Դ�ġ�view������Ȩ�ޡ������жϵ�Ȩ���ǡ�"system:user:view������ô��
		 *   Ҫ��role5=*:*:view������д���С�
		 *   ����ʵ�� ����Ȩ�ޣ�user:*:1
		 *   ����ʵ�� ����Ȩ�ޣ�user:auth:*
		 * 5.Shiro ��Ȩ���ַ���ȱʧ���ֵĴ���
		 * 		�硰user:view���ȼ��ڡ�user:view:*��������organization���ȼ��ڡ�organization:*������
		 *		��organization:*:*����������ô��⣬���ַ�ʽʵ����ǰ׺ƥ�䡣
		 *		�����硰user:*������ƥ���硰user:delete������user:delete������ƥ���硰user:delete:1����
		 *		��user:*:1������ƥ���硰user:view:1������user������ƥ�䡰user:view����user:view:1��
		 *		�� ���� *�� �� ƥ���� �У� ���� *�� �� ����ǰ ׺ƥ �䣻�� ���硰 *:view�� ����ƥ ��
		 *		��system:user:view������Ҫʹ�á�*:*:view��������׺ƥ�����ָ��ǰ׺�����ð�ž���Ҫ
		 *		���*��ƥ�䣩��
		 */
	}
}
