package ia;

import javafx.util.Pair;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		int size = 6;
		Case[][] table = new Case[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				table[i][j] = new Case(i, j);
			}
		}
		/* Initialisation of AI */
        StupidAI horizontalAI = new StupidAI(Fill.h);
        StupidAI verticalAI = new StupidAI(Fill.v);

		System.out.println(printTable(table));
		/* GAME LOOP */
		while (true) {


			
			/* AI V TURN */
			table = verticalAI.chooseCaseToFill(table, moveList(table, verticalAI.getMyFill()));
			System.out.println("--Vertical AI--\n"+printTable(table));
			if (moveList(table, verticalAI.getMyFill()).isEmpty() && moveList(table, horizontalAI.getMyFill()).isEmpty()) {
				System.out.println("Joueur vertical win");
				break;
			}
			long time = System.nanoTime();
			while(System.nanoTime()<time+1000000000) ;//wait 1s


            /* AI H TURN */
            table = horizontalAI.chooseCaseToFill(table, moveList(table, horizontalAI.getMyFill()));
            System.out.println("--Horizontal AI--\n"+printTable(table));
            if (moveList(table, verticalAI.getMyFill()).isEmpty() && moveList(table, horizontalAI.getMyFill()).isEmpty()) {
                System.out.println("Joueur horizontal win");
                break;
            }
            time = System.nanoTime();
            while(System.nanoTime()<time+1000000000) ;//wait 1s
			
		}

	}

    static ArrayList moveList(Case[][] table, Fill fill) {
        ArrayList<Pair<Case, Case>> moves = new ArrayList<>();
        if(fill==Fill.v){
            for(int i = 0;i < table.length-1;i++){
                for(int j = 0;j < table.length;j++){
                    if(table[i][j].filledBy()==Fill.blank && table[i+1][j].filledBy()==Fill.blank){
                        moves.add(new Pair<>(table[i][j], table[i+1][j]));
                    }
                }
            }
        }
        else{
            for (Case[] aTable : table) {
                for (int j = 0; j < table.length - 1; j++) {
                    if (aTable[j].filledBy() == Fill.blank && aTable[j + 1].filledBy() == Fill.blank) {
                        moves.add(new Pair<>(aTable[j], aTable[j + 1]));
                    }
                }
            }
        }
        return moves;
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

}
