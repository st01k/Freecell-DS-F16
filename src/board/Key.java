package board;

/**
 * Key enumeration that holds the key value, 
 * region, and element of respective region.
 * @author groovyLlama devteam
 * @version 0.1
 */
public enum Key {
		
	A("a", 1, 0),		// free cells
	B("b", 1, 1),
	C("c", 1, 2),
	D("d", 1, 3),
	E("e", 2, 0),		// home cells
	F("f", 2, 1),
	G("g", 2, 2),
	H("h", 2, 3),
	I("i", 3, 0),		// playing piles
	J("j", 3, 1),
	K("k", 3, 2),
	L("l", 3, 3),
	M("m", 3, 4),
	N("n", 3, 5),
	O("o", 3, 6),
	P("p", 3, 7);
	
	private final String key;			// key mapping on board
	private final int 	 region;		// 1-free, 2-home, 3-pile
	private final int 	 position;		// index of region
	
	Key(	final String key, 
			final int 	 region,
			final int 	 position	) {
		
		this.key 		= key;
		this.region 	= region;
		this.position 	= position;
	}
	
	/**
	 * Returns key identifier.
	 * @return key identifier
	 */
	public String getKey() {
		return this.key;
	}
	
	/**
	 * Returns region of board this key is in.
	 * 1 - Freecell, 2 - Homecell, 3 - Playing Pile
	 * @return region int
	 */
	public int getRegion() {
		return this.region;
	}
	
	/**
	 * Returns position in respective region.
	 * @return position in region int
	 */
	public int getPosition() {
		return this.position;
	}
	
	/**
	 * Returns true if key is a freecell.
	 * @return true if freecell
	 */
	public boolean isFreecell() {
		return region == 1;
	}
	
	/**
	 * Returns true if key is a homecell.
	 * @return true if homecell
	 */
	public boolean isHomecell() {
		return region == 2;
	}
	
	/**
	 * Returns true if key is a playing pile.
	 * @return true if pile
	 */
	public boolean isPlayingPile() {
		return region == 3;
	}
	
	/**
	 * Returns key as a string.
	 */
	public String toString() {
		return "key: " + key + " | region: " + region + " | position: " + position;
	}
	
	/**
	 * Returns true if both keys are the same.
	 * @param k target key
	 * @return true if equal
	 */
	public boolean equals(Key k) {
		
		return 
				key == k.getKey() &&
				region == k.getRegion() &&
				position == k.getPosition();
	}
}
