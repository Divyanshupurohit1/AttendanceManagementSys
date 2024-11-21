import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AttendanceGUI extends JFrame {
    private AttendanceManager manager;

    public AttendanceGUI() {
        manager = new AttendanceManager();
        
        
        setTitle("Attendance Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create buttons
        JButton addStudentButton = new JButton("Add Student");
        JButton addSubjectButton = new JButton("Add Subject");
        JButton markAttendanceButton = new JButton("Mark Attendance");
        JButton displayAttendanceButton = new JButton("Display Attendance");

        // Set button actions
        addStudentButton.addActionListener(e -> addStudent());
        addSubjectButton.addActionListener(e -> addSubject());
        markAttendanceButton.addActionListener(e -> markAttendance());
        displayAttendanceButton.addActionListener(e -> displayAttendance());

        // Layout for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(addStudentButton);
        panel.add(addSubjectButton);
        panel.add(markAttendanceButton);
        panel.add(displayAttendanceButton);
        
        add(panel, BorderLayout.CENTER);
    }

    private void addStudent() {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID:");
        String name = JOptionPane.showInputDialog(this, "Enter Student Name:");
        manager.addStudentToDB(id, name);  // Save to database
    }

    private void addSubject() {
        String code = JOptionPane.showInputDialog(this, "Enter Subject Code:");
        String name = JOptionPane.showInputDialog(this, "Enter Subject Name:");
        manager.addSubjectToDB(code, name);  // Save to database
    }

    private void markAttendance() {
        String studentId = JOptionPane.showInputDialog(this, "Enter Student ID:");
        String subjectCode = JOptionPane.showInputDialog(this, "Enter Subject Code:");
        String attendance = JOptionPane.showInputDialog(this, "Enter Attendance (P/A):");
        boolean isPresent = attendance.equalsIgnoreCase("P");
        manager.markAttendanceInDB(studentId, subjectCode, isPresent);  // Mark in database
    }

    private void displayAttendance() {
        manager.displayAttendanceFromDB();  // Fetch and display from database
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AttendanceGUI frame = new AttendanceGUI();
            frame.setVisible(true);
        });
    }
}