package es.uclm.sri.sis.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

public class Log {

    private static PrintWriter log;

    private static PrintWriter exceptionLog;

    static {
        synchronized (Log.class) {
            String pathLog = "";
            String mascara = Calendar.getInstance().getTime().toString();
            try {
                log = new PrintWriter(new FileWriter(new File(pathLog, mascara + "Sri_Landa.log"), true));
                exceptionLog = new PrintWriter(new FileWriter(new File(pathLog, mascara + "SriLanda_Exception.log"), true));

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
        log(exceptionLog, mensaje);
        if (exception != null)
            exception.printStackTrace(exceptionLog);
    }
    
    public static void log(String mensaje) {
        log(log, mensaje);
    }

    private static void log(PrintWriter pw, String mensaje) {
        if (pw == null) {
            System.out.println(mensaje);
        } else {
            Date date = Calendar.getInstance().getTime();
            synchronized (pw) {
                pw.print(date.getTime());
                pw.print(" - ");
                pw.println(mensaje);
                pw.flush();
            }
        }
    }

}
