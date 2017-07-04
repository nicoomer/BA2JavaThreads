/* 
    Nom : Omer
    Pr�nom : Nicolas
    Matricule : 000331695
    Ann�e : BA2 Sciences Informatiques
*/

public class Treatment implements Task
{
	/* Attribut */
	private String _name;
	
	/* Constructeur */
	public Treatment (String name)
	{
		_name = name;
	}
	
	/* Getter */
	public String getTreatmentName()
	{
		return _name;
	}
	
	public void process(Article article)
	{
		// Traitement de l'article avec la t�che associ�e
	    try 
	    {
	    	System.out.println(this.getTreatmentName() 
	    			+ " completed for " + article.getArticleName());
	    	Thread.sleep((long)(Math.random() * 1000));
	    }catch (InterruptedException ex) {}
	}
}