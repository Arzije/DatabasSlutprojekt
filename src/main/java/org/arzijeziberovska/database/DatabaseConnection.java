package org.arzijeziberovska.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.codehaus.jackson.map.ObjectMapper;
import org.arzijeziberovska.model.DatabaseConfig;

public abstract class DatabaseConnection {

    private static final String JSON_FILE_PATH = "src/main/resources/database-config.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static MysqlDataSource dataSource;

    protected DatabaseConnection(){

    }
    public static void configureDataSource() {
        try {
            System.out.printf("Configuring data source...");

            DatabaseConfig config = readConfigFromJson();

            dataSource = new MysqlDataSource();
            dataSource.setUser(config.getUsername());
            dataSource.setPassword(config.getPassword());
            dataSource.setUrl("jdbc:mysql://" + config.getUrl() + ":" + config.getPort() + "/" + config.getDatabase() +
                    "?serverTimezone=UTC");
            dataSource.setUseSSL(false);

            System.out.printf(" InitDb done!\n");
        } catch (SQLException | IOException e) {
            System.out.printf("InitDb failed!\n");
            System.exit(0);
        }
    }

    protected Connection getConnection() {
        if(dataSource == null){
            configureDataSource();
        }
        try {
            System.out.printf("Fetching connection to database...");
            Connection connection = dataSource.getConnection();
            System.out.printf("Conn done!\n");
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.printf("Conn failed!\n");
            System.exit(0);
            return null;
        }
    }
    public static DatabaseConfig readConfigFromJson() throws IOException {
        File jsonFile = new File(JSON_FILE_PATH);
        DatabaseConfig[] configs = objectMapper.readValue(jsonFile, DatabaseConfig[].class);
        return configs[0];
    }


}


