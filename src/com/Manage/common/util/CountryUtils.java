package com.Manage.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.Int;

import com.Manage.entity.CountryInfo;

/**
 * 国家地区常用工具类 java 端实现
 *
 * @author tangming 20150525
 */
public class CountryUtils {
	private Logger logger = LogUtil.getInstance(CountryUtils.class);

	/**
	 * 从表单传过来的 MCC 列表值, 结合存在的国家列表 输出需要的字段值
	 *
	 * @param mccList 以逗号分隔的MCC列表值, 如 "454,455"
	 * @param countries 允许可选的国家, 通常情况系全部国家
	 * @return 形如: "香港,454,28.0|台湾,455,33.0"
	 */
	public static String getCountryStringFromMCCList(String mccList, List<CountryInfo> countries) {
		if (StringUtils.isBlank(mccList) || null == countries || countries.size() == 0) {
			return ""; // 按之前的情况, 不宜返回null
		}

		// 标记选中 selected 的项
        List<CountryInfo> selectedCountries = new ArrayList<CountryInfo>();
//        if  (!StringUtils.isBlank(mccList)) {
            // 按统一 ,code, 的判断方法作一个修正
            // mccList = "," + mccList + ","; // 因为MCC固定为3位, 不需要前后补','了
            for (CountryInfo country : countries) {
                // String matchString = "," + String.valueOf(country.getCountryCode()) + ",";
                String matchString = String.valueOf(country.getCountryCode());
                if  (StringUtils.contains(mccList, matchString)) {
                    selectedCountries.add(country);
                }
            }
//        }

		List<String> stringList = new ArrayList<String>();
		for (CountryInfo item : selectedCountries) {
			stringList.add(item.getCountryName() + "," + String.valueOf(item.getCountryCode()) + "," + String.valueOf(item.getFlowPrice()));
		}

		return StringUtils.join(stringList, "|");
	}

	/**
	 * 根据OP，增加或删除某个国家, 输出需要的字段值
	 *
	 * @param countryListString 原值, 形如: "香港,454,28.0|台湾,455,33.0"
	 * @param countries 允许可选的国家, 通常情况系全部国家
	 * @param mcc 要增加或删除的某个国家MCC, 如 "455", 若增加可能系多个 "456,457" 等
	 * @param op 0: 删除, 1: 增加
	 * @return 对应上面的值, 删除455台湾, 输出形如: "香港,454,28.0" 目前设定可以允许删除最后一个国家
	 */
	public static String getUpdateCountryStringFromMCCList(String countryListString, List<CountryInfo> countries, String mcc, int op) {
		// TODO: null 判断/参数判断

		CountryListWrapper wrapper = new CountryListWrapper(countryListString);
		List<CountryInfo> oldCountries = wrapper.getmCountryList();

		String resultString = "";
		switch (op) {
		case 0:
		{
			for (Iterator<CountryInfo> iterator = oldCountries.iterator(); iterator.hasNext();) {
				CountryInfo item = (CountryInfo) iterator.next();
				if (mcc.equals(String.valueOf(item.getCountryCode()))) {
					iterator.remove();
				}
			}
			List<String> stringList = new ArrayList<String>();
			for (CountryInfo item : oldCountries) {
				stringList.add(String.valueOf(item.getCountryCode()));
			}
			resultString = StringUtils.join(stringList, ",");
			resultString = getCountryStringFromMCCList(resultString, countries);
		}
			break;
		case 1:
		{
			// 这种方法快速, 但没有考虑是否有重叠的情况, 所以需要前端保证. 有必要时再改善.
			resultString = getCountryStringFromMCCList(mcc, countries);
			resultString = countryListString + '|' + resultString;
		}
			break;
		default:
			resultString = countryListString; // 其他情况原样返回
			break;
		}

		return resultString;
	}

	/**
	 * 从表单传过来的 MCC 列表值, 结合存在的国家列表 输出 SIMInfo 表需要的字段 countryList 值 格式形如: MCC1|MCC2|MCC3
	 *
	 * @param mccList 以逗号分隔的MCC列表值, 如 "454,455"
	 * @param countries 允许可选的国家, 通常情况系全部国家
	 * @return 形如: "454|455|456"
	 */
	public static String getCountryStringFromMCCListForSiminfo(String mccList, List<CountryInfo> countries) {
		if (StringUtils.isBlank(mccList) || null == countries || countries.size() == 0) {
			return ""; // 按之前的情况, 不宜返回null
		}

//		// 目前仅简单替换! 最好做好验证 --> 应该验证
//		return StringUtils.replace(mccList, ",", "|");

		// 标记选中 selected 的项
        List<CountryInfo> selectedCountries = new ArrayList<CountryInfo>();
//        if  (!StringUtils.isBlank(mccList)) {
            // 按统一 ,code, 的判断方法作一个修正
            // mccList = "," + mccList + ","; // 因为MCC固定为3位, 不需要前后补','了
            for (CountryInfo country : countries) {
                // String matchString = "," + String.valueOf(country.getCountryCode()) + ",";
                String matchString = String.valueOf(country.getCountryCode());
                if  (StringUtils.contains(mccList, matchString)) {
                    selectedCountries.add(country);
                }
            }
//        }

		List<String> stringList = new ArrayList<String>();
		for (CountryInfo item : selectedCountries) {
			stringList.add(String.valueOf(item.getCountryCode()));
		}

		return StringUtils.join(stringList, "|");
	}

