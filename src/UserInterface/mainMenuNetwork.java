package UserInterface;

import java.util.ArrayList;

public class mainMenuNetwork {

	boolean doesHostExist(String hostName) {
		ArrayList<String> hostList = new ArrayList<String>();
		hostList.add("192.168.0.1:2020");
		//Get list of hosts
		//if hostName in list then return true else false
		if (hostList.contains(hostName))
			return true;
		else
			return false;
	}
	
}
