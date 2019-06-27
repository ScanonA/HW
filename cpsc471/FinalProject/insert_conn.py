import mysql.connector

#TO CHANGE ENCRYPTION OF PASSWORD
# ALTER USER 'yourusername'@'localhost' IDENTIFIED WITH mysql_native_password BY 'youpassword';

mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="471-my-root",
    database= "mysocial"
)

mycursor = mydb.cursor()

insert_to_user = "INSERT INTO USER (Name, ID, IP_Address) VALUES (%s, %s, %s)"

insert_to_logged = "INSERT INTO LOGGED_IN (Email_address, Password, Name, ID, IP_Address) VALUES (%s, %s, %s, %s, %s)"

insert_to_admin = "INSERT INTO ADMIN (Email_address, Password, Name, ID, IP_Address, Employee_ID) VALUES (%s, %s, %s, %s, %s, %s)"

insert_to_advertiser = "INSERT INTO ADVERTISER (Email_address, Password, Name, ID, IP_Address, Company_name) VALUES (%s, %s, %s, %s, %s, %s)"

inser_to_post = "INSERT INTO POST (Link, Caption, Email_address) VALUES (%s, %s, %s)"

insert_to_picture = "INSERT INTO PICTURE (Link, Caption, Resolution, Name, ID) VALUES (%s, %s, %s, %s, %s)"

insert_to_video = "INSERT INTO VIDEO (Link, Caption, Resolution, Length, Name, ID) VALUES (%s, %s, %s, %s, %s, %s)"

insert_to_ad = "INSERT INTO AD (Link, Caption, Num_views, Num_clicks, Email_address) VALUES (%s, %s, %s, %s, %s)"

insert_to_thread = "INSERT INTO THREAD (Name, Email_address) VALUES (%s, %s)"

insert_to_comment = "INSERT INTO COMMENT (CText, Name, ID, Link) VALUE(%s, %s, %s, %s)"

insert_to_contains = "INSERT INTO CONTAINS (Name, Link) VALUES (%s, %s)"

insert_to_subscribes = "INSERT INTO SUBSCRIBES_TO (Name, Email_address) VALUES (%s, %s)"

insert_to_moderates = "INSERT INTO MODERATES (Name, Email_address, Email_address2) VALUES (%s, %s)"

insert_to_follow = "INSERT INTO FOLLOW (Email_address, Email_address2) VALUES (%s, %s)"

insert_to_repost = "INSERT INTO REPOST (Link, Name, ID) VALUES (%s, %s, %s)"

insert_to_vote = "INSERT INTO VOTE (Link, Name, ID) VALUES (%s, %s, %s)"

insert_to_report = "INSERT INTO REPORT (Link, Name, ID) VALUES (%s, %s, %s)"

insert_to_tag = "INSERT INTO TAG (Link, Tagtext) VALUES (%s, %s)"

#user = ("anon", 471, 147)

#log = ("s@", "123qwe", "anon", 471, 312)

#mycursor.execute(insert_to_logged, log)

#mydb.commit()