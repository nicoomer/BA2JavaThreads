/* 
    Nom : Omer
    Prénom : Nicolas
    Matricule : 000331695
    Année : BA2 Sciences Informatiques
*/

import java.util.ArrayList;

public class Workstation 
{
	/* Attributs */
	private String _name;
	/* Liste de prédécesseur/successeurs d'un poste de travail */
	private ArrayList<Workstation> _prev;
	private ArrayList<Workstation> _succ; 
	private int _lastArticleId = 0; // id du dernier article traité
	private Task _task; /* Tâche associée au poste de travail */
	
	/* Constructeur */
	public Workstation(String name, String nameOfTask)
	{
		_name = name;
		_succ = new ArrayList<Workstation>();
		_prev = new ArrayList<Workstation>();
		_task = new Treatment(nameOfTask);
	}
	
	/* Getters */
	public String getWorkstationName()
	{
		return _name;
	}
	
	public ArrayList<Workstation> getSuccessors()
	{
		return _succ;
	}
	
	public Task getWorkstationTask()
	{
		return _task;
	}
	
	public ArrayList<Workstation> getPredecessor()
	{
		return _prev;
	}
	
	public int getLastArticleId()
	{
		return _lastArticleId;
	}
	
	/* Setters */
	public void setSuccessors(ArrayList<Workstation> succ)
	{
		_succ = succ;
	}

	public void setPredecessor(ArrayList<Workstation> prev)
	{
		_prev = prev;
	}
	
	public void setLastArticleId(int lastArticleId)
	{
		_lastArticleId = lastArticleId;
	}
	
	/* Ajoute un prédécesseur au poste */
	public void addPredecessor(Workstation workstation)
	{
		_prev.add(workstation);
	}
	
	/* Ajoute un successeur au poste */
	public void addSuccessor(Workstation workstation)
	{
		_succ.add(workstation);
	}
	
	/* Traite l'article sur le poste */
	public void process(Article article)
	{
        _task.process(article);
		this.setLastArticleId(article.getArticleId());
	}
	
	public void printWorkstationData()
	{
		System.out.print("Workstation ");
		System.out.print(this.getWorkstationName());
		System.out.print(" has ");
		for (Workstation workstation : this.getSuccessors()) 
	        System.out.print(workstation.getWorkstationName());
	    System.out.println(" for successors");
	    
	    System.out.print("Last article processed : ");
	    System.out.println(this.getLastArticleId());
	}
}