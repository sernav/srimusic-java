package es.uclm.sri.logica.borrosa;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import es.uclm.sri.sis.log.Log;

/**
 * jFuzzyLogic: a Java Library to Design Fuzzy Logic Controllers According to the 
 * Standard for Fuzzy Control Programming.
 * 
 * Cingolani, Pablo, and Jesús Alcalá-Fdez.
 * 
 * http://jfuzzylogic.sourceforge.net/
 * */
public final class MotorJFuzzyLogic {

    private FIS fis;
    private String[] variablesIn;
    private double[] valoresIn;
    private String variableOut;
    private double valorOut;

    public final static class Constantes {
        public static final String FILENAME = "src/es/uclm/sri/logica/borrosa/fcl/definiciones.fcl";
        public static final String INPUT_ESCUCHAS_HISTORICO = "escuchas_historico";
        public static final String INPUT_ESCUCHAS_ACTUALES = "escuchas_actuales";
        public static final String OUTPUT = "salida";
    }
    
    /**
     * Constructor con la ruta del fichero FCL.
     * */
    public MotorJFuzzyLogic(String filename) {
        if (filename == null || filename.equals("")) {
            filename = Constantes.FILENAME;
        }
        fis = FIS.load(filename, true);
        
        if (fis == null) {
            Log.log("No se ha podido cargar el archivo: '" + filename + "'", 3);
            System.exit(1);
        }
    }
    
    /**
     * Función principal.
     * Ejecuta el sistema de reglas para las variables indicadas
     * 
     * @param variablesIn
     * 			Array de nombres de variables de entrada
     * @param valoresIn
     * 			Array de valores de variables de entrada
     * @param variableOut
     * 			Variable resultante
     * @return double
     * */
    public double evaluar(String[] variablesIn, double[] valoresIn, String variableOut) {
        boolean ok = false;
        double resultado = 0;
        if (variablesIn.length != valoresIn.length) {
            return 9999;
        }

        if (!variablesValidas(valoresIn)) {
            return 0;
        }
        
        try {
        // Get default function block
        FunctionBlock fb = fis.getFunctionBlock(null);

        // Establecer variables de entrada
        for (int i = 0; i < variablesIn.length; i++) {
            Double varInput = valoresIn[i];
            if (varInput.isNaN()) {
                varInput = 0.0000001;
            }
            fb.setVariable(variablesIn[i], varInput * 100);
            Log.log("Evaluando variables de entrada: " + variablesIn[i] + " = " + valoresIn[i], 1);
        }
        
        // Pintar gráfico con variables de entrada
        // JFuzzyChart.get().chart(fb);
        
        // Evaluar
        fb.evaluate();
        
        // Pintar gráfico con la salida
        // JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

        // Desborrosificar variable de salida
        fb.getVariable(variableOut).defuzzify();
        Variable tip = fb.getVariable(variableOut);
        
        Log.log(tip.toStringCpp(), 1);
        
        // Calcular valor de salida con la variable desborrosificada para actualizar peso hist�rico
        resultado = calculoResultado(tip.getValue(), valoresIn[0]*100, valoresIn[1]*100);
        
        // Resultados
        resultado = resultado / 100;
        Double dResultado = new Double(resultado);
        if (dResultado.isNaN()) {
            resultado = 0;
        }
        
        Log.log("Resultado: " + resultado, 1);
        ok = true;
        
        } catch (Exception e) {
            e.printStackTrace();
            Log.log(e, "(" + MotorJFuzzyLogic.class.getSimpleName() + ") Excepción Motor de Reglas! " + e.getMessage());
        } finally {
            if (ok) {
                Log.log("Proceso de evaluación de reglas para las variables finalizado con éxito", 1);
            }
        }
        return resultado;  
    }
    
    private double calculoResultado(double salida, double historico, double actual) {
        Log.log("Cálculo de resultado para actualizar el histórico de usuario", 1);
        double aux = 0;
        if (salida > 0) {
            /*
             * El valor actual supera al guardado en el histórico
             * */
            aux = actual * (salida / 100);
        } else {
            /*
             * El valor de histórico es mayor que el actual
             * */
            aux = historico * (salida / 100);
        }
        return historico + aux;
    }
    
    @Deprecated
    protected void inicializarVariablesInOut(String[] variablesIn, String variableOut) {
        if (variablesIn == null || variablesIn.length == 0) {
            String[] varsIn = { Constantes.INPUT_ESCUCHAS_HISTORICO, Constantes.INPUT_ESCUCHAS_ACTUALES };
            this.variablesIn = varsIn;
        } else {
            this.variablesIn = variablesIn;
        }

        if (variableOut == null || variableOut.equals("")) {
            this.variableOut = Constantes.OUTPUT;
        } else {
            this.variableOut = variableOut;
        }
    }

    private boolean variablesValidas(double[] varIn) {
        boolean allNaN = true;
        int cont = 0;
        for (int i = 0; i < varIn.length; i++) {
            Double d = varIn[i];
            if (d.isNaN())
                cont++;
        }
        if (cont == varIn.length)
            allNaN = false;
        return allNaN;
    }

    public FIS getFis() {
        return fis;
    }

    public String[] getVariablesIn() {
        return variablesIn;
    }

    public double[] getValoresIn() {
        return valoresIn;
    }

    public String getVariableOut() {
        return variableOut;
    }

    public double getValorOut() {
        return valorOut;
    }

}
