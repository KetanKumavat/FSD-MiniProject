CREATE TABLE Student (
    StudentID SERIAL PRIMARY KEY,         -- Unique ID for each student
    First_name VARCHAR(255),            -- First name of the student
    Last_name VARCHAR(255),             -- Last name of the student
    Email VARCHAR(255),                -- Email address
    Phone_number VARCHAR(15)            -- Phone number (with a length that allows for different country formats)
);

CREATE TABLE Venue (
    venueId SERIAL PRIMARY KEY,
    RoomNumber INT,
    Floor_number INT
);

CREATE TABLE Orientation (
    DepartmentID INT PRIMARY KEY,               -- Primary Key for department
    Department_name VARCHAR(255),                -- Department Name
    Location VARCHAR(255),                      -- Location of the orientation
    Faculty_name VARCHAR(255),                   -- Faculty in charge
    Time VARCHAR(255)                           -- Time of the orientation
);

CREATE TABLE Orientation_Attendees (
    OrientationID INT,                          -- Foreign Key to Orientation table
    StudentID INT,                              -- Foreign Key to Student table
    PRIMARY KEY (OrientationID, StudentID),
    CONSTRAINT fk_orientation FOREIGN KEY (OrientationID) REFERENCES Orientation(DepartmentID),
    CONSTRAINT fk_student FOREIGN KEY (StudentID) REFERENCES Student(StudentID)
);

INSERT INTO Student (Studentid, email, first_name, last_name, phone_number) VALUES
(1, 'john.doe@example.com', 'John', 'Doe', '1234567890'),
(2, 'jane.smith@example.com', 'Jane', 'Smith', '0987654321'),
(3, 'alice.johnson@example.com', 'Alice', 'Johnson', '5555555555'),
(4, 'bob.brown@example.com', 'Bob', 'Brown', '4444444444'),
(5, 'charlie.davis@example.com', 'Charlie', 'Davis', '3333333333');

INSERT INTO Orientation (Attendees, Departmentid, Department_name, faculty_name, Location, Time) VALUES
(1, 1, 'Computer Science', 'Dr. Smith', 'Building A', '10:00 AM'),
(2, 2, 'Mathematics', 'Dr. Johnson', 'Building B', '11:00 AM'),
(3, 3, 'Physics', 'Dr. Brown', 'Building C', '12:00 PM'),
(4, 4, 'Chemistry', 'Dr. White','Building C', '01:00 PM'),
(5, 5, 'Biology', 'Dr. Green', 'Building D', '02:00 PM');

INSERT INTO Venue (roomnumber, floor_number) VALUES
(101, 1),
(102, 1),
(201, 2),
(202, 2),
(301, 3),
(302, 3);