package com.Manage.dao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.Easy2goUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.common.util.ByteUtils.Bytes;
import com.Manage.entity.SIMAndSN;
import com.Manage.entity.SIMCardUsageRecord;
import com.Manage.entity.SIMCost;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMRecord;
import com.Manage.entity.SIMcostStatistics;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.Manage.entity.report.SimStatusStatByCountry;

@SuppressWarnings("rawtypes")
@Repository
public class SIMInfoDao extends BaseDao
{

	private static final String NAMESPACE = SIMInfoDao.class.getName() + ".";



	/**
	 * 分页查询SIMInfo列表 返回json
	 * 
	 * @param searchDTO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPage(SearchDTO searchDTO, HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<SIMInfo> arr = (List<SIMInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMInfo a : arr)
			{
				if (a.getSIMActivateDate() != null)
				{
					a.setSIMActivateDate(a.getSIMActivateDate().substring(0, a.getSIMActivateDate().length() - 5));
				}
				if (a.getSIMEndDate() != null)
				{

					a.setSIMEndDate(a.getSIMEndDate().substring(0, a.getSIMEndDate().length() - 5));
				}
				if (StringUtils.isNotBlank(a.getSlotNumber()))
				{
					String[] strings = a.getSlotNumber().split("-");
					if (strings.length == 2)
					{
						a.setSlotNumber(strings[0] + "-" + (Integer.parseInt(strings[1]) + 1));
					}
				}
				JSONObject obj = JSONObject.fromObject(a);
				String codelistString = a.getCountryList();
				String cnameString = "";
				if (codelistString.indexOf("|") >= 0)
				{
					String[] codearr = codelistString.split("\\|");

					for (int i = 0; i < codearr.length; i++)
					{
						if (i == codearr.length - 1)
						{
							cnameString += mccNameMap.get(codearr[i]);
						}
						else
						{
							cnameString += mccNameMap.get(codearr[i]) + ",";
						}
					}
				}
				else
				{
					cnameString += mccNameMap.get(codelistString);
				}

				obj.put("countryName", cnameString); // 不做 left join, 手动设置国家名称,
														// 或者设法交由前端去处理
				// 将SIM代号的-后面数字加1显示

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}



	/**
	 * 分页查询已删除SIMInfo 返回json
	 * 
	 * @param searchDTO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPageDeleted(SearchDTO searchDTO, HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPageDeleted", "getcountDeleted", searchDTO);

			List<SIMInfo> arr = (List<SIMInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMInfo a : arr)
			{
				if (a.getSIMActivateDate() != null)
				{
					a.setSIMActivateDate(a.getSIMActivateDate().substring(0, a.getSIMActivateDate().length() - 5));
				}
				if (a.getSIMEndDate() != null)
				{

					a.setSIMEndDate(a.getSIMEndDate().substring(0, a.getSIMEndDate().length() - 5));
				}
				JSONObject obj = JSONObject.fromObject(a);
				obj.put("countryName", mccNameMap.get(a.getMCC())); // 不做 left
																	// join,
																	// 手动设置国家名称,
																	// 或者设法交由前端去处理
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}



	// /**
	// * 全部SIMInfo列表
	// */
	// public List<SIMInfo> getAll(String arg)
	// {
	// try {
	// return getSqlSession().selectList(NAMESPACE + "queryAll", arg);
	//
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new BmException(Constants.common_errors_1004, e);
	// }
	// }

