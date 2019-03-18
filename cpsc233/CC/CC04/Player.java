public abstract class Player extends Object {
  private int id;
  private int score = 0;

  public Player (int id) {
    if(id >= 1000 && id <= 9999) {
      this.id = id;
    }
    else {
      this.id = 1111;
    }
  }

  public Player (Player toCopy) {
    setId(toCopy.id);
  }

  protected int getId() {
    return id;
  }

  protected void setId(int id1) {
    id = id1;
  }

  protected void updateScore(int amount) {
      if(score >= 0) {
        score = amount;
      }
  }

  public int getScore() {
    return score;
  }

  public void takeTurn() {
    int divisor;
    int movePoints = chooseMove();
    divisor = getScore() % 7;
    if(divisor != 0) {
      score += (movePoints / divisor);
    }
    else {
      divisor = 1;
      score += (movePoints / divisor);
    }
  }

  public String getCategory() {
    if(score >= 0 && score <= 10) {
      return "Beginner";
    }
    else if(score >= 11 && score <= 100) {
      return "Intermediate";
    }
    else
      return "Expert";
  }

  public String getString() {
    String stringHolder = "ID: " +id +" Score: " +score;
    return stringHolder;
  }

  public abstract int chooseMove();


}
