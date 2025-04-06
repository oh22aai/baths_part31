/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

/**
 *
 * @author Administrator
 */
public class Man_O_War extends Ship
{
    private int marines;
    
    
    public Man_O_War(String nme,int skillLevel, String captain, int deck, int marine)
    {
        super( nme,ShipState.NULL,skillLevel,captain,"Man-O-War",false, false,false, 0, deck,0);
        marines = marine;
    }
    
    public void setFights()
    {
        if(super.getCost() > 0)
        {
            super.setBattle();
            super.setBlockade();
            
        }
        else
        {
            super.noBattle();
            super.noBlockade();
        }
    }
    
    
    public void setCost()
    {
        if(super.getType().equalsIgnoreCase("Man_O_War"))
        {
           super.setCost(super.getType());
           super.setState("Reserve");
        }
    }
    
    public void setStates()
    {
        super.setState("Reserve");
    }
    
    
    
    
    @Override
    public String toString()
    {
      String report = super.toString();
      report += "Number of marines: " + marines + "\n";
      return report;
    }
}

