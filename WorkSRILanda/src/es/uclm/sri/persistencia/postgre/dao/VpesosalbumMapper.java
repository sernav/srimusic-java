package es.uclm.sri.persistencia.postgre.dao;

import es.uclm.sri.persistencia.postgre.dao.model.Vpesosalbum;

public interface VpesosalbumMapper {
    int deleteByPrimaryKey(Integer ID_VPESOSA);

    int insert(Vpesosalbum record);

    int insertSelective(Vpesosalbum record);

    Vpesosalbum selectByPrimaryKey(Integer ID_VPESOSA);

    int updateByPrimaryKeySelective(Vpesosalbum record);

    int updateByPrimaryKey(Vpesosalbum record);
}