package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private SimpleWeightedGraph<String,DefaultWeightedEdge> grafo;
	private Map<String,String> tipi;
	private FoodDao dao;
	
	public Model() {
		this.dao=new FoodDao();
		this.tipi=new HashMap<>();
		for(String s:dao.tipi()) {
			tipi.put(s, s);
		}
	}
	
	public void creaGrafo(int k) {
		
		grafo=new SimpleWeightedGraph<String,DefaultWeightedEdge> (DefaultWeightedEdge.class);
		
		//VERTICI
		Graphs.addAllVertices(grafo, dao.vertici(k));

		
		//ARCHI
		for(Adiacenza a:dao.archi(k)) {
				if (this.grafo.vertexSet().contains(a.getN1()) && 
						this.grafo.vertexSet().contains(a.getN2())) {
			Graphs.addEdge(grafo,a.getN1(),a.getN2(),a.getPeso());
		}
		
		}
		
		
		
	}

	public SimpleWeightedGraph<String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(SimpleWeightedGraph<String, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}
	
	
	public List<Vicino> connessi(String tipo){
		List<Vicino> ritorno=new ArrayList<>();
		for(String s:Graphs.neighborListOf(grafo, tipo)) {
			DefaultWeightedEdge e=grafo.getEdge(tipo, s);
			Vicino v=new Vicino(s,grafo.getEdgeWeight(e));
			ritorno.add(v);
		}
		 return ritorno;
	}
	
	  
	
	
}
