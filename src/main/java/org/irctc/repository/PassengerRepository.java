package org.irctc.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PassengerRepository {
    HashMap<Integer, ArrayList<Object>> trainInfo = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    public void displayRates(Connection connection){
        System.out.println("\nTrain details ");

        try{
            String sql = "SELECT * FROM `trainInfo`";
            Statement statement = connection.createStatement();
            ResultSet data = statement.executeQuery(sql);

            int code;
            String name;
            int fare;

            while (data.next()) {
                code = data.getInt("code");
                name = data.getString("name").trim();
                fare = data.getInt("fare");

                System.out.println("Train No.: " + code + " | Name: " + name + " | Fare : " + fare);

                ArrayList<Object> details = new ArrayList<Object>();
                details.add(name);
                details.add(fare);
                trainInfo.put(code, details);
            }
            data.close();
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void bookTicket(Connection connection){
        System.out.println("\nTicket Window\nPlease enter details: ");

        System.out.print("Enter userID: ");
        String userID = sc.next();
        System.out.print("Enter name: ");
        String name = sc.next();
        System.out.print("Enter train number: ");
        int trainNo = sc.nextInt();
        System.out.print("Enter count: ");
        int count = sc.nextInt();
        ArrayList<Object> price = trainInfo.get(trainNo);
        int sum = count * (int) price.get(1);

        try{
            String sql = "INSERT INTO `passenger` (userID, name, trainNo, count, sum) VALUES (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            statement.setString(2, name);
            statement.setInt(3, trainNo);
            statement.setInt(4, count);
            statement.setInt(5, sum);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ticket booked successfully!");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printTicket(Connection connection){
        System.out.print("\nPrint Ticket\nEnter userID: ");
        String userID = sc.next();
        System.out.print("Enter train number: ");
        int trainNo = sc.nextInt();
        try {
            String sql = "SELECT * FROM `passenger` WHERE `userID` = '" + userID + "' AND `trainNo` = '" + trainNo + "'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data = statement.executeQuery(sql);
            System.out.println("Your ticket details ");

            String name;
            int count;
            int sum;

            while (data.next()) {
                name = data.getString("name").trim();
                count = data.getInt("count");
                sum = data.getInt("sum");

                ArrayList<Object> trainDetails = trainInfo.get(trainNo);
                String trainName = (String) trainDetails.get(0);

                System.out.println("Name: " + name
                        + " | TrainNo: " + trainNo + " | Train Name: " + trainName
                        + " | No. of Passengers : " + count
                        + " | Total Fare: " + sum);
            }
            data.close();
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateTicket(Connection connection){
        System.out.println("\nUpdate ticket");

        System.out.print("Enter userID: ");
        String userID = sc.next();
        System.out.print("Enter train number: ");
        int trainNo = sc.nextInt();
        System.out.print("Enter count: ");
        int count = sc.nextInt();
        ArrayList<Object> price = trainInfo.get(trainNo);
        int sum = count * (int) price.get(1);

        try{
            String sql = "UPDATE `passenger` SET `count` = ?, `sum` = ? WHERE `userID` = ? AND `trainNo` = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, count);
            statement.setInt(2, sum);
            statement.setString(3, userID);
            statement.setInt(4, trainNo);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ticket updated successfully!");
            }
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public void cancelTicket(Connection connection){
        System.out.println("\nCancel ticket");

        System.out.print("Enter userID: ");
        String userID = sc.next();
        System.out.print("Enter train number: ");
        int trainNo = sc.nextInt();

        try{
            String sql = "DELETE FROM `passenger` WHERE userID = ? AND `trainNo` = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            statement.setInt(2, trainNo);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ticket cancelled successfully!");
            }
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
