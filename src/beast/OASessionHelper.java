/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beast;

/**
 *
 * @author daniel.jones
 */
import com.fidessa.inf.oa.OAException;
import com.fidessa.inf.oa.OpenAccess;

public class OASessionHelper {
    
    public OASessionHelper() {
        
        
    }
    public static boolean login(String username, String password, String systemGroupId, String[] logonGatewayAddresses, int logonGatewayId) {
        // hardcoded username and password
        String application = null;
        String[] services;
        try
	{
	    OpenAccess.initialise(logonGatewayId, logonGatewayAddresses, systemGroupId, application);
	    OpenAccess.logon(username, password, -1);
            services = OpenAccess.getServices();
            
	    for (String s : services)
	    {
		System.out.println(s);
	    }            
            return true;
        } catch (OAException e)
	{            
            e.printStackTrace();
            return false;
	}
        
    }

}
