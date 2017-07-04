/* 
    Nom : Omer
    Pr�nom : Nicolas
    Matricule : 000331695
    Ann�e : BA2 Sciences Informatiques
*/

import java.util.Vector;

public class Article 
{
	/* Attributs */
	private int _id; // Identifiant unique d'un article
	private String _name; // Nom de l'article
	private Vector<String> _log; // Journal de bord de l'article
	// Nombre d'articles instanci�s
	private static int _numberOfInstances = 0; 
	
	/* Constructeur */
	public Article(String name)
	{    
		_numberOfInstances++;
		// Chaque article a un identifiant diff�rent croissant
	    _id = _numberOfInstances; 
	    _name = name;
	    _log = new Vector<String>(); // Log vide au d�but
	}
	
	/* Getters */
	public int getArticleId()
	{
		return _id;
	}
	
	public String getArticleName()
	{
		return _name;
	}
	
	public Vector<String> getArticleLog()
	{
		return _log;
	}
	
	public static int getNumberOfInstances()
	{
	    return _numberOfInstances;
	}
	
	/* Ajoute une entr�e dans le journal de bord d'un article */
	public void addEntryToLog(String entry)
	{
		_log.add(entry);
	}
	
	public void printArticleData()
	{
		System.out.print("Id of article ");
		System.out.print(this.getArticleName());
		System.out.print(" : ");
	    System.out.println(this.getArticleId());
	}
}