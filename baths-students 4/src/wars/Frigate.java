/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

/**
 *
 * @author Administrator
 */
public class Frigate extends Ship 
{
    private boolean pinnace;
    
    
    
    public Frigate(String nme, int skillLevel, String captain, int cannons)
    {
        super(nme, skillLevel, captain, "Frigate", true, true, true,0,0, cannons );
        pinnace = false;
        
        
    }
    
    public void setCost()
    {
        super.setCost("Frigate");
    }
    
    
    public void setPinnace(String pin)
    {
       if (pin.equalsIgnoreCase("Yes"))
       {
          pinnace = true;
       } 
       else 
       {
          pinnace = false; 
       }
    }
    
    @Override
    public String toString()
    {
      String report = super.toString();
      report += "Pinnace: " + pinnace + "\n";
      return report;
    }
}