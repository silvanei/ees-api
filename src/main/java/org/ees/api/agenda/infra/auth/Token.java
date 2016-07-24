package org.ees.api.agenda.infra.auth;

/**
 * Created by silvanei on 24/07/16.
 */
public class Token {

    private String token;

    public Token() {
        super();
    }

    public Token(String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
