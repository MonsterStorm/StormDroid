package com.cst.stormdroid.utils.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * json deserializer using Gson library, this is used when u have needs to define u own format of serialized string, et: DateTime
 * eg:
 * private class DateTimeDeserializer implements JsonDeserializer<DateTime> {
 *   public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
 *       return new DateTime(json.getAsJsonPrimitive().getAsString());
 *   }
 * }
 * @author MonsterStorm
 * @version 1.0
 */
public abstract class GsonBaseDeserializer<T> implements JsonDeserializer<T>{
	
	/**
	 * deserialize a json
	 */
	@Override
	public T deserialize(JsonElement json, Type type, JsonDeserializationContext jdc) throws JsonParseException {
		return fromJson(json.getAsJsonPrimitive().getAsString());
	}
	
	/**
	 * create a Object T, using string json
	 * @param json
	 * @return
	 */
	protected abstract T fromJson(final String json);
}
