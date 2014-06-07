package es.uclm.sri.persistencia.admon;

import java.sql.SQLException;

import org.apache.ibatis.exceptions.IbatisException;

import es.uclm.sri.persistencia.postgre.dao.PesosalbumMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;
import es.uclm.sri.sis.log.Log;

/**
 * Admon para atacar las operaciones de la tabla de base de datos PESOSALBUM.
 * Utiliza XML de mapeo postgre.dao.sqlmap.PesosalbumMapper.xml
 * 
 * @author Sergio Navarro
 * */
public class AdmonPesosAlbum extends AbstractAdmon {

    private PesosalbumMapper mapper = null;

    public AdmonPesosAlbum() {
        super();
        this.mapper = session.getMapper(PesosalbumMapper.class);
    }
    
    /**
     * Devuelve entidad Pesosalbum por Id.
     * 
     * @param Id: Integer
     * @return Pesosalbum
     * */
    public Pesosalbum devolverPesosAlbums(Integer idPesosAlbum) {
        return mapper.selectByPrimaryKey(idPesosAlbum);
    }

    /**
     * Devuelve array de entidades Pesosalbum por nombre de artista.
     * 
     * @param artista: String
     * @return Pesosalbum[]
     * */
    public Pesosalbum[] devolverPesosAlbumsDArtista(String artista) {
        return mapper.selectByArtista(artista.trim());
    }

    /**
     * Devuelve array de entidades Pesosalbum por artista de Album
     * 
     * @param album: Album
     * @return Pesosalbum[]
     * */
    public Pesosalbum[] devolverPesosAlbumsDArtista(Album album) {
        return mapper.selectByArtista(album.getArtista().trim());
    }

    /**
     * Devuelve array de entidades Pesosalbum por artista de Album de lastfm
     * 
     * @param album: de.umass.lastfm.Album
     * @return Pesosalbum[]
     * */
    public Pesosalbum[] devolverPesosAlbumsDArtista(de.umass.lastfm.Album album) {
        return mapper.selectByArtista(album.getArtist().trim());
    }

    /**
     * Devuelve array de entidades Pesosalbum por título de album.
     * 
     * @param album: String
     * @return Pesosalbum[]
     * */
    public Pesosalbum[] devolverPesosAlbum(String album) {
        return mapper.selectByAlbum(album.trim());
    }
    
    /**
     * Devuelve array de entidades Pesosalbum por Album
     * 
     * @param album: Album
     * @return Pesosalbum[]
     * */
    public Pesosalbum[] devolverPesosAlbum(Album album) {
        return mapper.selectByAlbum(album.getTitulo().trim());
    }
    
    /**
     * Devuelve array de entidades Pesosalbum por Album de lastfm
     * 
     * @param album: de.umass.lastfm.Album
     * @return Pesosalbum[]
     * */
    public Pesosalbum[] devolverPesosAlbum(de.umass.lastfm.Album album) {
        return mapper.selectByAlbum(album.getName().trim());
    }
    
    /**
     * Devuelve array de entidades Pesosalbum por título de album y artista
     * 
     * @param album: String
     * @param artista: String
     * @return Pesosalbum[]
     * */
    public Pesosalbum[] devolverPesosAlbum(String album, String artista) {
        return mapper.selectByAlbumYArtista(album.trim(), artista.trim());
    }
    
