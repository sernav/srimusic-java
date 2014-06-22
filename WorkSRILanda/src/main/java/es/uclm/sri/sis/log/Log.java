package es.uclm.sri.sis.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import es.uclm.sri.sis.utilidades.Utils;

/**
 * Centralización de los log para funciones del sistema, errores y scraping.
 * 
 * @author Sergio Navarro
 * */
public class Log {

    private static PrintWriter log;
    private static PrintWriter scrapingLog;
    private static PrintWriter exceptionLog;

    static {
        synchronized (Log.class) {
            String pathLog = "./logs/";
            String mascara = Utils.convertirFecha(Calendar.getInstance().getTime(), "ddMMyyyy");
            try {
                log = new PrintWriter(new FileWriter(new File(pathLog, mascara + "_SriLanda.log"), true));
                exceptionLog = new PrintWriter(new FileWriter(new File(pathLog, mascara + "_SriLandaException.log"), true));
                scrapingLog = new PrintWriter(new FileWriter(new File(pathLog, mascara + "_Srapring.log"), true));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void log(Throwable exception) {
        if (exception != null) {
            exception.printStackTrace(exceptionLog);
        }
    }
    
    /**
     * Log específico de las excepciones. Carga fichero concreto: XXXXX_SriLandaExeption.log
     * 
     * @param mensaje: String
     * */
    public static void log(Throwable exception, String mensaje) {
        mensaje = "[EXCEPTION] " + mensaje + ".\n\t" + exception.toString();
        log(exceptionLog, mensaje);
        if (exception != null)
            exception.printStackTrace(exceptionLog);
        System.err.println(mensaje + "\n\t" + exception.toString());
    }
    
    public static void log(String mensaje) {
        log(log, mensaje);
    }
    
    /**
     * Log específico del scraping. Carga fichero concreto:  XXXXX_Sraping.log
     * 
     * @param mensaje: String
     * */
    public static void logScraping(String mensaje) {
    	mensaje = "[SCRAPING] " + mensaje;
    	log(scrapingLog, mensaje);
    	System.out.println(mensaje);
    }
    
    /**
     * Log con opciones de cabecera que se seleccionan como parámetro
     *  1. INFO
     *  2. WARNING
     *  3. EXCEPTION
     *  4. CITE
     *  5. LINK
     *  6. SCRAPING
     *  
     *  @param mensaje: String
     *  @param tipo: Int
     * */
    public static void log(String mensaje, int tipo) {
        String cabecera = "";
        switch(tipo) {
            case 1: 
                cabecera = "[INFO]";
                break;
            case 2:
                cabecera = "[WARNING]";
                break;
            case 3:
                cabecera = "[EXCEPTION]";
                break;
            case 4:
                cabecera = "[CITE]";
                break;
            case 5:
                cabecera = "[LINK]";
                break;
            case 6:
            	cabecera = "[SCRAPING]";
            default:
                break;
        }
        log(log, cabecera + " " + mensaje);
        System.out.println(cabecera + " " + mensaje);
    }

    private static void log(PrintWriter pw, String mensaje) {
        if (pw == null) {
            System.out.println(mensaje);
        } else {
            String cabecera = Utils.convertirFecha(Calendar.getInstance().getTime(), "ddMMyyy HH:mm:ss");
            synchronized (pw) {
                pw.print(cabecera);
                pw.print(" - ");
                pw.println(mensaje);
                pw.flush();
            }
        }
    }

}
