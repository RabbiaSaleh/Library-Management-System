# Library-Management-System
# Project Overview

The Library Management System is a JavaFX-based application that allows users to manage a library efficiently. It includes functionalities to add, view, edit, and delete book records. The system integrates with a MySQL database using JDBC and provides a graphical user interface for seamless interaction.

# Technologies Used

JavaFX - For building the graphical user interface

MySQL - To store book and user information

JDBC - For database connectivity

XAMPP - Used to set up the MySQL database server

Eclipse IDE - Development environment for coding

# Key Features

Add New Books: Users can enter book details, including the ID, title, author, publisher, publication year, ISBN, and number of copies.

View Books - Display a list of books stored in the database.

Edit Book Details - Update existing book records.

Delete Books - Remove books that are no longer needed.

Clear Input Fields - Reset the form fields for new entries.

Exit Application - Close the system.

# Use Cases

1. Add Book

Actor: LibrarianDescription: The librarian adds a new book to the system by entering details such as title, author, publisher, year of publication, ISBN, and the number of copies available.

2. Edit Book Details

Actor: LibrarianDescription: Modify details of an existing book in the system.

3. Delete Book

Actor: LibrarianDescription: Remove a book from the library records.

4. View Book Details

Actor: Library UserDescription: A user views details of available books.

5. Clear Fields

Actor: LibrarianDescription: Reset all input fields.

6. Exit Application

Actor: Librarian, Library UserDescription: Close the application.

# Database Schema (ERD Overview)

Book Table

BookID (Primary Key)

Title

Author

Publisher

YearOfPublication

ISBN

NumberOfCopies

Author Table

Author_ID (Primary Key)

Name

A one-to-many relationship exists between authors and books.
