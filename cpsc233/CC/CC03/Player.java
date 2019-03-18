public class Player {
  private int id;
  private int score;

  public Player() {
  }

  public Player(int idA, int scoreA) {
    if(idA >= 1000 && idA <= 9999 && scoreA >= 0) {
      id = idA;
      score = scoreA;
    }
    else if(idA >= 1000 && idA <= 9999 && scoreA < 0) {
      id = idA;
      score = 0;
    }
    else if(idA < 1000 || idA > 9999 && scoreA >= 0) {
      id = 1111;
      score = scoreA;
    }
    else {
      id = 1111;
      score = 0;
    }
  }

  public Player (Player playerToCopy) {
    id = playerToCopy.getId();
    score = playerToCopy.getScore();
  }

  public String getLevel() {
    if (score >=0 && score <= 10) {
      return "beginner";
    }
    else if (score >= 11 && score <= 100) {
      return "intermediate";
    }
    else {
      return "expert";
    }
  }

  public int updateScore(int numToUpdate) {
    if(numToUpdate > 0) {
      score = score + numToUpdate;
    }
    return score;
  }

  public int getId() {
    return this.id;
  }

  public int getScore() {
    return this.score;
  }

  public void setId(int idToCopy) {
    if(idToCopy >= 1000 && idToCopy <= 9999) {
      id = idToCopy;
    }
    else {
      id = 1111;
    }
  }





}
