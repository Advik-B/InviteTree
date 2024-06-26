package dev.advik.invitetree.tree;

import dev.advik.invitetree.database.DatabaseSchema;

public class PlayerDatabase implements DatabaseSchema {
    String connectionUrl;


    @Override
    public void setConnectionUrl(String url) {
        connectionUrl = url;
    }

    @Override
    public void createTables() {
        // Create the tables
    }

    @Override
    public void connect() {
        // Connect to the database
    }
}
