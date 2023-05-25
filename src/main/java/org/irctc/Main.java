package org.irctc;

import org.irctc.config.DBConnection;
import org.irctc.repository.PassengerRepository;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnection dbConnect = new DBConnection();
        Connection connection = dbConnect.connect();
        DBConnection.createPassengerTable(connection);
        PassengerRepository passengerRepository = new PassengerRepository();
        passengerRepository.displayRates(connection);
        passengerRepository.bookTicket(connection);
        passengerRepository.printTicket(connection);
        passengerRepository.updateTicket(connection);
        passengerRepository.cancelTicket(connection);
        connection.close();
    }
}