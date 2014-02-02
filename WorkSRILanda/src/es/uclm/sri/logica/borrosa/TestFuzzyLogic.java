package es.uclm.sri.logica.borrosa;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class TestFuzzyLogic {
    
    public TestFuzzyLogic() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // Load from 'FCL' file
        String filename = "src/es/uclm/sri/logica/borrosa/fcl/definiciones.fcl";
        FIS fis = FIS.load(filename, true);

        if (fis == null) {
            System.err.println("Can't load file: '" + filename + "'");
            System.exit(1);
        }

        // Get default function block
        FunctionBlock fb = fis.getFunctionBlock(null);

        // Set inputs
        fb.setVariable("escuchas_historico", 33.3333);
        fb.setVariable("escuchas_actuales", 25);

        // Evaluate
        fb.evaluate();

        // Show output variable's chart
        fb.getVariable("salida").defuzzify();

        // Print ruleSet
        System.out.println(fb);
        System.out.println("Tip: " + fb.getVariable("salida").getValue());
        
    }
    
}
