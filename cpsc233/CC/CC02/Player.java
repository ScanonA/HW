public class Player {
	int id;
	int score;

	public int getId() {
		return this.id;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int setId (int idValue) {
		id  = 1111;		
		if (idValue >= 1000 && idValue <= 9999) {
			id = idValue;
			return id;
		}
		else {
			return id;
		}
			 
	}

	public int updateScore (int numToUpdate) {		
		if (numToUpdate > 0) {
			score += numToUpdate;
			return score;		
		}
		else {
			return score;
		}
	}

	public String getLevel() {
		if (score >=0 && score <= 10) {
			return "beginner";		
		}
		else if (score >10 && score <= 100) {
			return "intermediate";
		}				
		else {
			return "expert";
		}
	}
	

	public int getRating (Player[] players) {
		int i = 0;
		int rating;	
		int index= 0;
		while (i <= players.length) { 
				i++;
				if (score > players[i].getScore()){
					index = i; 				
				}
	 	}
		rating = index - players.length;
		if (players.length == 0){
			rating = 1;		
		}
		return rating; 
	}
}
