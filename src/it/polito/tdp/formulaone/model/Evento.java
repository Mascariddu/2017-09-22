package it.polito.tdp.formulaone.model;

public class Evento implements Comparable<Evento>{

	public enum Tipo{
		
		INIZIO_GIRO,
		FINE_GIRO
	}
	
	private Tipo tipo;
	private int lap;
	private double tempo;
	private Driver driver;
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public int getLap() {
		return lap;
	}
	public void setLap(int lap) {
		this.lap = lap;
	}
	public double getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public Evento(Tipo tipo, int lap, double tempo, Driver driver) {
		super();
		this.tipo = tipo;
		this.lap = lap;
		this.tempo = tempo;
		this.driver = driver;
	}
	@Override
	public int compareTo(Evento arg0) {
		// TODO Auto-generated method stub
		return (int)(this.tempo - arg0.tempo);
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	
}
