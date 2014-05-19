package es.uclm.sri.parches;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.uclm.sri.persistencia.ConnectionFactory;
import es.uclm.sri.persistencia.postgre.dao.PesosalbumMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.sis.entidades.AlbumPonderado;
import es.uclm.sri.sis.operaciones.csv.TratarCSVAlbum;

public class CargarAlbumsPondsToDb {
	
	private static SqlSessionFactory sqlMapper;
	private static SqlSession session = null;
	
	private static String path = "/Users/sergionavarro/PFC/CSV_Albums/CSV_Albums_Ponderados.csv";

	public static void main(String[] args) {
		establecerConexion();
		sqlMapper.openSession();
		PesosalbumMapper mapper = session.getMapper(PesosalbumMapper.class);
		
		ArrayList<Pesosalbum> lPesos = getListaPesosDAlbum(path);
		for (Pesosalbum pesos : lPesos) {
			try {
				mapper.insert(pesos);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		session.commit();
		session.close();
	}
	
	protected static ArrayList<Pesosalbum> getListaPesosDAlbum (String path) {
		ArrayList<AlbumPonderado> lAlbums = TratarCSVAlbum.leerCVSAlbumsPonderado(path);
		ArrayList<Pesosalbum> lPesos = new ArrayList<Pesosalbum>();
		for (AlbumPonderado album : lAlbums) {
			Pesosalbum pesos = new Pesosalbum();
			pesos.setALBUM(album.getTitulo());
			pesos.setARTISTA(album.getArtista());
			Double[] pesosAlbum = album.getPesosGeneros();
			anyadirValorPesos(pesos, pesosAlbum);
			
			lPesos.add(pesos);
		}
		return lPesos;
	}
	
	private static void anyadirValorPesos(Pesosalbum pesos, Double[] pesosAlbum) {
		pesos.setROCK(pesosAlbum[0]);
		pesos.setINDIE(pesosAlbum[1]);
		pesos.setALTERNATIVE(pesosAlbum[2]);
		pesos.setPOP(pesosAlbum[3]);
		pesos.setELECTRONIC(pesosAlbum[4]);
		pesos.setBRIT(pesosAlbum[5]);
		pesos.setFUNK(pesosAlbum[6]);
		pesos.setRAP(pesosAlbum[7]);
		pesos.setFOLK(pesosAlbum[8]);
		pesos.setSINGER(pesosAlbum[9]);
		pesos.setHEAVY(pesosAlbum[10]);
		pesos.setBLUES(pesosAlbum[11]);
		pesos.setGRUNGE(pesosAlbum[12]);
		pesos.setCLASSIC(pesosAlbum[13]);
		pesos.setPUNK(pesosAlbum[14]);
		pesos.setINSTRUMENTAL(pesosAlbum[15]);
		pesos.setAMBIENT(pesosAlbum[16]);
		pesos.setREGGAE(pesosAlbum[17]);
	}
	
	private static void establecerConexion() {
		sqlMapper = ConnectionFactory.getSession();
	}

}
