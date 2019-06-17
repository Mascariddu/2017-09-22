package it.polito.tdp.formulaone.model;

public class Evento implements Comparable<Evento>{

	public enum Tipo{
		
		INIZIO_GIRO,
		PIT_STOP,
		FINE_GIRO
	}
	
	private Tipo tipo;
	private int lap;
	private int tempo;
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
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public Evento(Tipo tipo, int lap, int tempo) {
		super();
		this.tipo = tipo;
		this.lap = lap;
		this.tempo = tempo;
	}
	@Override
	public int compareTo(Evento arg0) {
		// TODO Auto-generated method stub
		return this.tempo - arg0.tempo;
	}
	
	
}
