import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.sql.*;


public class LibraryManagement extends Application {


    private TextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    private Connection conn;


    @Override
    public void start(Stage primaryStage) {
        // Initialize the database connection
        initializeDB();


        primaryStage.setTitle("Library Management System");


        // Labels for the text fields
        Label label1 = new Label("Book ID");
        Label label2 = new Label("Book Title");
        Label label3 = new Label("Author");
        Label label4 = new Label("Publisher");
        Label label5 = new Label("Year of Publication");
        Label label6 = new Label("ISBN");
        Label label7 = new Label("Number of Copies");


        // Text fields for user input
        textField1 = new TextField();
        textField2 = new TextField();
        textField3 = new TextField();
        textField4 = new TextField();
        textField5 = new TextField();
        textField6 = new TextField();
        textField7 = new TextField();


        // Buttons for different operations
        Button addButton = new Button("Add");
        Button viewButton = new Button("View");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");
        Button exitButton = new Button("Exit");


        // Set button actions
        addButton.setOnAction(e -> addBook());
        viewButton.setOnAction(e -> viewBooks());
        editButton.setOnAction(e -> editBook());
        deleteButton.setOnAction(e -> deleteBook());
        clearButton.setOnAction(e -> clearFields());
        exitButton.setOnAction(e -> primaryStage.close());


        // Create a grid layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Horizontal gap between columns
        gridPane.setVgap(10); // Vertical gap between rows
        gridPane.setPadding(new Insets(10, 10, 10, 10)); // Padding around the grid


        // Add labels and text fields to the grid
        gridPane.add(label1, 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(label2, 0, 1);
        gridPane.add(textField2, 1, 1);
        gridPane.add(label3, 0, 2);
        gridPane.add(textField3, 1, 2);
        gridPane.add(label4, 0, 3);
        gridPane.add(textField4, 1, 3);
        gridPane.add(label5, 0, 4);
        gridPane.add(textField5, 1, 4);
        gridPane.add(label6, 0, 5);
        gridPane.add(textField6, 1, 5);
        gridPane.add(label7, 0, 6);
        gridPane.add(textField7, 1, 6);


        // Add buttons to the grid
        gridPane.add(addButton, 0, 7);
        gridPane.add(viewButton, 1, 7);
        gridPane.add(editButton, 2, 7);
        gridPane.add(deleteButton, 3, 7);
        gridPane.add(clearButton, 4, 7);
        gridPane.add(exitButton, 5, 7);


        // Set the scene with the grid pane
        Scene scene = new Scene(gridPane, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void initializeDB() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection to MySQL database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/library", "root", "");
            // Create a statement to execute SQL queries
            Statement stmt = conn.createStatement();
            // SQL query to create the books table if it doesn't exist
            String sql = "CREATE TABLE IF NOT EXISTS books (" +
                    "id VARCHAR(255) PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "author VARCHAR(255), " +
                    "publisher VARCHAR(255), " +
                    "year INT, " +
                    "isbn VARCHAR(255), " +
                    "copies INT)";
            stmt.execute(sql);  // Execute the SQL query
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();  // Print any SQL errors
        }
    }


    private void addBook() {
        try {
            // SQL query to insert a new book
            String sql = "INSERT INTO books (id, title, author, publisher, year, isbn, copies) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // Set the parameters for the query
            pstmt.setString(1, textField1.getText());
            pstmt.setString(2, textField2.getText());
            pstmt.setString(3, textField3.getText());
            pstmt.setString(4, textField4.getText());
            pstmt.setInt(5, Integer.parseInt(textField5.getText()));
            pstmt.setString(6, textField6.getText());
            pstmt.setInt(7, Integer.parseInt(textField7.getText()));
            pstmt.executeUpdate();  // Execute the update
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully");
            clearFields();  // Clear the input fields
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add book");
        }
    }


    private void viewBooks() {
        try {
            // SQL query to select all books
            String sql = "SELECT * FROM books";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            StringBuilder booksList = new StringBuilder();
            while (rs.next()) {
                booksList.append(String.format("ID: %s, Title: %s, Author: %s, Publisher: %s, Year: %d, ISBN: %s, Copies: %d%n",
                        rs.getString("id"), rs.getString("title"), rs.getString("author"),
                        rs.getString("publisher"), rs.getInt("year"),
                        rs.getString("isbn"), rs.getInt("copies")));
            }
            showAlert(Alert.AlertType.INFORMATION, "Books", booksList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to retrieve books");
        }
    }


    private void editBook() {
        try {
            // SQL query to update a book
            String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, year = ?, isbn = ?, copies = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // Set the parameters for the query
            pstmt.setString(1, textField2.getText());
            pstmt.setString(2, textField3.getText());
            pstmt.setString(3, textField4.getText());
            pstmt.setInt(4, Integer.parseInt(textField5.getText()));
            pstmt.setString(5, textField6.getText());
            pstmt.setInt(6, Integer.parseInt(textField7.getText()));
            pstmt.setString(7, textField1.getText());
            pstmt.executeUpdate();  // Execute the update
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book edited successfully");
            clearFields();  // Clear the input fields
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to edit book");
        }
    }


    private void deleteBook() {
        try {
            // SQL query to delete a book
            String sql = "DELETE FROM books WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // Set the parameter for the query
            pstmt.setString(1, textField1.getText());
            pstmt.executeUpdate();  // Execute the update
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book deleted successfully");
            clearFields();  // Clear the input fields
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete book");
        }
    }


    private void clearFields() {
        // Clear all text fields
        textField1.clear();
        textField2.clear();
        textField3.clear();
        textField4.clear();
        textField5.clear();
        textField6.clear();
        textField7.clear();
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        // Show an alert with the given type, title, and content
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
