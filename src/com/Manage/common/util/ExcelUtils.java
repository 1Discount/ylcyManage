package com.Manage.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtils {

	/**
	 * 设备导入模板列信息
	 */
	public static final class DeviceInfoImportConstants {
        private DeviceInfoImportConstants() {}

        public static final int TITLE_COUNT = 1; // 此模板的标题的行数 目前未引用，提供以供参考

        // 0 为序号列
        public static final int COL_SN = 1;
        public static final int COL_CardID = 2;
        public static final int COL_deviceColour = 3;
        public static final int COL_distributorName = 4;
        public static final int COL_remark = 5;

        // 添加到数据库时, 需要自定义的字段, 从后面添加 -> 这个值目前暂时不用
//        public static final int COL_GENERATED_START_INDEX = 6;

        public static final int COL_supportCountry = 6;
        public static final int COL_modelNumber = 7;
        public static final int COL_frequencyRange = 8;
        public static final int COL_repertoryStatus = 9;
        // 此值使用来判断此 excel 是否大概系所需要的模板而非因错误使用了其他模板
        // 使用列的数量来大概判断, 带上开头的序号列. 这个值可以不与模板的实际列数量相同,
        // 因为可以让终端用户去决定, 这样对他们来说较自由. 例如有实际需求在后面添加其他列作记录,
        // 但这些列又不需要导入. 不管怎样, 当然要保证开头的此数量的列, 系导入的内容.
        public static final int TEMPLAGE_FILE_CHECK_COL_NUMBERS = 15;

	}

	/**
	 * 漫游SIM卡导入模板列信息
	 * 注意中间插入或删除时,前后几个保证仔细检查对应!
	 *
	 */
	public static final class RoamingSIMInfoImportConstants {
        private RoamingSIMInfoImportConstants() {}

        public static final int TITLE_COUNT = 2; // 此模板的标题的行数 目前未引用，提供以供参考

        // 0 为序号列
        public static final int COL_simAlias = 1;
        public static final int COL_lastDeviceSN = COL_simAlias + 1;
        public static final int COL_ICCID = COL_lastDeviceSN + 1;
        public static final int COL_phone = COL_ICCID + 1;
        public static final int COL_PUK = COL_phone + 1;
        public static final int COL_PIN = COL_PUK +1; // 6

        // ?? 购卡时间 暂确定使用 SIMActivateDate SIM卡激活时间来保存
        // --> 20160118 现在激活时间需要使用了，所以改用 planEndDate 去保存 , 同时加上激活时间
        public static final int COL_planEndDate = COL_PIN + 1;
        public static final int COL_SIMActivateDate = COL_planEndDate + 1;
        public static final int COL_remark = COL_SIMActivateDate + 1; // 8

        // --> 20160118 在备注后增加可使用国家导入
        public static final int COL_countryList = COL_remark + 1;

        // 以下部分不是何广超的表中的列, 系对应漫游卡编辑所增加的列, 表示可支持这些
        // 字段的输入, 但目前这几个全部系可选.
        public static final int COL_cardStatus = COL_countryList + 1;
//        public static final int COL_APN = COL_cardStatus + 1;
//        public static final int COL_MCC = COL_APN + 1;
        public static final int COL_trademark = COL_cardStatus + 1;
        public static final int COL_planType = COL_trademark + 1;
        public static final int COL_cardBalance = COL_planType + 1;
        public static final int COL_planData = COL_cardBalance + 1;
        public static final int COL_planRemainData = COL_planData + 1; // 14

        // 添加到数据库时, 需要自定义的字段, 从后面添加 -> 这个值目前暂时不用
        public static final int COL_GENERATED_START_INDEX = COL_planRemainData + 1;

        public static final int COL_SIMInfoID = COL_GENERATED_START_INDEX;
        public static final int COL_SIMServerID = COL_SIMInfoID + 1;
        public static final int COL_serverIP = COL_SIMServerID + 1;
        public static final int COL_SIMCategory = COL_serverIP + 1;
        public static final int COL_SIMIfActivated = COL_SIMCategory + 1; // 可能有必要根据激活时间设定这个, 但要确认若不填入激活日期时是否肯定系未激活?

        public static final int COL_creatorUserID = COL_SIMIfActivated + 1;
        public static final int COL_creatorUserName = COL_creatorUserID + 1;
        public static final int COL_creatorDate = COL_creatorUserName + 1;
        public static final int COL_sysStatus = COL_creatorDate + 1;

        // 此值使用来判断此 excel 是否大概系所需要的模板而非因错误使用了其他模板
        // 使用列的数量来大概判断, 带上开头的序号列. 这个值可以不与模板的实际列数量相同,
        // 因为可以让终端用户去决定, 这样对他们来说较自由. 例如有实际需求在后面添加其他列作记录,
        // 但这些列又不需要导入. 不管怎样, 当然要保证开头的此数量的列, 系导入的内容.
        // LOG: 0910 添加 trademark运营商
        public static final int TEMPLAGE_FILE_CHECK_COL_NUMBERS = COL_GENERATED_START_INDEX - 1; // 大约等于 COL_GENERATED_START_INDEX - 1
	}

	/**
	 * 本地SIM卡导入模板列信息
	 *
	 * 若需要旧的版本模板，从 git 里导出. 注意中间插入或删除时,前后几个保证仔细检查对应!
	 */
	public static final class SIMInfoImportConstants {
        private SIMInfoImportConstants() {}

        public static final int TITLE_COUNT = 2; // 此模板的标题的行数 目前未引用，提供以供参考

        // 0 为序号列

        public static final int COL_IMSI = 1;
        public static final int COL_ICCID = COL_IMSI + 1; //2;

//        public static final int COL_SIMCategory = 1;
//        public static final int COL_cardStatus = 2;

        // public static final int COL_SIMType = 3; // 目前不用 SIM/USIM 已不使用
        public static final int COL_speedType = COL_ICCID + 1; //3;

        public static final int COL_simAlias = COL_speedType + 1; //4;
        public static final int COL_ifRoam = COL_simAlias + 1; //5;

//        // 5 为(国家)中文名称, 6为国家字母缩写/代号

        public static final int COL_MCC = COL_ifRoam + 1; //6;
        public static final int COL_trademark = COL_MCC + 1;
        public static final int COL_countryList = COL_trademark + 1; //7;

//        public static final int COL_trademark = 9;
//        public static final int COL_phone = 12;
//        public static final int COL_registerInfo = 13;

        public static final int COL_PIN = COL_countryList +1; //8;
        public static final int COL_PUK = COL_PIN + 1; //9;
        public static final int COL_APN = COL_PUK + 1; //10;
        public static final int COL_IMEI = COL_APN + 1; //10;
        public static final int COL_planType = COL_IMEI + 1; //11;
        public static final int COL_planData = COL_planType + 1; //12;
        public static final int COL_planRemainData = COL_planData + 1; //13;
        public static final int COL_simBillMethod = COL_planRemainData + 1; //14;

        public static final int COL_planPrice = COL_simBillMethod + 1; //15;
        public static final int COL_cardInitialBalance = COL_planPrice + 1; //16;
        public static final int COL_cardBalance = COL_cardInitialBalance + 1; //17;
        public static final int SIMIfActivated = COL_cardBalance + 1; //18; // 卡是否激活
        public static final int COL_simActivateCode = SIMIfActivated + 1; //19;

//        public static final int COL_planActivateDate = 25;
//        public static final int COL_planEndDate = 26;
        public static final int COL_SIMActivateDate = COL_simActivateCode + 1; //20;
        public static final int COL_SIMEndDate = COL_SIMActivateDate + 1; //21;

//        public static final int COL_cardInitialBalance = 21; // 提前与套餐金额和可用余额一起
//        public static final int COL_planActivateCode = 30;
        public static final int COL_queryMethod = COL_SIMEndDate + 1; //22;
        public static final int COL_rechargeMethod = COL_queryMethod + 1; //23;
        public static final int COL_remark = COL_rechargeMethod + 1; //24;

        // 添加到数据库时, 需要自定义的字段, 从后面添加 -> 这个值目前暂时不用
        public static final int COL_GENERATED_START_INDEX = COL_remark + 1; //25; //34;

        public static final int COL_SIMInfoID = COL_GENERATED_START_INDEX;
        public static final int COL_SIMServerID = COL_SIMInfoID + 1; //26;
        public static final int COL_serverIP = COL_SIMServerID + 1; //27;
        public static final int COL_MNC = COL_serverIP + 1; //28;
        public static final int COL_SIMCategory = COL_MNC + 1; //29;
        public static final int COL_cardStatus = COL_SIMCategory + 1; //30;
        public static final int COL_creatorUserID = COL_cardStatus + 1; //31;
        public static final int COL_creatorUserName = COL_creatorUserID + 1; //32;
        public static final int COL_creatorDate = COL_creatorUserName + 1; //33;
        public static final int COL_cardSource = COL_creatorDate + 1; //33;

        // 此值使用来判断此 excel 是否大概系所需要的模板而非因错误使用了其他模板
        // 使用列的数量来大概判断, 带上开头的序号列. 这个值可以不与模板的实际列数量相同,
        // 因为可以让终端用户去决定, 这样对他们来说较自由. 例如有实际需求在后面添加其他列作记录,
        // 但这些列又不需要导入. 不管怎样, 当然要保证开头的此数量的列, 系导入的内容.
        // LOG: 0910 添加 trademark运营商
        // 20160113 添加IMEI到 APN之后
        //public static final int TEMPLAGE_FILE_CHECK_COL_NUMBERS = 29; // 大约等于 COL_GENERATED_START_INDEX - 1
	}

/* // 旧版本以对照

       // 0 为序号列
        public static final int COL_SIMCategory = 1;
        public static final int COL_cardStatus = 2;

        // public static final int COL_SIMType = 3; // 目前不用 SIM/USIM 已不使用
        public static final int COL_speedType = 3;

        public static final int COL_simAlias = 4;
        // 5 为(国家)中文名称, 6为国家字母缩写/代号
        public static final int COL_MCC = 7;
        public static final int COL_countryList = 8;
        public static final int COL_trademark = 9;
        public static final int COL_IMSI = 10;
        public static final int COL_ICCID = 11;
        public static final int COL_phone = 12;
        public static final int COL_registerInfo = 13;
        public static final int COL_PIN = 14;
        public static final int COL_PUK = 15;
        public static final int COL_APN = 16;
        public static final int COL_planType = 17;
        public static final int COL_planData = 18;
        public static final int COL_planRemainData = 19;
        public static final int COL_simBillMethod = 20;

        public static final int COL_planPrice = 21;
        public static final int COL_cardBalance = 22;
        public static final int SIMIfActivated = 23; // 卡是否激活
        public static final int COL_simActivateCode = 24;

        public static final int COL_planActivateDate = 25;
        public static final int COL_planEndDate = 26;
        public static final int COL_SIMActivateDate = 27;
        public static final int COL_SIMEndDate = 28;

        public static final int COL_cardInitialBalance = 29;
        public static final int COL_planActivateCode = 30;
        public static final int COL_queryMethod = 31;
        public static final int COL_rechargeMethod = 32;
        public static final int COL_remark = 33;
 */

	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 *
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[][] getData(File file, int ignoreRows)
			throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet st = wb.getSheetAt(sheetIndex);
			// 前面 ignoreRows 行均为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = true; //false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// 注意：一定要设成这个，否则可能会出现乱码
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							value = StringUtils.trim(cell.getStringCellValue());
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd")
											.format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("0").format(cell
										.getNumericCellValue());
							}

							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							// value = ""; // 优化
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y"
									: "N");
							break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) { // 首列序号很重要, 必须保证非空
						hasValue = false;
						break;
					}
					values[columnIndex] = value; // ahming notes: 优化前, 这里系每一列都 trim 其实只需要trim某些列即可
					// hasValue = true;
				}

				if (hasValue) {
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}

	/**
	 * 去掉字符串右边的空格
	 *
	 * @param str
	 *            要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
}
