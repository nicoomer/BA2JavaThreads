/* 
    Nom : Omer
    Prénom : Nicolas
    Matricule : 000331695
    Année : BA2 Sciences Informatiques
*/

import java.util.ArrayList;
import java.util.HashMap;

public class Graph<Node>
{
	/* Attributs */
	private ArrayList<Node> _stationsList = new ArrayList<Node>();
    private HashMap<Node, ArrayList<Node>> _successors = new HashMap<Node, ArrayList<Node>>();
    
    /* Constructeur */
	public Graph(ArrayList<Node> stationsList, HashMap<Node, ArrayList<Node>> successors)
	{
		_stationsList = stationsList;
		_successors = successors;
	}
	
	/* Getters */
	public ArrayList<Node> getStationsList()
	{
		return _stationsList;
	}
	
	public HashMap<Node, ArrayList<Node>> getSuccessorsMap()
	{
		return _successors;
	}
	
	public ArrayList<Node> getSuccessors(Node ws)
	{
		// Retourne la liste des noeuds successeurs
		return _successors.get(ws);
	}
	
	
	/* Retourne la liste des noeuds sans prédécesseurs */
	public ArrayList<Node> getInitiators()
	{
		// Au début, la liste contient toutes les stations du graphe
		ArrayList<Node> initiators = new ArrayList<Node>();
		for(Node node : this.getStationsList()) 
		{
			initiators.add(node);
		}
		
		short i = 0;
		while (i < this.getStationsList().size())
		{
			Node currentNode = this.getStationsList().get(i);
			ArrayList<Node> succ = getSuccessors(currentNode);
			
			// Si une station apparaît dans une liste de successeurs
			for (Node node : succ) 
			{
			    if (initiators.contains(node))
			    {
			    	// Cette station a un prédécesseur, on l'enlève
				    initiators.remove(node);
			    }
			}
			i++;
		}
		
		return initiators;
	}
	
	public void prettyPrint()
	{
		System.out.print("List of stations : \n");
		for (Node target : getStationsList()) 
	        System.out.println(target);
	    
	    System.out.print("List of relations : ");
	    System.out.println(getSuccessorsMap());
	}
}