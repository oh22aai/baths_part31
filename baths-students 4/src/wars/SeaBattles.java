package wars;

import java.util.*;
import java.io.*;
/**
 * This class implements the behaviour expected from the BATHS
 system as required for 5COM2007 Cwk1B BATHS - Feb 2025
 * 
 * @author A.A.Marczyk 
 * @version 16/02/25
 */

import java.util.*;
import java.io.*;
/**
 * This class implements the behaviour expected from the BATHS
 system as required for 5COM2007 Cwk1B BATHS - Feb 2025
 * 
 * @author A.A.Marczyk 
 * @version 16/02/25
 */

public class SeaBattles implements BATHS 
{
    // may have one HashMap and select on stat

    private String admiral;
    private double warChest;
    private Map< String, Ship> reserve;
    private Map< String, Ship> squadron;
    private ArrayList<Ship>AllShips;
    private Map< Integer, Encounter> encounterList;
    


//**************** BATHS ************************** 
    /** Constructor requires the name of the admiral
     * @param adm the name of the admiral
     */  
    public SeaBattles(String adm)
    {
       admiral = adm;
       warChest = 1000.0; 
       reserve = new HashMap<>();
       squadron = new HashMap<>();
       AllShips = new ArrayList<>();
       encounterList = new HashMap<>();
       setupShips();
       setupEncounters();
    }
    
    /** Constructor requires the name of the admiral and the
     * name of the file storing encounters
     * @param admir the name of the admiral
     * @param filename name of file storing encounters
     */  
    public SeaBattles(String admir, String filename)  //Task 3
    {
      
        
       setupShips();
       // setupEncounters();
       // uncomment for testing Task 
       // readEncounters(filename);
    }
    
    
    /**Returns a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     * @return a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     **/
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Admiral: ").append(admiral).append("\n");
        sb.append("War Chest: ").append(warChest).append("\n");

        if (isDefeated()) 
        {
           sb.append("Status: Defeated\n\n");
        } else 
        {
           sb.append("Status: Surviving\n\n");
        }
        
        sb.append("Squadron:\n");
        String squadronShips = getSquadron();
        if (squadronShips.trim().isEmpty()) 
        {
           sb.append("No ships commissioned\n");
        } else 
        {
           sb.append(squadronShips).append("\n");
        }

        sb.append("Reserve Fleet:\n");
        String reserveShips = getReserveFleet();
        if (reserveShips.trim().isEmpty())
        {
            sb.append("No ships in reserve\n");
        } else
        {
           sb.append(reserveShips).append("\n");
        }

