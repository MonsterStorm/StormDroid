package com.cst.test.utils.gson;
/**
 * field 
 * @author MonsterStorm
 *
 */
public class Field {
	class Field_Desc {
		private Long nid;// int(10) 内容ID，活动ID
		private String value;// longtext 活动详情内容。
		private String format;// varchar(255) 活动详情格式。

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
		private Long nid;// int(10) 内容ID，活动ID。
		private String value;// varchar(255) 活动类型。值为“1”，“2”，“3”…

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
		private Long nid;// int(10);// 内容ID，活动ID。
		private Long fid;// int(10);// 文件ID。

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
		private Long nid;// int(10) 内容ID，活动ID。
		private Long fid;// int(10) 文件ID。

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
		private Long nid;// int(10) 内容ID，活动ID。
		private Long value;// int(11) 活动开始时间。

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
		private Long nid;// int(10) 内容ID，活动ID。
		private Long value;// int(11) 活动结束时间。

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
		private Long nid;// int(10) 内容ID，活动ID。
		private String value;// varchar(255) 活动地址取值。

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
		private Long nid;// int(10) 内容ID，活动ID。
		private String value;// varchar(255) 报名方式。例如存储一条微信号为sooyung的报名方式为（weixin:sooyung）。其他为：qq:1234567，mail:test@sooyung.com，phone:13312345678。最多可以存放四条。以数组的方式存储。

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
