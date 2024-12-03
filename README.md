# Eagle Academy - Desktop Application

**Eagle Academy Desktop Application** is a Java-based GUI application designed for efficient management of academic institutions. It offers features to streamline student and staff management, attendance tracking, and email communication, all within a user-friendly interface.

---

## Features

### Student Management
- **Add Students:** Register new students with complete personal and academic details.
- **Edit Details:** Update student records, including academic performance and grades.
- **Delete Students:** Remove records of students no longer enrolled.
- **View Profiles:** Display a list of students with advanced search and filter options.

### Attendance Tracking
- **Mark Attendance:** Allow teachers to record attendance for each class.
- **Generate Reports:** Create attendance reports for individual students or entire classes.
- **Email Notifications:** Notify students and parents about attendance-related updates.

### Email Communication
- **Send Emails:** Communicate with students, parents, and staff directly from the application.
- **Pre-defined Templates:** Use templates for announcements, reminders, and alerts.
- **Track History:** Keep a record of sent emails.

### Staff Management
- **Manage Staff:** Add, update, and remove staff records.
- **Assign Roles:** Designate teachers to classes or specific subjects.

### Reporting & Analytics
- Generate detailed reports for student performance and attendance.
- Export data in PDF or Excel formats for sharing and archiving.

### Security
- **User Authentication:** Secure login system to prevent unauthorized access.
- **Role-Based Access:** Admins, teachers, and staff have role-specific permissions.

---

## Technology Stack

- **Programming Language:** Java
- **GUI Framework:** Java Swing or JavaFX
- **Database:** MySQL or SQLite
- **Email Integration:** JavaMail API
- **Report Generation:** Apache POI (Excel) and iText (PDF)

---

## Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/sanodyaJinadasa/Eagle-Academy.git
   ```

2. **Navigate to the Project Directory:**
   ```bash
   cd Eagle-Academy
   ```

3. **Set Up the Database:**
   - Install MySQL or SQLite.
   - Run the provided SQL scripts to set up the database schema and seed data.

4. **Compile and Run the Application:**
   ```bash
   javac Main.java
   java Main
   ```

5. **Email Configuration:**
   - Configure SMTP settings in the application for email functionality.
   - Add your email credentials securely in the configuration file.

---

## Usage

1. **Login:** Admins and staff log in with their credentials.
2. **Navigate the Dashboard:** Access various management features from the main menu.
3. **Manage Data:** Add or update records for students, staff, and attendance.
4. **Send Notifications:** Use the email module for communication.
5. **Generate Reports:** Create and export performance and attendance reports.

---

## Future Enhancements

- **Mobile Integration:** Develop a companion mobile app for better accessibility.
- **Cloud Support:** Implement a cloud database for multi-device synchronization.
- **LMS Integration:** Integrate with learning management systems for online course management.
- **SMS Notifications:** Add an SMS feature for urgent alerts.

---

## Contribution

Contributions are welcome! Follow these steps:

1. **Fork the Repository**
2. **Create a Feature Branch:**
   ```bash
   git checkout -b feature-name
   ```
3. **Commit Changes:**
   ```bash
   git commit -m "Add feature description"
   ```
4. **Push Branch:**
   ```bash
   git push origin feature-name
   ```
5. **Open a Pull Request:** Submit your changes for review.

---

## Links

- **LinkedIn:** [Sanodya V. Jinadasa](https://www.linkedin.com/in/sanodya-v-jinadasa/)
- **GitHub:** [Sanodya Jinadasa](https://github.com/sanodyaJinadasa)

---

## License

This project is open-source and licensed under the [MIT License](LICENSE).

---

## Acknowledgments

Special thanks to contributors and testers who made this project a success.
