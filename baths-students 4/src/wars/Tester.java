/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

/**
 *
 * @author Administrator
 */
public class Tester
{
    public static void main (String[]args)
    {
        Man_O_War ship = new Man_O_War("Daniel",ShipState.RESERVE,3,"L",5,30);
        ship.setCost();
        String x = ship.toString();
        System.out.println(ship.getShipState());
        System.out.println(x);
        
        Man_O_War shipw = new Man_O_War("Leol",ShipState.SUNK,2,"h",2,30);
        shipw.setCost();
        String w = shipw.toString();
        System.out.println(w);
        
        Sloop ships = new Sloop("Lewis",ShipState.RESTING, "fred", 500);
        ships.setDoctor("GOO");
        String y = ships.toString();
        System.out.println(y);
        
        Sloop shipq = new Sloop("Lawis",ShipState.ACTIVE, "frad", 700);
        shipq.setDoctor("YES");
        String p = shipq.toString();
        System.out.println(p);
        
        Frigate shipf = new Frigate("Gerald",ShipState.RESERVE,4,"pete",60,true);
        shipf.setBlockades();
        shipf.setCost();
        String f = shipf.toString();
        System.out.println(f);
        
        Frigate shipt = new Frigate("Gemld",ShipState.SUNK,4,"pate",40, false);
        shipt.setBlockades();
        shipt.setCost();
        String t = shipt.toString();
        System.out.println(t);
        
        Encounter encounter_1 = new Encounter("Blockade", 5, "Riverdale", 100);
        String s = encounter_1.toString();
        System.out.println(s);
        
        Encounter encounter_2 = new Encounter("Battle", 6, "Nairobi", 200);
        String l = encounter_2.toString();
        System.out.println(l);
        
        
        
    }
    
    

}
