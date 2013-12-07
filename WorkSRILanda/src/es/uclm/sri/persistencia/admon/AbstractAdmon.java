package es.uclm.sri.persistencia.admon;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.uclm.sri.persistencia.ConnectionFactory;

public abstract class AbstractAdmon {
    
    static SqlSessionFactory sqlMapper;
    static SqlSession session = null;
    
    public AbstractAdmon() {
        try {
            establecerConexion();
            session = sqlMapper.openSession();
            session.getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void establecerConexion() {
        sqlMapper = ConnectionFactory.getSession();
    }

}
