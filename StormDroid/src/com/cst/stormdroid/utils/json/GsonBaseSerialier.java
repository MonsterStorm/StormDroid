package com.cst.stormdroid.utils.json;
/**
 * Sometimes default representation is not what you want. This is often the case when dealing with library classes (DateTime, etc).
 * Gson allows you to register your own custom serializers and deserializers. This is done by defining two parts:
 * Json Serialiers: Need to define custom serialization for an object
 * Json Deserializers: Needed to define custom deserialization for a type
 * Instance Creators: Not needed if no-args constructor is available or a deserializer is registered
 */
import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * json serializer using Gson library, this is used when u have needs to define u own format of serialized string, et: DateTime
 * eg:
 * private class DateTimeSerializer implements JsonSerializer<DateTime> {
 *   public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
 *       return new JsonPrimitive(src.toString());
 *   }
 * }
 * @author MonsterStorm
 * @version 1.0
 */
public abstract class GsonBaseSerialier<T> implements JsonSerializer<T>{
	
	/**
	 * serialize function
	 */
	public JsonElement serialize(T t, Type type, JsonSerializationContext jsc) {
		return new JsonPrimitive(toJson(t));
	};
	
	/**
	 * function must implemented to serizlize a T
	 * @param t
	 * @return
	 */
	protected abstract String toJson(T t);
}
