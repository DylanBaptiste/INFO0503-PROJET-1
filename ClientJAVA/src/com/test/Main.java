package com.test;

import org.json.*;

public class Main {
	public static void main(String arg[]) {
		try {
			JSONObject test = new JSONObject();
			test.put("key", "value");
			System.out.println(test.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
