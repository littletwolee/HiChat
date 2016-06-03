package com.client.models;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "TOKEN".
 */
public class Token {

    private Long id;
    /** Not-null value. */
    private String token;

    public Token() {
    }

    public Token(Long id) {
        this.id = id;
    }

    public Token(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getToken() {
        return token;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setToken(String token) {
        this.token = token;
    }

}