	/**
	 * 通过保存的SIMInfo可用国家值 countryList, 形如454|455|456, 结合存在的国家列表
	 * 输出国家名称值, 逗号分隔 格式形如: 中国,香港
	 *
	 * @param countryList 形如: "454|455|456"
	 * @param countries 允许可选的国家, 通常情况系全部国家
	 * @return 形如: "中国,香港"
	 */
	public static String getCountryNamesFromSiminfoCountryList(String countryList, List<CountryInfo> countries) {
		if (StringUtils.isBlank(countryList) || null == countries || countries.size() == 0) {
			return ""; // 按之前的情况, 不宜返回null
		}

		// 标记选中 selected 的项
        List<CountryInfo> selectedCountries = new ArrayList<CountryInfo>();
        if  (!StringUtils.isBlank(countryList)) {
            // 按统一 ,code, 的判断方法作一个修正
            // mccList = "," + mccList + ","; // 因为MCC固定为3位, 不需要前后补','了
            for (CountryInfo country : countries) {
                // String matchString = "," + String.valueOf(country.getCountryCode()) + ",";
                String matchString = String.valueOf(country.getCountryCode());
                if  (StringUtils.contains(countryList, matchString)) {
                    selectedCountries.add(country);
                }
            }
        }

		List<String> stringList = new ArrayList<String>();
		for (CountryInfo item : selectedCountries) {
			stringList.add(item.getCountryName());
		}

		return StringUtils.join(stringList, ",");
	}

	public static class CountryListWrapper {
		/**
		 * 保存到套餐的国家地区字符串格式
		 *
		 * 目前前后端约定: 套餐前端国家 value 为国家代码的值, 表单提交串形如 454,455 的串 通过查询国家列表
		 * 获取需要的值拼成形如:
		 * 		"香港,454,28.0|台湾,455,33.0"
		 * 这种串值考虑的因素系数据库设计的需求, 方便查询, 减少表关联, 值长尽量小等
		 */
		private String mCountryListString;

		public List<CountryInfo> getmCountryList() {
			return mCountryList;
		}

		public void setmCountryList(List<CountryInfo> mCountryList) {
			this.mCountryList = mCountryList;
		}

		/**
		 * 内部 国家地区列表
		 */
		private List<CountryInfo> mCountryList = new ArrayList<CountryInfo>();

//		private static class MockCountryInfo {
//			public String countryName;
//			public String countryCode;
//			public String flowPrice;
//			public String getCountryName() {
//				return countryName;
//			}
//			public void setCountryName(String countryName) {
//				this.countryName = countryName;
//			}
//			public String getCountryCode() {
//				return countryCode;
//			}
//			public void setCountryCode(String countryCode) {
//				this.countryCode = countryCode;
//			}
//			public String getFlowPrice() {
//				return flowPrice;
//			}
//			public void setFlowPrice(String flowPrice) {
//				this.flowPrice = flowPrice;
//			}
//
//		}

		public CountryListWrapper() {

		}

		/**
		 * 从 CountryList 字符串值构造
		 * @param countryListString
		 */
		public CountryListWrapper(String countryListString) {
			super();
			if(StringUtils.isBlank(countryListString)) {
//				logger.debug("countryListString is empty!");
			}
			this.mCountryListString = countryListString;
			parseFromString();
		}

		/**
		 * 从国家列表构造
		 * @param mCountryList
		 */
		public CountryListWrapper(List<CountryInfo> mCountryList) {
			super();
			this.mCountryList = mCountryList;

			// TODO: 更新 mCountryListString 字符串
		}

		public String getCountryListString() {
			return mCountryListString;
		}

		public void setCountryListString(String countryListString) {
			this.mCountryListString = countryListString;

			parseFromString();
		}

		/**
		 * 从 String countryListString 创建 List<CountryInfo> mCountryList
		 * @return List<CountryInfo>
		 */
		private boolean parseFromString() {
			if (null == mCountryListString) {
				return false;
			} else if (StringUtils.isBlank(mCountryListString)) {
				return true;
			}
			mCountryList.clear();
			boolean result = true;
			String countries[] = StringUtils.split(mCountryListString, "|");
			for(String item : countries) {
				List<String> fields = Arrays.asList(StringUtils.split(item, ","));
				if	(fields.size() == 3) { // valid
					CountryInfo info = new CountryInfo();
					info.setCountryName(fields.get(0));
					info.setCountryCode(Integer.valueOf(fields.get(1)));
					info.setFlowPrice(Double.valueOf(fields.get(2)));
					mCountryList.add(info);
				} else { // invalid
					result = false;
					mCountryList.clear();
					break;
				}
			}

			return result;
		}

		
		
		/**
		 * 从 List<CountryInfo> mCountryList 创建 String countryListString
		 * @return String
		 */
		private boolean parseFromCountryList() {
			return false;
		}

		/**
		 * 使用构建国家列表的方式输出地区名, 用逗号分隔
		 *
		 * TODO: 为了优化, 可以考虑使用 StringUtils 直接输出.
		 *
		 * @return 形如 "香港,台湾" 字符串
		 */
		public String getCountryNameStrings() {
			if (StringUtils.isBlank(mCountryListString)) {
				return "";
			}
			if	(0 == mCountryList.size()) { // 不能为 null == mCountryList
				parseFromString();
			}
			List<String> names = new ArrayList<String>();
			for(CountryInfo item : mCountryList) {
				names.add(item.getCountryName());
			}
			return (0 == names.size()) ? "" : StringUtils.join(names, ", ");
		}
	}

}