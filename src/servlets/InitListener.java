package servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import services.ConnectionManager;

public class InitListener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent arg0){
		ConnectionManager.close();
	}
	public void contextInitialized(ServletContextEvent event)  {
    	ConnectionManager.open();
		System.out.println("Connected to database");
    }

}
