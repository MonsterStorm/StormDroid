package com.cst.stormdroid.utils.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * Json parser and encoder using GSON library
 * @author MonsterStorm
 * @version 1.0
 */
public class GsonUtil {
	
	/**
	 * to json
	 * @param obj
	 * @return
	 */
	public static <T> String toJson(final T obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * convert from json to Object
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(final String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}

	/**
	 * parse a collection, T is the type of collection, such as List<String>
	 * Collections Limitations:
	 * 1. Can serialize collection of arbitrary objects but can not deserialize from it
	 * 1.1. Because there is no way for the user to indicate the type of the resulting object
	 * 2. While deserializing, Collection must be of a specific generic type
	 * @param json
	 * @return
	 */
	public static <T> T parseCollection(final String json) {// Option 1
		Gson gson = new Gson();
		Type collectionType = new TypeToken<T>() {
		}.getType();
		T t = gson.fromJson(json, collectionType);
		return t;
	}

	/**
	 * parse a collection, given the every element type of the collection
	 * You can serialize the collection with Gson without doing anything specific: toJson(collection) would write out the desired output. However,
	 * deserialization with fromJson(json, Collection.class) will not work since Gson has no way of knowing how to map the input to the types.
	 * Gson requires that you provide a genericised version of collection type in fromJson. So, you have three options:
	 * Option 1: Use Gson's parser API (low-level streaming parser or the DOM parser JsonParser) to parse the array elements and then use Gson.
	 * fromJson() on each of the array elements.This is the preferred approach. Here is an example that demonstrates how to do this.
	 * Option 2: Register a type adapter for Collection.class that looks at each of the array members and maps them to appropriate objects.
	 * the disadvantage of this approach is that it will screw up deserialization of other collection types in Gson.
	 * Option 3: Register a type adapter for MyCollectionMemberType and use fromJson with Collection<MyCollectionMemberType>
	 * This approach is practical only if the array appears as a top-level element or if you can change the field type holding the collection to be of type Collection<MyCollectionMemberTyep>.
	 * @param json
	 * @param clazzs
	 * @return
	 */
	public static <T> List<T> parseList(final String json, Class<T> clazz) {// Option 1
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(json).getAsJsonArray();
		List<T> list = new ArrayList<T>();
		int num = array.size();
		for (int i = 0; i < num; i++) {
			list.add(gson.fromJson(array.get(i), clazz));
		}
		return list;
	}

	/**
	 * parse a collection, given the every element type of the collection
	 * You can serialize the collection with Gson without doing anything specific: toJson(collection) would write out the desired output. However,
	 * deserialization with fromJson(json, Collection.class) will not work since Gson has no way of knowing how to map the input to the types.
	 * Gson requires that you provide a genericised version of collection type in fromJson. So, you have three options:
	 * Option 1: Use Gson's parser API (low-level streaming parser or the DOM parser JsonParser) to parse the array elements and then use Gson.
	 * fromJson() on each of the array elements.This is the preferred approach. Here is an example that demonstrates how to do this.
	 * Option 2: Register a type adapter for Collection.class that looks at each of the array members and maps them to appropriate objects.
	 * the disadvantage of this approach is that it will screw up deserialization of other collection types in Gson.
	 * Option 3: Register a type adapter for MyCollectionMemberType and use fromJson with Collection<MyCollectionMemberType>
	 * This approach is practical only if the array appears as a top-level element or if you can change the field type holding the collection to be of type Collection<MyCollectionMemberTyep>.
	 * @param json
	 * @param clazzs
	 * @return
	 */
	public static <T> List<Object> parseList(final String json, Class<T>... clazzs) {// Option 1
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(json).getAsJsonArray();
		List<Object> list = new ArrayList<Object>();
		int num = Math.min(clazzs.length, array.size());
		for (int i = 0; i < num; i++) {
			list.add(gson.fromJson(array.get(i), clazzs[i]));
		}
		return list;
	}
}
