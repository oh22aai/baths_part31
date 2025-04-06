/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

/**
 *
 * @author Administrator
 */
public class Encounter 
{
    private static int counter = 0;
    private int e_Number;
    private String e_Type;
    private int e_Level;
    private String e_Location;
    private double e_Prize;
    
    public Encounter(String type, int skillLevel, String location, double prize)
    {
        e_Number = ++counter;
        e_Type = type;
        e_Level = skillLevel;
        e_Location = location;
        e_Prize = prize;
    }
    
    public int getE_Number()
    {
        return e_Number;   
    }
    
    public String getE_Type()
    {
        return e_Type;
    }
    
    public int getE_Level()
    {
        return e_Level;
    }
    
    public String getE_Location()
    {
        return e_Location;
    }
    
    public double getE_Prize()
    {
        return e_Prize;
    }
    
    public String toString()
    {
        StringBuilder report = new StringBuilder();
        report.append("\n").append("Encounter: ").append( e_Number ).append("\n");
        report.append("Type of encounter: ").append( e_Type).append("\n");
        report.append("Skill Level: ").append(e_Level).append("\n");
        report.append("Location: ").append(e_Location).append("\n");
        report.append("Prize Money: ").append(e_Prize).append("\n");
        return report.toString();
    }
    
    
}