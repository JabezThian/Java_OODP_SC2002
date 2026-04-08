package core;

import entities.*;
import actions.*;
import ui.*;
import java.util.Comparator;
import java.util.List;

public class TurnOrderStrategy {
    // Sorts combatants by speed (Highest first).
    // If speeds are equal, it maintains the current list order.
    public void sortBySpeed(List<Combatant> participants) {
        participants.sort(Comparator.comparingInt((Combatant c) -> c.getSpeed().getValue()).reversed());
    }


    // Checks if the game has reached a clear ending.
    // Returns true if only one side (Player or Enemies) remains.
    public boolean isGameOver(Player player, List<Enemy> enemies) {
        return player.getHealth().isDead() || enemies.isEmpty();
    }


    public void announceWinner(Player player, List<Enemy> enemies) {
        if (player.getHealth().isDead()) {
            System.out.println("GAME OVER: The enemies have triumphed.");
        } else {
            System.out.println("VICTORY: " + player.getName() + " has defeated all foes!");
        }
    }
}