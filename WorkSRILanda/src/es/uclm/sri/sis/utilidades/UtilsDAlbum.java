package es.uclm.sri.sis.utilidades;

import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.sis.entidades.AlbumPonderado;

public class UtilsDAlbum {
    
    private UtilsDAlbum() {
    }
    
    public static String tratarTituloAlbum(String titulo) {
        boolean continuar = true;
        String newTitulo = "";
        titulo = titulo.trim();
        for (int i=0; i < titulo.length() && continuar; i++) {
            if (Character.isDigit(titulo.charAt(i)))
                continuar = false;
            else
                newTitulo += titulo.charAt(i);
        }
        return newTitulo;
    }
    
    public static boolean isEtiquetaCompuesta(String etiqueta) {
        if(etiqueta.equals("indietronica") ||
                etiqueta.equals("electropop")) {
            return true;
        }
        
        if (etiqueta.contains("-") && !etiqueta.equals("hip-hop")
                && !etiqueta.equals("singer-songwriter")) {
            return true;
        }
        
        if (etiqueta.contains(" ") && !etiqueta.equals("hip hop")
                && !etiqueta.equals("trip hop") && !etiqueta.equals("trip hop")
                && !etiqueta.equals("female vocalist") && !etiqueta.equals("male vocalist") 
                && !etiqueta.equals("drum and bass")) {
            return true;
        }
        
        return false;
    }
    
    public static String[] descomponerEtiquetaCompuesta(String etiqueta) {
        String[] etiquetas = new String[2];
        if(etiqueta.equals("indietronica")) {
            etiquetas[0] = "indie";
            etiquetas[1] = "electronic";
        } else if (etiqueta.equals("electropop")){
            etiquetas[0] = "electronic";
            etiquetas[1] = "pop";
        } else if (etiqueta.contains("-") && 
                !etiqueta.equals("hip-hop") && !etiqueta.equals("singer-songwriter")) {
            //if (etiqueta.split("-").length == 2)
                etiquetas = etiqueta.split("-");
        } else if (etiqueta.contains(" ") && !etiqueta.equals("hip hop")
                && !etiqueta.equals("trip hop") && !etiqueta.equals("trip hop")
                && !etiqueta.equals("female vocalist") && !etiqueta.equals("male vocalist") 
                && !etiqueta.equals("drum and bass")) {
            etiquetas = etiqueta.split(" ");
        }
        return etiquetas;
    }
    
    public static void descomponerPesosGeneros(Pesosalbum pesos, AlbumPonderado album) {
        for(int i = 0; i < album.getPesosGeneros().length; i++) {
            try {
            pesos.setROCK(album.getPesosGeneros()[i]);
            pesos.setINDIE(album.getPesosGeneros()[i]);
            } catch (Exception e) {
                
            }
        }
    }

}
