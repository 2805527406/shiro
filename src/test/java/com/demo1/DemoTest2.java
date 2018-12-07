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
 * 权限
 * @author Administrator
 *
 */
public class DemoTest2 {

	/**
	 * shiro 支持三种方式的授权
	 * 1.编程式：
	 * 		Subject subject = SecurityUtils.getSubject();
	 * 		if(subjec.hasRole("admin")){
	 * 			//有权限
	 * 		}else{
	 * 			//无权限
	 * 		}
	 * 
	 * 2.注解式：
	 * 		@RequiresRoles("admin")
	 * 		public void hello(){//有权限}
	 * 
	 * 3.JSP标签
	 * 		<shiro:hasRole name="admin"><!--有权限--></shiro:hasRole>
	 */
	/**
	 * 登录基本步骤
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
	 * 判断用户角色（不会抛出异常）
	 */
	@Test
	public void testHasRole() {
		login("classpath:role/shiro-role.ini");
		Subject subject = SecurityUtils.getSubject();
		System.out.println("是否拥有role1:"+subject.hasRole("role1"));
		System.out.println("是否拥有role2:"+subject.hasAllRoles(Arrays.asList("role1","role3")));
		System.out.println("是否拥有role3:"+subject.hasRoles(Arrays.asList("role1","role3")));
	}
	
	/**
	 * 判断用户角色(没有角色 抛出异常)
	 */
	@Test
	public void testCheckRole() {
		login("classpath:role/shiro-role.ini");
		Subject subject = SecurityUtils.getSubject();
		subject.checkRole("role1");
		subject.checkRoles("role1","role3");
	}
	
	/**
	 * 判断用户角色是否拥有权限--不抛出异常
	 * isPermitted--返回boolean类型 不抛出异常
	 * checkPermission--没有返回值 抛出异常
	 * 
	 */
	@Test
	public void testIsPermitted() {
		login("classpath:role/shiro-permission.ini","zhang","123");
		Subject subject = SecurityUtils.getSubject();
		System.out.println("账号是否有权限1："+subject.isPermitted("user:create"));
		System.out.println("账号是否有权限2："+subject.isPermittedAll("user:update","user:delete"));
		System.out.println("账号是否有权限3："+subject.isPermittedAll("user:delete"));
		subject.checkPermission("user:create"); 
	}
	
	/**
	 * 字符串通配符权限
	 */
	@Test
	public void testPermission() {
		/**
		 * “:”表示资源/操作/实例的分割；“,”表示操作的分割；“*”表示任意资源/操作/实例。
		 * 1.单个资源权限
		 * 		subject().checkPermissions("system:user:update"); 
		 * 2.单个资源多权限
		 * 		ini文件配置
		 * 		role41=system:user:update,system:user:delete
		 * 		代码判断
		 * 		subject().checkPermissions("system:user:update", "system:user:delete"); 
		 * 			
		 * 		简写：
		 * 		role42="system:user:update,delete"
		 * 		subject().checkPermissions("system:user:update,delete"); 
		 * 3.全部资源权限
		 * 		role52=system:user:* 或 role53=system:user 
		 * 		subject().checkPermissions("system:user:*");
		 *		subject().checkPermissions("system:user"); 
		 * 4.所有资源权限：
		 * 		role61=*:view 
		 * 		subject().checkPermissions("user:view"); 
		 * 		用户拥有所有资源的“view”所有权限。假设判断的权限是“"system:user:view”，那么需
		 *   要“role5=*:*:view”这样写才行。
		 *   单个实例 所有权限：user:*:1
		 *   所有实例 单个权限：user:auth:*
		 * 5.Shiro 对权限字符串缺失部分的处理
		 * 		如“user:view”等价于“user:view:*”；而“organization”等价于“organization:*”或者
		 *		“organization:*:*”。可以这么理解，这种方式实现了前缀匹配。
		 *		另外如“user:*”可以匹配如“user:delete”、“user:delete”可以匹配如“user:delete:1”、
		 *		“user:*:1”可以匹配如“user:view:1”、“user”可以匹配“user:view”或“user:view:1”
		 *		等 。即 *可 以 匹配所 有， 不加 *可 以 进行前 缀匹 配；但 是如“ *:view” 不能匹 配
		 *		“system:user:view”，需要使用“*:*:view”，即后缀匹配必须指定前缀（多个冒号就需要
		 *		多个*来匹配）。
		 */
	}
}
