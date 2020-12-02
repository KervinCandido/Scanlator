CREATE TABLE GENRE(
	ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(200) NOT NULL
);
CREATE TABLE AUTHOR(
	ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(200) NOT NULL
);
CREATE TABLE ARTIST(
	ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(200) NOT NULL
);
CREATE TABLE LITERARY_WORK(
	ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    TITLE VARCHAR(200) NOT NULL
);
CREATE TABLE CHAPTER(
	ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CHAPTER_NUMBER BIGINT NOT NULL,
    ID_LITERARY_WORK BIGINT(20) NOT NULL,
    FOREIGN KEY (ID_LITERARY_WORK) REFERENCES LITERARY_WORK(ID)
);
CREATE TABLE PAGE(
	ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    PAGE_NUMBER BIGINT NOT NULL,
	PAGE_PATH VARCHAR(200) NOT NULL,
    ID_CHAPTER BIGINT(20) NOT NULL,
    FOREIGN KEY(ID_CHAPTER) REFERENCES CHAPTER(ID)
);
CREATE TABLE AUTHOR_LITERARY_WORK(
    ID_AUTHOR BIGINT(20) NOT NULL,
    ID_LITERARY_WORK BIGINT(20) NOT NULL,
    FOREIGN KEY (ID_AUTHOR) REFERENCES AUTHOR(ID),
    FOREIGN KEY (ID_LITERARY_WORK) REFERENCES LITERARY_WORK(ID),
    PRIMARY KEY(ID_AUTHOR, ID_LITERARY_WORK)
);
CREATE TABLE ARTIST_LITERARY_WORK(
    ID_ARTIST BIGINT(20) NOT NULL,
    ID_LITERARY_WORK BIGINT(20) NOT NULL,
    FOREIGN KEY (ID_ARTIST) REFERENCES ARTIST(ID),
    FOREIGN KEY (ID_LITERARY_WORK) REFERENCES LITERARY_WORK(ID),
    PRIMARY KEY(ID_ARTIST, ID_LITERARY_WORK)
);
CREATE TABLE GENRE_LITERARY_WORK(
    ID_GENRE BIGINT(20) NOT NULL,
    ID_LITERARY_WORK BIGINT(20) NOT NULL,
    FOREIGN KEY (ID_GENRE) REFERENCES GENRE(ID),
    FOREIGN KEY (ID_LITERARY_WORK) REFERENCES LITERARY_WORK(ID),
    PRIMARY KEY(ID_GENRE, ID_LITERARY_WORK)
);

