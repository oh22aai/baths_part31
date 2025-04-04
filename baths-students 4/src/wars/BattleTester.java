/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

/**
 *
 * @author Administrator
 */
public class BattleTester
{
   public static void main (String[]args)
   {
       SeaBattles sea = new SeaBattles("Napoleon");
       sea.commissionShip("Daniel");
       sea.commissionShip("Leol");
       System.out.println(sea.getSquadron());
   }
}
