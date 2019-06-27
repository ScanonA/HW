import mysql.connector

#TO CHANGE ENCRYPTION OF PASSWORD
# ALTER USER 'yourusername'@'localhost' IDENTIFIED WITH mysql_native_password BY 'youpassword';

mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="471-my-root"
)
mycursor = mydb.cursor()

mycursor.execute("DROP DATABASE IF EXISTS mysocial")
mycursor.execute("CREATE DATABASE mysocial")

mycursor.execute("use mysocial")

mycursor.execute("SHOW DATABASES")
print("\n----------------\nSHOW DATABASES\n----------------")
for x in mycursor:
    print(x)



mycursor.execute("CREATE TABLE USER (Name CHAR(16) NOT NULL, \
						ID INT NOT NULL, \
						IP_Address CHAR(45) NOT NULL, \
					PRIMARY KEY(Name, ID))")

mycursor.execute("CREATE TABLE LOGGED_IN (Email_address CHAR(255) NOT NULL, \
						Password VARCHAR(128) NOT NULL, \
						Name CHAR(16) NOT NULL, \
						ID INT NOT NULL, \
						IP_Address CHAR(45) NOT NULL, \
					PRIMARY KEY(Email_address),UNIQUE(Name, ID), \
					FOREIGN KEY(Name,ID) \
						REFERENCES USER(Name,ID)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE ADMIN (Email_address CHAR(255) NOT NULL, \
						Password VARCHAR(128) NOT NULL, \
						Name CHAR(16) NOT NULL, \
						ID INT NOT NULL, \
						IP_Address CHAR(45) NOT NULL, \
						Employee_ID INT NOT NULL, \
					PRIMARY KEY(Email_address),UNIQUE(Name, ID, Employee_ID), \
					FOREIGN KEY(Name,ID) \
						REFERENCES USER(Name,ID)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Email_address) \
						REFERENCES Logged_in(Email_address)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE ADVERTISER (Email_address CHAR(255) NOT NULL, \
						Password VARCHAR(128) NOT NULL, \
						Name CHAR(16) NOT NULL, \
						ID INT NOT NULL, \
						IP_Address CHAR(45) NOT NULL, \
						Company_name CHAR(48) NOT NULL, \
					PRIMARY KEY(Email_address),UNIQUE(Name, ID, Company_name), \
					FOREIGN KEY(Name,ID) \
						REFERENCES USER(Name,ID)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Email_address) \
						REFERENCES Logged_in(Email_address)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE POST (Link CHAR(255) NOT NULL, \
						Caption VARCHAR(140), \
						Email_address CHAR(255), \
					PRIMARY KEY(Link), \
					FOREIGN KEY(Email_address) \
						REFERENCES Logged_in(Email_address)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE PICTURE (Link CHAR(255) NOT NULL, \
						Caption VARCHAR(140), \
						Resolution CHAR(16) NOT NULL, \
						Name CHAR(16) NOT NULL, \
						ID INT NOT NULL, \
					PRIMARY KEY(Link), \
					FOREIGN KEY(Link)  \
						REFERENCES POST(Link)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Name,ID) \
						REFERENCES USER(Name,ID)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE VIDEO (Link CHAR(255) NOT NULL, \
						Caption VARCHAR(140), \
						Resolution CHAR(16) NOT NULL, \
						Length INT NOT NULL, \
						Name CHAR(16) NOT NULL, \
						ID INT NOT NULL, \
					PRIMARY KEY(Link), \
					FOREIGN KEY(Link) \
						REFERENCES POST(Link)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Name,ID) \
						REFERENCES USER(Name,ID)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE AD (Link CHAR(255) NOT NULL, \
						Caption VARCHAR(140), \
						Num_views INT,\
						Num_clicks INT,\
						Email_address CHAR(255) NOT NULL,\
						Name CHAR(16) NOT NULL,\
						ID INT NOT NULL,\
					PRIMARY KEY(Link), \
					FOREIGN KEY(Link) \
						REFERENCES POST(Link)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Email_address) \
						REFERENCES Logged_in(Email_address)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Name,ID) \
						REFERENCES USER(Name,ID)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE THREAD (Name CHAR(16) NOT NULL,\
						Email_address CHAR(255) NOT NULL,\
					PRIMARY KEY(Name),\
					FOREIGN KEY(Email_address) \
						REFERENCES Logged_in(Email_address)ON DELETE CASCADE ON UPDATE CASCADE)")

