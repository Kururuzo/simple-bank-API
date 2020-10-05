package com.bank.repository;

import java.sql.Connection;
import java.sql.SQLException;

public class Utils {
    private Utils() {
    }

    public static void closeQuietly(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ignore) {
            //NOP
        }
    }
}
