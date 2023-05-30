package org.arzijeziberovska;

import org.arzijeziberovska.database.DatabaseHandler;
import org.arzijeziberovska.view.InitialView;

import java.sql.SQLException;

import static org.arzijeziberovska.database.DatabaseConnection.configureDataSource;

public class Main {
    public static void main(String[] args) throws SQLException {
        configureDataSource();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        InitialView initialView = new InitialView();

    }
}