package com.mycompany.myapp.web.rest.domineering;

import java.util.ArrayList;
import com.mycompany.myapp.web.rest.domineering.Main.Fill;

class StupidAI implements AI {
	private Fill myFill;

	StupidAI(Fill myFill) {
		this.myFill = myFill;
	}

	public Fill getMyFill(){
	    return myFill;
    }

	/**
	 * Choisis une case a remplir (ici rempli la premiere case vide disponible)
	 * @param table : la table horizontal
	 * @return la table avec les changements
	 */
    public void chooseCaseToFill(Case[][] table) {
        ArrayList<Pair<Case, Case>> moves = moveList(table, myFill);
        if(!moves.isEmpty()) {
            play(table, moves.get(0), myFill);
        }
	}

    public void play(Case[][] table, Pair<Case, Case> move, Fill fill) {
        if(move != null){
            table[move.getKey().row()][move.getKey().col()].fill(fill);
            table[move.getValue().row()][move.getValue().col()].fill(fill);
        }
    }

    public ArrayList moveList(Case[][] table, Fill fill) {
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


}
