/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

/**
 *
 * @author Administrator
 */
public class Sloop extends Ship 
{
    private boolean doctor;
    
    
    public Sloop(String nme, String captain, double cost, boolean doctor)
    {
        super (nme,ShipState.NULL, 5, captain,"Sloop", true, true, false, cost,0,0);
        this.doctor = doctor;
    }
    
    public void setCost()
    {
        if(super.getType().equalsIgnoreCase("Sloop"))
        {
           super.setCost(super.getType());
        }
    }
    
    public void setStates()
    {
        super.setState("Reserve");
    }
       
    
    public void setDoctor(String doc)
    {
       if (doc.equalsIgnoreCase("Yes"))
       {
          doctor = true;
       } 
       else 
       {
          doctor = false; 
       }
    }
    
    
    @Override
    public String toString()
    {
        String report = super.toString();
        report += "Doctor: " + doctor + "\n";
        return report;
    }
    
}