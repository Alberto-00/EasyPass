DROP database if exists easyPass;
CREATE DATABASE easyPass;
USE easyPass;

CREATE TABLE Dipartimento (
	Codice_Dip VARCHAR(20) PRIMARY KEY,
    Nome TEXT NOT NULL,
    ID_formato INT NOT NULL,
    foreign key (ID_formato) references Formato (ID_formato)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Formato(
	ID_formato INT PRIMARY KEY,
    N_studenti BOOLEAN NOT NULL default false,
    GP_validi BOOLEAN NOT NULL default false,
    GP_nonValidi BOOLEAN NOT NULL default false,
    Nome_studente BOOLEAN NOT NULL default false,
    Cognome_studente BOOLEAN NOT NULL default false,
    Ddn BOOLEAN NOT NULL default false
);

CREATE TABLE Docente (
	Username_Doc TEXT PRIMARY KEY,
    Nome_Doc VARCHAR(20) NOT NULL,
    Cognome_Doc VARCHAR (20) NOT NULL,
    Password_Doc TEXT NOT NULL,
	Codice_Dip VARCHAR(20) NOT NULL,
    foreign key (Codice_Dip) references Dipartimento (Codice_Dip)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Direttore (
	Username_Dir TEXT PRIMARY KEY,
    Nome_Dir VARCHAR(20) NOT NULL,
    Cognome_Dir VARCHAR (20) NOT NULL,
    Password_Dir TEXT NOT NULL,
	Codice_Dir VARCHAR(20) NOT NULL,
    foreign key (Codice_Dip) references Formato (Codice)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Sessione (
	QRcode TEXT PRIMARY KEY,
    Username_Doc TEXT NOT NULL,
    foreign key (Username_Doc) references Docente (Username_Doc)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Report (
	ID_report INT auto_increment PRIMARY KEY,
    Orario TIME NOT NULL,
    Data_report DATE NOT NULL,
    PathFile TEXT NOT NULL,
    
    Username_Dir TEXT NOT NULL,
    foreign key (Username_Dir) references Direttore (Username_Dir)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    
    QRcode_session TEXT NOT NULL,
	foreign key (QRcode_session) references Sessione (QRcode)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Esito (
	ID_Esito INT auto_increment PRIMARY KEY,
    Valido BOOLEAN NOT NULL,
    ID_report INT auto_increment NOT NULL,
    foreign key (ID_report) references Report (ID_report)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
