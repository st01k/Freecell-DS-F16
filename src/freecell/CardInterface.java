package freecell;

public interface CardInterface 
{
	@SuppressWarnings("rawtypes")
	Enum getRank();
	
	void setRank();
	
	String getSuit();
	
	void setSuit();

}
