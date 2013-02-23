package es.uclm.sri.persistencia.postgre.dao;

import es.uclm.sri.persistencia.postgre.dao.model.Vpesosuser;

public interface VpesosuserMapper {
	
    int deleteByPrimaryKey(Integer ID_VPESOSU);

    int insert(Vpesosuser record);

    int insertSelective(Vpesosuser record);

    Vpesosuser selectByPrimaryKey(Integer ID_VPESOSU);

    int updateByPrimaryKeySelective(Vpesosuser record);

    int updateByPrimaryKey(Vpesosuser record);
}