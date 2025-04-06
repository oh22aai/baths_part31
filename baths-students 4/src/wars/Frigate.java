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
    
    
    
    public Frigate(String nme, int skillLevel, String captain, int cannons, boolean pinnace)
    {
        super(nme,ShipState.NULL, skillLevel, captain, "Frigate", true, true,false,0,0, cannons );
        this.pinnace = pinnace;
        
        
    }
    
    public void setCost()
    {
        if(super.getType().equalsIgnoreCase("Frigate"))
        {
           super.setCost(super.getType());
        }
    }
    
    public void setStates()
    {
        super.setState("Reserve");
    }
    
    
   
    public void setBlockades()
    {
        if(pinnace == true)
        {
            super.setBlockade();
        }
        else
        {
            super.noBlockade();
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

