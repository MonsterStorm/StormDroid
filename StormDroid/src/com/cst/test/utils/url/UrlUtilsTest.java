package com.cst.test.utils.url;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.cst.stormdroid.utils.url.UrlUtil;

public class UrlUtilsTest extends AndroidTestCase  {

	public void testBuildUrl() throws Throwable {
		Assert.assertEquals(UrlUtil.buildUrl("www.baidu.com", "search", null), "http://www.baidu.com/search");
		Assert.assertEquals(UrlUtil.buildUrl(UrlUtil.PREFIX_HTTPS, "www.baidu.com", "search", null), "https://www.baidu.com/search");

		Assert.assertEquals(UrlUtil.buildUrl("www.baidu.com", null, null), "http://www.baidu.com");
		Assert.assertEquals(UrlUtil.buildUrl(UrlUtil.PREFIX_HTTPS, "www.baidu.com", null, null), "https://www.baidu.com");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("a", "1");
		Assert.assertEquals(UrlUtil.buildUrl("www.baidu.com", "search", params), "http://www.baidu.com/search?a=1");
		Assert.assertEquals(UrlUtil.buildUrl(UrlUtil.PREFIX_HTTPS, "www.baidu.com", "search", params), "https://www.baidu.com/search?a=1");
		
		Assert.assertEquals(UrlUtil.buildUrl("www.baidu.com", null, params), "http://www.baidu.com/?a=1");
		Assert.assertEquals(UrlUtil.buildUrl(UrlUtil.PREFIX_HTTPS, "www.baidu.com", null, params), "https://www.baidu.com/?a=1");

		params.put("b", 2);
		Assert.assertEquals(UrlUtil.buildUrl("www.baidu.com", "search", params), "http://www.baidu.com/search?b=2&a=1");
		Assert.assertEquals(UrlUtil.buildUrl(UrlUtil.PREFIX_HTTPS, "www.baidu.com", "search", params), "https://www.baidu.com/search?b=2&a=1");
	}
}
