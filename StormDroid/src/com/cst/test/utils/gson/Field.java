package com.cst.test.utils.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * field
 * @author MonsterStorm
 * 
 */
public class Field {
	public static class Field_Desc {
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

		public static class Field_Desc_Deserializer implements JsonDeserializer<Field_Desc> {
			public Field_Desc deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				Field_Desc fdesc = new Field_Desc();
				if (json.isJsonObject()) {
					JsonObject obj = json.getAsJsonObject();
					if (obj != null) {
						JsonElement field = obj.get("field_desc");
						JsonArray array = null;
						if (field == null) {
							array = obj.getAsJsonArray("und");
						} else if (!field.isJsonArray()){
							array = field.getAsJsonObject().getAsJsonArray("und");
						}
						if (array != null) {
							for (int i = 0; i < array.size(); i++) {
								JsonObject element = array.get(i).getAsJsonObject();
								if (element != null) {
									if (element.get("nid") != null) {
										fdesc.nid = element.get("nid").getAsLong();
									} else {
										fdesc.nid = null;
									}
									if (element.get("value") != null) {
										fdesc.value = element.get("value").getAsString();
									} else {
										fdesc.value = null;
									}
									if (element.get("format") != null) {
										fdesc.format = element.get("format").getAsString();
									} else {
										fdesc.format = null;
									}
								}
							}
						}
					}
				}
				return fdesc;
			}
		}
	}

	public static class Field_Type {
		private List<Long> nid;// int(10) 内容ID，活动ID。
		private List<String> value;// varchar(255) 活动类型。值为“1”，“2”，“3”…

		public List<Long> getNid() {
			return nid;
		}

		public void setNid(List<Long> nid) {
			this.nid = nid;
		}

		public List<String> getValue() {
			return value;
		}

		public void setValue(List<String> value) {
			this.value = value;
		}

		public static class Field_Type_Deserializer implements JsonDeserializer<Field_Type> {
			public Field_Type deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				Field_Type ftype = new Field_Type();
				if (json.isJsonObject()) {
					JsonObject obj = json.getAsJsonObject();
					if (obj != null) {
						JsonElement field = obj.get("field_type");
						JsonArray array = null;
						if (field == null) {
							array = obj.getAsJsonArray("und");
						} else if (!field.isJsonArray()){
							array = field.getAsJsonObject().getAsJsonArray("und");
						}
						if (array != null) {
							ftype.nid = new ArrayList<Long>();
							ftype.value = new ArrayList<String>();
							for (int i = 0; i < array.size(); i++) {
								JsonObject element = array.get(i).getAsJsonObject();
								if (element != null) {
									if (element.get("nid") != null) {
										ftype.nid.add(element.get("nid").getAsLong());
									} else {
										ftype.nid.add(null);
									}
									if (element.get("value") != null) {
										ftype.value.add(element.get("value").getAsString());
									} else {
										ftype.value.add(null);
									}
								}
							}
						}
					}
				}
				return ftype;
			}
		}
	}

	public static class Field_Poster {
		private List<Long> nid;// int(10);// 内容ID，活动ID。
		private List<Long> fid;// int(10);// 文件ID。

		public List<Long> getNid() {
			return nid;
		}

		public void setNid(List<Long> nid) {
			this.nid = nid;
		}

		public List<Long> getFid() {
			return fid;
		}

		public void setFid(List<Long> fid) {
			this.fid = fid;
		}

		public static class Field_Poster_Deserializer implements JsonDeserializer<Field_Poster> {
			public Field_Poster deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				Field_Poster fposter = new Field_Poster();
				if (json.isJsonObject()) {
					JsonObject obj = json.getAsJsonObject();
					if (obj != null) {
						JsonElement field = obj.get("field_poster");
						JsonArray array = null;
						if (field == null) {
							array = obj.getAsJsonArray("und");
						} else if (!field.isJsonArray()){
							array = field.getAsJsonObject().getAsJsonArray("und");
						}
						if (array != null) {
							fposter.nid = new ArrayList<Long>();
							fposter.fid = new ArrayList<Long>();
							for (int i = 0; i < array.size(); i++) {
								JsonObject element = array.get(i).getAsJsonObject();
								if (element != null) {
									if (element.get("nid") != null) {
										fposter.nid.add(element.get("nid").getAsLong());
									} else {
										fposter.nid.add(null);
									}
									if (element.get("fid") != null) {
										fposter.fid.add(element.get("fid").getAsLong());
									} else {
										fposter.fid.add(null);
									}
								}
							}
						}
					}
				}
				return fposter;
			}
		}
	}

	public static class Field_Photos {
		private List<Long> nid;// int(10) 内容ID，活动ID。
		private List<Long> fid;// int(10) 文件ID。

		public List<Long> getNid() {
			return nid;
		}

		public void setNid(List<Long> nid) {
			this.nid = nid;
		}

		public List<Long> getFid() {
			return fid;
		}

		public void setFid(List<Long> fid) {
			this.fid = fid;
		}

		public static class Field_Photos_Deserializer implements JsonDeserializer<Field_Photos> {
			public Field_Photos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				Field_Photos fphoto = new Field_Photos();
				if (json.isJsonObject()) {
					JsonObject obj = json.getAsJsonObject();
					if (obj != null) {
						JsonElement field = obj.get("field_photos");
						JsonArray array = null;
						if (field == null) {
							array = obj.getAsJsonArray("und");
						} else if (!field.isJsonArray()){
							array = field.getAsJsonObject().getAsJsonArray("und");
						}
						if (array != null) {
							fphoto.nid = new ArrayList<Long>();
							fphoto.fid = new ArrayList<Long>();
							for (int i = 0; i < array.size(); i++) {
								JsonObject element = array.get(i).getAsJsonObject();
								if (element != null) {
									if (element.get("nid") != null) {
										fphoto.nid.add(element.get("nid").getAsLong());
									} else {
										fphoto.nid.add(null);
									}
									if (element.get("fid") != null) {
										fphoto.fid.add(element.get("fid").getAsLong());
									} else {
										fphoto.fid.add(null);
									}
								}
							}
						}
					}
				}
				return fphoto;
			}
		}
	}

	public static class Field_Start {
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

		public static class Field_Start_Deserializer implements JsonDeserializer<Field_Start> {
			public Field_Start deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				Field_Start fstart = new Field_Start();
				if (json.isJsonObject()) {
					JsonObject obj = json.getAsJsonObject();
					if (obj != null) {
						JsonElement field = obj.get("field_start");
						JsonArray array = null;
						if (field == null) {
							array = obj.getAsJsonArray("und");
						} else if (!field.isJsonArray()){
							array = field.getAsJsonObject().getAsJsonArray("und");
						}
						if (array != null) {
							for (int i = 0; i < array.size(); i++) {
								JsonObject element = array.get(i).getAsJsonObject();
								if (element != null) {
									if (element.get("nid") != null) {
										fstart.nid = element.get("nid").getAsLong();
									} else {
										fstart.nid = null;
									}
									if (element.get("value") != null) {
										fstart.value = element.get("value").getAsLong();
									} else {
										fstart.value = null;
									}
								}
							}
						}
					}
				}
				return fstart;
			}
		}
	}

	public static class Field_End {
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

		public static class Field_End_Deserializer implements JsonDeserializer<Field_End> {
			public Field_End deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				Field_End fend = new Field_End();
				if (json.isJsonObject()) {
					JsonObject obj = json.getAsJsonObject();
					if (obj != null) {
						JsonElement field = obj.get("field_end");
						JsonArray array = null;
						if (field == null) {
							array = obj.getAsJsonArray("und");
						} else if (!field.isJsonArray()){
							array = field.getAsJsonObject().getAsJsonArray("und");
						}
						if (array != null) {
							for (int i = 0; i < array.size(); i++) {
								JsonObject element = array.get(i).getAsJsonObject();
								if (element != null) {
									if (element.get("nid") != null) {
										fend.nid = element.get("nid").getAsLong();
									} else {
										fend.nid = null;
									}
									if (element.get("value") != null) {
										fend.value = element.get("value").getAsLong();
									} else {
										fend.value = null;
									}
								}
							}
						}
					}
				}
				return fend;
			}
		}

	}

	public static class Field_Address {
		private List<Long> nid;// int(10) 内容ID，活动ID。
		private List<String> value;// varchar(255) 活动地址取值。

		public List<Long> getNid() {
			return nid;
		}

		public void setNid(List<Long> nid) {
			this.nid = nid;
		}

		public List<String> getValue() {
			return value;
		}

		public void setValue(List<String> value) {
			this.value = value;
		}

		public static class Field_Address_Deserializer implements JsonDeserializer<Field_Address> {
			public Field_Address deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				Field_Address faddress = new Field_Address();
				if (json.isJsonObject()) {
					JsonObject obj = json.getAsJsonObject();
					if (obj != null) {
						JsonElement field = obj.get("field_address");
						JsonArray array = null;
						if (field == null) {
							array = obj.getAsJsonArray("und");
						} else if (!field.isJsonArray()){
							array = field.getAsJsonObject().getAsJsonArray("und");
						}
						if (array != null) {
							faddress.nid = new ArrayList<Long>();
							faddress.value = new ArrayList<String>();
							for (int i = 0; i < array.size(); i++) {
								JsonObject element = array.get(i).getAsJsonObject();
								if (element != null) {
									if (element.get("nid") != null) {
										faddress.nid.add(element.get("nid").getAsLong());
									} else {
										faddress.nid.add(null);
									}
									if (element.get("value") != null) {
										faddress.value.add(element.get("value").getAsString());
									} else {
										faddress.value.add(null);
									}
								}
							}
						}
					}
				}
				return faddress;
			}
		}
	}

	public static class Field_Contact {
		private List<Long> nid;// int(10) 内容ID，活动ID。
		private List<String> value;// varchar(255) 报名方式。例如存储一条微信号为sooyung的报名方式为（weixin:sooyung）。其他为：qq:1234567，mail:test@sooyung.com，phone:13312345678。最多可以存放四条。以数组的方式存储。

		public List<Long> getNid() {
			return nid;
		}

		public void setNid(List<Long> nid) {
			this.nid = nid;
		}

		public List<String> getValue() {
			return value;
		}

		public void setValue(List<String> value) {
			this.value = value;
		}

		public static class Field_Contact_Deserializer implements JsonDeserializer<Field_Contact> {
			public Field_Contact deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				Field_Contact faddress = new Field_Contact();
				if (json.isJsonObject()) {
					JsonObject obj = json.getAsJsonObject();
					if (obj != null) {
						JsonElement field = obj.get("field_contact");
						JsonArray array = null;
						if (field == null) {
							array = obj.getAsJsonArray("und");
						} else if (!field.isJsonArray()){
							array = field.getAsJsonObject().getAsJsonArray("und");
						}
						if (array != null) {
							faddress.nid = new ArrayList<Long>();
							faddress.value = new ArrayList<String>();
							for (int i = 0; i < array.size(); i++) {
								JsonObject element = array.get(i).getAsJsonObject();
								if (element != null) {
									if (element.get("nid") != null) {
										faddress.nid.add(element.get("nid").getAsLong());
									} else {
										faddress.nid.add(null);
									}
									if (element.get("value") != null) {
										faddress.value.add(element.get("value").getAsString());
									} else {
										faddress.value.add(null);
									}
								}
							}
						}
					}
				}
				return faddress;
			}
		}
	}

}
