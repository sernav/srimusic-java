package es.uclm.sri.sis.fabricas;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.uclm.sri.clustering.weka.WekaClusteringFacade;
import es.uclm.sri.clustering.weka.WekaSRIInstance;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosusuario;

public class FabricaDRecomendacionesTest {

	private double[] attValues;

	@Before
	public void setUp() throws Exception {
		attValues = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
					0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	}

	@After
	public void tearDown() throws Exception {
		attValues = null;
	}

	@Test
	public void testRecomendacionesAll() throws Exception {
		WekaClusteringFacade clusterer = WekaClusteringFacade.getInstance();
		WekaSRIInstance inst = new WekaSRIInstance(1.0, attValues);
		WekaSRIInstance[] wekaInst = clusterer.generarRecomendacionesWekaAll(inst);
		
		assertNotNull("Total de recomendaciones generadas", wekaInst);
	}
	
	@Test
	public void testRecomendaciones() throws Exception {
		WekaClusteringFacade clusterer = WekaClusteringFacade.getInstance();
		WekaSRIInstance inst = new WekaSRIInstance(1.0, attValues);
		WekaSRIInstance[] wekaInst = clusterer.generarRecomendacionesWeka(inst);
		
		assertNotNull("Recomendaciones generadas", wekaInst);
	}
	
	@Test
	public void testRecomendacionesUsuario() throws Exception {
		HashMap<String, Pesosalbum> hashAlbums = new HashMap<String, Pesosalbum>();
		
		FabricaDUsuarios makeupUser = new FabricaDUsuarios("djAguadilla", hashAlbums, true);
		assertNotNull("Usuario cargado", makeupUser);
		
        makeupUser.run();
        Pesosusuario pesosUser = makeupUser.getPesosManufactura();
        assertNotNull("Hay pesos de usuario", pesosUser);
        
        WekaClusteringFacade clusterer = WekaClusteringFacade.getInstance();

        WekaSRIInstance inst = new WekaSRIInstance(pesosUser.getSINGER(), pesosUser.getRAP(), pesosUser.getAMBIENT(), pesosUser.getINDIE(),
                pesosUser.getBLUES(), pesosUser.getREGGAE(), pesosUser.getPUNK(), pesosUser.getHEAVY(), pesosUser.getALTERNATIVE(), pesosUser.getCLASSIC(),
                pesosUser.getELECTRONIC(), pesosUser.getROCK(), pesosUser.getPOP(), pesosUser.getBRIT(), pesosUser.getFOLK(), pesosUser.getFUNK(),
                pesosUser.getINSTRUMENTAL(), pesosUser.getGRUNGE());
        
        WekaSRIInstance[] wekaInst = clusterer.generarRecomendacionesWekaAll(inst);
        
        assertNotNull("Total de recomendaciones generadas para el usuario", wekaInst);
	}

}
