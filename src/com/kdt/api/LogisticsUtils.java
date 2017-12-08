package com.kdt.api;

import java.util.HashMap;
import java.util.Map;

public class LogisticsUtils {


	/**
	 * 为原来发货物流的数据字典"快递公司"建立映射关系, 这样方便在原发货页面上集成有赞的物流
	 * 做法: (1) 按 https://www.showapi.com/api/lookPoint/64 左侧"支持的快递公司列表" 把需要的快递公司录入数据字典,
	 * 关键系拼音代码
	 * (2) 按 https://open.koudaitong.com/doc/api?method=kdt.logistics.online.confirm 接口文档 把对应公司的数字代码
	 * 放入这个映射
	 * (3) 在前端使用时仍然按之前的 select 项/值即可. 属有赞的订单会切换到有赞的查询接口
	 */
	public static Map<String, Integer> PY2YZ_EXPRESS = new HashMap<String, Integer>() {{
		put("shunfeng", 7); // 顺丰速运
		put("yuantong", 2); // 圆通速递
		put("shentong", 1); // 申通E物流
		put("zhongtong", 3); // 中通速递
		put("pingyou", 12); // 中国邮政快递 邮政平邮
		put("ems", 10); // 10 => EMS经济快递 11 => EMS
		put("gnxb", 8); // 邮政国内小包
		put("guotong", 40); // 国通快递
		put("tiantian", 5); // 天天快递
		put("zitiziqu", 0); // 自提自取
	}};
}