# exported this from phpmyadmin once I got comments working
mycursor.execute("CREATE TABLE `comment` (\
 				`CText` varchar(140) NOT NULL,\
 				`Name` char(16) NOT NULL,\
 				`ID` int(11) NOT NULL,\
 				`Link` char(255) NOT NULL,\
 				PRIMARY KEY (`Name`,`ID`,`CText`,`Link`) USING BTREE,\
 				KEY `Link` (`Link`) USING BTREE,\
 				CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`Link`) REFERENCES `post` (`Link`) ON DELETE CASCADE ON UPDATE CASCADE,\
 				CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`Name`, `ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE CONTAINS (Name CHAR(16) NOT NULL,\
						Link CHAR(255) NOT NULL,\
					PRIMARY KEY(Name, Link),\
					FOREIGN KEY(Name) \
						REFERENCES THREAD(Name)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Link) \
						REFERENCES POST(Link)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE SUBSCRIBES_TO (Name CHAR(16) NOT NULL,\
						Email_address CHAR(255) NOT NULL,\
					PRIMARY KEY(Name, Email_address),\
					FOREIGN KEY(Name) \
						REFERENCES THREAD(Name)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Email_address) \
						REFERENCES Logged_in(Email_address)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE MODERATES (Name CHAR(16) NOT NULL,\
						Email_address CHAR(255) NOT NULL,\
						Email_address2 CHAR(255) NOT NULL,\
					PRIMARY KEY(Name, Email_address, Email_address2),\
					FOREIGN KEY(Name) \
						REFERENCES THREAD(Name)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Email_address) \
						REFERENCES Logged_in(Email_address)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Email_address2) \
						REFERENCES Logged_in(Email_address)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE FOLLOW (Email_address1 CHAR(255) NOT NULL,\
						Email_address2 CHAR(255) NOT NULL,\
					PRIMARY KEY(Email_address1, Email_address2),\
					FOREIGN KEY(Email_address1) \
						REFERENCES Logged_in(Email_address)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Email_address2) \
						REFERENCES Logged_in(Email_address)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE REPOST (Link CHAR(255) NOT NULL,\
						Name CHAR(16) NOT NULL,\
						ID INT NOT NULL,\
					PRIMARY KEY(Link, Name, ID),\
					FOREIGN KEY(Name,ID) \
						REFERENCES USER(Name,ID)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Link) \
						REFERENCES POST(Link)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE VOTE(Link CHAR(255) NOT NULL,\
						Name CHAR(16) NOT NULL,\
						ID INT NOT NULL,\
					PRIMARY KEY(Link, Name, ID),\
					FOREIGN KEY(Name,ID) \
						REFERENCES USER(Name,ID)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Link) \
						REFERENCES POST(Link)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE REPORT (Link CHAR(255) NOT NULL,\
						Name CHAR(16) NOT NULL,\
						ID INT NOT NULL,\
					PRIMARY KEY(Link, Name, ID),\
					FOREIGN KEY(Name,ID) \
						REFERENCES USER(Name,ID)ON DELETE CASCADE ON UPDATE CASCADE, \
					FOREIGN KEY(Link) \
						REFERENCES POST(Link)ON DELETE CASCADE ON UPDATE CASCADE)")

mycursor.execute("CREATE TABLE TAG (Link CHAR(255) NOT NULL,\
						Tagtext CHAR(24) NOT NULL,\
					PRIMARY KEY(Link,Tagtext),\
					FOREIGN KEY(Link) \
						REFERENCES POST(Link)ON DELETE CASCADE ON UPDATE CASCADE)")

# mycursor.execute("DROP TABLE IF EXISTS `PICTURE`")

print("\n----------------\nSHOW TABLES\n----------------")
mycursor.execute("SHOW TABLES")
for x in mycursor:
    print(x)