	/**
	 * 详情查询（根据SIMInfo id）
	 */
	public SIMInfo getById(String id)
	{
		try
		{
			return (SIMInfo) getSqlSession().selectOne(NAMESPACE + "queryById", id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 详情查询（根据SIMInfo IMSI）
	 */
	public SIMInfo getByImsi(String imsi)
	{
		try
		{
			return (SIMInfo) getSqlSession().selectOne(NAMESPACE + "queryByImsi", imsi);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 插入
	 * 
	 * @param info
	 * @return
	 */
	public int insertInfo(SIMInfo info)
	{
		try
		{
			return getSqlSession().insert(NAMESPACE + "insertInfo", info);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}



	/**
	 * 更新
	 * 
	 * @param info
	 * @return
	 */
	public int updateInfo(SIMInfo info)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateInfo", info);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 更新 sysStatus
	 * 
	 * @param info
	 * @return
	 */
	public int updateInfoSysStatus(SIMInfo info)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 统计查询
	 * 
	 * @param SIMInfo
	 *            info
	 * @return
	 */
	public Map<String, String> allCount(SIMInfo info)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "allCount", info);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}



	/**
	 * 通过SIM卡状态查询各地区SIM卡数量
	 * 
	 * @param info
	 * @return
	 */
	public List<SimStatusStatByCountry> getSimStatusStatByCountry(SIMInfo info)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "querySimStatusStatByCountry", info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 通过SIM卡状态查询各地区SIM卡数量 根据searchDTO 返回 Page 数据, 在前端再考虑数据的格式 注意目前实际不分页
	 * 
	 * @param searchDTO
	 * @return
	 */
	public Page getPageOfSimStatusStatByCountry(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPageOfSimStatusStatByCountry", "queryCountOfSimStatusStatByCountry", searchDTO);

			// List<SIMInfo> arr = (List<SIMInfo>) page.getRows();
			// JSONObject object = new JSONObject();
			// object.put("success", true);
			// object.put("totalRows", page.getTotal());
			// object.put("curPage", page.getCurrentPage());
			// JSONArray ja = new JSONArray();
			// for (SIMInfo a : arr) {
			// JSONObject obj = JSONObject.fromObject(a);
			// obj.put("countryName", mccNameMap.get(a.getMCC())); // 不做 left
			// join, 手动设置国家名称, 或者设法交由前端去处理
			// ja.add(obj);
			// }
			// object.put("data", ja);
			return page; // object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}



	/**
	 * 通过SIM卡状态查询各运营商SIM卡数量 根据searchDTO 返回 Page 数据, 在前端再考虑数据的格式 注意目前实际不分页
	 * 
	 * @param searchDTO
	 * @return
	 */
	public Page getPageOfSimStatusStatByOperator(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPageOfSimStatusStatByOperator", "queryCountOfSimStatusStatByOperator", searchDTO);
			return page;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}



	/**
	 * 查询SIM卡状态
	 */
	public List<SIMInfo> getcardStatus(SIMInfo simInfo)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getcardStatus", simInfo);

		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 通过返回 count 数 查询是否存在此 IMSI
	 * 
	 * @param imsi
	 * @return
	 */
	public int getCountByIMSI(String imsi)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getCountByIMSI", imsi);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 根据服务器获得SIM卡
	 * 
	 * @return
	 */
	public List<SIMInfo> getSIMbyserverID(SIMInfo simInfo)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getSIMbyserverID", simInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 根据 simAlias查询SIM卡信息
	 * 
	 * @return
	 */
	public SIMInfo getSIMInfoBysimAlias(SIMInfo simInfo)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getSIMInfoBysimAlias", simInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 标记SIM卡 notes 字段
	 * 
	 * @param info
	 * @return
	 */
	public int updateSimNotes(SIMInfo info)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateNotes", info);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public int updataStatusByIMSI(SIMInfo info)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updataStatusByIMSI", info);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 查询需要开启的SIM卡
	 * 
	 * @return
	 */
	public List<SIMInfo> selectTimeOpenCard()
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "selectTimeOpenCard");
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 查询需要关闭的SIM卡
	 * 
	 * @return
	 */
	public List<SIMInfo> selectTimeShutdownCard()
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "selectTimeShutdownCard");
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public int updateTimeOpenCard(SIMInfo IMSI)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateTimeOpenCard", IMSI);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public int updateTimeShutdownCard(SIMInfo IMSI)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateTimeShutdownCard", IMSI);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 分页查询SIM历史绑定SN列表
	 * 
	 * @param searchDTO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPageSIMAndSN(SearchDTO searchDTO, HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPageSIMAndSNByIMSI", "getcountSIMAndSN", searchDTO);

			List<SIMAndSN> arr = (List<SIMAndSN>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMAndSN a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
				String codelistString = a.getMCC();
				String cnameString = "";
				if (codelistString.indexOf("|") >= 0)
				{
					String[] codearr = codelistString.split("\\|");

					for (int i = 0; i < codearr.length; i++)
					{
						if (i == codearr.length - 1)
						{
							cnameString += mccNameMap.get(codearr[i]);
						}
						else
						{
							cnameString += mccNameMap.get(codearr[i]) + ",";
						}
					}
				}
				else
				{
					cnameString += mccNameMap.get(codelistString);
				}

				obj.put("countryName", cnameString); // 不做 left join, 手动设置国家名称,
														// 或者设法交由前端去处理
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}



	public List<SIMInfo> excelimportSIM(SIMInfo simInfo)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "excelimportSIM", simInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public List<SIMRecord> getSIMRecordByDate(SIMRecord simRecord)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getSIMRecordByDate", simRecord);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	@SuppressWarnings("unchecked")
	public String getSIMRecordPage(SearchDTO searchDTO, HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "getSIMRecordPageByDate", "getSIMRecordPageCountByDate", searchDTO);

			List<SIMRecord> arr = (List<SIMRecord>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMRecord simRecord : arr)
			{
				JSONObject jo = new JSONObject();
				jo.put("country", mccNameMap.get(simRecord.getCountry()));
				jo.put("useFlow", simRecord.getUseFlow());
				jo.put("useCount", simRecord.getUseCount());
				jo.put("SIMCount", simRecord.getSIMCount());
				jo.put("creatorDate", simRecord.getCreatorDate());

				ja.add(jo);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	@SuppressWarnings("unchecked")
	public String getSIMRecordPageByCountry(SearchDTO searchDTO, HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "getSIMRecordPageByCountry", "getSIMRecordPageCountByCountry", searchDTO);

			List<SIMRecord> arr = (List<SIMRecord>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMRecord simRecord : arr)
			{
				simRecord.setCountry(mccNameMap.get(simRecord.getCountry()));
				ja.add(simRecord);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public SIMRecord getSIMRecordByIMSI(SIMRecord simRecord)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getSIMRecordByIMSI", simRecord);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * SIM卡成本统计记录
	 * 
	 * @param searchDTO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getSIMCardUsageRecordPage(SearchDTO searchDTO, HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "getSIMCardUsageRecordPage", "getSIMCardUsageRecordCount", searchDTO);

			List<SIMCardUsageRecord> arr = (List<SIMCardUsageRecord>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMCardUsageRecord scur : arr)
			{
				DecimalFormat format = new DecimalFormat("0.00");
				scur.setMoneyCount(format.format(new BigDecimal(scur.getMoneyCount())));
				JSONObject jo = JSONObject.fromObject(scur);
				String codelistString = scur.getCountry();
				String cnameString = "";
				if (codelistString.indexOf("|") >= 0)
				{
					String[] codearr = codelistString.split("\\|");

					for (int i = 0; i < codearr.length; i++)
					{
						if (i == codearr.length - 1)
						{
							cnameString += mccNameMap.get(codearr[i]);
						}
						else
						{
							cnameString += mccNameMap.get(codearr[i]) + ",";
						}
					}
				}
				else
				{
					cnameString += mccNameMap.get(codelistString);
				}
				jo.put("countryName", cnameString); // 不做 left join, 手动设置国家名称,
													// 或者设法交由前端去处理
				ja.add(jo);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 查询总消耗金额
	 * 
	 * @return
	 */
	public String searchMoneySum(SIMCardUsageRecord simCardUsageRecord)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "searchMoneySum", simCardUsageRecord);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 获取所有SIM卡信息
	 * 
	 * @return
	 */
	public List<SIMInfo> getAll(SIMInfo simInfo)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getAll", simInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public List<SIMInfo> getDayUserAll(SIMInfo simInfo)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getDayUserAll", simInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public int insertSIMcostStatistics(SIMcostStatistics siMcostStatistics)
	{
		try
		{
			return getSqlSession().insert(NAMESPACE + "insertSIMcostStatistics", siMcostStatistics);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1001, e);
		}
	}



	/**
	 * 分页查询SIMInfo列表 返回json
	 * 
	 * @param searchDTO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPageSIMcostStatistics1(SearchDTO searchDTO, HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "SIMcostStatisticsQueryPage", "SIMcostStatisticsQueryPageCount", searchDTO);

			List<SIMcostStatistics> arr = (List<SIMcostStatistics>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMcostStatistics a : arr)
			{
				String codelistString = a.getCountryList();

				String cnameString = "";
				if (codelistString.indexOf("|") >= 0)
				{
					String[] codearr = codelistString.split("\\|");

					for (int i = 0; i < codearr.length; i++)
					{
						if (i == codearr.length - 1)
						{
							cnameString += mccNameMap.get(codearr[i]);
						}
						else
						{
							cnameString += mccNameMap.get(codearr[i]) + ",";
						}
					}
				}
				else
				{
					cnameString += mccNameMap.get(codelistString);
				}

				String codeString = a.getCountry();
				String cnameString1 = "";
				if (codeString.indexOf("|") >= 0)
				{
					String[] codearr = codeString.split("\\|");

					for (int i = 0; i < codearr.length; i++)
					{
						if (i == codearr.length - 1)
						{
							cnameString1 += mccNameMap.get(codearr[i]);
						}
						else
						{
							cnameString1 += mccNameMap.get(codearr[i]) + ",";
						}
					}
				}
				else
				{
					cnameString1 += mccNameMap.get(codeString);
				}

				a.setCountryList(cnameString);
				a.setCountry(cnameString1);

				JSONObject obj = JSONObject.fromObject(a);
				obj.put("useFlow", Easy2goUtil.lldw(String.valueOf(a.getUseFlow()) + "k"));
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("message:" + e.getMessage());
			System.out.println("cause:" + e.getCause());
			throw new BmException(Constants.common_errors_1004, e);
		}

	}



	public String getPageSIMcostStatistics(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "SIMcostQueryPage", "SIMcostQueryPageCount", searchDTO);

			List<SIMCost> arr = (List<SIMCost>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMCost a : arr)
			{
				double totalPrice = a.getTotalPrice();
				Long totaldata = a.getTotalData();
				Long totalFlow = a.getTotalFlow();
				
				String countryName = a.getCountryName();
				List<SIMCost> simCosts= null;
				if("泰国".equals(countryName)){
					try
					{
						((SIMInfo)(searchDTO.getObj())).setMCC("520");
						simCosts =  getSqlSession().selectList(NAMESPACE+"SIMcostQueryInfoPageInfo",searchDTO);
					}
					catch (Exception e)
					{
						throw new BmException(Constants.common_errors_1004, e);
					}
					if(simCosts!=null){
						totalPrice = 0;
						totaldata = 0L;
						for (SIMCost simCost : simCosts)
						{
							totalPrice+=simCost.getTotalPrice()*4;
							totaldata+= simCost.getTotalData()*4;
						}
						a.setTotalPrice(totalPrice);
						a.setTotalData(totaldata);
					}
				}
				
				
				JSONObject obj = new JSONObject();
				obj.put("country", a.getCountry());
				obj.put("totalSim", a.getTotalSim());
				obj.put("totalPrice", a.getTotalPrice());

				obj.put("totalData", Easy2goUtil.lldw(String.valueOf(a.getTotalData()) + "k"));

				if (totaldata == 0)
				{
					obj.put("primeCost", "");
				}
				else
				{
					obj.put("primeCost", (int) (totalPrice / (totaldata / 1024) * (totalFlow / 1024)));
				}
				
				obj.put("useFlow", Easy2goUtil.lldw(String.valueOf(a.getTotalFlow()) + "k"));
				obj.put("countryName", a.getCountryName());

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	private Object SIMInfo(Object obj)
	{
		// TODO Auto-generated method stub
		return null;
	}



	public String getPageSIMcostStatisticsByDay(SearchDTO searchDTO, HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "getPageSIMcostStatisticsByDay", "getPageSIMcostStatisticsByDayCount", searchDTO);
			List<SIMcostStatistics> arr = (List<SIMcostStatistics>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMcostStatistics a : arr)
			{
				
				String codelistString = a.getCountryList();
				String cnameString = "";
				if (codelistString.indexOf("|") >= 0)
				{
					String[] codearr = codelistString.split("\\|");

					for (int i = 0; i < codearr.length; i++)
					{
						if (i == codearr.length - 1)
						{
							cnameString += mccNameMap.get(codearr[i]);
						}
						else
						{
							cnameString += mccNameMap.get(codearr[i]) + ",";
						}
					}
				}
				else
				{
					cnameString += mccNameMap.get(codelistString);
				}

				
				String codeString = a.getCountry();
				String cnameString1 = "";
				if (codeString.indexOf("|") >= 0)
				{
					String[] codearr = codeString.split("\\|");

					for (int i = 0; i < codearr.length; i++)
					{
						if (i == codearr.length - 1)
						{
							cnameString1 += mccNameMap.get(codearr[i]);
						}
						else
						{
							cnameString1 += mccNameMap.get(codearr[i]) + ",";
						}
					}
				}
				else
				{
					cnameString1 += mccNameMap.get(codeString);
				}

				a.setCountryList(cnameString);
				a.setCountry(cnameString1);

				
				
				JSONObject obj = JSONObject.fromObject(a);
				
				obj.put("useFlow", Easy2goUtil.lldw(String.valueOf(a.getUseFlow()) + "k"));


				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public String getPageSIMcostStatisticsInfo(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "SIMcostQueryInfoPageInfo", "SIMcostQueryPageInfoCount", searchDTO);

			List<SIMCost> arr = (List<SIMCost>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMCost a : arr)
			{
				// 1.5GB/7天套餐*4
				
				if(a.getPlanType()!=null && (a.getPlanType().equals("1.5GB/7天") || a.getPlanType().equals("2.5GB/7天"))){
					a.setTotalData((a.getTotalData())*4);
					a.setTotalPrice(a.getTotalPrice()*4);
				}

				double totalPrice = a.getTotalPrice();
				Long totaldata = a.getTotalData();
				Long totalFlow = a.getTotalFlow();

				JSONObject obj = JSONObject.fromObject(a);

				obj.put("totalData", Easy2goUtil.lldw(String.valueOf(a.getTotalData()) + "k"));
				obj.put("totalFlow", Easy2goUtil.lldw(String.valueOf(a.getTotalFlow()) + "k"));

				if (totaldata == 0)
				{
					obj.put("primeCost", "");
				}
				else
				{
					obj.put("primeCost", (int) (totalPrice / (totaldata / 1024) * (totalFlow / 1024)));
				}

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	
	
	
	
	public String getPageSiminfopirmeCostInfoThree(SearchDTO searchDTO)
	{
		try
		{
			SIMInfo info = (SIMInfo) searchDTO.getObj();
			Page page = queryPage(NAMESPACE, "SIMcostQueryInfoPageInfoThree", "SIMcostQueryInfoPageInfoThreeCount", searchDTO);

			List<SIMCost> arr = (List<SIMCost>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMCost a : arr)
			{
				String assignedSN = a.getAssignedSN();
				String[] assignedSNarr =  assignedSN.split(",");
				Map<String, String> map = new HashMap<>();
				for (int i = 0; i < assignedSNarr.length; i++)
				{
					map.put(assignedSNarr[i].substring(9), "");
				}
				
				assignedSN = ""; 
				for (String key : map.keySet())
				{
					assignedSN = assignedSN+key+"，";
				}
				assignedSN = assignedSN.substring(0,assignedSN.length()-1);
				a.setAssignedSN(assignedSN);
				
				
				// 1.5GB/7天套餐*4
				if(a.getPlanType().equals("1.5GB/7天") || a.getPlanType().equals("2.5GB/7天")){
					a.setTotalData((a.getTotalData())*4);
					a.setTotalPrice(a.getTotalPrice()*4);
				}
				
				double totalPrice = a.getTotalPrice();
				Long totaldata = a.getTotalData();
				Long totalFlow = a.getTotalFlow();
				
				JSONObject obj = JSONObject.fromObject(a);
				obj.put("totalData", Easy2goUtil.lldw(a.getTotalData()+"k"));
				obj.put("totalFlow",  Easy2goUtil.lldw(a.getTotalFlow()+"k"));
				
				if (totaldata == 0)
				{
					obj.put("primeCost", "");
				}
				else
				{
					obj.put("primeCost", (int) (totalPrice / (totaldata / 1024) * (totalFlow / 1024)));
				}
				
				SIMCost cost = new SIMCost();
				cost.setIMSI(a.getIMSI());
				cost.setBegindate(info.getBegindate());
				cost.setEnddate(info.getEnddate());
				cost.setMCC(info.getMCC());
				
				int userDays = this.seelctSIMrecordbyIMSI(cost);
				obj.put("userDays", userDays);
				
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	
	public int seelctSIMrecordbyIMSI(SIMCost cost){
		try
		{
			return getSqlSession().selectOne(NAMESPACE+"seelctSIMrecordbyIMSI",cost);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	
	
	public String getSIMRecordPageByIMSI(SearchDTO searchDTO,HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "getSIMRecordPageByIMSI", "getSIMRecordPageByIMSICount", searchDTO);

			List<SIMRecord> arr = (List<SIMRecord>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMRecord a : arr)
			{
				String assignedSN = a.getAssignedSN();
				String[] assignedSNarr =  assignedSN.split(",");
				assignedSN = ""; 
				for (int i = 0; i < assignedSNarr.length; i++)
				{
					assignedSN+=assignedSNarr[i].substring(9)+"，";
				}
				assignedSN=assignedSN.substring(0,assignedSN.length()-1);
				a.setAssignedSN(assignedSN);
				
				
				
				String codelistString = a.getCountry();
				String cnameString = "";
				if (codelistString.indexOf("|") >= 0)
				{
					String[] codearr = codelistString.split("\\|");

					for (int i = 0; i < codearr.length; i++)
					{
						if (i == codearr.length - 1)
						{
							cnameString += mccNameMap.get(codearr[i]);
						}
						else
						{
							cnameString += mccNameMap.get(codearr[i]) + ",";
						}
					}
				}
				else
				{
					cnameString += mccNameMap.get(codelistString);
				}
				
				a.setCountry(cnameString);
				JSONObject obj = JSONObject.fromObject(a);
				obj.put("useFlow",Easy2goUtil.lldw(  String.valueOf(a.getUseFlow())+"k"  ));
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	
	public String getpageSIMUserInfoByplanType(SearchDTO searchDTO,HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "getpageSIMUserInfoByplanType", "getpageSIMUserInfoByplanTypeCount", searchDTO);

			List<SIMCost> arr = (List<SIMCost>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMCost a : arr)
			{
				// SN去重
				String assignedSN = a.getAssignedSN();
				String[] assignedSNarr =  assignedSN.split(",");
				Map<String, String> map = new HashMap<>();
				for (int i = 0; i < assignedSNarr.length; i++)
				{
					map.put(assignedSNarr[i].substring(9), "");
				}
				
				assignedSN = ""; 
				for (String key : map.keySet())
				{
					assignedSN = assignedSN+key+"，";
				}
				assignedSN = assignedSN.substring(0,assignedSN.length()-1);
				a.setAssignedSN(assignedSN);
				
				
				// 国家去重
				String country = a.getCountry();
				String[] countryarr =  country.split(",");
				Map<String, String> mapcountry = new HashMap<>();
				for (int i = 0; i < countryarr.length; i++)
				{
					mapcountry.put(countryarr[i], "");
				}
				
				country = ""; 
				for (String key : mapcountry.keySet())
				{
					country = country+key+",";
				}
				country = country.substring(0,country.length()-1);
				a.setCountry(country);
				
				
				
				// 转换国家
				String codelistString = a.getCountry();
				String cnameString = "";
				if (codelistString.indexOf(",") >= 0)
				{
					String[] codearr = codelistString.split(",");

					for (int i = 0; i < codearr.length; i++)
					{
						if (i == codearr.length - 1)
						{
							cnameString += mccNameMap.get(codearr[i]);
						}
						else
						{
							cnameString += mccNameMap.get(codearr[i]) + ",";
						}
					}
				}
				else
				{
					cnameString += mccNameMap.get(codelistString);
				}
				a.setCountry(cnameString);
				
				// 1.5GB/7天套餐*4
				if(a.getPlanType().equals("1.5GB/7天") || a.getPlanType().equals("2.5GB/7天")){
					a.setTotalData((a.getTotalData())*4);
					a.setTotalPrice(a.getTotalPrice()*4);
				}
				
				double totalPrice = a.getTotalPrice();
				Long totaldata = a.getTotalData();
				Long totalFlow = a.getTotalFlow();
				
				JSONObject obj = JSONObject.fromObject(a);
				obj.put("totalData", Easy2goUtil.lldw(a.getTotalData()+"k"));
				obj.put("totalFlow",  Easy2goUtil.lldw(a.getTotalFlow()+"k"));
				
				
				double primeCost = 0;
				
				if (totaldata != 0)
				{
					primeCost = (double) (totalPrice / (totaldata / 1024) * (totalFlow / 1024));
				}
				
				double availability=0;
				if(a.getTotalPrice()!=0){
					availability = primeCost/a.getTotalPrice();
				}
				
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.0000");  
				
				obj.put("availability",Double.parseDouble(df.format(availability))*100+"%");
				
				java.text.DecimalFormat   df1   =new   java.text.DecimalFormat("0.00"); 
				
				obj.put("primeCost", df1.format(primeCost));
				
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	public String getpagesimbyplanType(SearchDTO searchDTO,HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "getpagesimbyplanType", "getpagesimbyplanTypeCount", searchDTO);

			List<SIMCost> arr = (List<SIMCost>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMCost a : arr)
			{
				// SN去重
				String assignedSN = a.getAssignedSN();
				String[] assignedSNarr =  assignedSN.split(",");
				Map<String, String> map = new HashMap<>();
				for (int i = 0; i < assignedSNarr.length; i++)
				{
					map.put(assignedSNarr[i].substring(9), "");
				}
				
				assignedSN = ""; 
				for (String key : map.keySet())
				{
					assignedSN = assignedSN+key+"，";
				}
				assignedSN = assignedSN.substring(0,assignedSN.length()-1);
				a.setAssignedSN(assignedSN);
				
				// 国家去重
				String country = a.getCountry();
				String[] countryarr =  country.split(",");
				Map<String, String> mapcountry = new HashMap<>();
				for (int i = 0; i < countryarr.length; i++)
				{
					mapcountry.put(countryarr[i], "");
				}
				
				country = ""; 
				for (String key : mapcountry.keySet())
				{
					country = country+key+",";
				}
				country = country.substring(0,country.length()-1);
				a.setCountry(country);
				
				//转换国家
				String codelistString = a.getCountry();
				String cnameString = "";
				if (codelistString.indexOf(",") >= 0)
				{
					String[] codearr = codelistString.split(",");

					for (int i = 0; i < codearr.length; i++)
					{
						if (i == codearr.length - 1)
						{
							cnameString += mccNameMap.get(codearr[i]);
						}
						else
						{
							cnameString += mccNameMap.get(codearr[i]) + ",";
						}
					}
				}
				else
				{
					cnameString += mccNameMap.get(codelistString);
				}
				a.setCountry(cnameString);
				
				
				// 1.5GB/7天套餐*4
				if(a.getPlanType().equals("1.5GB/7天") || a.getPlanType().equals("2.5GB/7天")){
					a.setTotalData((a.getTotalData())*4);
					a.setTotalPrice(a.getTotalPrice()*4);
				}
				
				double totalPrice = a.getTotalPrice();
				Long totaldata = a.getTotalData();
				Long totalFlow = a.getTotalFlow();
				JSONObject obj = JSONObject.fromObject(a);
				obj.put("totalData", Easy2goUtil.lldw(a.getTotalData()+"k"));
				obj.put("totalFlow",  Easy2goUtil.lldw(a.getTotalFlow()+"k"));
				
				
				double primeCost = 0;
				
				if (totaldata != 0)
				{
					primeCost = (double) (totalPrice / (totaldata / 1024) * (totalFlow / 1024));
				}
				java.text.DecimalFormat   df1   =new   java.text.DecimalFormat("0.00"); 
				
				obj.put("primeCost", df1.format(primeCost));
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	
	public List<SIMCost> simcostexportbycountry(SIMInfo info){
		try
		{
			return getSqlSession().selectList(NAMESPACE+"simcostexportbycountry",info);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
}
