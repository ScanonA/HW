import java.util.ArrayList;

public class Team {
  private String name;
  private ArrayList<Player> playerList = new ArrayList<Player>();

  public Team(String nameToCopy) {
    name = nameToCopy;
  }

  public void addPlayer(Player playerToAdd) {
    playerList.add(new Player(playerToAdd));
  }

  public Player getPlayerWithHighestScore() {

    Player c = playerList.get(0);
    return c;

  }

  public String getName() {
    return this.name;
  }

  public ArrayList<Player> getPlayerList() {
    return this.playerList;
  }






}
