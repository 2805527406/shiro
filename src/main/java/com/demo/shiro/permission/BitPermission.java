package com.demo.shiro.permission;

import org.apache.shiro.authz.Permission;

import com.alibaba.druid.util.StringUtils;
/**
 * BitPermission ����ʵ��λ�Ʒ�ʽ��Ȩ�ޣ�������ǣ�
	Ȩ���ַ�����ʽ��+��Դ�ַ���+Ȩ��λ+ʵ�� ID����+��ͷ �м�ͨ��+�ָȨ�ޣ�0 ��ʾ
	����Ȩ�ޣ�1 �����������ƣ�0001����2 �޸ģ������ƣ�0010����4 ɾ���������ƣ�0100����
	8 �鿴�������ƣ�1000������ +user+10 ��ʾ����Դ user ӵ���޸�/�鿴Ȩ�ޡ�
 * @author Administrator
 *
 */
public class BitPermission implements Permission{
	private String resourceIdentify;
	private int permissionBit;
	private String instanceId;
	
	public BitPermission(String permissionString) {
		String[] array = permissionString.split("\\+");
		 if(array.length > 1) {
		 resourceIdentify = array[1];
		 }
		 if(StringUtils.isEmpty(resourceIdentify)) {
		 resourceIdentify = "*";
		 }
		 if(array.length > 2) {
		 permissionBit = Integer.valueOf(array[2]);
		 }
		 if(array.length > 3) {
		 instanceId = array[3];
		 }
		 if(StringUtils.isEmpty(instanceId)) {
		 instanceId = "*";
		 } 
	}
	
	public boolean implies(Permission p) {
		if(!(p instanceof BitPermission)) {
			 return false;
			 }
			 BitPermission other = (BitPermission) p;
			 if(!("*".equals(this.resourceIdentify) ||
			this.resourceIdentify.equals(other.resourceIdentify))) {
			 return false;
			 }
			 if(!(this.permissionBit ==0 || (this.permissionBit & other.permissionBit) != 0)) {
			 return false;
			 }
			 if(!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))) {
			 return false;
			 }
			 return true;
	}

}
