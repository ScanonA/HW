Readme.md

To run program. Start with MainMenu.java


	MERGING STUFF:

-Merged Mike's work:
	-Changed files: AuthorizeReviewers.java
		-Admin can now select a reviewer application and accept it. It updates to the database as well

-Merged Brian's work:
	-Changed files: AdminMenu.java (added "paper" button)  |  Created: JournalList.java
		-JournalList.java shows submitted papers and lets you change their status to accept, declined, minor revision, major revision
		-The JList in JournalList.java only shows papers with a "Submitted" status and currently doesn't refresh to remove entries that have been assigned a status.

-Merged Erika's work:
	-Changed files: AuthorMenu.java | "download paper button" changed
		-When you select a file with the "download paper" button it copies the file to the user's download folder

-Merged StevenC's work:
	-changes to AuthorPickReviewer.java and ReviewRepository.java, 
		-New method in ReviewRepository addReviewer();
		-AuthorPickReviewer now allows for multiple reviewers to be chosen
		-When paper is uploaded, UPLOADS table and REVIEWS table become updated with correct corresponding information




My Changes:

Login and Registering txtboxes removed:
	
	-Replaced with pop up boxes with messages inside

New Class Created called LogInDataContainer.java:

	-When a user logs in, it stores the Username (email) and RoleID in this file.
		-You can use LogInDataContainer.getEmail() to get the email of the current logged in user

Changes to ManageReviewers.java:

	-Added go back button to go back to AdminMenu.java

	-Try block in method valueChanged(); changed constructor to AuthorizeReviewers(selectedUsername, SelectedApprovalStatus)

	-JList is now inside a scrollablePane (DATABASE OF REVIEWERS CURRENTLY NOT LONG ENOUGH SO IT LOOKS LIKE A NORMAL LIST)
		-Label Above Pane stating "Applying Reviewers"


Changes to AuthorizeReviewers.java:
	
	- Changed updateRoleIDInDatabase(); Method.
		-UserRepository.updateUserStatusByName(conn.getConn(), this.username, this.roleid);  // changed this.roleid to static int 1 | Change shown on following line
		-UserRepository.updateUserStatusByName(conn.getConn(), this.username, 1);	// This now sets approved to 1. 
			-1: Approved to Review Papers.
			-0: Not approved to review (i.e. blacklisted) 
			-NULL: Means application hasn't been reviewed and Admin has to approve or deny the user

	-Changed public void drawWindow(); to a constructor for AuthorizeReviewers.java	
		-AuthorizeReviewers(selectedUsername, SelectedApprovalStatus)  CONSTRUCTOR NOW REQUIRES INPUTS

	-approvedToReview(int roleID); changed variable names to approvedStatus as roleID isn't actually used | new method on next line
		-ApprovedToReview(Integer approvedStatus)


	-AuthorizeReviewers.java now has button to decline reviewer (i.e. Change "approved" to 0) 
		-0 and NULL are different for "approved" column in database and in meaning. Might be best to discuss
			-NULL: Reviewer has their application pending
			-0: Reviewer is Blacklisted


Changes to Database:
	-Changed all "approved" statuses to 1 for ADMINS and AUTHORS.
		-"approved" is used for whether a reviewer can review papers

	-Changed "Angela Whyte" first_name
		-First name contained a space at the end of it
			-This caused problems in "ManageReviewers.java" with call to "UserRepository.getApprovalByName"


Changes to AuthorPickReviewer.java:

	-small change in copyToDirectory path. made "/uploads/" to "\\uploads\\" (honestly think both ways work. might've been a waste of time

	-Removed dispose(); in listener for ChooseFile button. So user can now close the file chooser and choose a different reviewer if they need to

	-Added LogInDataContainer.getEmail to emailDirectory Path

	-Imported java.io.File;
		-Used in creating the directory named after a users Email

	-Uploading files now uploads to the directory named after a users Email


Changes to ReviewerMenu.java:

	-Download function copies files to the users downloads folder
	-Upload function only copies to "uploads" directory. Trying to get assigned papers uploading and downloading to work but it's alot harder than expected


PRINT DEBUG STATEMENT LOCATIONS:

	-UserRepository.java | Method: getApprovalByName();
	-AuthorizeReviewers.java | Literally Everywhere
	-ManageReviewers.java | Literally Everywhere