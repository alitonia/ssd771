CREATE TABLE AppUser
(
    id       INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    username VARCHAR(45)                        NOT NULL,
    password VARCHAR(128)                       NOT NULL
);

CREATE TABLE BikeType
(
    id               INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
    bikeTypeName     VARCHAR(45)                        NOT NULL,
    monetaryValue    INTEGER                            NOT NULL,
    electricType     BOOLEAN                            NOT NULL,
    noOfSaddles      INTEGER                            NOT NULL,
    noOfPedals       INTEGER                            NOT NULL,
    noOfRearSeats    INTEGER                            NOT NULL,
    description      VARCHAR(100),
    bikeTypeImageURL VARCHAR(100)
);
CREATE UNIQUE INDEX FK_BikeType_bikeTypeName1_idx ON BikeType (bikeTypeName);

CREATE TABLE Station
(
    id                   INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name                 VARCHAR(45)                        NOT NULL,
    address              VARCHAR(100)                       NOT NULL,
    distance             INTEGER                            NOT NULL,
    maxCapacity          INTEGER                            NOT NULL,
    capacity             INTEGER                            NOT NULL,
    walkingTimeByMinutes INTEGER                            NOT NULL
);

CREATE
    FULLTEXT INDEX FK_Station_name1_idx ON Station (name);
CREATE
    FULLTEXT INDEX FK_Station_address1_idx ON Station (address);

CREATE TABLE Bike
(
    barcode            VARCHAR(45) PRIMARY KEY NOT NULL,
    type               INTEGER                 NOT NULL,
    licensePlateNumber VARCHAR(45)             NOT NULL,
    location           INTEGER,
    available          BOOLEAN DEFAULT TRUE,
    batteryPercentage  INTEGER DEFAULT 0 CHECK (batteryPercentage >= 0 AND batteryPercentage <= 100),
    CONSTRAINT fk_Bike_Station1 FOREIGN KEY (location) REFERENCES Station (id),
    CONSTRAINT fk_Bike_BikeType1 FOREIGN KEY (type) REFERENCES BikeType (id)
);

CREATE TABLE RentalStrategy
(
    id                   INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name                 VARCHAR(45)            NOT NULL,
    description          VARCHAR(150),
    depositStrategy      VARCHAR(45),
    costStrategy         VARCHAR(45),
    countingTimeStrategy VARCHAR(45)
);

CREATE TABLE Rental
(
    id                      INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    userId                  INTEGER                            NOT NULL,
    bikeBarcode             VARCHAR(45)                        NOT NULL,
    rentalDurationByMinutes INTEGER DEFAULT 0,
    startTime               VARCHAR(45)                        NOT NULL,
    finishTime              VARCHAR(45),
    currentPauseTime        VARCHAR(45),
    currentRestartTime      VARCHAR(45),
    status                  INTEGER DEFAULT 0,
    strategy                INTEGER,
    CONSTRAINT fk_Rental_User1 FOREIGN KEY (userId) REFERENCES AppUser (id),
    CONSTRAINT fk_Rental_Bike1 FOREIGN KEY (bikeBarcode) REFERENCES Bike (barcode),
    CONSTRAINT fk_Rental_Strategy1 FOREIGN KEY (strategy) REFERENCES RentalStrategy (id)
);

CREATE TABLE CreditCard
(
    cardCode    VARCHAR(45) PRIMARY KEY NOT NULL,
    owner       VARCHAR(45)             NOT NULL,
    dateExpired VARCHAR(20)                 NOT NULL,
    cvvCode     VARCHAR(45)             NOT NULL
);

CREATE TABLE PaymentTransaction
(
    id        VARCHAR(45) PRIMARY KEY NOT NULL,
    rentalId  INTEGER,
    cardCode  VARCHAR(45)         NOT NULL,
    amount    INTEGER             NOT NULL,
    contents  VARCHAR(100)        NOT NULL,
    errorCode VARCHAR(100)        NOT NULL,
    createdAt VARCHAR(45)         NOT NULL,
    CONSTRAINT fk_PaymentTransaction_CreditCard1 FOREIGN KEY (cardCode) REFERENCES CreditCard (cardCode)
);

CREATE TABLE Invoice
(
    id            INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    transactionId VARCHAR(45) DEFAULT NULL,
    type          INTEGER                NOT NULL CHECK (type = 1 OR type = -1),
    rentalId      INTEGER                NOT NULL,
    amount        INTEGER,
    CONSTRAINT fk_Invoice_Rental1 FOREIGN KEY (rentalId) REFERENCES Rental (id),
    CONSTRAINT fk_Invoice_PaymentTransaction1 FOREIGN KEY (transactionId) REFERENCES PaymentTransaction (id)
);

CREATE UNIQUE INDEX FK_Invoice_typeRentalId1_idx ON Invoice (type, rentalId);
