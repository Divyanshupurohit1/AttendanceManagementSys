import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AttendanceManager {
    private Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance_db", "root", "Pass@7037!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    public void addStudentToDB(String id, String name) {
        String query = "INSERT INTO students (id, name) VALUES (?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.executeUpdate();
            System.out.println("Student added to database.");
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    public void addSubjectToDB(String code, String name) {
        String query = "INSERT INTO subjects (code, name) VALUES (?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code);
            stmt.setString(2, name);
            stmt.executeUpdate();
            System.out.println("Subject added to database.");
        } catch (SQLException e) {
            System.out.println("Error adding subject: " + e.getMessage());
        }
    }

    public void markAttendanceInDB(String studentId, String subjectCode, boolean isPresent) {
        String query = "INSERT INTO attendance (student_id, subject_code, is_present) VALUES (?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE is_present = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, subjectCode);
            stmt.setBoolean(3, isPresent);
            stmt.setBoolean(4, isPresent);
            stmt.executeUpdate();
            System.out.println("Attendance marked in database.");
        } catch (SQLException e) {
            System.out.println("Error marking attendance: " + e.getMessage());
        }
    }

    public void displayAttendanceFromDB() {
        String query = "SELECT s.id, s.name, subj.name AS subject_name, a.is_present " +
                       "FROM students s " +
                       "JOIN attendance a ON s.id = a.student_id " +
                       "JOIN subjects subj ON a.subject_code = subj.code " +
                       "ORDER BY s.id";
        try (Connection conn = connect(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Attendance Report:");
            String currentStudent = "";
            while (rs.next()) {
                String studentId = rs.getString("id");
                String studentName = rs.getString("name");
                String subjectName = rs.getString("subject_name");
                boolean isPresent = rs.getBoolean("is_present");
                String status = isPresent ? "Present" : "Absent";

                if (!studentId.equals(currentStudent)) {
                    System.out.println("Student: " + studentName + " (ID: " + studentId + ")");
                    currentStudent = studentId;
                }
                System.out.println("  - " + subjectName + ": " + status);
            }
        } catch (SQLException e) {
            System.out.println("Error displaying attendance: " + e.getMessage());
        }
    }
}