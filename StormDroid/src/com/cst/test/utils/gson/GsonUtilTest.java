package com.cst.test.utils.gson;

import java.util.List;

import android.test.AndroidTestCase;

import com.cst.stormdroid.utils.json.GsonUtil;
import com.cst.test.utils.gson.Field.Field_Address;
import com.cst.test.utils.gson.Field.Field_Address.Field_Address_Deserializer;
import com.cst.test.utils.gson.Field.Field_Contact;
import com.cst.test.utils.gson.Field.Field_Contact.Field_Contact_Deserializer;
import com.cst.test.utils.gson.Field.Field_Desc;
import com.cst.test.utils.gson.Field.Field_Desc.Field_Desc_Deserializer;
import com.cst.test.utils.gson.Field.Field_End;
import com.cst.test.utils.gson.Field.Field_End.Field_End_Deserializer;
import com.cst.test.utils.gson.Field.Field_Photos;
import com.cst.test.utils.gson.Field.Field_Photos.Field_Photos_Deserializer;
import com.cst.test.utils.gson.Field.Field_Poster;
import com.cst.test.utils.gson.Field.Field_Poster.Field_Poster_Deserializer;
import com.cst.test.utils.gson.Field.Field_Start;
import com.cst.test.utils.gson.Field.Field_Start.Field_Start_Deserializer;
import com.cst.test.utils.gson.Field.Field_Type;
import com.cst.test.utils.gson.Field.Field_Type.Field_Type_Deserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtilTest extends AndroidTestCase  {

	public void testParseCollection() throws Throwable {
		List<Boolean> list = GsonUtil.parseCollection("[false, true, 1]");
		assertEquals(list.size(), 3);
		
		Long l = GsonUtil.fromJson("100", Long.class);
		assertEquals(l.longValue(), 100);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Field_Address.class, new Field_Address_Deserializer());
		gsonBuilder.registerTypeAdapter(Field_Poster.class, new Field_Poster_Deserializer());
		gsonBuilder.registerTypeAdapter(Field_Photos.class, new Field_Photos_Deserializer());
		gsonBuilder.registerTypeAdapter(Field_Start.class, new Field_Start_Deserializer());
		gsonBuilder.registerTypeAdapter(Field_End.class, new Field_End_Deserializer());
		gsonBuilder.registerTypeAdapter(Field_Desc.class, new Field_Desc_Deserializer());
		gsonBuilder.registerTypeAdapter(Field_Type.class, new Field_Type_Deserializer());
		gsonBuilder.registerTypeAdapter(Field_Contact.class, new Field_Contact_Deserializer());
		Gson gson = gsonBuilder.create();

		String json = "{\"nid\":\"510\",\"vid\":\"510\",\"type\":\"event\",\"language\":\"und\",\"title\":\"活动报名方式存储修改测试\",\"uid\":\"1\",\"status\":\"1\",\"created\":\"1373876340\",\"changed\":\"1374239699\",\"comment\":\"2\",\"promote\":\"0\",\"sticky\":\"0\",\"tnid\":\"0\",\"translate\":\"0\",\"uri\":\"http://www.sooyung.com/rest/node/510\"}";
		Node node = gson.fromJson(json, Node.class);
		assertNotNull(node);

		
		/*json = "{\"field_address\": {\"und\": [{\"value\": \"活动地址\",\"format\": null,\"safe_value\": \"活动地址\"}]}}";
		Field_Address address = gson.fromJson(json, Field_Address.class);
		assertNotNull(address);
		
		json = "{\"field_poster\": [ ]}";
		Field_Poster poster = gson.fromJson(json, Field_Poster.class);
		assertNotNull(poster);
		
		json = "{\"field_photos\": [ ]}";
		Field_Photos photos = gson.fromJson(json, Field_Photos.class);
		assertNotNull(photos);
		
		json="{\"field_start\": {\"und\": [{\"value\": \"1373876100\",\"timezone\": \"Asia/Shanghai\",\"timezone_db\": \"UTC\",\"date_type\": \"datestamp\"}]}}";
		Field_Start start = gson.fromJson(json, Field_Start.class);
		assertNotNull(start);
		
		json="{\"field_end\": {\"und\": [{\"value\": \"1373876100\",\"timezone\": \"Asia/Shanghai\",\"timezone_db\": \"UTC\",\"date_type\": \"datestamp\"}]}}";
		Field_End end = gson.fromJson(json, Field_End.class);
		assertNotNull(end);
		
		json = "{\"field_desc\": {\"und\": [{\"value\": \"<p>5</p>\",\"summary\": \"\",\"format\": \"filtered_html\",\"safe_value\": \"<p>5</p>\",\"safe_summary\": \"\"}]}}";
		Field_Desc desc = gson.fromJson(json, Field_Desc.class);
		assertNotNull(desc);
		
		json = "{\"field_type\": {\"und\": [{\"value\": \"1\"}]}}";
		Field_Type type = gson.fromJson(json, Field_Type.class);
		assertNotNull(type);
		
		json = "{\"field_contact\": {\"und\": [{\"value\": \"phone:1\",\"format\": null,\"safe_value\": \"phone:1\"}, {\"value\": \"weixin:2\",\"format\": null,\"safe_value\": \"weixin:2\"}, {\"value\": \"qq:3\",\"format\": null,\"safe_value\": \"qq:3\"},{\"value\": \"mail:4\",\"format\": null,\"safe_value\": \"mail:4\"}]}}";
		Field_Contact contact = gson.fromJson(json, Field_Contact.class);
		assertNotNull(contact);*/
		
		json = "{\"vid\": \"510\",\"uid\": \"1\",\"title\": \"活动报名方式存储修改测试\",\"log\": \"\", \"status\": \"1\",\"comment\": \"2\",\"promote\": \"0\",\"sticky\": \"0\",\"nid\": \"510\",\"type\": \"event\",\"language\": \"und\",\"created\": \"1373876340\",\"changed\": \"1374239699\",\"tnid\": \"0\",\"translate\": \"0\",\"revision_timestamp\": \"1374239699\",\"revision_uid\": \"1\",\"field_poster\": [ ],\"field_address\": {\"und\": [{\"value\": \"活动地址\",\"format\": null,\"safe_value\": \"活动地址\"}]},\"field_type\": {\"und\": [{\"value\": \"1\"}]}, \"field_photos\": [],\"field_desc\": {\"und\": [{\"value\": \"<p>5</p>\", \"summary\": \"dd\",\"format\": \"filtered_html\",\"safe_value\": \"<p>5</p>\",\"safe_summary\": \"dd\"}]},\"field_start\": {\"und\": [{\"value\": \"1373876100\",\"timezone\": \"Asia/Shanghai\",\"timezone_db\": \"UTC\",\"date_type\": \"datestamp\"}]}, \"field_end\": {\"und\": [{\"value\": \"1373876100\",\"timezone\": \"Asia/Shanghai\",\"timezone_db\": \"UTC\",\"date_type\": \"datestamp\"}]},\"field_contact\": {\"und\": [{ \"value\": \"phone:1\",\"format\": null,\"safe_value\": \"phone:1\"},{\"value\": \"weixin:2\",\"format\": null,\"safe_value\": \"weixin:2\"},{\"value\": \"qq:3\", \"format\": null,\"safe_value\": \"qq:3\"},{\"value\": \"mail:4\",\"format\": null,\"safe_value\": \"mail:4\"}]},\"cid\": \"0\",\"last_comment_timestamp\": \"1373876340\",\"last_comment_name\": null,\"last_comment_uid\": \"1\",\"comment_count\": \"0\",\"community_tags_form\": null,\"name\": \"sooyung\",\"picture\": \"0\",\"data\": \"b:0;\",\"path\": \"http://www.sooyung.com/node/510\"}";
		Event event = gson.fromJson(json, Event.class);
		assertNotNull(event);
	}
}
