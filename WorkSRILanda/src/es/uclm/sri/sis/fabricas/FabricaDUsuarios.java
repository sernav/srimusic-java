package es.uclm.sri.sis.fabricas;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import de.umass.lastfm.User;
import es.uclm.sri.logica.borrosa.MotorJFuzzyLogic;
import es.uclm.sri.persistencia.admon.AdmonPesosUsuario;
import es.uclm.sri.persistencia.admon.AdmonUsuarios;
import es.uclm.sri.persistencia.postgre.dao.model.Dusuarios;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosusuario;
import es.uclm.sri.sis.log.Log;
import es.uclm.sri.sis.utilidades.UtilsDAlbum;

/**
 * Construye el usuario, as’ como sus pesos e invoca al sistema de reglas de
 * c‡lculo de pesos para actualizarlos.
 * 
 * @author Sergio Navarro
 * */
public class FabricaDUsuarios implements IFabrica {

    private String usuario;
    private Dusuarios uapp;
    private User usuarioLfm;
    private HashMap<String, Pesosalbum> hashPesosAlbums = new HashMap<String, Pesosalbum>();
    private Pesosusuario pusuario;
    private Pesosusuario historico;
    private Pesosusuario actuales;
    private HashMap<Integer, String> avisosDSistema;

    public FabricaDUsuarios(String usuario, boolean isUserLfm) {
        this.usuario = usuario;
        if (isUserLfm)
            this.usuarioLfm = User.getInfo(usuario, UtilsDAlbum.getApiKeyLastfm());

        this.avisosDSistema = new HashMap<Integer, String>();

        if (usuario == null || usuario.equals("")) {
            this.avisosDSistema.put(new Integer(avisosDSistema.size() + 1), "No hay usuario");
        }
    }

    public FabricaDUsuarios(String usuario, HashMap<String, Pesosalbum> hashPesosAlbums, boolean isUserLfm) {
        this.usuario = usuario;
        if (isUserLfm)
            this.usuarioLfm = User.getInfo(usuario, UtilsDAlbum.getApiKeyLastfm());

        this.avisosDSistema = new HashMap<Integer, String>();

        if (usuario == null || usuario.equals("")) {
            this.avisosDSistema.put(new Integer(avisosDSistema.size() + 1), "No hay usuario");
        }
        this.hashPesosAlbums = hashPesosAlbums;
    }

    public FabricaDUsuarios(User usuario, HashMap<String, Pesosalbum> hashPesosAlbums) {
        this.usuario = usuario.getName();
        this.usuarioLfm = usuario;
        this.hashPesosAlbums = hashPesosAlbums;
        this.avisosDSistema = new HashMap<Integer, String>();
    }
    
