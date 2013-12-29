package es.uclm.sri.persistencia.admon;

import es.uclm.sri.persistencia.postgre.dao.PesosalbumMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;

/**
 * 
 * @author Sergio Navarro
 * @date Dic, 2013
 * */
public class AdmonPesosAlbum extends AbstractAdmon {

    private PesosalbumMapper mapper = null;
    
    public AdmonPesosAlbum() {
        super();
        this.mapper = session.getMapper(PesosalbumMapper.class);
    }
    
    public Pesosalbum[] devolverPesosAlbumsDArtista(String artista) {
        return mapper.selectByArtista(artista.trim());
    }
    
    public Pesosalbum[] devolverPesosAlbumsDArtista(Album album) {
        return mapper.selectByArtista(album.getArtista().trim());
    }
    
    public Pesosalbum[] devolverPesosAlbumsDArtista(de.umass.lastfm.Album album) {
        return mapper.selectByArtista(album.getArtist().trim());
    }
    
    public Pesosalbum[] devolverPesosAlbum(String album) {
        return mapper.selectByAlbum(album.trim());
    }
    
    public Pesosalbum[] devolverPesosAlbum(Album album) {
        return mapper.selectByAlbum(album.getTitulo().trim());
    }
    
    public Pesosalbum[] devolverPesosAlbum(de.umass.lastfm.Album album) {
        return mapper.selectByAlbum(album.getName().trim());
    }
    
    public Pesosalbum[] devolverPesosAlbum(String album, String artista) {
        return mapper.selectByAlbumYArtista(album.trim(), artista.trim());
    }
    
    public void insertarPesosAlbum(AlbumPonderado albumPonderado) {
        Pesosalbum record = new Pesosalbum();
        record.setALBUM(albumPonderado.getTitulo());
        record.setARTISTA(albumPonderado.getArtista());
        
        if (!albumPonderado.getPesosGeneros()[0].isNaN() && 
                albumPonderado.getPesosGeneros()[0] > 0) {
            record.setSINGER(albumPonderado.getPesosGeneros()[0]);
        }
        
        if (!albumPonderado.getPesosGeneros()[1].isNaN() && 
                albumPonderado.getPesosGeneros()[1] > 0) {
            record.setRAP(albumPonderado.getPesosGeneros()[1]);
        }
        
        if (!albumPonderado.getPesosGeneros()[2].isNaN() && 
                albumPonderado.getPesosGeneros()[2] > 0) {
            record.setAMBIENT(albumPonderado.getPesosGeneros()[2]);
        }
        
        if (!albumPonderado.getPesosGeneros()[3].isNaN() && 
                albumPonderado.getPesosGeneros()[3] > 0) {
            record.setINDIE(albumPonderado.getPesosGeneros()[3]);
        }
        
        if (!albumPonderado.getPesosGeneros()[4].isNaN() && 
                albumPonderado.getPesosGeneros()[4] > 0) {
            record.setBLUES(albumPonderado.getPesosGeneros()[4]);
        }
        
        if (!albumPonderado.getPesosGeneros()[5].isNaN() && 
                albumPonderado.getPesosGeneros()[5] > 0) {
            record.setREGGAE(albumPonderado.getPesosGeneros()[5]);
        }
        
        if (!albumPonderado.getPesosGeneros()[6].isNaN() && 
                albumPonderado.getPesosGeneros()[6] > 0) {
            record.setPUNK(albumPonderado.getPesosGeneros()[6]);
        }
        
        if (!albumPonderado.getPesosGeneros()[7].isNaN() && 
                albumPonderado.getPesosGeneros()[7] > 0) {
            record.setHEAVY(albumPonderado.getPesosGeneros()[7]);
        }
        
        if (!albumPonderado.getPesosGeneros()[8].isNaN() && 
                albumPonderado.getPesosGeneros()[8] > 0) {
            record.setALTERNATIVE(albumPonderado.getPesosGeneros()[8]);
        }
        
        if (!albumPonderado.getPesosGeneros()[9].isNaN() && 
                albumPonderado.getPesosGeneros()[9] > 0) {
            record.setCLASSIC(albumPonderado.getPesosGeneros()[9]);
        }
        
        if (!albumPonderado.getPesosGeneros()[10].isNaN() && 
                albumPonderado.getPesosGeneros()[10] > 0) {
            record.setELECTRONIC(albumPonderado.getPesosGeneros()[10]);
        }
        
        if (!albumPonderado.getPesosGeneros()[11].isNaN() && 
                albumPonderado.getPesosGeneros()[11] > 0) {
            record.setROCK(albumPonderado.getPesosGeneros()[11]);
        }
        
        if (!albumPonderado.getPesosGeneros()[12].isNaN() && 
                albumPonderado.getPesosGeneros()[12] > 0) {
            record.setPOP(albumPonderado.getPesosGeneros()[12]);
        }
        
        if (!albumPonderado.getPesosGeneros()[13].isNaN() && 
                albumPonderado.getPesosGeneros()[13] > 0) {
            record.setBRIT(albumPonderado.getPesosGeneros()[13]);
        }
        
        if (!albumPonderado.getPesosGeneros()[14].isNaN() && 
                albumPonderado.getPesosGeneros()[14] > 0) {
            record.setFOLK(albumPonderado.getPesosGeneros()[14]);
        }
        
        if (!albumPonderado.getPesosGeneros()[15].isNaN() && 
                albumPonderado.getPesosGeneros()[15] > 0) {
            record.setFUNK(albumPonderado.getPesosGeneros()[15]);
        }
        
        if (!albumPonderado.getPesosGeneros()[16].isNaN() && 
                albumPonderado.getPesosGeneros()[16] > 0) {
            record.setINSTRUMENTAL(albumPonderado.getPesosGeneros()[16]);
        }
        
        if (!albumPonderado.getPesosGeneros()[17].isNaN() && 
                albumPonderado.getPesosGeneros()[17] > 0) {
            record.setGRUNGE(albumPonderado.getPesosGeneros()[17]);
        }
        
        mapper.insertSelective(record);
        
    }
    
    public void insertarPesosAlbum(Pesosalbum record) {
        mapper.insert(record);
    }
    
}
