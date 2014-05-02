package es.uclm.sri.sis.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import es.uclm.sri.sis.utilidades.Utils;

public class Log {

    private static PrintWriter log;
    private static PrintWriter exceptionLog;

    static {
        synchronized (Log.class) {
            String pathLog = "logs/";
            String mascara = Utils.convertirFecha(Calendar.getInstance().getTime(), "ddMMyyyy");
            try {
                log = new PrintWriter(new FileWriter(new File(pathLog, mascara + "_SriLanda.log"), true));
                exceptionLog = new PrintWriter(new FileWriter(new File(pathLog, mascara + "_SriLandaException.log"), true));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void log(Throwable exception) {
        if (exception != null)
            exception.printStackTrace(exceptionLog);
    }
    
    public static void log(Throwable exception, String mensaje) {
        mensaje = "[EXCEPTION] " + mensaje;
        log(exceptionLog, mensaje);
        if (exception != null)
            exception.printStackTrace(exceptionLog);
    }
    
    public static void log(String mensaje) {
        log(log, mensaje);
    }
    
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
            default:
                break;
        }
        log(log, cabecera + " " + mensaje);
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