    /**
     * Funci—n principal de las f‡bricas, comœn a todas las fabricas que implementan la 
     * interface IFabrica.
     * 
     * La f‡brica sigue el siguente proceso de construcci—n de usuario:
     *  1. Busca al usuario en el sistema. De no existir crea el usaurio con los datos b‡sicos.
     *  2. Si el usuario existe, recoge el hist—rico de pesos del mismo.
     *  3. Con los pesos del hist—rico y los actuales, invoca al sistema de reglas.
     *  
     * @exception SQLException
     * */
    public void run() throws SQLException {
        boolean ok = false;
        AdmonUsuarios admonUsuario = new AdmonUsuarios();
        AdmonPesosUsuario admonPesos = new AdmonPesosUsuario();
        int idUsuario = -1;
        try {
            if (this.usuarioLfm != null) {
                uapp = admonUsuario.devolverUsuario(this.usuarioLfm);
            } else if (this.usuario != null && this.usuario.length() > 0) {
                uapp = admonUsuario.devolverUsuario(this.usuario);
            } else {
                this.avisosDSistema.put(new Integer(avisosDSistema.size() + 1), "No se puede buscar un usuario sin usuario ÁAs’ no!");
                Log.log("ÁNo hay usuario!", 2);
            }

            if (uapp == null && this.usuarioLfm != null) {
                /**
                 * 1. Se inserta el usuario 
                 * 2. Se generan sus pesos iniciales
                 * */
                Log.log("New User! El usuario " + usuarioLfm.getName().toUpperCase() + " es nuevo usuario en el sistema", 1);
                admonUsuario.insertarUsuario(this.usuarioLfm);
                uapp = admonUsuario.devolverUsuario(usuarioLfm);
                this.pusuario = generarPesosUsuario();
                this.pusuario.setID_DUSUARIO_FK(uapp.getID_DUSUARIO());
                this.pusuario.setFECHSESI(Calendar.getInstance().getTime());
                admonPesos.insertarPesosUsuario(this.pusuario);
                Log.log("Pesos de usuario generados: " + pusuario.toString(), 1);
            } else {
                Log.log("El usuario " + usuarioLfm.getName().toUpperCase() + " es un viejo conocido", 1);
                idUsuario = uapp.getID_DUSUARIO();
                /**
                 * 1. Recoger el hist—rico de pesos del usuario 
                 * 2. Calcular los pesos actuales 
                 * 3. Pasar el sistema de reglas 
                 * 4. Actualizar hist—rico de pesos
                 * */
                historico = admonPesos.devolverPesosUsuario(idUsuario)[0];
                Log.log("H¼ de pesos de " + usuarioLfm.getName().toUpperCase() + ": " + historico.toString(), 1);
                this.pusuario = actuales = generarPesosUsuario();
                this.pusuario.setID_DUSUARIO_FK(idUsuario);
                this.pusuario.setID_PESOSUSUARIO(historico.getID_PESOSUSUARIO());
                this.pusuario.setFECHSESI(Calendar.getInstance().getTime());
                Log.log("====== LANZANDO SISTEMA DE REGLAS ======", 2);
                aplicarSistemaDReglas();
                admonPesos.actualizarPesosUsuario(this.pusuario);
                Log.log("Nuevos pesos de usuario: " + pusuario.toString(), 1);
            }
            ok = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.log(e, "(" + FabricaDUsuarios.class.getSimpleName() + ") Excepci—n! " + e.getMessage());
        } finally {
            if (ok) {
                Log.log("Proceso de F‡brica de Usuarios finalizado con Žxito", 1);
            } else {
                Log.log("Proceso de F‡brica de Usuarios finalizado con errores", 2);
            }
        }
    }
    
