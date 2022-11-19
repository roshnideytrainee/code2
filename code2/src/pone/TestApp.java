package pone;

import java.sql.*;
import java.util.Scanner;

import static java.lang.System.exit;

class Employee {
    Connection con;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rs;
    Scanner sc = new Scanner(System.in);
    void CrudOperations(int opt) throws SQLException {
        // Step 1: Loading driver
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Driver Loaded");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //Step 2: Establishing the Connectivity
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@LAPTOP-FVKFC8JT:1521:xe", "nri_test2", "pinakib");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (opt == 1) {
            System.out.println("Enter Employee ID: ");
            int empid = sc.nextInt();

            System.out.println("Enter Employee name: ");
            String empname = sc.next();

            System.out.println("Enter Employee Salary: ");
            double empsal = sc.nextDouble();

            String sql = "INSERT INTO employee (employeeid, employeename, employeesalary) VALUES (?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, empid);
            pstmt.setString(2, empname);
            pstmt.setDouble(3, empsal);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new row was inserted successfully!");
            } else {
                System.out.println("There was some error in insertion");
            }
        }
        else if (opt == 2) {
            System.out.println("Enter Employee ID to search");
            int empid = sc.nextInt();
            String sql1 = "SELECT employeeid, employeename, employeesalary from employee where employeeid = ?";
            pstmt = con.prepareStatement(sql1);
            pstmt.setInt(1, empid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                empid = rs.getInt("employeeid");
                String empname = rs.getString("employeename");
                double empsalary = rs.getDouble("employeesalary");

                System.out.printf("The required Employee details are: %d, %s and %f  \n", empid, empname, empsalary);
            } else {
                System.out.printf("The %d Employee ID doesnt exist  \n", empid);
            }
        }
        else if (opt == 3) {
            System.out.println("Enter Employee ID: ");
            int empid = sc.nextInt();

            System.out.println("Enter Employee name: ");
            String empname = sc.next();

            System.out.println("Enter Employee Salary: ");
            double empsalary = sc.nextDouble();

            String sql2 = "UPDATE employee SET employeeid =?,employeename=?, employeesalary=? WHERE employeeid=?";
            pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, empid);
            pstmt.setString(2, empname);
            pstmt.setDouble(3, empsalary);

            int rowsUpdated = pstmt.executeUpdate();

            rs.updateRow();
            if (rowsUpdated>0) {
                System.out.println("An existing user was updated successfully!");
            }
        }
        else if(opt == 4){
            System.out.println("Enter Employee ID: ");
            int empid = sc.nextInt();
            String sql3 = "DELETE FROM employee WHERE employeeid = ?";
            pstmt = con.prepareStatement(sql3);
            pstmt.setInt(1,empid);
            int deletedRow = pstmt.executeUpdate();
            if (deletedRow>0){
                System.out.println("A row was deleted successfully");
            }
        }
        else if(opt == 5){
            System.out.println("Successfully Exit ");
            exit(0);
        }
    }
}
public class TestApp {
    public static void main(String[] args) {
        boolean go = true;
        while(go) {
            System.out.println("** MENU ***");
            System.out.println("1- INSERT");
            System.out.println("2- SEARCH");
            System.out.println("3- UPDATE");
            System.out.println("4- DELETE");
            System.out.println("5-EXIT");
            System.out.println("What do you want to perform?");
            Scanner sc = new Scanner(System.in);
            int opt = sc.nextInt();
            Employee sampobj = new Employee();
            try {
                sampobj.CrudOperations(opt);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}