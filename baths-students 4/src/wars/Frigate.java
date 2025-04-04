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
    
    
    
    public Frigate(String nme, ShipState state, int skillLevel, String captain, int cannons, boolean pinnace)
    {
        super(nme,state, skillLevel, captain, "Frigate", true, true,false,0,0, cannons );
        this.pinnace = pinnace;
        
        
    }
    
    public void setCost()
    {
        super.setCost("Frigate");
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

