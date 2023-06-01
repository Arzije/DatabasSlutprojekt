package org.arzijeziberovska;

import org.arzijeziberovska.database.DatabaseHandler;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.UserRepository;
import org.arzijeziberovska.service.UserService;
import org.arzijeziberovska.view.InitialView;

import java.io.IOException;
import java.sql.SQLException;

import static org.arzijeziberovska.database.DatabaseConnection.configureDataSource;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        configureDataSource();

        DatabaseHandler databaseHandler = new DatabaseHandler();

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);


        InitialView initialView = new InitialView(userService, userRepository );
        initialView.firstView();


    }
}