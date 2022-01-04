DROP database if exists easypass;
CREATE DATABASE easypass;
USE easypass;

CREATE TABLE Formato(
	ID_formato VARCHAR(10) PRIMARY KEY,
    N_studenti BOOLEAN NOT NULL default false,
    GP_validi BOOLEAN NOT NULL default false,
    GP_nonValidi BOOLEAN NOT NULL default false,
    Nome_Cognome BOOLEAN NOT NULL default false,
    Ddn BOOLEAN NOT NULL default false
);

CREATE TABLE Dipartimento (
	Codice_Dip VARCHAR(10) PRIMARY KEY,
    Nome VARCHAR(150) NOT NULL,
    ID_formato VARCHAR(10) NOT NULL,
    foreign key (ID_formato) references Formato (ID_formato)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Docente (
	Username_Doc VARCHAR(50) PRIMARY KEY,
    Nome_Doc VARCHAR(20) NOT NULL,
    Cognome_Doc VARCHAR(20) NOT NULL,
    Password_Doc VARCHAR(80) NOT NULL,
	Codice_Dip VARCHAR(10) NOT NULL,
    foreign key (Codice_Dip) references Dipartimento (Codice_Dip)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Direttore (
	Username_Dir VARCHAR(50) PRIMARY KEY,
    Nome_Dir VARCHAR(20) NOT NULL,
    Cognome_Dir VARCHAR(20) NOT NULL,
    Password_Dir VARCHAR(80) NOT NULL,
	Codice_Dip VARCHAR(10) NOT NULL,
    foreign key (Codice_Dip) references Dipartimento (Codice_Dip)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Sessione (
	QRcode VARCHAR(50) PRIMARY KEY,
    Username_Doc VARCHAR(50) NOT NULL,
    isInCorso BOOLEAN NOT NULL,
    foreign key (Username_Doc) references Docente (Username_Doc)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Report (
	ID_report INT auto_increment PRIMARY KEY,
    Orario TIME NOT NULL,
    Data_report DATE NOT NULL,
    PathFile VARCHAR(100) NOT NULL,
    Codice_Dip VARCHAR(10) NOT NULL,
    foreign key (Codice_Dip) references Dipartimento (Codice_Dip)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    Username_Doc VARCHAR(50) NOT NULL,
	foreign key (Username_Doc) references Docente (Username_Doc)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Esito (
	ID_Esito INT auto_increment PRIMARY KEY,
    Valido BOOLEAN NOT NULL,
    ID_Report INT,
    Nome_Studente VARCHAR(20) NOT NULL,
    Cognome_Studente VARCHAR(20) NOT NULL,
    Ddn_Studente date NOT NULL,
    QRcodeSession VARCHAR(50) NOT NULL,
    foreign key (ID_report) references Report (ID_report)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    foreign key (QRcodeSession) references Sessione (QRcode)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
