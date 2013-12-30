package es.uclm.sri.sis;

public class KSistema {
    
    public final class Cache {
        
        private Cache() {
        }
        
        public static final String cacheHistUser = "HISTUSER";
        public static final String cacheVPesosUser = "VPESOSUSER";
        public static final String cacheVPesosAlbum = "PESOSALBUM";
        public static final String cacheClusters = "CLUSTERS";
        
    }
    
    public final class Tablas {
        
        private Tablas() {
        }
        
        public static final String albums = "ALBUMSAPP";
        public static final String users = "USERSAPP";
        public static final String historico = "HISTUSER";
        public static final String pesosUser = "VPESOSUSER";
        public static final String pesosAlbum = "PESOSALBUM";
    }
    
    public final class Recursos {
        
        private Recursos() {
        }
        
        // Properties
        public static final String PATH_DATABASE_PROPERTIES = "src/es/uclm/sri/recursos/database.properties";
        public static final String PATH_GENEROS_ESTANDAR_PROPERTIES = "src/es/uclm/sri/recursos/generoEstandar.properties";
        public static final String PATH_LOG4J_PROPERTIES = "src/es/uclm/sri/recursos/log4j.properties";
        
        // XML
        public static final String PATH_MAPPING_POSTGRE = "src/es/uclm/sri/recursos/xml/configurationPostgre.xml";
        
    }

}
