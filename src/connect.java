import java.sql.*;

public class connect {

    private String URL = "jdbc:mysql://localhost:3306/bld";
    private String USER = "root";
    private String PASSWORD = "root";
    private String query;

    public void add(String username, String password) {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            System.out.println("Insertion cannot contain null or empty values.");
            return;
        }
        query = "INSERT INTO bld.Users (username, password) VALUES (?, ?)";

        try (
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query);
        ) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("successful insertion");
            } else {
                System.out.println("unsuccessful insertion");
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR: " + e.getMessage());
        }
    }

    public ResultSet show() {
        query = "SELECT * FROM bld.users";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = con.prepareStatement(query);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return null;
        }
    }

    public void delete(int id) {
        query = "DELETE FROM bld.users WHERE id = ?";

        try (
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query);
        ) {
            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("successful deletion");
            } else {
                System.out.println("unsuccessful deletion");
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR: " + e.getMessage());
        }
    }

//    public void update(int id, String username, String phone, String city) {
//        if (username == null || name.trim().isEmpty() ||
//                phone == null || phone.trim().isEmpty() ||
//                city == null || city.trim().isEmpty()) {
//            System.out.println("Updating cannot contain null or empty values.");
//            return;
//        }
//        query = "UPDATE Phonebook.contacts SET name = ?, phone = ?, city = ? WHERE id = ?";
//
//        try (
//                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
//                PreparedStatement pstmt = con.prepareStatement(query);
//        ) {
//            pstmt.setInt(4, id);
//            pstmt.setString(1, name);
//            pstmt.setString(2, phone);
//            pstmt.setString(3, city);
//
//            int affectedRows = pstmt.executeUpdate();
//
//            if (affectedRows > 0) {
//                System.out.println("successful update");
//            } else {
//                System.out.println("unsuccessful update");
//            }
//
//        } catch (SQLException e) {
//            System.out.println("SQL ERROR: " + e.getMessage());
//        }
//    }
}