    /**
     * Invoca al motor de reglas con el fichero de reglas FCL indicado.
     * Para cada uno de los tags aplica el sistema de reglas recalculando Žste.
     * Toda f‡brica que implementa IFabrica, debe sobreescribir esta funci—n.
     * 
     * @exception Exception
     * */
    public void aplicarSistemaDReglas() throws Exception {
        MotorJFuzzyLogic motor = new MotorJFuzzyLogic("/Users/sergionavarro/git/SRILanda-Local/WorkSRILanda/src/es/uclm/sri/logica/borrosa/fcl/definiciones.fcl");
        String[] varsInput = { "escuchas_historico", "escuchas_actuales" };

        if (this.historico == null || this.actuales == null) {
            Log.log("No es posible aplicar el sistema de reglas para los pesos del usuario " + this.usuario, 2);
        } else {
            double[] inSinger = { this.historico.getSINGER(), this.actuales.getSINGER() };
            double outSinger = motor.evaluar(varsInput, inSinger, new String("salida"));

            double[] inRap = { this.historico.getRAP(), this.actuales.getRAP() };
            double outRap = motor.evaluar(varsInput, inRap, new String("salida"));

            double[] inAmbient = { this.historico.getAMBIENT(), this.actuales.getAMBIENT() };
            double outAmbient = motor.evaluar(varsInput, inAmbient, new String("salida"));

            double[] inIndie = { this.historico.getINDIE(), this.actuales.getINDIE() };
            double outIndie = motor.evaluar(varsInput, inIndie, new String("salida"));

            double[] inBlues = { this.historico.getBLUES(), this.actuales.getBLUES() };
            double outBlues = motor.evaluar(varsInput, inBlues, new String("salida"));

            double[] inReggae = { this.historico.getREGGAE(), this.actuales.getREGGAE() };
            double outReggae = motor.evaluar(varsInput, inReggae, new String("salida"));

            double[] inPunk = { this.historico.getPUNK(), this.actuales.getPUNK() };
            double outPunk = motor.evaluar(varsInput, inPunk, new String("salida"));

            double[] inHeavy = { this.historico.getHEAVY(), this.actuales.getHEAVY() };
            double outHeavy = motor.evaluar(varsInput, inHeavy, new String("salida"));

            double[] inAlternative = { this.historico.getALTERNATIVE(), this.actuales.getALTERNATIVE() };
            double outAlternative = motor.evaluar(varsInput, inAlternative, new String("salida"));

            double[] inClassic = { this.historico.getCLASSIC(), this.actuales.getCLASSIC() };
            double outClassic = motor.evaluar(varsInput, inClassic, new String("salida"));

            double[] inElectronic = { this.historico.getELECTRONIC(), this.actuales.getELECTRONIC() };
            double outElectronic = motor.evaluar(varsInput, inElectronic, new String("salida"));

            double[] inRock = { this.historico.getROCK(), this.actuales.getROCK() };
            double outRock = motor.evaluar(varsInput, inRock, new String("salida"));

            double[] inPop = { this.historico.getPOP(), this.actuales.getPOP() };
            double outPop = motor.evaluar(varsInput, inPop, new String("salida"));

            double[] inBrit = { this.historico.getBRIT(), this.actuales.getBRIT() };
            double outBrit = motor.evaluar(varsInput, inBrit, new String("salida"));

            double[] inFolk = { this.historico.getFOLK(), this.actuales.getFOLK() };
            double outFolk = motor.evaluar(varsInput, inFolk, new String("salida"));

            double[] inFunk = { this.historico.getFUNK(), this.actuales.getFUNK() };
            double outFunk = motor.evaluar(varsInput, inFunk, new String("salida"));

            double[] inInstrumental = { this.historico.getINSTRUMENTAL(), this.actuales.getINSTRUMENTAL() };
            double outInstrumental = motor.evaluar(varsInput, inInstrumental, new String("salida"));

            double[] inGrunge = { this.historico.getGRUNGE(), this.actuales.getGRUNGE() };
            double outGrunge = motor.evaluar(varsInput, inGrunge, new String("salida"));

            Double ptotal = outSinger + outRap + outAmbient + outIndie + outBlues + outReggae + outPunk + outHeavy + outAlternative + outClassic + outElectronic
                    + outRock + outPop + outBrit + outFolk + outFunk + outInstrumental + outGrunge;
            ptotal = Math.abs(ptotal);
            if (outSinger > 0)
                this.pusuario.setSINGER(outSinger / ptotal);
            else
                this.pusuario.setSINGER(Double.NaN);
            if (outRap > 0)
                this.pusuario.setRAP(outRap / ptotal);
            else
                this.pusuario.setRAP(Double.NaN);
            if (outAmbient > 0)
                this.pusuario.setAMBIENT(outAmbient / ptotal);
            else
                this.pusuario.setAMBIENT(Double.NaN);
            if (outIndie > 0)
                this.pusuario.setINDIE(outIndie / ptotal);
            else
                this.pusuario.setINDIE(Double.NaN);
            if (outBlues > 0)
                this.pusuario.setBLUES(outBlues / ptotal);
            else
                this.pusuario.setBLUES(Double.NaN);
            if (outReggae > 0)
                this.pusuario.setREGGAE(outReggae / ptotal);
            else
                this.pusuario.setREGGAE(Double.NaN);
            if (outPunk > 0)
                this.pusuario.setPUNK(outPunk / ptotal);
            else
                this.pusuario.setPUNK(Double.NaN);
            if (outHeavy > 0)
                this.pusuario.setHEAVY(outHeavy / ptotal);
            else
                this.pusuario.setHEAVY(Double.NaN);
            if (outAlternative > 0)
                this.pusuario.setALTERNATIVE(outAlternative / ptotal);
            else
                this.pusuario.setALTERNATIVE(Double.NaN);
            if (outClassic > 0)
                this.pusuario.setCLASSIC(outClassic / ptotal);
            else
                this.pusuario.setCLASSIC(Double.NaN);
            if (outElectronic > 0)
                this.pusuario.setELECTRONIC(outElectronic / ptotal);
            else
                this.pusuario.setELECTRONIC(Double.NaN);
            if (outRock > 0)
                this.pusuario.setROCK(outRock / ptotal);
            else
                this.pusuario.setROCK(Double.NaN);
            if (outPop > 0)
                this.pusuario.setPOP(outPop / ptotal);
            else
                this.pusuario.setPOP(Double.NaN);
            if (outBrit > 0)
                this.pusuario.setBRIT(outBrit / ptotal);
            else
                this.pusuario.setBRIT(Double.NaN);
            if (outFolk > 0)
                this.pusuario.setFOLK(outFolk / ptotal);
            else
                this.pusuario.setFOLK(Double.NaN);
            if (outFunk > 0)
                this.pusuario.setFUNK(outFunk / ptotal);
            else
                this.pusuario.setFUNK(Double.NaN);
            if (outInstrumental > 0)
                this.pusuario.setINSTRUMENTAL(outInstrumental / ptotal);
            else
                this.pusuario.setINSTRUMENTAL(Double.NaN);
            if (outGrunge > 0)
                this.pusuario.setGRUNGE(outGrunge / ptotal);
            else
                this.pusuario.setGRUNGE(Double.NaN);
        }
    }
    
