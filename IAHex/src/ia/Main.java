package ia;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		int size = 14;
		Case[][] table = new Case[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				table[i][j] = new Case(i, j);
			}
		}
		
		//Le rouge essaye de lier une colonne
		//Le bleu essaye de lier une ligne
		
		/* Initialisation of AI */
       AI redAI = new RandomAI(size, Fill.r);
       AI blueAI = new RandomAI(size, Fill.b);

		System.out.println(printTable(table));
		/* GAME LOOP */
		while (true) {		
			/* blueAI TURN */
			blueAI.chooseCaseToFill(table);
			System.out.println(printTable(table));
			if(detectEnd(table)) {
				System.out.println("Blue Player win");
			}
			
			long time = System.nanoTime();
			while(System.nanoTime()<time+1000000000) ;//wait 1s


            /* redAI TURN */
			redAI.chooseCaseToFill(table);
			System.out.println(printTable(table));
			if(detectEnd(table)) {
				System.out.println("Blue Player win");
			}
            time = System.nanoTime();
            while(System.nanoTime()<time+1000000000) ;//wait 1s
			
		}

	}






    private static boolean detectEnd(Case[][] table) {
		// TODO Auto-generated method stub
		return false;
	}






	private static String printTable(Case[][] table) {
		StringBuilder res = new StringBuilder();
		String indent = " ";
		String haut = "";
		for(int i = 0; i < table.length;i++) {
			haut += " / "+ "\\";
		}
		
		String bas = "";
		for(int i = 0; i < table.length;i++) {
			bas += " \\ /";
		}
		res.append(indent+haut+"\n");
		for (int i = 0; i < table.length; i++) {
			res.append(indent+"|");
			for (int j = 0; j < table.length; j++) {
				switch (table[i][j].filledBy()) {
				case r:
					res.append(" r |");
					break;
				case b:
					res.append(" b |");
					break;
				case blank:
					res.append(" - |");
					break;
				default:
					break;
				}
			}
			res.append("\n");
			if(i< table.length-1) {
				res.append(indent+bas+ " \\"+"\n");
			}else {
				res.append(indent+bas);
			}
			indent += "  ";
		}
		return res.toString();
	}

	enum Fill {
		r, b, blank
	}

}
