package main.java.es.uclm.sri.lastfm;

import de.umass.lastfm.Caller;

public class TestLastfm {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        PlaybackDUsuario analisisLastfm = new PlaybackDUsuario("djAguadilla");
        analisisLastfm.run();
        
        Caller caller = Caller.getInstance();
        caller.getUserAgent();
        
        
    }

}
