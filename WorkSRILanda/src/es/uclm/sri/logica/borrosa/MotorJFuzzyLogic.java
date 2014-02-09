package es.uclm.sri.logica.borrosa;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class MotorJFuzzyLogic {
    
    private FIS fis;
    
    private String[] variablesIn;
    
    private double[] valoresIn;
    
    private String variableOut;
    
    private double valorOut;
    
    public MotorJFuzzyLogic(String filename) {
        fis = FIS.load(filename, true);
        
        if (fis == null) {
            System.err.println("No se ha podido cargar el archivo: '" + filename + "'");
            System.exit(1);
        }
    }
    
    public Object run(String[] variablesIn, double[] valoresIn, String variableOut) {
        if (variablesIn.length != valoresIn.length) {
            System.err.println("El nœmero de variales y de valores no se corresponden");
            return null;
        }
        this.variablesIn = variablesIn;
        this.valoresIn = valoresIn;
        
        // Get default function block
        FunctionBlock fb = fis.getFunctionBlock(null);
        
        // Set inputs
        for (int i = 0; i < variablesIn.length; i++) {
            fb.setVariable(variablesIn[i], valoresIn[i]);
            System.out.println(variablesIn[i] + " = " + valoresIn[i]);
        }
        
        // Evaluate
        fb.evaluate();
        
        // Show output variable's chart
        fb.getVariable(variableOut).defuzzify();
        
        // Print ruleSet
        System.out.println(fb);
        System.out.println("Tip: " + fb.getVariable(variableOut).getValue());
        
        return null;
    }
    
}
