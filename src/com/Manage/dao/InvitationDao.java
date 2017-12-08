package com.Manage.dao;
import org.springframework.stereotype.Repository;
import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.Invitation;
@Repository
public class InvitationDao extends BaseDao<InvitationDao> {
	private static final String NAMESPACE = InvitationDao.class.getName() + ".";
	
	/**
	 * 发送邀请码时先插入邀请记录.
	 * @param invi
	 * @return
	 */
	public int insertinfo(Invitation invi){
		try {
			return getSqlSession().insert(NAMESPACE+"insertinfo",invi);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	/**
	 * 检验邀请码是否有效
	 * @param code
	 * @return
	 */
	public Invitation getinvitabycode(String code){
		try {
			return getSqlSession().selectOne(NAMESPACE+"getinsertbycode",code);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 更新邀请码状态
	 * @param inviteID
	 * @return
	 */
	public int updatestatus(String inviteCode){
		try {
			return getSqlSession().update(NAMESPACE+"updatestatus",inviteCode);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
		
	}
	
	
	
	
	
}
