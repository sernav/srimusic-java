package es.uclm.sri.logica.borrosa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MotorJFuzzyLogicTest {
	
	private double escuchas_historico;
	private double escuchas_actuales;
	private double salida_esperada;
	
	@Before
	public void setUp() throws Exception {
		escuchas_historico = 49.600;
		escuchas_actuales = 20.200;
		salida_esperada = -20.8;
	}
	
	@After
	public void tearDown() throws Exception {
		escuchas_historico = 0;
		escuchas_actuales = 0;
		salida_esperada = 0;
	}

	@Test
	public void test() {
		String filename = "src/test/java/es/uclm/sri/logica/borrosa/fcl/definiciones.fcl";
        FIS fis = FIS.load(filename, true);
        
        assertNotNull("Fichero de reglas cargado", fis);

        FunctionBlock fb = fis.getFunctionBlock(null);

        // Variables de entrada
        fb.setVariable("escuchas_historico", escuchas_historico);
        fb.setVariable("escuchas_actuales", escuchas_actuales);

        // Evaluar
        fb.evaluate();

        fb.getVariable("salida").defuzzify();
        
        double resultado = fb.getVariable("salida").getValue();
        
        //Redondeo a un único decimal
        double resultadoDecimales = Math.round(resultado*Math.pow(10,1))/Math.pow(10,1);
        
        assertEquals(salida_esperada, resultadoDecimales, 0);

	}

}
