package ia;

import ia.Main.Fill;
import javafx.util.Pair;

import java.util.ArrayList;

class StupidAI {
	private Fill myFill;

	StupidAI(Fill myFill) {
		this.myFill = myFill;
	}

	Fill getMyFill(){
	    return myFill;
    }

    /**
	 * joue a l'endroit indiqu�
	 * @param table : la table (ici horizontal)
	 * @param move : case où jouer
	 */
	private void playHere(Case[][] table, Pair<Case, Case> move) {
        table[move.getKey().row()][move.getKey().col()].fill(myFill);
        table[move.getValue().row()][move.getValue().col()].fill(myFill);
    }

	/**
	 * Choisis une case a remplir (ici rempli la premiere case vide disponible)
	 * @param table : la table horizontal
	 * @return la table avec les changements
	 */
    Case[][] chooseCaseToFill(Case[][] table, ArrayList<Pair<Case, Case>> moves) {
		if(!moves.isEmpty()) {
            playHere(table, moves.get(0));
        }
        return table;
	}


}