    /**
     * Devuelve un album ponderado (con sus pesos equilibrados) por album y artista
     * 
     * @param album: String
     * @param artista: String
     * @return AlbumPonderado
     * */
    public AlbumPonderado devolverAlbumPonderado(String album, String artista) {
        Pesosalbum[] pesosAlbum = mapper.selectByAlbumYArtista(album.trim(), artista.trim());
        AlbumPonderado albumPonderado = new AlbumPonderado();
        if (pesosAlbum != null && pesosAlbum.length > 0) {
            albumPonderado.setTitulo(pesosAlbum[0].getALBUM());
            albumPonderado.setArtista(pesosAlbum[0].getARTISTA());
            /*
             * Falta generar vector de pesos
             */
            Double[] generos = new Double[18];
            iniciarVectorGeneros(generos);
            if (!pesosAlbum[0].getSINGER().isNaN()) {
                generos[0] = pesosAlbum[0].getSINGER();
            }
            if (!pesosAlbum[1].getSINGER().isNaN()) {
                generos[1] = pesosAlbum[0].getRAP();
            }
            if (!pesosAlbum[2].getRAP().isNaN()) {
                generos[2] = pesosAlbum[0].getAMBIENT();
            }
            if (!pesosAlbum[3].getINDIE().isNaN()) {
                generos[3] = pesosAlbum[0].getINDIE();
            }
            if (!pesosAlbum[4].getBLUES().isNaN()) {
                generos[4] = pesosAlbum[0].getBLUES();
            }
            if (!pesosAlbum[5].getREGGAE().isNaN()) {
                generos[5] = pesosAlbum[0].getREGGAE();
            }
            if (!pesosAlbum[6].getPUNK().isNaN()) {
                generos[6] = pesosAlbum[0].getPUNK();
            }
            if (!pesosAlbum[7].getHEAVY().isNaN()) {
                generos[7] = pesosAlbum[0].getHEAVY();
            }
            if (!pesosAlbum[8].getALTERNATIVE().isNaN()) {
                generos[8] = pesosAlbum[0].getALTERNATIVE();
            }
            if (!pesosAlbum[9].getCLASSIC().isNaN()) {
                generos[9] = pesosAlbum[0].getCLASSIC();
            }
            if (!pesosAlbum[10].getELECTRONIC().isNaN()) {
                generos[10] = pesosAlbum[0].getELECTRONIC();
            }
            if (!pesosAlbum[11].getROCK().isNaN()) {
                generos[11] = pesosAlbum[0].getROCK();
            }
            if (!pesosAlbum[12].getPOP().isNaN()) {
                generos[12] = pesosAlbum[0].getPOP();
            }
            if (!pesosAlbum[13].getBRIT().isNaN()) {
                generos[13] = pesosAlbum[0].getBRIT();
            }
            if (!pesosAlbum[14].getFOLK().isNaN()) {
                generos[14] = pesosAlbum[0].getFOLK();
            }
            if (!pesosAlbum[15].getFUNK().isNaN()) {
                generos[15] = pesosAlbum[0].getFUNK();
            }
            if (!pesosAlbum[16].getINSTRUMENTAL().isNaN()) {
                generos[16] = pesosAlbum[0].getINSTRUMENTAL();
            }
            if (!pesosAlbum[17].getGRUNGE().isNaN()) {
                generos[17] = pesosAlbum[0].getGRUNGE();
            }

            albumPonderado.setGeneros(generos);
        }
        return albumPonderado;
    }

    private Double[] iniciarVectorGeneros(Double[] generos) {
        for (int i = 0; i < generos.length; i++) {
            Double cero = new Double(0);
            generos[i] = cero;
        }
        return generos;
    }
    
    /**
     * Inserta album ponderado
     * 
     * @param AlbumPonderado
     * */
    public Pesosalbum insertarPesosAlbum(AlbumPonderado albumPonderado) throws SQLException, Exception {
        Pesosalbum record = convertirAlbumPonderado(albumPonderado);
        try {
            mapper.insertSelective(record);
        } catch (IbatisException e) {
            e.printStackTrace();
        }
        return record;

    }
    
