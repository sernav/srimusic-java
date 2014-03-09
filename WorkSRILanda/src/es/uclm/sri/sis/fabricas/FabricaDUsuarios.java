package es.uclm.sri.sis.fabricas;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.drools.rule.builder.dialect.asm.GeneratorHelper.GetMethodBytecodeMethod;

import de.umass.lastfm.User;
import es.uclm.sri.logica.borrosa.MotorJFuzzyLogic;
import es.uclm.sri.persistencia.admon.AdmonPesosUsuario;
import es.uclm.sri.persistencia.admon.AdmonUsuarios;
import es.uclm.sri.persistencia.postgre.dao.model.Dusuarios;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosusuario;
import es.uclm.sri.sis.utilidades.UtilsDLastfm;

public class FabricaDUsuarios implements IFabrica {

    private String usuario;
    private User usuarioLfm;
    private HashMap<String, Pesosalbum> hashPesosAlbums = new HashMap<String, Pesosalbum>();
    private Pesosusuario pusuario;
    
    private Pesosusuario historico;
    private Pesosusuario actuales;
    
    private HashMap<Integer, String> avisosDSistema;

    public FabricaDUsuarios(String usuario, boolean isUserLfm) {
        this.usuario = usuario;
        if (isUserLfm)
            this.usuarioLfm = User.getInfo(usuario, UtilsDLastfm.getApiKey());

        this.avisosDSistema = new HashMap<Integer, String>();

        if (usuario == null || usuario.equals("")) {
            this.avisosDSistema.put(new Integer(avisosDSistema.size() + 1), "No hay usuario");
        }
    }

    public FabricaDUsuarios(String usuario, HashMap<String, Pesosalbum> hashPesosAlbums, boolean isUserLfm) {
        new FabricaDUsuarios(usuario, isUserLfm);
        this.hashPesosAlbums = hashPesosAlbums;
    }

    public void run() {
        /**
         * 1. Comprobar si el usuario est� en el sistema 
         * 2. Si no est� lo insertamos
         * */
        AdmonUsuarios admonUsuario = new AdmonUsuarios();
        AdmonPesosUsuario admonPesos = new AdmonPesosUsuario();
        Dusuarios uapp = null;
        int idUsuario = -1;
        if (this.usuarioLfm != null) {
            uapp = admonUsuario.devolverUsuario(this.usuarioLfm);
        } else if (this.usuario != null && this.usuario.length() > 0) {
            uapp = admonUsuario.devolverUsuario(this.usuario);
        } else {
            this.avisosDSistema.put(new Integer(avisosDSistema.size() + 1), "No se puede buscar un usuario sin usuario �As� no!");
        }

        if (uapp == null && this.usuarioLfm != null) {
            /**
             * 1. Se inserta el usuario 
             * 2. Se generan sus pesos iniciales
             * */
            idUsuario = admonUsuario.insertarUsuario(this.usuarioLfm);
            this.pusuario = generarPesosUsuario();
            this.pusuario.setID_DUSUARIO_FK(idUsuario);
            this.pusuario.setFECHSESI(Calendar.getInstance().getTime());
            admonPesos.insertarPesosUsuario(this.pusuario);
        } else {
            idUsuario = uapp.getID_DUSUARIO();
            /**
             * Se actualizan los pesos del usuario.
             * Pasar el sistema de reglas.
             * */
            historico = admonPesos.devolverPesosUsuario(idUsuario)[0];
            actuales = generarPesosUsuario();
        }

    }
    
    public void aplicarSistemaDReglas(String[] variablesIn, double[] valoresIn, String variableOut) {
        
    }
    
