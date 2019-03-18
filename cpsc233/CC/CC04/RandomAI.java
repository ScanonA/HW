import java.util.Random;

public class RandomAI extends Player{
  private int level = 1;

  public RandomAI(int id, int level) {
    super(id);
    if(level > 0 && level <= 10) {
      this.level = level;
    }
    else if (level == 0) {
      level = 1;
    }
    else if (level == 10)
      level = 10;
  }

  public RandomAI(RandomAI toCopy) {
    super(toCopy);
    setLevel(toCopy.level);
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    if(level > 0 && level <= 10){
      this.level = level;
    }
  }

  public int chooseMove() {
    Random rand = new Random();
    int num = rand.nextInt(10) * level;
    return num;
  }

  public String toString() {
    String stringHolder = ""+super.toString()+ " level: "+level;
    return stringHolder;
  }

}
