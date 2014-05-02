package es.uclm.sri.sis.fabricas;

import java.sql.SQLException;
import java.util.HashMap;

public interface IFabrica {
    
    public void run() throws SQLException;
    
    public void aplicarSistemaDReglas() throws Exception;
    
    public HashMap<Integer, String> getAvisosDSistema();

}
