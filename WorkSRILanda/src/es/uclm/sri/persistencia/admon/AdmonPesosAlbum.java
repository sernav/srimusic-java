package es.uclm.sri.persistencia.admon;

import es.uclm.sri.persistencia.postgre.dao.PesosalbumMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.sis.entidades.Album;

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
        return mapper.selectByAlbumYArtista(album, artista);
    }
    
}
