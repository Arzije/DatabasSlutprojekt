package org.arzijeziberovska.model;


public class DatabaseConfig {

    private String url;
    private int port;
    private String database;
    private String username;
    private String password;

    public DatabaseConfig() {
    }

    public DatabaseConfig(String url, int port, String database, String username, String password) {
        this.url = url;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


