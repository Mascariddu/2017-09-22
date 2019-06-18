package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.formulaone.db.FormulaOneDAO;
import it.polito.tdp.formulaone.model.Evento.Tipo;

public class Simulatore {
	
	FormulaOneDAO dao;
	HashMap<Integer, Driver> classifica;
	List<Driver> drivers;
	HashMap<Driver, ArrayList<Float>> giri;
	Race race;
	HashMap<Integer, Integer> arrivi;
	PriorityQueue<Evento> queue;
	int lapAttuale;
	double probability;
	double pitTime;
	int Driverstotali;
	HashMap<Integer, Driver> idMap;
	int giro;
	
	public Simulatore() {
		
		dao = new FormulaOneDAO();
		queue = new PriorityQueue<Evento>();
		arrivi = new HashMap<Integer, Integer>();
		
	}

	public void init(Race race, double p, double t,HashMap<Integer,Driver> idMap) {
		// TODO Auto-generated method stub
		classifica = new HashMap<Integer, Driver>();
		giri = new HashMap<Driver, ArrayList<Float>>();
		this.idMap = new HashMap<Integer, Driver>(idMap);
		this.race = race;
		drivers = new ArrayList<Driver>(dao.getDrivers(race,this.idMap));
		this.probability = p;
		this.pitTime = t;
		Driverstotali = 0;
		arrivi.clear();
		giro = dao.getLaps(race.getRaceId());
		
		
		for(Driver driver : drivers) {
			
			ArrayList<Float> laps = new ArrayList<Float>(dao.getGiro(race.getRaceId(),driver.getDriverId()));
			System.out.println(laps.size());
			giri.put(driver, laps);
			Driverstotali++;
			System.out.println("driver "+Driverstotali);
		
			queue.add(new Evento(Tipo.INIZIO_GIRO,1,0,driver));
	
			}
		
		for(int i = 1; i <= giro; i++)
			arrivi.put(i, 0);
	}

	public void run() {
		// TODO Auto-generated method stub
		Evento evento;
		
		while((evento = queue.poll()) != null) {
			
			switch (evento.getTipo()) {
			
			case INIZIO_GIRO:
				
					
					Random random = new Random(1);
					
					lapAttuale = evento.getLap();
					System.out.println("partito nel giro "+lapAttuale+": "+evento.getDriver().getSurname());
					
					if(random.nextDouble() < this.probability) {
						
						if(giri.get(evento.getDriver()).size() >= lapAttuale ) {
						double milliseconds = giri.get(evento.getDriver()).get(lapAttuale-1);
						queue.add(new Evento(Tipo.FINE_GIRO,lapAttuale,(evento.getTempo() + this.pitTime + milliseconds),evento.getDriver()));
						System.out.println("Schedulo fine giro "+lapAttuale+" di: "+evento.getDriver().getSurname());
						}
						
					} else {
						
						if(giri.get(evento.getDriver()).size() >= lapAttuale ) {
						System.out.println("Schedulo fine giro "+lapAttuale+" di: "+evento.getDriver().getSurname());
						double milliseconds = giri.get(evento.getDriver()).get(lapAttuale-1);
						queue.add(new Evento(Tipo.FINE_GIRO,lapAttuale,(evento.getTempo() + milliseconds),evento.getDriver()));
						}
					}
					
				break;
				
			case FINE_GIRO:
				
				lapAttuale = evento.getLap();
				System.out.println("Arrivato nel giro "+lapAttuale+" "+evento.getDriver().getSurname());
				
				if(arrivi.get(lapAttuale) == 0) {
					classifica.put(lapAttuale, evento.getDriver());
					System.out.println("Nuovo primo : giro "+lapAttuale+" "+evento.getDriver().getForename()+" "+evento.getDriver().getSurname());
				}
				
				arrivi.replace(lapAttuale, arrivi.get(lapAttuale)+1);
				
				queue.add(new Evento(Tipo.INIZIO_GIRO,lapAttuale+1,evento.getTempo(),evento.getDriver()));
				System.out.println("Schedulo inizio giro "+(lapAttuale+1)+" di: "+evento.getDriver().getSurname());
				
				break;
			}
			
		}
		
	}

	public HashMap<Integer, Driver> getClassifica() {
		// TODO Auto-generated method stub
		return this.classifica;
	}

}
