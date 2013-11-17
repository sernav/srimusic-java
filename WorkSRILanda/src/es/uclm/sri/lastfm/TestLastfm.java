package es.uclm.sri.lastfm;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Session;

public class TestLastfm {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String apiKey = "58169c7f645f6ad54529f3012548fc3a";
        String secret = "d99785f1e3ee9305b7c67b6e81bce5b1";
        
        Caller caller = Caller.getInstance();
        caller.getUserAgent();
        
        String token = Authenticator.getToken(apiKey);
        
        Session  session = Authenticator.getSession(token, apiKey, secret); //Session.createSession(apiKey, secret, token);
        String userName = session.getUsername();
        System.out.println(userName);
        
//        Session.createSession(apiKey, secret, sessionKey, username)
//        Authenticator.getToken(apiKey);

    }

}
