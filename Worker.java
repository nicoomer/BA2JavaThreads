/* 
    Nom : Omer
    Pr�nom : Nicolas
    Matricule : 000331695
    Ann�e : BA2 Sciences Informatiques
*/

import java.util.ArrayList;

public class Worker extends Thread
{
	/* Attributs */
	private String _name;
    private ArrayList<Article> _articlesToBuild; /* Liste des articles � construire */
    private Graph<Workstation> _precedenceGraph;
    private ArrayList<String> _log; /* Journal de bord */
    
    /* Constructeur */
    Worker(String name, ArrayList<Article> articlesToBuild, Graph<Workstation> precGraph)
    {
    	_name = name;
    	_log = new ArrayList<String>(); // Journal de bord vide pour commencer
    	_articlesToBuild = articlesToBuild;
    	_precedenceGraph = precGraph;	
    }
    
    /* Getters */
    public String getWorkerName()
	{
		return _name;
	}
    
    public ArrayList<String> getWorkerLog()
	{
		return _log;
	}
    
    public ArrayList<Article> getArticlesToBuild()
	{
		return _articlesToBuild;
	}
    
    public Graph<Workstation> getPrecedenceGraph()
    {
    	return _precedenceGraph;
    }
    
    /* Setter */
    public void setArticlesToBuild(ArrayList<Article> articlesToBuild)
	{
		_articlesToBuild = articlesToBuild;
	}
    
    /* Ajoute un article � construire � la liste d'un artisan */
    public void addArticleToBuild(Article article)
    {
    	_articlesToBuild.add(article);
    }
	
	/* Ajoute une entr�e dans le journal de bord d'un artisan */
	public void addEntryToLog(String entry)
	{
		_log.add(entry);
	}
	
	/* D�termine si un 'article' peut �tre trait� sur une 'workstation' */
	public synchronized boolean isReady(Workstation workstation, Article article)
	{
		boolean isReady = false;
		ArrayList<Workstation> predecessors = workstation.getPredecessor();
		
		if (predecessors.isEmpty())
		{
			// Un poste de travail construit Oi seulement si Oi-1 y est pass�
			if (workstation.getLastArticleId() == article.getArticleId() - 1)
			{
				isReady = true;
			}
		}
		
		else
		{
			short count = 0;
            // On v�rifie si l'article est pass� par tous les pr�d�cesseurs de 'workstation'
			for (Workstation pred : predecessors)
			{
				if (pred.getLastArticleId() >= article.getArticleId())
				{
					count++;
				}
			}
			
			boolean condition = workstation.getLastArticleId() == article.getArticleId() - 1;
			if ((count == predecessors.size()) && (condition))
			{
				isReady = true;
			}
		}
		
		return isReady;
	}
	
	/* D�termine si un 'article' a d�j� �t� trait� sur une 'workstation' */
	public boolean processed(Workstation workstation, Article article)
	{
		return (article.getArticleId() <= workstation.getLastArticleId());
	}
	
	/* L'artisan construit ses articles */
	public void run()
	{
		for (Article currentArticle : this.getArticlesToBuild())
        {
        	// Les premiers postes � consid�rer sont ceux sans pr�d�cesseurs
			ArrayList<Workstation> initiators = this.getPrecedenceGraph().getInitiators();    
			
			// Tant que l'article n'est pas pass� par tous les postes
			while (! initiators.isEmpty()) 
			{
				// On essaye de prendre un poste au hasard
				int maxTries = 0;
				int number = Main.randomGenerator(0, initiators.size());		
				Workstation currentStation = initiators.get(number);
				
				while ((maxTries < 3) && !(this.isReady(currentStation, currentArticle)))
				{
					// Si c'est le dernier essai, on prend d'office le premier poste
					if (maxTries == 2)
						number = 0;
					
					else
						number = Main.randomGenerator(0, initiators.size());
					
					currentStation = initiators.get(number);
					maxTries += 1;
				}
				
				// Une fois qu'on a choisi un poste
				synchronized (currentStation)
				{
					// Si l'article n'a pas d�j� �t� trait� sur ce poste
					if (! this.processed(currentStation, currentArticle))
					{
						boolean skip = false;
						
					    while (! this.isReady(currentStation, currentArticle) && ! skip)
					    {
						    try 
		                    {
						    	if (! this.processed(currentStation, currentArticle))
								{
						    	    System.out.println(this.getWorkerName() 
						    	    + " is waiting at " + currentStation.getWorkstationName() 
						    	    + " with " + currentArticle.getArticleName());
						    	    currentStation.wait();
								}
						    	
						// L'article peut avoir �t� trait� alors qu'on attendait sur le poste
						    	else 
						    	{
						    		skip = true;
						    	}
		                    }
		                    catch (InterruptedException e) {}
					    }
					    if (! skip) // On traite l'article avec la t�che associ�e
					    {
					        currentStation.process(currentArticle);
					        String name = currentStation.getWorkstationName();
					        String entry = " processed at " + name;
			                this.addEntryToLog(currentArticle.getArticleName() + entry);
			                currentArticle.addEntryToLog(this.getWorkerName() + entry);
					    }
					    
					    // L'article a �t� "skipped", on l'indique dans le journal de bord
					    else 
					    {
					    	String entry = " skipped " + currentStation.getWorkstationName();
			                this.addEntryToLog(currentArticle.getArticleName() + entry);
			                currentArticle.addEntryToLog(this.getWorkerName() + entry);
					    }
					    
					    // On enl�ve le poste trait�, on ajoute ses successeurs
					    initiators.remove(currentStation);
		                for (Workstation workstation : currentStation.getSuccessors())
		                {
		        	        if (! initiators.contains(workstation))
		        	        {
		                        initiators.add(workstation);
		        	        }
		        	    }
		        	    // Fin du traitement des donn�es sensibles
		                currentStation.notifyAll();	    
		            }
			
				    else // Si l'article a d�j� �t� trait� sur ce poste, on enl�ve ce dernier
				    {
					    initiators.remove(currentStation);
				    }
				}
			}
        } 
	}
	
	public void printWorkerData()
	{
		System.out.print("Worker ");
		System.out.print(this.getWorkerName());
		System.out.print(" has to build : ");
		for (Article article : this.getArticlesToBuild()) 
	        System.out.print(article.getArticleName() + " ");
		System.out.println();
	}
}