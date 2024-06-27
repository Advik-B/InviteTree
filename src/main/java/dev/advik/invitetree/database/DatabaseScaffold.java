package dev.advik.invitetree.database;

import java.util.Map;

public interface DatabaseScaffold {

    public void createTables();

    public void connect();

    public String executeQuery(String query, Map<Integer, Object> params);

    public void executeUpdate(String query, Map<Integer, Object> params);

    public void closeConnection();
}