CREATE TABLE Contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    phoneNumber VARCHAR(255),
    email VARCHAR(255),
    linkedId INT,
    linkPrecedence ENUM('primary', 'secondary'),
    createdAt DATETIME,
    updatedAt DATETIME,
    deletedAt DATETIME
);

INSERT INTO Contacts (phoneNumber, email, linkedId, linkPrecedence, createdAt, updatedAt, deletedAt)
VALUES ('1234567890', 'example1@email.com', NULL, 'primary', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT INTO Contacts (phoneNumber, email, linkedId, linkPrecedence, createdAt, updatedAt, deletedAt)
VALUES ('1234567430', 'example2@email.com', NULL, 'primary', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT INTO Contacts (phoneNumber, email, linkedId, linkPrecedence, createdAt, updatedAt, deletedAt)
VALUES ('1234227890', 'example3@email.com', NULL, 'primary', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