        return sb.toString();
    }
    
    
    /** returns true if War Chest <=0 and the admiral's squadron has no ships which 
     * can be retired. 
     * @returns true if War Chest <=0 and the admiral's fleet has no ships 
     * which can be retired. 
     */
    public boolean isDefeated()
    {
        if(warChest <= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /** returns the amount of money in the War Chest
     * @returns the amount of money in the War Chest
     */
    public double getWarChest()
    {
        return warChest;
    }
    
    
    /**Returns a String representation of all ships in the reserve fleet
     * @return a String representation of all ships in the reserve fleet
     **/
    public String getReserveFleet()
    { 
       StringBuilder reserveList = new StringBuilder(); 
       for (Ship ship : reserve.values()) 
       {  
        reserveList.append(ship.toString())  
                    .append("\n");  
       }
       return reserveList.toString(); 
}

    
    
    /**Returns a String representation of the ships in the admiral's squadron
     * or the message "No ships commissioned"
     * @return a String representation of the ships in the admiral's fleet
     **/
    public String getSquadron()
    {
         if (squadron.isEmpty()) 
         {
            return "No ships"; 
         }

         StringBuilder squadronList = new StringBuilder();
         for (Ship ship : squadron.values()) 
         {
            squadronList.append(ship.toString()).append("\n"); 
         }
         return squadronList.toString();
    }
    
    /**Returns a String representation of the ships sunk (or "no ships sunk yet")
     * @return a String representation of the ships sunk
     **/
    public String getSunkShips()
    {
       StringBuilder s = new StringBuilder();
       for(Ship ship : AllShips)
       {
          if (ship.getShipState() ==  ShipState.SUNK)
          {
             s.append(ship.toString()).append("\n"); 
             return s.toString();
          }
          
       }
       return "no such ship";
    }
    
    /**Returns a String representation of the all ships in the game
     * including their status
     * @return a String representation of the ships in the game
     **/
    public String getAllShips()
    {
        if (AllShips.isEmpty()) 
        {
        return "No ships"; 
        }

        StringBuilder shipsList = new StringBuilder();
        for (Ship ship : AllShips) 
        {
        shipsList.append(ship.toString()).append("\n"); 
        }
        return shipsList.toString();
    }
    
    
    /** Returns details of any ship with the given name
     * @return details of any ship with the given name
     **/
    public String getShipDetails(String nme)
    {
        String s = "";
        for (Ship ship : AllShips) 
        {
            if (ship.getShipName().equalsIgnoreCase(nme)) 
            {
                 return s+=ship.toString();
            }
            
        }
        return "no such ship";
    }     
 
    // ***************** Fleet Ships ************************   
    /** Allows a ship to be commissioned to the admiral's squadron, if there 
     * is enough money in the War Chest for the commission fee.The ship's 
     * state is set to "active"
     * @param nme represents the name of the ship
     * @return "Ship commissioned" if ship is commissioned, "Not found" if 
     * ship not found, "Not available" if ship is not in the reserve fleet, "Not 
     * enough money" if not enough money in the warChest
     **/        
    public String commissionShip(String nme)
    {
        Ship ship = reserve.get(nme);
        if (ship != null && ship.getShipState() == ShipState.RESERVE) {
             if (ship.getCost() <= warChest) 
             {
                 warChest -= ship.getCost();
                 reserve.remove(nme);
                 squadron.put(nme, ship);
                 ship.setState("Active");
                 return "- " + ship.getShipName() + " commissioned to squadron";
             } 
             else 
             {
                 return "- Not enough funds to commission " + ship.getShipName();
             }
        }
        return "no such ship found";

    }
        
    /** Returns true if the ship with the name is in the admiral's squadron, false otherwise.
     * @param nme is the name of the ship
     * @return returns true if the ship with the name is in the admiral's squadron, false otherwise.
     **/
    public boolean isInSquadron(String nme)
    {
        for (Ship ship : squadron.values()) 
        {
            if (ship.getShipName().equalsIgnoreCase(nme)) 
            {
                return true;
            }
            
        }
        return false;
    }
    
    /** Decommissions a ship from the squadron to the reserve fleet (if they are in the squadron)
     * pre-condition: isInSquadron(nme)
     * @param nme is the name of the ship
     * @return true if ship decommissioned, else false
     **/
    public boolean decommissionShip(String nme)
    {
        for(Map.Entry<String,Ship > entry: squadron.entrySet())
        {
            Ship ship = entry.getValue();
            if (ship.getShipName().equalsIgnoreCase(nme))
            {
                if(ship.getShipState() != ShipState.SUNK && ship.getShipState() != ShipState.RESERVE)
                {
                  String shipID = entry.getKey(); 
                  squadron.remove(shipID);      
                  reserve.put(shipID, ship);
                  warChest = warChest + ship.getCost()/2;
                
                  ship.setState("Reserve");  
                  return true;
                }
                
            }
            
        }
        return false;
    }
    
  
    /**Restores a ship to the squadron by setting their state to ACTIVE 
     * @param ref the name of the ship to be restored
     */
    public void restoreShip(String ref)
    {
        for (Ship ship : squadron.values()) 
        {
            if (ship.getShipName().equalsIgnoreCase(ref)) 
            {
                if (ship.getShipState() == ShipState.RESTING)
                {
                    ship.setState("Active");
                }
            }
        }
    }
    
//**********************Encounters************************* 
    /** returns true if the number represents a encounter
     * @param num is the reference number of the encounter
     * @returns true if the reference number represents a encounter, else false
     **/
     public boolean isEncounter(int num)
     {
         
        for (Encounter encounter : encounterList.values()) 
        {
             if (encounter.getE_Number() == num) 
             {
                 return true; // Found a match
             }
        }
        return false; // No matches found after checking all


     }
     
     
/** Retrieves the encounter represented by the encounter 
      * number.Finds a ship from the fleet which can fight the 
      * encounter.The results of fighting an encounter will be 
      * one of the following: 
      * 0-Encounter won by...(ship reference and name)-add prize money to War 
      * Chest and set ship's state to RESTING,  
      * 1-Encounter lost as no ship available - deduct prize from the War Chest,
      * 2-Encounter lost on battle skill and (ship name) sunk" - deduct prize 
      * from War Chest and set ship state to SUNK.
      * If an encounter is lost and admiral is completely defeated because there 
      * are no ships to decommission,add "You have been defeated " to message, 
      * -1 No such encounter
      * Ensure that the state of the war chest is also included in the return message.
      * @param encNo is the number of the encounter
      * @return a String showing the result of fighting the encounter
      */ 
    public String fightEncounter(int encNo)
    {
       String s = "";
       String t = "";
       String u = "";
       String v = "";
       String y = "";
       String z = "";
       String c = "";
       String d = "";
       if (isEncounter(encNo))
        {
           Encounter enc = getEncounters(encNo);
           for (Ship ship : squadron.values())
           {
              if(squadron.isEmpty())
                  {
                      warChest = warChest - enc.getE_Prize();
                      s += ship.toString() + enc.toString() + 
                      "Encounter lost as no suitable ship available, prize lost is: " + enc.getE_Prize() + "\n"
                      + "State of warchest: " + warChest + "\n";
                      return s;
                  }
                  else if(squadron.isEmpty() && warChest <= 0)
                  {
                      t += ship.toString() + enc.toString() + 
                      "Encounter is lost and you lose your job"+ "\n"
                      + "State of warchest: " + warChest + "\n";
                      return t;
                  } 
                  
                  
              if(enc.getE_Type().equalsIgnoreCase("Battle"))
              {
                   if(ship.getBattle() == true)
                   {
                      if(ship.getSkillLevel() >= enc.getE_Level())
                      {
                          warChest += enc.getE_Prize();
                          ship.setState("Resting");
                          u += ship.toString() + enc.toString() + 
                          "Encounter won by skill level, prize won is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return u;
                      }
                      else if(ship.getSkillLevel() < enc.getE_Level())
                      {
                          ship.setState("Sunk");
                          warChest -= enc.getE_Prize();
                          v += ship.toString() + enc.toString() + 
                          "Encounter lost on skill level, prize lost is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return v;
                      }
                      
                   } 
                
             }
             
              if(enc.getE_Type().equalsIgnoreCase("Skirmish"))
              {
                   if(ship.getSkirmish() == true)
                   {
                      if(ship.getSkillLevel() >= enc.getE_Level())
                      {
                          warChest += enc.getE_Prize();
                          ship.setState("Resting");
                          y += ship.toString() + enc.toString() + 
                          "Encounter won by skill level, prize won is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return y;
                      }
                      else if(ship.getSkillLevel() < enc.getE_Level())
                      {
                          ship.setState("Sunk");
                          warChest = warChest - enc.getE_Prize();
                          z += ship.toString() + enc.toString() + 
                          "Encounter lost on skill level, prize lost is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return z;
                      }
                      
                   } 
                
             }
             
              if(enc.getE_Type().equalsIgnoreCase("Blockade"))
              {
                   if(ship.getBlockade() == true)
                   {
                      if(ship.getSkillLevel() >= enc.getE_Level())
                      {
                          warChest = warChest + enc.getE_Prize();
                          ship.setState("Resting");
                          c += ship.toString() + enc.toString() + 
                          "Encounter won by skill level, prize won is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return c;
                      }
                      else if(ship.getSkillLevel() < enc.getE_Level())
                      {
                          warChest = warChest - enc.getE_Prize();
                          ship.setState("Sunk");
                          d += ship.toString() + enc.toString() + "\n" + 
                          "Encounter lost on skill level, prize lost is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + getWarChest() + "\n";
                          return d;
                      }
                      
                   } 
                
             }
             
           }
        }
        
       return "No such encounter";
    }

    /** Provides a String representation of an encounter given by 
     * the encounter number
     * @param num the number of the encounter
     * @return returns a String representation of a encounter given by 
     * the encounter number
     **/
    public String getEncounter(int num)
    {
        for (Encounter encounter : encounterList.values()) 
        {
         if (encounter.getE_Number() == num) 
         {
            return encounter.toString();
                
         }
        }
        return "No such encounter"; 
    }
    
    /** Provides a String representation of all encounters 
     * @return returns a String representation of all encounters
     **/
    public String getAllEncounters()
    {
        if (encounterList.isEmpty()) 
        {
          return "No such encounter"; 
        }

        StringBuilder encList = new StringBuilder();
        for (Encounter enc : encounterList.values()) 
        {
            encList.append(enc.toString()).append("\n"); 
        }
        return encList.toString();
    }
    

    //****************** private methods for Task 4 functionality*******************
    //*******************************************************************************
     private void setupShips()
     {
         Man_O_War ship_1 = new Man_O_War("Daniel",3,"L",5,30);
         Man_O_War ship_2 = new Man_O_War("Leol",2,"h",2,30);
         Frigate shipf = new Frigate("Gerald",4,"pete",60,true);
         Frigate shipt = new Frigate("Gemld",4,"pate",40, true);
         Sloop ships = new Sloop("Lewis", "fred", 500, true);
         Sloop shipq = new Sloop("Lawis", "frad", 700, false);
         Man_O_War ship3 = new Man_O_War("Victory", 3, "Alan Aikin", 3, 30);
         ship3.setCost(ship3.getType());
         ship3.setStates();
         ship3.setFights();
         Frigate ship4 = new Frigate("Sophie", 8, "Ben Baggins", 16, true);
         ship4.setCost(ship4.getType());
         ship4.setStates();
         ship4.setBlockades();
         Man_O_War ship5 = new Man_O_War("Endeavour", 4, "Col Cannon", 2, 20);
         ship5.setCost(ship5.getType());
         ship5.setStates();
         ship5.setFights();
         Sloop ship6 = new Sloop("Arrow", "Dan Dare", 150, true);
         ship6.setCost(ship6.getType());
         ship6.setStates();
         ship6.setDoctor("Yes");
         Man_O_War ship7 = new Man_O_War("Belerophon", 8, "Ed Evans", 3, 50);
         ship7.setCost(ship7.getType());
         ship7.setStates();
         ship7.setFights();
         Frigate ship8 = new Frigate("Surprise", 6, "Fred Fox",10, false);
         ship8.setCost(ship8.getType());
         ship8.setStates();
         ship8.setBlockades();
         Frigate ship9 = new Frigate("Jupiter", 7, "Gil Gamage",20, false);
         ship9.setCost(ship9.getType());
         ship9.setStates();
         ship9.setBlockades();
         Sloop ship10 = new Sloop("Paris", "Hal Henry", 200, true);
         ship10.setCost(ship10.getType());
         ship10.setStates();
         ship10.setDoctor("Yes");
         Sloop ship11 = new Sloop("Beast", "Ian Idle", 400, false);
         ship11.setCost(ship11.getType());
         ship11.setStates();
         ship11.setDoctor("No");
         Sloop ship12 = new Sloop("Athena", "John Jones", 100, true);
         ship12.setCost(ship12.getType());
         ship12.setStates();
         ship12.setDoctor("Yes");
         
         
         reserve.put("Daniel",ship_1);
         reserve.put("Leol",ship_2);
         reserve.put("Gerald",shipf);
         reserve.put("Gemld",shipt);
         reserve.put("Lewis",ships);
         reserve.put("Lawis",shipq);
         reserve.put("Victory",ship3);
         reserve.put("Sophie",ship4);
         reserve.put("Endeavour",ship5);
         reserve.put("Arrow",ship6);
         reserve.put("Belerophon",ship7);
         reserve.put("Surprise",ship8);
         reserve.put("Jupiter",ship9);
         reserve.put("Paris",ship10);
         reserve.put("Beast",ship11);
         reserve.put("Athena",ship12);
         
         AllShips.add(ship_1);
         AllShips.add(ship_2);
         AllShips.add(shipf);
         AllShips.add(shipt);
         AllShips.add(ships);
         AllShips.add(shipq);
         AllShips.add(ship3);
         AllShips.add(ship4);
         AllShips.add(ship5);
         AllShips.add(ship6);
         AllShips.add(ship7);
         AllShips.add(ship8);
         AllShips.add(ship9);
         AllShips.add(ship10);
         AllShips.add(ship11);
         AllShips.add(ship12);
       

     }
     
    private void setupEncounters()
    {
        Encounter enc1 = new Encounter("Battle", 3, "Trafalgar", 300);
        Encounter enc2 = new Encounter("Skirmish", 3, "Belle Isle", 120);
        Encounter enc3 = new Encounter("Blockade", 3, "Brest", 150);
        Encounter enc4 = new Encounter("Battle", 9, "St Malo", 200);
        Encounter enc5 = new Encounter("Blockade", 7, "Dieppe", 90.0);
        Encounter enc6 = new Encounter("Skirmish", 8, "Jersey", 45);
        Encounter enc7 = new Encounter("Blockade", 6, "Nantes", 130);
        Encounter enc8 = new Encounter("Battle", 4, "Finisterre", 100);
        Encounter enc9 = new Encounter("Skirmish", 5, "Biscay", 200);
        Encounter enc10 = new Encounter("Battle", 1, "Cadiz", 250);
       
        
        encounterList.put(1,enc1);
        encounterList.put(2,enc2);
        encounterList.put(3,enc3);
        encounterList.put(4,enc4);
        encounterList.put(5,enc5);
        encounterList.put(6,enc6);
        encounterList.put(7,enc7);
        encounterList.put(8,enc8);
        encounterList.put(9,enc9);
        encounterList.put(10,enc10);
        
        
  
    }
    
    private Ship getShip(String shipName)
    {
        for (Ship ships : reserve.values()) 
        {
           if (ships.getShipName().equalsIgnoreCase(shipName))
           {
               return ships;
           }
        }
        System.out.println("Error" + shipName + "not found");
        return null;
    }
    
    private Encounter getEncounters(int encId)
    {
        for (Encounter enc : encounterList.values()) 
        {
           if (enc.getE_Number() == encId)
           {
               return enc;
           }
        }
        System.out.println("Error" + encId + "not found");
        return null;
    }
    
    // Useful private methods to "get" objects from collections/maps

    //*******************************************************************************
    //*******************************************************************************
  
    /************************ Task 3 ************************************************/

    
    //******************************** Task 3.5 **********************************
    /** reads data about encounters from a text file and stores in collection of 
     * encounters.Data in the file is editable
     * @param filename name of the file to be read
     */
    public void readEncounters(String filename)
    { 
      
        
        
    }   
 
    
    // ***************   file write/read  *********************
    /** Writes whole game to the specified file
     * @param fname name of file storing requests
     */
    public void saveGame(String fname)
    {   // uses object serialisation 
           
    }
    
    /** reads all information about the game from the specified file 
     * and returns 
     * @param fname name of file storing the game
     * @return the game (as an SeaBattles object)
     */
    public SeaBattles loadGame(String fname)
    {   // uses object serialisation 
       
        return null;
    } 
    
    
 
}
