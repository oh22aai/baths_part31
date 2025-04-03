package wars; 


/**
 * Details of your team
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Teamwork
{
    private String[] details = new String[12];
    
    public Teamwork()
    {   // in each line replace the contents of the String 
        // with the details of your team member
        // Please list the member details alphabetically by surname 
        // i.e. the surname of member1 should come alphabetically 
        // before the surname of member 2...etc
        details[0] = " CS41";
        
        details[1] = "Hector-Fowobaje";
        details[2] = "Okikioluwa";
        details[3] = "21092594";

        details[4] = "Jayaraman";
        details[5] = "Gunalseen";
        details[6] = "22088627";

        details[7] = "Onyekachi";
        details[8] = "Okanu";
        details[9] = "22001639";


        details[10] = "Wagura";
        details[11] = "Daniel";
        details[12] = "22104722";

	
	   // only if applicable
        details[13] = "surname of member5";
        details[14] = "first name of member5";
        details[15] = "SRN of member5";


    }
    
    public String[] getTeamDetails()
    {
        return details;
    }
    
    public void displayDetails()
    {
        for(String temp:details)
        {
            System.out.println(temp.toString());
        }
    }
}
        
