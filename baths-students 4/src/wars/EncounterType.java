package wars;

import java.io.Serializable;
/**
 * Enumeration class EncounterManager - write a description of the enum class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
import java.io.*;
/**
 * Enumeration class EncounterType - write a description of the enum class here
 * 
 * @author A.Marczyk
 * @version 12/02/2025
 */
public enum EncounterType implements Serializable
{
    BLOCKADE(" Blockade"), BATTLE(" Battle"), SKIRMISH (" Skirmish"), INVALID (" Invalid");
    private String type;
    
    private EncounterType(String ty)
    {
        type = ty;
    }
    
    public String toString()
    {
        return type;
    }
}