    /**
     * Inserta entidad Pesosalbum
     * 
     * @param Pesosalbum
     * */
    public void insertarPesosAlbum(Pesosalbum record) throws SQLException {
        try {
            mapper.insert(record);
        } catch (IbatisException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza registo de PESOSALBUM para un album ponderado
     * 
     * @param AlbumPonderado
     * */
    public Pesosalbum actualizarPesosAlbum(AlbumPonderado albumPonderado) throws SQLException, Exception {
        Pesosalbum record = convertirAlbumPonderado(albumPonderado);
        try {
            mapper.updateByPrimaryKey(record);
        } catch (IbatisException e) {
            e.printStackTrace();
        }
        return record;
    }
    
    /**
     * Actualiza registo de PESOSALBUM
     * 
     * @param Pesosalbum
     * */
    public int actualizarPesosAlbum(Pesosalbum record) {
        return mapper.updateByPrimaryKey(record);
    }

    private Pesosalbum convertirAlbumPonderado(AlbumPonderado albumPonderado) throws Exception {
        Pesosalbum record = new Pesosalbum();
        record.setALBUM(albumPonderado.getTitulo());
        record.setARTISTA(albumPonderado.getArtista());
        try {
            if (!albumPonderado.getPesosGeneros()[0].isNaN() && albumPonderado.getPesosGeneros()[0] > 0) {
                record.setSINGER(albumPonderado.getPesosGeneros()[0]);
            }

            if (!albumPonderado.getPesosGeneros()[1].isNaN() && albumPonderado.getPesosGeneros()[1] > 0) {
                record.setRAP(albumPonderado.getPesosGeneros()[1]);
            }

            if (!albumPonderado.getPesosGeneros()[2].isNaN() && albumPonderado.getPesosGeneros()[2] > 0) {
                record.setAMBIENT(albumPonderado.getPesosGeneros()[2]);
            }

            if (!albumPonderado.getPesosGeneros()[3].isNaN() && albumPonderado.getPesosGeneros()[3] > 0) {
                record.setINDIE(albumPonderado.getPesosGeneros()[3]);
            }

            if (!albumPonderado.getPesosGeneros()[4].isNaN() && albumPonderado.getPesosGeneros()[4] > 0) {
                record.setBLUES(albumPonderado.getPesosGeneros()[4]);
            }

            if (!albumPonderado.getPesosGeneros()[5].isNaN() && albumPonderado.getPesosGeneros()[5] > 0) {
                record.setREGGAE(albumPonderado.getPesosGeneros()[5]);
            }

            if (!albumPonderado.getPesosGeneros()[6].isNaN() && albumPonderado.getPesosGeneros()[6] > 0) {
                record.setPUNK(albumPonderado.getPesosGeneros()[6]);
            }

            if (!albumPonderado.getPesosGeneros()[7].isNaN() && albumPonderado.getPesosGeneros()[7] > 0) {
                record.setHEAVY(albumPonderado.getPesosGeneros()[7]);
            }

            if (!albumPonderado.getPesosGeneros()[8].isNaN() && albumPonderado.getPesosGeneros()[8] > 0) {
                record.setALTERNATIVE(albumPonderado.getPesosGeneros()[8]);
            }

            if (!albumPonderado.getPesosGeneros()[9].isNaN() && albumPonderado.getPesosGeneros()[9] > 0) {
                record.setCLASSIC(albumPonderado.getPesosGeneros()[9]);
            }

            if (!albumPonderado.getPesosGeneros()[10].isNaN() && albumPonderado.getPesosGeneros()[10] > 0) {
                record.setELECTRONIC(albumPonderado.getPesosGeneros()[10]);
            }

            if (!albumPonderado.getPesosGeneros()[11].isNaN() && albumPonderado.getPesosGeneros()[11] > 0) {
                record.setROCK(albumPonderado.getPesosGeneros()[11]);
            }

            if (!albumPonderado.getPesosGeneros()[12].isNaN() && albumPonderado.getPesosGeneros()[12] > 0) {
                record.setPOP(albumPonderado.getPesosGeneros()[12]);
            }

            if (!albumPonderado.getPesosGeneros()[13].isNaN() && albumPonderado.getPesosGeneros()[13] > 0) {
                record.setBRIT(albumPonderado.getPesosGeneros()[13]);
            }

            if (!albumPonderado.getPesosGeneros()[14].isNaN() && albumPonderado.getPesosGeneros()[14] > 0) {
                record.setFOLK(albumPonderado.getPesosGeneros()[14]);
            }

            if (!albumPonderado.getPesosGeneros()[15].isNaN() && albumPonderado.getPesosGeneros()[15] > 0) {
                record.setFUNK(albumPonderado.getPesosGeneros()[15]);
            }

            if (!albumPonderado.getPesosGeneros()[16].isNaN() && albumPonderado.getPesosGeneros()[16] > 0) {
                record.setINSTRUMENTAL(albumPonderado.getPesosGeneros()[16]);
            }

            if (!albumPonderado.getPesosGeneros()[17].isNaN() && albumPonderado.getPesosGeneros()[17] > 0) {
                record.setGRUNGE(albumPonderado.getPesosGeneros()[17]);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            Log.log(e, "(" + AdmonPesosAlbum.class.getSimpleName() + ") Index Out Of Bounds Expcetion! Convertir Album Ponderado " + e.getMessage());
        }

        return record;
    }

}
