package com.kdt.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;

import com.Manage.common.util.DateUtils;
import com.kdt.api.KdtApiClient;

/*
 * 这是个例子
 */
public class KDTApiTest {
	/** 店铺: 途狗全球漫游WIFI */
//	private static final String APP_ID = "d1a9742f514ea88923"; //这里换成你的app_id
//	private static final String APP_SECRET = "7350aa0eea8e1709841af3dde2bd5574"; //这里换成你的app_secret
	/** 店铺: 途狗测试店铺 */
	private static final String APP_ID = "2b261b89f52a67aa6b"; //这里换成你的app_id
	private static final String APP_SECRET = "515ab338e7bcfdedb50b3a44b31d5504"; //这里换成你的app_secret

	public static void main(String[] args){
		//sendGetShopBasic();
		//sendGet();
		//sendPost();

		//sendGetTradesSold();
		System.out.println(DateUtils.getDate());
	}

	/*
	 * 测试获取店铺信息
	 */
	private static void sendGetShopBasic(){
		String method = "kdt.shop.basic.get";
		HashMap<String, String> params = new HashMap<String, String>();
		//params.put("num_iid", "2651514");

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(); //(APP_ID, APP_SECRET);
			response = kdtApiClient.get(method, params);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 测试获取单个商品信息
	 */
	private static void sendGet(){
		String method = "kdt.items.onsale.get"; //"kdt.item.get";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("num_iid", "2651514");

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.get(method, params);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 测试获取添加商品
	 */
	private static void sendPost(){
		String method = "kdt.item.add";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("price", "999.01");
		params.put("title", "测试商品");
		params.put("desc", "这是一个号商铺");
		params.put("is_virtual", "0");
		params.put("post_fee", "10.01");
		params.put("sku_properties", "");
		params.put("sku_quantities", "");
		params.put("sku_prices", "");
		params.put("sku_outer_ids", "");
		String fileKey = "images[]";
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("/Users/xuexiaozhe/Desktop/1.png");
		filePaths.add("/Users/xuexiaozhe/Desktop/2.png");

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.post(method, params, filePaths, fileKey);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 测试获取全部订单信息
	 */
	private static void sendGetTradesSold(){
		String method = "kdt.trades.sold.get";
		HashMap<String, String> params = new HashMap<String, String>();
		//params.put("num_iid", "2651514");

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(); //(APP_ID, APP_SECRET);
			response = kdtApiClient.get(method, params);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
