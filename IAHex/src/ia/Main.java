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
		/* Initialisation of AI */
       

		System.out.println(printTable(table));
		/* GAME LOOP */
		while (true) {


			
			/* AI V TURN */
			
			long time = System.nanoTime();
			while(System.nanoTime()<time+1000000000) ;//wait 1s


            /* AI H TURN */
            
            time = System.nanoTime();
            while(System.nanoTime()<time+1000000000) ;//wait 1s
			
		}

	}






    private static String printTable(Case[][] table) {
		StringBuilder res = new StringBuilder();
		
		for (Case[] aTable : table) {
			for (Case anATable : aTable) {
				switch (anATable.filledBy()) {
				case r:
					res.append(" r ");
					break;
				case b:
					res.append(" b ");
					break;
				case blank:
					res.append(" - ");
					break;
				default:
					break;
				}
			}
			res.append("\n");
		}
		return res.toString();
	}

	enum Fill {
		r, b, blank
	}

}
