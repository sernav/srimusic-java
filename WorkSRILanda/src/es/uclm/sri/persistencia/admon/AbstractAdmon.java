package es.uclm.sri.persistencia.admon;

import java.net.ConnectException;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.uclm.sri.persistencia.ConnectionFactory;
import es.uclm.sri.sis.log.Log;

public abstract class AbstractAdmon {
    
    static SqlSessionFactory sqlMapper;
    static SqlSession session = null;
    
    public AbstractAdmon() {
        try {
            if (session == null) {
                establecerConexion();
                session = sqlMapper.openSession();
                session.getConnection().setAutoCommit(true);
                Log.log("Conection Factory and new session OK!", 1);
            }
        } catch (ConnectException e) {
            e.printStackTrace();
            Log.log(e);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.log(e);
        }
    }
    
    private static void establecerConexion() throws ConnectException {
        sqlMapper = ConnectionFactory.getSession();
    }

}
