package es.uclm.sri.persistencia.admon;

import es.uclm.sri.persistencia.postgre.dao.HistoricoMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Historico;

/**
 * 
 * @author Sergio Navarro
 * @date Dic, 2013
 * */
public class AdmonHistorico extends AbstractAdmon {

    private HistoricoMapper mapper = null;

    public AdmonHistorico() {
        super();
        this.mapper = session.getMapper(HistoricoMapper.class);
    }

    public Historico[] devolverHistoricoDUsuario(int id_usuario) {
        return mapper.selectByIdUser(id_usuario);
    }

    public Historico devolverHistorico(int id_historico) {
        return mapper.selectByPrimaryKey(id_historico);
    }

    public int insertarHistoricoDUsuario(Historico record) {
        return mapper.insert(record);
    }

    public int elimitarRegistroHistorico(int id_historico) {
        return mapper.deleteByPrimaryKey(id_historico);
    }

    public int actualizarRegistroHistorico(Historico record) {
        return mapper.updateByPrimaryKey(record);
    }

}
