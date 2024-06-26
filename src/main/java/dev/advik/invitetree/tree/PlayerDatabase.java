package dev.advik.invitetree.tree;

import dev.advik.invitetree.database.DatabaseSchema;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerDatabase implements DatabaseSchema {
    String connectionUrl;
    Logger logger;
    Connection dbConn;

    public PlayerDatabase(String dbURL, Logger logger) {
        connectionUrl = dbURL;
        this.logger = logger;
    }

    @Override
    public void connect() {
        // Connect to the database

    }
}
