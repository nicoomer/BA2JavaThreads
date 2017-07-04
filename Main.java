/* 
    Nom : Omer
    PrÈnom : Nicolas
    Matricule : 000331695
    AnnÈe : BA2 Sciences Informatiques
*/

import java.util.ArrayList;
import java.util.HashMap;

public class Main 
{	
	/* Construction des postes de travail */
	public static ArrayList<Workstation> initializeWorkstations()
	{
		Workstation wsA = new Workstation("Workstation_A", "task A");
		Workstation wsB = new Workstation("Workstation_B", "task B");
		Workstation wsC = new Workstation("Workstation_C", "task C");
		Workstation wsD = new Workstation("Workstation_D", "task D");
		Workstation wsE = new Workstation("Workstation_E", "task E");
		Workstation wsF = new Workstation("Workstation_F", "task F");
		Workstation wsG = new Workstation("Workstation_G", "task G");
		Workstation wsH = new Workstation("Workstation_H", "task H");
		Workstation wsI = new Workstation("Workstation_I", "task I");
		
		wsA.addSuccessor(wsB);
		wsA.addSuccessor(wsC);
		
		wsB.addPredecessor(wsA);
		wsB.addSuccessor(wsD);
		wsB.addSuccessor(wsE);
		
		wsC.addPredecessor(wsA);
		wsC.addSuccessor(wsE);
		wsC.addSuccessor(wsF);
		
		wsD.addPredecessor(wsB);
		wsD.addSuccessor(wsG);
		
		wsE.addPredecessor(wsB);
		wsE.addPredecessor(wsC);
		wsE.addSuccessor(wsG);
		wsE.addSuccessor(wsH);
		
		wsF.addPredecessor(wsC);
		wsF.addSuccessor(wsH);
		
		wsG.addPredecessor(wsD);
		wsG.addPredecessor(wsE);
		wsG.addSuccessor(wsI);
		
		wsH.addPredecessor(wsE);
		wsH.addPredecessor(wsF);
		wsH.addSuccessor(wsI);
		
		wsI.addPredecessor(wsG);
		wsI.addPredecessor(wsH);
		
		ArrayList<Workstation> stationsList = new ArrayList<Workstation>();
		stationsList.add(wsA);
		stationsList.add(wsB);
		stationsList.add(wsC);
		stationsList.add(wsD);
		stationsList.add(wsE);
		stationsList.add(wsF);
		stationsList.add(wsG);
		stationsList.add(wsH);
		stationsList.add(wsI);
		
		return stationsList;
	}
	
	/* CréÈation de la HashMap des dÈépendances */
	public static HashMap<Workstation, ArrayList<Workstation>> initializeWorkstationDependency(ArrayList<Workstation> stationsList)
	{
		HashMap<Workstation, ArrayList<Workstation>> workstationDependency = new HashMap<Workstation, ArrayList<Workstation>>();

		for (Workstation current : stationsList) 
			workstationDependency.put(current, current.getSuccessors());
		
		return workstationDependency;
	}
	
	/* Instanciation des articles */
	public static ArrayList<Article> initializeArticles()
	{
		Article item1 = new Article("item1");
		Article item2 = new Article("item2");
		Article item3 = new Article("item3");
		Article item4 = new Article("item4");
		
		ArrayList<Article> listOfArticles = new ArrayList<Article>();
		listOfArticles.add(item1);
		listOfArticles.add(item2);
		listOfArticles.add(item3);
		listOfArticles.add(item4);
		
		return listOfArticles;
	}
	
	/* CréÈation des artisans */
	public static ArrayList<Worker> initializeWorkers(ArrayList<Article> listOfArticles, Graph<Workstation> graph)
	{
		ArrayList<Article> articleToBuild1 = new ArrayList<Article>();
		Worker alpha1 = new Worker("alpha1", articleToBuild1, graph);
		alpha1.addArticleToBuild(listOfArticles.get(0));
		alpha1.addArticleToBuild(listOfArticles.get(2));
		
		ArrayList<Article> articleToBuild2 = new ArrayList<Article>();
		Worker alpha2 = new Worker("alpha2", articleToBuild2, graph);
		alpha2.addArticleToBuild(listOfArticles.get(1));
		alpha2.addArticleToBuild(listOfArticles.get(3));
		
		ArrayList<Article> articleToBuild3 = new ArrayList<Article>();
		Worker alpha3 = new Worker("alpha3", articleToBuild3, graph);
		alpha3.addArticleToBuild(listOfArticles.get(2));
		alpha3.addArticleToBuild(listOfArticles.get(3));
		
		ArrayList<Article> articleToBuild4 = new ArrayList<Article>();
		Worker alpha4 = new Worker("alpha4", articleToBuild4, graph);
		alpha4.addArticleToBuild(listOfArticles.get(0));
		alpha4.addArticleToBuild(listOfArticles.get(1));
		
		ArrayList<Worker> listOfWorkers = new ArrayList<Worker>();
		listOfWorkers.add(alpha1);
		listOfWorkers.add(alpha2);
		listOfWorkers.add(alpha3);
		listOfWorkers.add(alpha4);
		
		// On affiche les tâ‚ches de chacun
		for(Worker worker : listOfWorkers)
		{
			worker.printWorkerData();
		}
		
		return listOfWorkers;
	}
	
	/* Renvoie un nombre alÈéatoire entre les bornes en paramètËres */
	public static int randomGenerator(int lower, int higher)
	{
		return (int)(Math.random() * (higher - lower)) + lower;
	}
	
	/* Affiche les journaux de bord des articles et des artisans */
	public static void finalPrint(ArrayList<Article> listOfArticles, ArrayList<Worker> listOfWorkers)
	{
		System.out.println("\nLog of articles :");
		for (Article article : listOfArticles) 
		{
			String message = "Article " + article.getArticleName() + ", id #" + article.getArticleId() + " : ";
			System.out.print(message);
			System.out.println(article.getArticleLog());
		}
		
		System.out.println("\nLog of workers :");
		for (Worker worker : listOfWorkers) 
		{
			String message = "Worker " + worker.getWorkerName() + " : ";
			System.out.print(message);
			System.out.println(worker.getWorkerLog());
		}
	}
	
	public static void main(String[] args) 
	{ 
		// On créeÈ les workstation et leurs déÈpendences
		ArrayList<Workstation> workstationsList = initializeWorkstations();
		HashMap<Workstation, ArrayList<Workstation>> workstationDependency = initializeWorkstationDependency(workstationsList);
		
		// On créeÈ un graphe avec ces paramètËres
		Graph<Workstation> graph = new Graph<Workstation>(workstationsList, workstationDependency);
		
		// On instancie des articles et des artisans
		ArrayList<Article> listOfArticles = initializeArticles();
		ArrayList<Worker> listOfWorkers = initializeWorkers(listOfArticles, graph);
		
		// On lance les threads (les artisans)
		for (Worker worker : listOfWorkers) 
		{
			worker.start();
		}
		
		try // Lorsque les threads sont terminéÈs
		{
			for (Worker worker : listOfWorkers) 
			{
				worker.join();
			}
		}catch (InterruptedException e) {}
		
		// On affiche les journaux de bord des articles et des artisans
		finalPrint(listOfArticles, listOfWorkers);
	}
}