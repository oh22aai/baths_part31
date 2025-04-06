/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

public class BattleTester {
    public static void main(String[] args) {
        SeaBattles game = new SeaBattles();

        // Initialize game state
        game.setupFleet();
        game.setupEncounters();

        System.out.println("=== All Ships ===");
        System.out.println(game.getAllShips());

        System.out.println("=== Adding Ships to Squadron ===");
        System.out.println(game.addToSquadron("Black Pearl"));
        System.out.println(game.addToSquadron("Sea Serpent"));
        System.out.println(game.addToSquadron("Ghost Raider"));

        System.out.println("\n=== Current Squadron ===");
        System.out.println(game.getSquadron());

        System.out.println("\n=== Ship Details ===");
        System.out.println(game.getShipDetails("Sea Serpent"));  // Expect valid details
        System.out.println(game.getShipDetails("Unknown Ship")); // Expect error message

        System.out.println("\n=== Is in Squadron? ===");
        System.out.println("Black Pearl: " + game.isInSquadron("Black Pearl"));
        System.out.println("Flying Dutchman: " + game.isInSquadron("Flying Dutchman"));

        System.out.println("\n=== All Encounters ===");
        System.out.println(game.getAllEncounters());

        System.out.println("\n=== Fight Encounters ===");
        System.out.println(game.fightEncounter(1)); // Expect a result (win/loss)
        System.out.println(game.fightEncounter(2));
        System.out.println(game.fightEncounter(3));

        System.out.println("\n=== War Chest ===");
        System.out.println("Gold: " + game.getWarChest());

        System.out.println("\n=== Sunken Ships ===");
        System.out.println(game.getSunkShips());

        System.out.println("\n=== Is Defeated? ===");
        System.out.println(game.isDefeated() ? "Yes" : "No");

        System.out.println("\n=== Adding a sunken ship back (should fail) ===");
        System.out.println(game.addToSquadron("Ghost Raider")); // May be sunk already

        System.out.println("\n=== Attempting to fight invalid encounter ===");
        System.out.println(game.fightEncounter(99)); // Should return "No such encounter"
    }
}
