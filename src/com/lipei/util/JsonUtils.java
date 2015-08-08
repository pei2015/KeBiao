package com.lipei.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
/**
 * json工具类
 * @author 赵李沛
 *
 */
public class JsonUtils {

	public JsonUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static JSONObject getJsonFromMap(Map<String,Object> map)
	{
		JSONObject o=new JSONObject(map);
		return o;
	}

	public static <T> JSONObject getJsonFromBean(T t){
		Map<String, String> map = getValueMap(t);
		JSONObject o = new JSONObject(map);
		return o;
	}
	/**
	 * 从实体bean中映射属性及属性值封装到Map中  
	 * @param t
	 * @return
	 */
	private static <T> Map<String, String> getValueMap(T t){
		Map<String, String> map = new HashMap<String, String>();
		Class<? extends Object> clzz = t.getClass();
		Field[] fields = clzz.getDeclaredFields();
		for(int i = 0;i<fields.length;i++){
			String method = "get" + fields[i].getName().substring(0, 1).toUpperCase()+fields[i].getName().substring(1);
			try {
				Method getMethod = clzz.getMethod(method,new Class[]{});
				Object value = getMethod.invoke(t,new Object[]{});
				map.put(fields[i].getName(), value.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	/**
	 * json转换String
	 * @param jsonString
	 * @return String
	 */
	public static String GetStringFromJsonString(String jsonString)
	{
		String string="";
		try {
			JSONObject object=new JSONObject(jsonString);
			if (!object.isNull("result"))
			{
				string=object.getString("result");
			}
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return string;
		
	}
	 /**
     * 将整个json字符串解析，并放置到map<String,Object>中
     * 
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> getJosn(String jsonStr) {
            Map<String, Object> map = new HashMap<String, Object>();
            try {

            if (!TextUtils.isEmpty(jsonStr)) {
                    JSONObject json = new JSONObject(jsonStr);
                    Iterator i = json.keys();
                    while (i.hasNext()) {
                            String key = (String) i.next();
                            String value = json.getString(key);
                            if (value.indexOf("{") == 0) {
                                    map.put(key.trim(), getJosn(value));
                            } else if (value.indexOf("[") == 0) {
                                    map.put(key.trim(), getList(value));
                            } else {
                                    map.put(key.trim(), value.trim());
                            }
                    }
            }
            return map;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
            return map;
    }
    /**
     * 将单个json数组字符串解析放在list中
     * 
     * @param jsonStr
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getList(String jsonStr)
                    throws Exception {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            JSONArray ja = new JSONArray(jsonStr);
            for (int j = 0; j < ja.length(); j++) {
                    String jm = ja.get(j) + "";
                    if (jm.indexOf("{") == 0) {
                            Map<String, Object> m = getJosn(jm);
                            list.add(m);
                    }
            }
            return list;
    }


	/**
	 * 将jsonString转换为List<String>类型。
	 * 
	 * @param key
	 * @param jsonString
	 * @return
	 */
	public static List<String> getListFromJsonString(String key,
			String jsonString) {
		List<String> list = new ArrayList<String>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				String msg = jsonArray.getString(i);
				list.add(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	/**
	 * 将jsonString转化为 List<Map<String, Object>>类型。
	 * 
	 * @param key
	 * @param jsonString
	 * @return
	 */
	public static List<Map<String, Object>> getListMapFromJsonString(
			String key, String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				@SuppressWarnings("unchecked")
				Iterator<String> iterator = jsonObject2.keys();
				while (iterator.hasNext()) {
					String json_key = iterator.next();
					Object json_value = jsonObject2.get(json_key);
					if (json_value == null) {
						json_value = "";
					}
					map.put(json_key, json_value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public Map<String, Object> getMapFromJson(String jsonString){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			@SuppressWarnings("unchecked")
			Iterator<String> iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				String json_key = iterator.next();
				Object json_value = jsonObject.get(json_key);
				if (json_value == null) {
					json_value = "";
				}
				map.put(json_key, json_value);
			}
			return map;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;		
	}
}
