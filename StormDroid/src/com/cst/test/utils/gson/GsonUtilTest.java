package com.cst.test.utils.gson;

import java.util.List;

import android.test.AndroidTestCase;

import com.cst.stormdroid.utils.json.GsonUtil;
import com.google.gson.Gson;

public class GsonUtilTest extends AndroidTestCase  {

	public void testParseCollection() throws Throwable {
		List<Boolean> list = GsonUtil.parseCollection("[false, true, 1]");
		assertEquals(list.size(), 3);
		
		Long l = GsonUtil.fromJson("100", Long.class);
		assertEquals(l.longValue(), 100);
		
		String json = "{\"nid\":\"510\",\"vid\":\"510\",\"type\":\"event\",\"language\":\"und\",\"title\":\"活动报名方式存储修改测试\",\"uid\":\"1\",\"status\":\"1\",\"created\":\"1373876340\",\"changed\":\"1374239699\",\"comment\":\"2\",\"promote\":\"0\",\"sticky\":\"0\",\"tnid\":\"0\",\"translate\":\"0\",\"uri\":\"http://www.sooyung.com/rest/node/510\"}";
		Gson gson = new Gson();
		Node event = gson.fromJson(json, Node.class);
		assertNotNull(event);
	}
}
