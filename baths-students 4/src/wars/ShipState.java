package wars;

import java.io.Serializable;
/**
 * Write a description of class ShipState here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
/**
 * Enumeration class UnitState - write a description of the enum class here
 * 
 * @author A.Marczyk
 * @version 12/02/2025
 */
public enum ShipState implements Serializable
{
    RESERVE(" In reserve fleet"), ACTIVE(" Active in squadron"), RESTING(" Resting"), SUNK (" Sunk"), NULL("Null");
    private String state;
    
    private ShipState(String st)
    {
        state = st;
    }
    
    public String toString()
    {
        return state;
    }
}