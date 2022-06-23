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
	private List<Vicino> migliore;
	
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
	
	public List<Vicino> cercaCammino(int n,String s){
		this.migliore=new ArrayList<>();
		
		List<Vicino> parziale=new ArrayList<>();
		
		Vicino v=new Vicino(s,0.0);
		parziale.add(v);
		
		this.cerca(parziale,n);
		return migliore;
		
	}
	
	
	

	private void cerca(List<Vicino> parziale, int n) {
		if(parziale.size()==n) {
			if(this.calcolaPeso(parziale)>this.calcolaPeso(migliore)) {
				migliore=new ArrayList<>(parziale);
				return;
			}
		}
		else {
			for(Vicino v:this.calcolaPossibili(parziale, parziale.get(parziale.size()-1))) {
				parziale.add(v);
				this.cerca(parziale, n);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
	
	
	
	public double calcolaPeso(List<Vicino> parziale) {
		double peso=0.0;
		for(Vicino v: parziale) {
			peso+=v.getPeso();
		}
		return peso;
	}
	
	private List<Vicino> calcolaPossibili(List<Vicino> parziale,Vicino v){
		List<Vicino> ritorno=this.connessi(v.getN());
		List<Vicino> possibili=new ArrayList<>();
		for(Vicino vi:ritorno) {
			int t=0;
			for(Vicino vv:parziale) {
				if(vi.getN().compareTo(vv.getN())==0)
					t=1;
			}
		    if(t==0)
		    	possibili.add(vi);
		}
		return possibili;
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
