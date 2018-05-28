package com.mycompany.myapp.web.rest.domineering;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		int size = 5;
		Case[][] table = new Case[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				table[i][j] = new Case(i, j);
			}
		}
		/* Initialisation of AI */
        LessStupidAI horizontalAI = new LessStupidAI(Fill.h,4);
        LessStupidAI verticalAI = new LessStupidAI(Fill.v,2);

		System.out.println(printTable(table));
		/* GAME LOOP */
		while (true) {


			
			/* AI V TURN */
			verticalAI.chooseCaseToFill(table);
			System.out.println("--Vertical AI--\n"+printTable(table));
			if (verticalAI.moveList(table, verticalAI.getMyFill()).isEmpty() && horizontalAI.moveList(table, horizontalAI.getMyFill()).isEmpty()) {
				System.out.println("Joueur vertical win");
				break;
			}
			long time = System.nanoTime();
			while(System.nanoTime()<time+1000000000) ;//wait 1s


            /* AI H TURN */
            horizontalAI.chooseCaseToFill(table);
            System.out.println("--Horizontal AI--\n"+printTable(table));
            if (verticalAI.moveList(table, verticalAI.getMyFill()).isEmpty() && horizontalAI.moveList(table, horizontalAI.getMyFill()).isEmpty()) {
                System.out.println("Joueur horizontal win");
                break;
            }
            time = System.nanoTime();
            while(System.nanoTime()<time+1000000000) ;//wait 1s
			
		}

	}






    private static String printTable(Case[][] table) {
		StringBuilder res = new StringBuilder();

		for (Case[] aTable : table) {
			for (Case anATable : aTable) {
				switch (anATable.filledBy()) {
				case h:
					res.append("h ");
					break;
				case v:
					res.append("v ");
					break;
				case blank:
					res.append("b ");
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
		h, v, blank
	}
	
	/**Returns an int corresponding to the state of the game
	 * @param table
	 * game current state
	 * @param ai
	 * ai the player is facing
	 * @param turn
	 * true if it is AI's turn
	 * @return
     *   0 : not finished
     *   1 : player win
     *   2 : player lose
	 */
	public static int detectEnd(Case[][] table, AI ai, boolean turn){
	    
	    Fill oppFill = ai.getMyFill()==Fill.v?Fill.h:Fill.v;
	    
	    if(turn){
	        if(ai.moveList(table, ai.getMyFill()).isEmpty()){
	            return 1; 
	        }
	    }else{
	        if(ai.moveList(table, oppFill).isEmpty()){
                return 2; 
            }
	    }
	    
	    return 0;
	}

}
