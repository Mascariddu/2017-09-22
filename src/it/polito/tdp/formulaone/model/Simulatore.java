package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.formulaone.db.FormulaOneDAO;
import it.polito.tdp.formulaone.model.Evento.Tipo;

public class Simulatore {
	
	FormulaOneDAO dao;
	HashMap<Driver, Integer> punti;
	List<Driver> drivers;
	Race race;
	PriorityQueue<Evento> queue;
	
	public Simulatore() {
		
		dao = new FormulaOneDAO();
		queue = new PriorityQueue<Evento>();
		
	}

	public void init(Race race, double p, double t) {
		// TODO Auto-generated method stub
		punti = new HashMap<Driver, Integer>();
		this.race = race;
		drivers = new ArrayList<Driver>(dao.getDrivers(race));
		
		for(Driver driver : drivers) {
			
			for(Integer integer : dao.getGiro(driver.getDriverId(),race.getRaceId())) {
				
				queue.add(new Evento(Tipo.INIZIO_GIRO,0,0));
				
			}
		}
		
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

}
