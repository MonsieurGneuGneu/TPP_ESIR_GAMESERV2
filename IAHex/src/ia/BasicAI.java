package ia;

import java.util.ArrayList;

import ia.Main.Fill;

public class BasicAI implements AI {
	private int size;
	private Fill myFill;
	private Fill oppFill;

	BasicAI(int size, Fill myFill) {
		this.size = size;
		this.myFill = myFill;
		oppFill = myFill == Fill.r ? Fill.b : Fill.r;
	}

	@Override
	public void chooseCaseToFill(Case[][] table) {

		for (Case[] atable : table) {
			for (Case antable : atable) {

			}
		}

	}

	/**
	 * 
	 * @param depth
	 * @param table
	 * @param testingFill
	 * @return
	 */
	private Pair<Integer, Case> minimax(int depth, Case[][] table, Fill testingFill) {
		ArrayList<Case> possiblemove = moveList(table, testingFill);

		int bestScore = (myFill == testingFill) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int currentScore;
		Case bestMove = null;

		if (depth == 0) {
			bestScore = evaluate(table);
		} else {
			for (Case move : possiblemove) {
				play(table, move, testingFill);
				if (myFill == testingFill) {
					currentScore = minimax(depth - 1, table, oppFill).getKey();
					if (currentScore > bestScore) {
						bestScore = currentScore;
						bestMove = move;
					}
				} else {
					currentScore = minimax(depth - 1, table, myFill).getKey();
					if (currentScore < bestScore) {
						bestScore = currentScore;
						bestMove = move;
					}
				}

				play(table, move, Fill.blank);
			}
		}
		return new Pair<>(bestScore, bestMove);
	}

	/**
	 * 
	 * @param table
	 * @param move
	 * @param fill
	 */
	private void play(Case[][] table, Case move, Fill fill) {
		if (move != null) {
			table[move.row()][move.col()].fill(fill);
		}
	}

	/**
	 * 
	 * @param table
	 * @param fill
	 * @return
	 */
	private ArrayList<Case> moveList(Case[][] table, Fill fill) {
		ArrayList<Case> moveList = new ArrayList<>();
		if (fill == myFill) {
			for (int i = 0; i < table.length - 1; i++) {
				for (int j = 0; j < table.length; j++) {
					if (table[i][j].filledBy() == Fill.blank) {
						moveList.add(table[i][j]);
					}
				}
			}
		}
		return moveList;
	}

	/**
	 * 
	 * @param table
	 * @return
	 */
	private int evaluate(Case[][] table) {
		// TODO Auto-generated method stub
		return 0;
	}

}
