package board;

public enum KeyEnum {
		
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
	
	private final String 	key;			// key mapping on board
	private final int 		region;			// 1-free, 2-home, 3-pile
	private final int 		position;		// index of region
	
	KeyEnum(	final String 	key, 
				final int 		region,
				final int 		position	) {
		
		this.key 		= key;
		this.region 	= region;
		this.position 	= position;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public int getRegion() {
		return this.region;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public boolean isFreecell() {
		return region == 1;
	}
	
	public boolean isHomecell() {
		return region == 2;
	}
	
	public boolean isPlayingPile() {
		return region == 3;
	}
}
