package com.cst.test.utils.gson;
/**
 * field 
 * @author MonsterStorm
 *
 */
public class Field {
	class Field_Desc {
		private Long nid;// int(10) ����ID���ID
		private String value;// longtext ��������ݡ�
		private String format;// varchar(255) ������ʽ��

		public Long getNid() {
			return nid;
		}

		public void setNid(Long nid) {
			this.nid = nid;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getFormat() {
			return format;
		}

		public void setFormat(String format) {
			this.format = format;
		}

	}

	class Field_Type {
		private Long nid;// int(10) ����ID���ID��
		private String value;// varchar(255) ����͡�ֵΪ��1������2������3����

		public Long getNid() {
			return nid;
		}

		public void setNid(Long nid) {
			this.nid = nid;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	class Field_Poster {
		private Long nid;// int(10);// ����ID���ID��
		private Long fid;// int(10);// �ļ�ID��

		public Long getNid() {
			return nid;
		}

		public void setNid(Long nid) {
			this.nid = nid;
		}

		public Long getFid() {
			return fid;
		}

		public void setFid(Long fid) {
			this.fid = fid;
		}
	}

	class Field_Photos {
		private Long nid;// int(10) ����ID���ID��
		private Long fid;// int(10) �ļ�ID��

		public Long getNid() {
			return nid;
		}

		public void setNid(Long nid) {
			this.nid = nid;
		}

		public Long getFid() {
			return fid;
		}

		public void setFid(Long fid) {
			this.fid = fid;
		}

	}

	class Field_Start {
		private Long nid;// int(10) ����ID���ID��
		private Long value;// int(11) ���ʼʱ�䡣

		public Long getNid() {
			return nid;
		}

		public void setNid(Long nid) {
			this.nid = nid;
		}

		public Long getValue() {
			return value;
		}

		public void setValue(Long value) {
			this.value = value;
		}

	}

	class Field_End {
		private Long nid;// int(10) ����ID���ID��
		private Long value;// int(11) �����ʱ�䡣

		public Long getNid() {
			return nid;
		}

		public void setNid(Long nid) {
			this.nid = nid;
		}

		public Long getValue() {
			return value;
		}

		public void setValue(Long value) {
			this.value = value;
		}

	}

	class Field_Address {
		private Long nid;// int(10) ����ID���ID��
		private String value;// varchar(255) ���ַȡֵ��

		public Long getNid() {
			return nid;
		}

		public void setNid(Long nid) {
			this.nid = nid;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	class Field_Contact {
		private Long nid;// int(10) ����ID���ID��
		private String value;// varchar(255) ������ʽ������洢һ��΢�ź�Ϊsooyung�ı�����ʽΪ��weixin:sooyung��������Ϊ��qq:1234567��mail:test@sooyung.com��phone:13312345678�������Դ��������������ķ�ʽ�洢��

		public Long getNid() {
			return nid;
		}

		public void setNid(Long nid) {
			this.nid = nid;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
