package ia;

import ia.Main.Fill;

public class BasicAI implements AI {
	private int size;
	private Fill myFill;
	private Fill oppFill;
	
	BasicAI(int size, Fill myFill){
		this.size = size;
		this.myFill = myFill;
		oppFill = myFill==Fill.r?Fill.b:Fill.r;
	}
	
	@Override
	public void chooseCaseToFill(Case[][] table) {
		

	}
}
