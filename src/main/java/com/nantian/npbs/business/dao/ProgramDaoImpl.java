package com.nantian.npbs.business.dao;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.nantian.npbs.business.model.TbBiMenu;
import com.nantian.npbs.business.model.TbSmProgram;
import com.nantian.npbs.core.orm.impl.BaseHibernateDao;

/**
 * 应用程序更新
 * 
 * @author MDB
 * 
 */
@Repository(value = "pragramDao")
public class ProgramDaoImpl extends BaseHibernateDao implements ProgramDao {

	private static Logger logger = LoggerFactory.getLogger(ProgramDaoImpl.class);

	/*@Override
	public TbSmProgram getProgrameByFilepath(String filepath) {
		TbSmProgram b = (TbSmProgram)get(filepath);
		return b;
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public TbSmProgram getProgrameByFilepath(String filepath) {
		List<TbSmProgram> program = new LinkedList<TbSmProgram>();
		StringBuffer sql = new StringBuffer();
		sql.append(" from TbSmProgram p where p.filepath = '"+filepath+"'");
		
		try {
			program = find(sql.toString());
			logger.info("sql:[{}]",sql );
			logger.info("数据库应用程序个数{} ",program.size());
		} catch (Exception e) {
			logger.error("查询所有菜单目录 =[" + sql + "]", e);
		} finally{
			sql = null;
		}
		if(program.size() != 0){
			return (TbSmProgram)program.get(0);
		}else {
			return null;
		}
		
	}

}
