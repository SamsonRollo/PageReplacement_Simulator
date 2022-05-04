package algorithms;

public enum Algorithm {
	FIFO, OPT, LRU, CLOCK;
	
	private static Algorithm[] algoValues = values();
	
	public Algorithm next(){
		return algoValues[(this.ordinal()+1) % algoValues.length];
	}
	
	public Algorithm previous(){
		if(this.ordinal() == 0)
			return CLOCK;
		else
			return algoValues[(this.ordinal()-1) % algoValues.length];
	}
} 
