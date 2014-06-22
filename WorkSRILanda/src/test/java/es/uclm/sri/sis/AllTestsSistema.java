package es.uclm.sri.sis;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uclm.sri.clustering.weka.WekaClusteringLauncherTest;
import es.uclm.sri.lastfm.PlaybackDUsuarioTest;
import es.uclm.sri.logica.borrosa.MotorJFuzzyLogicTest;
import es.uclm.sri.sis.fabricas.FabricaDRecomendaciones;
import es.uclm.sri.sis.fabricas.FabricaDUsuariosTest;
import es.uclm.sri.sis.operaciones.PonderacionDAlbumTest;

@RunWith(Suite.class)
@SuiteClasses({WekaClusteringLauncherTest.class , PlaybackDUsuarioTest.class , 
			   MotorJFuzzyLogicTest.class , FabricaDUsuariosTest.class , 
			   FabricaDRecomendaciones.class , PonderacionDAlbumTest.class})
public class AllTestsSistema {

}
