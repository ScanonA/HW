import mysql.connector


mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="471-my-root",
    database= "mysocial"
)
mycursor = mydb.cursor()
###############################################################################################################################################################################

#Show home feed (subscribed threads) - so in other words user gets all the posts from the threads they subscribe to.
homefeed_subbed = "SELECT SUBSCRIBES_TO.Name, POST.Caption, CONTAINS.link \
					FROM SUBSCRIBES_TO AS SUB, LOGGED_IN AS LI, POST AS P, CONTAINS AS C, THREAD AS T \
					WHERE LI.Email_address = %s \
					 		AND SUB.Email_address = LI.Email_address \
					 		AND SUB.Name = CONTAINS.Name \
					 		AND SUB.Name = THREAD.Name AND \
					 		CONTAINS.Link = POST.Link"

#deleting all information from POST, PICTURE, VIDEO, NATURAL, AD - reseting the users account to having no posts
reset = "DELETE FROM (((POST NATURAL JOIN PICTURE) NATURAL JOIN VIDEO) NATURAL JOIN AD) AS P \
			WHERE P.Email_address = %s"

#showing who is following this specific user
followers = "SELECT F.Name, F.ID \
				FROM LOGGED_IN AS U, FOLLOW AS F \
				WHERE U.Email_address = U.Email_address \
					AND F.Email_address \
					AND F.Email_address2 IN \
						SELECT Email_address \
						FROM LOGGED_IN AS U1 \
						WHERE U1.Name = %s \
						AND U1.ID = %s"

#getting all posts that have been reported in a specific thread 
thread_reported_posts = "SELECT POST * \
							FROM POST, THREAD, CONTAINS, REPORT \
							WHERE THREAD.Name = %s \
									AND POST.Link = CONTAINS.Link \
									AND CONTAINS.Name = THREAD.Name \
									AND POST.Link = REPORT.Link"

#get all the posts that have a certain tag
get_users_post_by_tag = "SELECT P* \
							FROM (((POST NATURAL JOIN PICTURE) NATURAL JOIN VIDEO) NATURAL JOIN AD) AS P, TAG AS T \
							WHERE T.tagtext = %s AND P.Email_address = %s AND P.Link IN " 

#get all the posts that have been reported with a specific tag
reported_posts_with_tag = "SELECT R* \
							FROM REPORT AS R, TAG AS T, POST AS P \
							WHERE T.tagtext = %s AND T.link = P.link AND P.link = R.link"

#the the total amount of reports that a user has 
users_total_reports = "SELECT Name, ID, COUNT(*) \
						FROM (USER NATURAL JOIN REPORT) \
						GROUP BY NAME, ID"

#get the threads that a specific logged in user is subcribed to 
thread_subscribers = "SELECT COUNT(*) \
						FROM SUBSCRIBES_TO AS ST \
						WHERE ST.Name = %s"

#get the posts that have a number of votes between 100-300
midVotes = "SELECT POST* \
			FROM POST, VOTE \
			WHERE POST.Link = VOTE.Link \
			GROUP BY VOTE.link \
			HAVING COUNT(*) > 100 AND COUNT(*) < 300"


#user_and_tag = ("email@email.com", "tag")
#mycursor.execute(get_users_post_by_tag, user_and_tag) #fails too long, doesn't read query after certain length

#tag_text = ("sfw",)
# mycursor.execute(reported_posts_with_tag, tag_text)#fails too long, doesn't read query after certain length

#mycursor.execute(users_total_reports) #runs

#thread_name = ("threadName",)
 #mycursor.execute(thread_subscribers, thread_name) #runs

#mycursor.execute(midVotes) #fails too long, doesn't read querry after certain length


#myresult = mycursor.fetchall()

#for result in myresult:
    #print(result)
