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
    
    public final class Excepciones {
        
        private Excepciones() {
        }
        
        public static final String EXCEPCION_GENERAL = "EXCEGNRL";
        public static final String EXCEPCION_SQL = "EXCEPSQL";
        public static final String EXCEPCION_IBATIS = "EXCEBATI";
        public static final String EXCEPCION_IO = "EXCEINOU";
        public static final String EXCEPCION_CONEXION = "EXCECONN";
    }
    
    public final class Recursos {
        
        private Recursos() {
        }
        
        /*
         * Properties
         * */
        public static final String PATH_DATABASE_PROPERTIES = "./src/main/resources/database.properties";
        public static final String PATH_GENEROS_ESTANDAR_PROPERTIES = "./src/main/resources/generoEstandar.properties";
        public static final String PATH_LOG4J_PROPERTIES = "./src/main/resources/log4j.properties";
        
        /*
         * XML
         * */
        public static final String PATH_MAPPING_POSTGRE = "../src/main/resources/xml/sqlMapConfig.xml";
        
    }
    
    public final class Generos {
        
        private Generos() {
        }
        
        /*
         * Géneros básicos de aplicación
         * */
        public static final String ALTERNATIVE = "alternative";
        public static final String AMBIENT = "ambient";
        public static final String BLUES = "blues";
        public static final String BRIT = "brit";
        public static final String CLASSIC = "classic";
        public static final String ELECTRONIC = "electronic";
        public static final String FOLK = "folk";
        public static final String FUNK = "funk";
        public static final String GRUNGE = "grunge";
        public static final String HEAVY = "heavy";
        public static final String INDIE = "indie";
        public static final String INSTRUMENTAL = "instrumental";
        public static final String POP = "pop";
        public static final String PUNK = "punk";
        public static final String RAP = "rap";
        public static final String REGGAE = "reggae";
        public static final String ROCK = "rock";
        public static final String SINGER = "singer";
    }

}
