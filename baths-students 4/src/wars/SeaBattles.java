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
       warChest = 1000; 
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
    {   //assumes reserves is a Hashmap
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
        StringBuilder squadronList = new StringBuilder();
        for(Ship ship : squadron.values())
        {
            if(!squadron.isEmpty())
            {
                squadronList.append(ship.toString()).append("\n");
            }
            else
            {
                return "No ships";
            }
            
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
          }
          else
          {
              return "No sunken ship";
          }
       }
       return s.toString();
    }
    
    /**Returns a String representation of the all ships in the game
     * including their status
     * @return a String representation of the ships in the game
     **/
    public String getAllShips()
    {
        StringBuilder shipsList = new StringBuilder();
        for(Ship ship : AllShips)
        {
            if(!AllShips.isEmpty())
            {
                shipsList.append(ship.toString()).append("\n");
            }
            else
            {
                return "No ships";
            }
            
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
                 s += ship.toString();
            }
            else
            {
                return "No such ship";
            }
        }
        return s;
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
        String s = "";
        for(Map.Entry<String,Ship > entry: reserve.entrySet())
        {
            Ship ship = entry.getValue();
            if (ship.getShipName().equalsIgnoreCase(nme))
            {
                if(ship.getShipState() == ShipState.RESERVE)
                {
                if(ship.getCost() < warChest)
                {
                    warChest = warChest - ship.getCost();
                    String shipID = entry.getKey(); // Get the key (ID) for removal
                    reserve.remove(shipID);      // Remove from reserve
                    squadron.put(shipID, ship);  // Add to squadron

                    ship.setState("Active");  // Optional: change state to ACTIVE
                    s += "- " + ship.getShipName() + " commissioned to squadron";
                    return s;
                }
                else if(ship.getCost() > warChest)
                {
                    s += "- Not enough funds to commission " + ship.getShipName();
                    return s;
                }
                }
              
                
            }
            
        }
        return "- Ship not found";
    }
        
    /** Re§turns true if the ship with the name is in the admiral's squadron, false otherwise.
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
            else
            {
                return false;
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
                  String shipID = entry.getKey(); // Get the key (ID) for removal
                  squadron.remove(shipID);      // Remove from squadron
                  reserve.put(shipID, ship);// Add to reserve
                  warChest = warChest + ship.getCost()/2;
                
                  ship.setState("Reserve");  // Optional: change state to reserve
                  return true;
                }
                else
                {
                    return false;
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
                if (ship.getShipState() != ShipState.RESERVE && ship.getShipState() != ShipState.SUNK && ship.getShipState() != ShipState.ACTIVE)
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
         for(Integer encounterId : encounterList.keySet())
         {
             Encounter encounter = encounterList.get(encounterId);
             if(encounter.getE_Number() == num)
             {
                 return true;
             }
             else
             {
                return false; 
             }
         }
         return false;
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
       if (isEncounter(encNo))
        {
           Encounter enc = getEncounters(encNo);
           for (Ship ship : squadron.values())
           {
              if(enc.getE_Type().equalsIgnoreCase("Battle"))
              {
                  if(squadron.isEmpty())
                  {
                      warChest -= enc.getE_Prize();
                      s += ship.toString() + enc.toString() + 
                      "Encounter lost as no suitable ship available, prize lost is: " + enc.getE_Prize() + "\n"
                      + "State of warchest: " + warChest + "\n";
                      return s;
                  }
                  else if(squadron.isEmpty() && warChest <= 0)
                  {
                      s += ship.toString() + enc.toString() + 
                      "Encounter is lost and you lose your job"+ "\n"
                      + "State of warchest: " + warChest + "\n";
                      return s;
                  }
                  else
                  {
                   if(ship.getBattle() == true)
                   {
                      if(ship.getSkillLevel() >= enc.getE_Level())
                      {
                          warChest += enc.getE_Prize();
                          ship.setState("Resting");
                          s += ship.toString() + enc.toString() + 
                          "Encounter won by skill level, prize won is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return s;
                      }
                      else if(ship.getSkillLevel() < enc.getE_Level())
                      {
                          ship.setState("Sunk");
                          warChest -= enc.getE_Prize();
                          s += ship.toString() + enc.toString() + 
                          "Encounter lost on skill level, prize lost is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return s;
                      }
                      
                   } 
                }
             }
             
              if(enc.getE_Type().equalsIgnoreCase("Skirmish"))
              {
                  if(squadron.isEmpty())
                  {
                      warChest -= enc.getE_Prize();
                      s += ship.toString() + enc.toString() + 
                      "Encounter lost as no suitable ship available, prize lost is: " + enc.getE_Prize() + "\n"
                      + "State of warchest: " + warChest + "\n";
                      return s;
                  }
                  else if(squadron.isEmpty() && warChest <= 0)
                  {
                      s += ship.toString() + enc.toString() + 
                      "Encounter is lost and you lose your job"+ "\n"
                      + "State of warchest: " + warChest + "\n";
                      return s;
                  }
                  else
                  {
                   if(ship.getSkirmish() == true)
                   {
                      if(ship.getSkillLevel() >= enc.getE_Level())
                      {
                          warChest += enc.getE_Prize();
                          ship.setState("Resting");
                          s += ship.toString() + enc.toString() + 
                          "Encounter won by skill level, prize won is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return s;
                      }
                      else if(ship.getSkillLevel() < enc.getE_Level())
                      {
                          ship.setState("Sunk");
                          warChest -= enc.getE_Prize();
                          s += ship.toString() + enc.toString() + 
                          "Encounter lost on skill level, prize lost is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return s;
                      }
                      
                   } 
                }
             }
             
              if(enc.getE_Type().equalsIgnoreCase("Blockade"))
              {
                  if(squadron.isEmpty())
                  {
                      warChest -= enc.getE_Prize();
                      s += ship.toString() + enc.toString() + 
                      "Encounter lost as no suitable ship available, prize lost is: " + enc.getE_Prize() + "\n"
                      + "State of warchest: " + warChest + "\n";
                      return s;
                  }
                  else if(squadron.isEmpty() && warChest <= 0)
                  {
                      s += ship.toString() + enc.toString() + 
                      "Encounter is lost and you lose your job"+ "\n"
                      + "State of warchest: " + warChest + "\n";
                      return s;
                  }
                  else
                  {
                   if(ship.getBlockade() == true)
                   {
                      if(ship.getSkillLevel() >= enc.getE_Level())
                      {
                          warChest += enc.getE_Prize();
                          ship.setState("Resting");
                          s += ship.toString() + enc.toString() + 
                          "Encounter won by skill level, prize won is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return s;
                      }
                      else if(ship.getSkillLevel() < enc.getE_Level())
                      {
                          ship.setState("Sunk");
                          warChest -= enc.getE_Prize();
                          s += ship.toString() + enc.toString() + 
                          "Encounter lost on skill level, prize lost is: " + enc.getE_Prize() + "\n"
                          + "State of warchest: " + warChest + "\n";
                          return s;
                      }
                      
                   } 
                }
             }
             
           }
        }
        else
        {
            return "No such encounter";
        }
        return s;
    }

    /** Provides a String representation of an encounter given by 
     * the encounter number
     * @param num the number of the encounter
     * @return returns a String representation of a encounter given by 
     * the encounter number
     **/
    public String getEncounter(int num)
    {
        for(Integer encounterId : encounterList.keySet())
        {
             Encounter encounter = encounterList.get(encounterId);
             if(encounter.getE_Number() == num)
             {
                 return encounter.toString();
             }
             else
             {
                return "No such encounter"; 
             }
        }
        return "No such encounter";
    }
    
    /** Provides a String representation of all encounters 
     * @return returns a String representation of all encounters
     **/
    public String getAllEncounters()
    {
        StringBuilder encList = new StringBuilder();
        for(Encounter enc : encounterList.values())
        {
            if(!encounterList.isEmpty())
            {
                encList.append(enc.toString()).append("\n");
            }
            else
            {
                encList.append("No ships");
            }
            
        }
        return encList.toString();
    }
    

    //****************** private methods for Task 4 functionality*******************
    //*******************************************************************************
     private void setupShips()
     {
       Man_O_War ship_1 = new Man_O_War("Daniel",ShipState.RESERVE,3,"L",5,30);
         Man_O_War ship_2 = new Man_O_War("Leol",ShipState.ACTIVE,2,"h",2,30);
         Frigate shipf = new Frigate("Gerald",ShipState.RESERVE,4,"pete",60,true);
         Frigate shipt = new Frigate("Gemld",ShipState.SUNK,4,"pate",40, true);
         Sloop ships = new Sloop("Lewis",ShipState.RESTING, "fred", 500);
         Sloop shipq = new Sloop("Lawis",ShipState.ACTIVE, "frad", 700);
         
         reserve.put("Daniel",ship_1);
         reserve.put("Leol",ship_2);
         reserve.put("Gerald",shipf);
         reserve.put("Gemld",shipt);
         reserve.put("Lewis",ships);
         reserve.put("Lawis",shipq);


     }
     
    private void setupEncounters()
    {
        
  
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



