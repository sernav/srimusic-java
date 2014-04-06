package es.uclm.sri.logica.borrosa;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

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
        if (filename == null || filename.equals("")) {
            filename = Constantes.FILENAME;
        }
        fis = FIS.load(filename, true);
        
        if (fis == null) {
            System.err.println("No se ha podido cargar el archivo: '" + filename + "'");
            System.exit(1);
        }
    }
    
    public double evaluar(String[] variablesIn, double[] valoresIn, String variableOut) {
        if (variablesIn.length != valoresIn.length) {
            System.err.println("El nœmero de variales y de valores no se corresponden");
            return 9999;
        }
        
        if (!variablesValidas(valoresIn)) {
            return 0;
        }
        
        // Get default function block
        FunctionBlock fb = fis.getFunctionBlock(null);
        
        // Establecer variables de entrada
        for (int i = 0; i < variablesIn.length; i++) {
            Double varInput = valoresIn[i];
            if (varInput.isNaN()) {
                varInput = 0.01;
            }
            fb.setVariable(variablesIn[i], varInput * 100);
            System.out.println(variablesIn[i] + " = " + valoresIn[i]);
        }
        
        // Evaluar
        fb.evaluate();

        // Desborrosificar variable de salida
        fb.getVariable(variableOut).defuzzify();
        
        // Resultados
        System.out.println(fb);
        System.out.println("Tip: " + fb.getVariable(variableOut).getValue());
        
        double resultado = fb.getVariable(variableOut).getValue() / 100;
        if (resultado < 0.001)
            return 0;
        else
            return resultado;
    }
    
    private void inicializarVariablesInOut(String[] variablesIn, String variableOut) {
        if (variablesIn == null || variablesIn.length == 0) {
            String[] varsIn = {Constantes.INPUT_ESCUCHAS_HISTORICO , Constantes.INPUT_ESCUCHAS_ACTUALES};
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
