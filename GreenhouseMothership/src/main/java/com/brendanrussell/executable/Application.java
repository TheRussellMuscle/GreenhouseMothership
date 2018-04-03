package com.brendanrussell.executable;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.brendanrussell.greenhousemothership.MotherShip;

@ComponentScan(basePackages = { "controllers" }) // Tells spring to find controllers there
@Component
public class Application {

	private static Logger logger = Logger.getLogger(Application.class);
	private static final int SECONDS = 900;

	@EventListener(ContextRefreshedEvent.class)
	public void startMothership() {
		// create MotherShip object
		MotherShip mothership = new MotherShip();

		// Run
		boolean running = true;
		while (running) {
			try {
				mothership.run();
				Thread.sleep(SECONDS * 1000);
				System.out.println("RUN");
			} catch (Exception e) {
				logger.error("Exception caught in message loop processing", e);
			}
		}
	}

}