    /**
     * Funci—n que genera los pesos ponderados del usuario, bien para usuarios nuevos o
     * para usuarios que ya tienen un hist—rico de pesos.
     * 
     * @return Pesousuario
     * @exception Exception
     * */
    protected Pesosusuario generarPesosUsuario() throws Exception {
        AdmonPesosUsuario admon = new AdmonPesosUsuario();
        Pesosusuario puser = new Pesosusuario();

        if (hashPesosAlbums.size() > 0) {
            if (hashPesosAlbums.size() > 1) {
                Iterator<Entry<String, Pesosalbum>> it = hashPesosAlbums.entrySet().iterator();
                Double singer = 0.0, rap = 0.0, ambient = 0.0, indie = 0.0, blues = 0.0, reggae = 0.0, punk = 0.0, heavy = 0.0, alternative = 0.0, classic = 0.0, electronic = 0.0, rock = 0.0, pop = 0.0, brit = 0.0, folk = 0.0, funk = 0.0, instrumental = 0.0, grunge = 0.0;
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    Pesosalbum palbum = (Pesosalbum) pairs.getValue();
                    if (palbum.getSINGER() != null)
                        singer += palbum.getSINGER();
                    if (palbum.getRAP() != null)
                        rap += palbum.getRAP();
                    if (palbum.getAMBIENT() != null)
                        ambient += palbum.getAMBIENT();
                    if (palbum.getINDIE() != null)
                        indie += palbum.getINDIE();
                    if (palbum.getBLUES() != null)
                        blues += palbum.getBLUES();
                    if (palbum.getREGGAE() != null)
                        reggae += palbum.getREGGAE();
                    if (palbum.getPUNK() != null)
                        punk += palbum.getPUNK();
                    if (palbum.getHEAVY() != null)
                        heavy += palbum.getHEAVY();
                    if (palbum.getALTERNATIVE() != null)
                        alternative += palbum.getALTERNATIVE();
                    if (palbum.getCLASSIC() != null)
                        classic += palbum.getCLASSIC();
                    if (palbum.getELECTRONIC() != null)
                        electronic += palbum.getELECTRONIC();
                    if (palbum.getROCK() != null)
                        rock += palbum.getROCK();
                    if (palbum.getPOP() != null)
                        pop += palbum.getPOP();
                    if (palbum.getBRIT() != null)
                        brit += palbum.getBRIT();
                    if (palbum.getFOLK() != null)
                        folk += palbum.getFOLK();
                    if (palbum.getFUNK() != null)
                        funk += palbum.getFUNK();
                    if (palbum.getINSTRUMENTAL() != null)
                        instrumental += palbum.getINSTRUMENTAL();
                    if (palbum.getGRUNGE() != null)
                        grunge += palbum.getGRUNGE();

                }
                Double ptotal = singer + rap + ambient + indie + blues + reggae + punk + heavy + alternative + classic + electronic + rock + pop + brit + folk
                        + funk + instrumental + grunge;
                if (singer.doubleValue() > 0.0)
                    puser.setSINGER(singer / ptotal);
                else
                    puser.setSINGER(Double.NaN);
                if (rap.doubleValue() > 0.0)
                    puser.setRAP(rap / ptotal);
                else
                    puser.setRAP(Double.NaN);
                if (ambient.doubleValue() > 0.0)
                    puser.setAMBIENT((ambient) / ptotal);
                else
                    puser.setAMBIENT(Double.NaN);
                if (indie.doubleValue() > 0.0)
                    puser.setINDIE(indie / ptotal);
                else
                    puser.setINDIE(Double.NaN);
                if (blues.doubleValue() > 0.0)
                    puser.setBLUES(blues / ptotal);
                else
                    puser.setBLUES(Double.NaN);
                if (reggae.doubleValue() > 0.0)
                    puser.setREGGAE(reggae / ptotal);
                else
                    puser.setREGGAE(Double.NaN);
                if (punk.doubleValue() > 0.0)
                    puser.setPUNK(punk / ptotal);
                else
                    puser.setPUNK(Double.NaN);
                if (heavy.doubleValue() > 0.0)
                    puser.setHEAVY(heavy / ptotal);
                else
                    puser.setHEAVY(Double.NaN);
                if (alternative.doubleValue() > 0.0)
                    puser.setALTERNATIVE(alternative / ptotal);
                else
                    puser.setALTERNATIVE(Double.NaN);
                if (classic.doubleValue() > 0.0)
                    puser.setCLASSIC(classic / ptotal);
                else
                    puser.setCLASSIC(Double.NaN);
                if (electronic.doubleValue() > 0.0)
                    puser.setELECTRONIC(electronic / ptotal);
                else
                    puser.setELECTRONIC(Double.NaN);
                if (rock.doubleValue() > 0.0)
                    puser.setROCK(rock / ptotal);
                else
                    puser.setROCK(Double.NaN);
                if (pop.doubleValue() > 0.0)
                    puser.setPOP(pop / ptotal);
                else
                    puser.setPOP(Double.NaN);
                if (brit.doubleValue() > 0.0)
                    puser.setBRIT(brit / ptotal);
                else
                    puser.setBRIT(Double.NaN);
                if (folk.doubleValue() > 0.0)
                    puser.setFOLK(folk / ptotal);
                else
                    puser.setFOLK(Double.NaN);
                if (funk.doubleValue() > 0.0)
                    puser.setFUNK(funk / ptotal);
                else
                    puser.setFUNK(Double.NaN);
                if (instrumental.doubleValue() > 0.0)
                    puser.setINSTRUMENTAL(instrumental / ptotal);
                else
                    puser.setINSTRUMENTAL(Double.NaN);
                if (grunge.doubleValue() > 0.0)
                    puser.setGRUNGE(grunge / ptotal);
                else
                    puser.setGRUNGE(Double.NaN);
            } else {
                Pesosalbum paux = null;
                Iterator<Map.Entry<String, Pesosalbum>> it = hashPesosAlbums.entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, Pesosalbum> aux = it.next();
                    if (aux.getValue() != null) {
                        paux = aux.getValue();
                    }
                }
                if (paux != null)
                    puser = settearGeneros(paux);
            }
        } else {
            // Generar todos los pesos de usuario a NaN.
            puser.setSINGER(Double.NaN);
            puser.setRAP(Double.NaN);
            puser.setAMBIENT(Double.NaN);
            puser.setINDIE(Double.NaN);
            puser.setBLUES(Double.NaN);
            puser.setREGGAE(Double.NaN);
            puser.setPUNK(Double.NaN);
            puser.setHEAVY(Double.NaN);
            puser.setALTERNATIVE(Double.NaN);
            puser.setCLASSIC(Double.NaN);
            puser.setELECTRONIC(Double.NaN);
            puser.setROCK(Double.NaN);
            puser.setPOP(Double.NaN);
            puser.setBRIT(Double.NaN);
            puser.setFOLK(Double.NaN);
            puser.setFUNK(Double.NaN);
            puser.setINSTRUMENTAL(Double.NaN);
            puser.setGRUNGE(Double.NaN);
        }

        return puser;
    }

    private Pesosusuario settearGeneros(Pesosalbum palbum) throws Exception {
        Pesosusuario puser = new Pesosusuario();

        puser.setSINGER(palbum.getSINGER());
        puser.setRAP(palbum.getRAP());
        puser.setAMBIENT(palbum.getAMBIENT());
        puser.setINDIE(palbum.getINDIE());
        puser.setBLUES(palbum.getBLUES());
        puser.setREGGAE(palbum.getREGGAE());
        puser.setPUNK(palbum.getPUNK());
        puser.setHEAVY(palbum.getHEAVY());
        puser.setALTERNATIVE(palbum.getALTERNATIVE());
        puser.setCLASSIC(palbum.getCLASSIC());
        puser.setELECTRONIC(palbum.getELECTRONIC());
        puser.setROCK(palbum.getROCK());
        puser.setPOP(palbum.getPOP());
        puser.setBRIT(palbum.getBRIT());
        puser.setFOLK(palbum.getFOLK());
        puser.setFUNK(palbum.getFUNK());
        puser.setINSTRUMENTAL(palbum.getINSTRUMENTAL());
        puser.setGRUNGE(palbum.getGRUNGE());

        return puser;
    }

    public String getUsuario() {
        return usuario;
    }

    public User getUsuarioLfm() {
        return usuarioLfm;
    }
    
    public Dusuarios getDUsuario() {
        return uapp;
    }

    public HashMap<String, Pesosalbum> getHashPesosAlbums() {
        return hashPesosAlbums;
    }

    protected Pesosusuario getPesosManufactura() {
        return this.pusuario;
    }

    public HashMap<Integer, String> getAvisosDSistema() {
        return this.avisosDSistema;
    }

}
