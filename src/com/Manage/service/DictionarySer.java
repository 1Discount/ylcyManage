package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.dao.DictionaryDao;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.common.SearchDTO;
/**
 * @author lipeng
 */
@Service
public class DictionarySer extends BaseService{

	private Logger logger = LogUtil.getInstance(DictionarySer.class);

	/**
	 * 分页，排序，条件查询.
	 * @param searchDTO
	 * @return
	 */
	  public String getpageString(SearchDTO searchDTO){
		  logger.debug("分页server开始");
		  try {
			  String jsonString=dictonaryDao.getpage(searchDTO);
			  logger.debug("分页查询结果:"+jsonString);
			  return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	  }
	  
	    /**
	     * 删除类型
	     * @param dictionary
	     * @return
	     */
		public Dictionary getDicDelete(String dictionary){
			return dictonaryDao.getDicDelete(dictionary);
		}
		/**
		 * 保存
		 * @param dic
		 * @return
		 */
		public int getSaveDic(Dictionary dic){
			return dictonaryDao.getSaveDic(dic);
		}
		/**
		 * getdata
		 * @param device
		 * @return
		 */
		public Dictionary getDicEdit(String dictID){
			return dictonaryDao.getDicUdata(dictID);
		}
		/**
		 * 修改
		 * @param dictionary
		 * @return
		 */
		public int updateDicToEnd(Dictionary dictionary){
			return dictonaryDao.getUpdateDic(dictionary);
		}
		/**
		 * 查看所有类别
		 * @return
		 */
		public List<Dictionary> getAlldescription(){
			return dictonaryDao.getAlldescription();
		}
		
		/**
		 * 通过类别名称查询到值
		 * @param description
		 * @return
		 */
		public List<Dictionary> getAllList(String description){
			return dictonaryDao.getAlllaelListdao(description);
		}
		
		public List<Dictionary> getalertType(String description){
			try {
				return dictonaryDao.getalertType(description);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
		}
		
		public Dictionary getDictByValue(Dictionary dictionary){
			try {
				return dictonaryDao.getDictByValue(dictionary);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
}	
