package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	FormulaOneDAO dao;
	SimpleWeightedGraph<Race, DefaultWeightedEdge> grafo;
	List<Race> races;
	
	public Model() {
		
		dao = new FormulaOneDAO();
		
	}

	public List<Season> getSeasons() {
		// TODO Auto-generated method stub
		return dao.getAllSeasons();
	}
	
	public void creaGrafo(Season anno) {
		grafo = new SimpleWeightedGraph<Race, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		races = new ArrayList<Race>(dao.getRaces(anno));
		Graphs.addAllVertices(grafo, races);
		
		for(Race race : races) {
			
			for(Race race2 : races) {
				
				if(!race.equals(race2)) {
					
					int peso = dao.getPeso(race.getRaceId(),race2.getRaceId());
					
					Graphs.addEdge(grafo, race, race2, peso);
					System.out.println("aggiunto!");
					
				}
			}
		}
		
		System.out.println("#vertici: "+grafo.vertexSet().size()+"\n");
		System.out.println("#archi: "+grafo.edgeSet().size());
	}

	public List<String> getMigliore() {
		// TODO Auto-generated method stub
		List<String> list =  new ArrayList<String>();
		DefaultWeightedEdge best = new DefaultWeightedEdge();
		
		for(DefaultWeightedEdge edge : grafo.edgeSet()) {
			
			System.out.println("Gara 1: "+grafo.getEdgeSource(edge).getName()+"/ Gara 2: "+grafo.getEdgeTarget(edge).getName()+" /con peso: "+grafo.getEdgeWeight(edge));
			if(grafo.getEdgeWeight(edge) > grafo.getEdgeWeight(best))
				best = edge;
			
		}
		
		for(DefaultWeightedEdge edge : grafo.edgeSet()) {
			
			if(grafo.getEdgeWeight(edge) == grafo.getEdgeWeight(best)) {
				
				Race source = grafo.getEdgeSource(edge);
				Race target = grafo.getEdgeTarget(edge);
				list.add("Gara 1: "+source.getName()+"/ Gara 2: "+target.getName()+" /con peso: "+grafo.getEdgeWeight(edge));
			}
		}
		
		return list;
	}

	public List<Race> getRaces(Season anno) {
		// TODO Auto-generated method stub
		return dao.getRaces(anno);
	}

	public void simula(Race race,double p, double t) {
		// TODO Auto-generated method stub
		Simulatore simulatore = new Simulatore();
		
		simulatore.init(race,p,t);
		
		simulatore.run();
		
	}

}
