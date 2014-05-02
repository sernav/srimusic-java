package es.uclm.sri.logica.borrosa;

import java.io.IOException;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import es.uclm.sri.sis.log.Log;

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

    public MotorJFuzzyLogic(String filename) {
        /**
         * jFuzzyLogic: a Java Library to Design Fuzzy Logic Controllers According to the 
         * Standard for Fuzzy Control Programming.
         * 
         * Cingolani, Pablo, and Jesœs Alcal‡-Fdez.
         * http://jfuzzylogic.sourceforge.net/
         * */
        
        if (filename == null || filename.equals("")) {
            filename = Constantes.FILENAME;
        }
        fis = FIS.load(filename, true);
        
        if (fis == null) {
            Log.log("No se ha podido cargar el archivo: '" + filename + "'", 3);
            System.exit(1);
        }
    }

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
                varInput = 0.01;
            }
            fb.setVariable(variablesIn[i], varInput * 100);
            Log.log("Evaluando variables de entrada: " + variablesIn[i] + " = " + valoresIn[i], 1);
        }

        // Evaluar
        fb.evaluate();

        // Desborrosificar variable de salida
        fb.getVariable(variableOut).defuzzify();

        // Resultados
        Log.log("Resultados: " + fb.getVariable(variableOut).getValue(), 1);

        resultado = fb.getVariable(variableOut).getValue() / 100;
        ok = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.log(e, "(" + MotorJFuzzyLogic.class.getSimpleName() + ") Excepci—n Motor de Reglas! " + e.getMessage());
        }
        if (ok) {
            Log.log("Proceso de evaluaci—n de reglas para las variables finalizado con Žxito", 1);
        }
        return resultado;
        
    }

    private void inicializarVariablesInOut(String[] variablesIn, String variableOut) {
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