    public void aplicarSistemaDReglas() {
        MotorJFuzzyLogic motor = new MotorJFuzzyLogic("");
        String[] varsInput = {"escuchas_historico" , "escuchas_actuales"};
        
        double[] inSinger = {this.historico.getSINGER() , this.actuales.getSINGER()};
        double outSinger = motor.evaluar(varsInput, inSinger, new String());
        
        double[] inRap = {this.historico.getRAP() , this.actuales.getRAP()};
        double outRap = motor.evaluar(varsInput, inRap, new String());
        
        double[] inAmbient = {this.historico.getAMBIENT() , this.actuales.getAMBIENT()};
        double outAmbient = motor.evaluar(varsInput, inAmbient, new String());
        
        double[] inIndie = {this.historico.getINDIE() , this.actuales.getINDIE()};
        double outIndie = motor.evaluar(varsInput, inIndie, new String());
        
        double[] inBlues = {this.historico.getBLUES() , this.actuales.getBLUES()};
        double outBlues = motor.evaluar(varsInput, inBlues, new String());
        
        double[] inReggae = {this.historico.getREGGAE() , this.actuales.getREGGAE()};
        double outReggae = motor.evaluar(varsInput, inReggae, new String());
        
        Method[] metodos = Pesosusuario.class.getMethods();
        for (int i = 0; i < metodos.length; i++) {
            if (metodos.toString().startsWith("get")) {
                Pesosusuario.class.
            }
        }
        
    }

    protected Pesosusuario generarPesosUsuario() {
        AdmonPesosUsuario admon = new AdmonPesosUsuario();
        Pesosusuario puser = null;
        if (hashPesosAlbums.size() > 0) {
            if (hashPesosAlbums.size() > 1) {
                Iterator<Entry<String, Pesosalbum>> it = hashPesosAlbums.entrySet().iterator();
                Double singer = 0.0, rap = 0.0, ambient = 0.0, indie = 0.0, blues = 0.0, reggae = 0.0, punk = 0.0, 
                        heavy = 0.0, alternative = 0.0, classic = 0.0, electronic = 0.0, rock = 0.0, pop = 0.0, 
                        brit = 0.0, folk = 0.0, funk = 0.0, instrumental = 0.0, grunge = 0.0;
                while (it.hasNext()) {
                    Pesosalbum palbum = (Pesosalbum) it.next();
                    singer += palbum.getSINGER();
                    rap += palbum.getRAP();
                    ambient += palbum.getAMBIENT();
                    indie += palbum.getINDIE();
                    blues += palbum.getBLUES();
                    reggae += palbum.getREGGAE();
                    punk += palbum.getPUNK();
                    heavy += palbum.getHEAVY();
                    alternative += palbum.getALTERNATIVE();
                    classic += palbum.getCLASSIC();
                    electronic += palbum.getELECTRONIC();
                    rock += palbum.getROCK();
                    pop += palbum.getPOP();
                    brit += palbum.getBRIT();
                    folk += palbum.getFOLK();
                    funk += palbum.getFUNK(); 
                    instrumental += palbum.getINSTRUMENTAL();
                    grunge += palbum.getGRUNGE();

                }
                Double ptotal = singer + rap + ambient + indie + blues + reggae + punk + heavy + alternative + classic + 
                        electronic + rock + pop + brit + folk + funk + instrumental + grunge;
                
                puser.setSINGER((singer*100)/ptotal);
                puser.setRAP((rap*100)/ptotal);
                puser.setAMBIENT((ambient*100)/ptotal);
                puser.setINDIE((indie*100)/ptotal);
                puser.setBLUES((blues*100)/ptotal);
                puser.setREGGAE((reggae*100)/ptotal);
                puser.setPUNK((punk*100)/ptotal);
                puser.setHEAVY((heavy*100)/ptotal);
                puser.setALTERNATIVE((alternative*100)/ptotal);
                puser.setCLASSIC((classic*100)/ptotal);
                puser.setELECTRONIC((electronic*100)/ptotal);
                puser.setROCK((rock*100)/ptotal);
                puser.setPOP((pop*100)/ptotal);
                puser.setBRIT((brit*100)/ptotal);
                puser.setFOLK((folk*100)/ptotal);
                puser.setFUNK((funk*100)/ptotal);
                puser.setINSTRUMENTAL((instrumental*100)/ptotal);
                puser.setGRUNGE((grunge*100)/ptotal);
            } else {
                puser = settearGeneros((Pesosalbum) hashPesosAlbums.values());
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

    private Pesosusuario settearGeneros(Pesosalbum palbum) {
        Pesosusuario puser = null;

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

    public HashMap<String, Pesosalbum> getHashPesosAlbums() {
        return hashPesosAlbums;
    }

    public HashMap<Integer, String> getAvisosDSistema() {
        return this.avisosDSistema;
    }

}
