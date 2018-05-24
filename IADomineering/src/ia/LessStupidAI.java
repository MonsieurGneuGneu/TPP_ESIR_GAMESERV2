package ia;

import javafx.util.Pair;
import java.util.ArrayList;
import ia.Main.Fill;


class LessStupidAI {
    private Fill myFill;
    private Fill oppFill;

    LessStupidAI(Fill myFill) {
        this.myFill = myFill;
        this.oppFill = myFill==Fill.v?Fill.h:Fill.v;
    }

    Fill getMyFill(){
        return myFill;
    }

    /**
     * Choisis une case a remplir (ici rempli la premiere case vide disponible)
     * @param table : la table horizontal
     * @return la table avec les changements
     */
    Case[][] chooseCaseToFill(Case[][] table) {
        Pair<Case, Case> move = minimax(4, table, myFill).getValue();
        if(move != null) {
            play(table, move, myFill);
        }else{
            if(!moveList(table, myFill).isEmpty()){
                play(table, moveList(table, myFill).get(0), myFill);
            }
        }
        return table;
    }

    private Case[][] play(Case[][] table, Pair<Case, Case> move, Fill fill) {
        if(move != null){
            table[move.getKey().row()][move.getKey().col()].fill(fill);
            table[move.getValue().row()][move.getValue().col()].fill(fill);
        }
        return table;
    }

    private Pair<Integer, Pair> minimax(int depth, Case[][] table, Fill testingFill){
        ArrayList<Pair<Case, Case>> possiblemove = moveList(table, testingFill);

        int bestScore = (myFill == testingFill) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        Pair<Case, Case> bestMove = null;

        if(depth == 0){
            bestScore = evaluate(table);
        }
        else {
            for(Pair<Case, Case> move : possiblemove){
                play(table, move, testingFill);
                if(myFill==testingFill){
                    currentScore = minimax(depth-1, table, oppFill).getKey();
                    if(currentScore > bestScore){
                        bestScore=currentScore;
                        bestMove = move;
                    }
                } else {
                    currentScore = minimax(depth-1, table, myFill).getKey();
                    if(currentScore < bestScore){
                        bestScore=currentScore;
                        bestMove = move;
                    }
                }

                play(table, move, Fill.blank);
            }
        }
        return new Pair<>(bestScore, bestMove);
    }

    private int evaluate(Case[][] table) {
        ArrayList<Pair<Case, Case>> myMoves = moveList(table, myFill);
        ArrayList<Pair<Case, Case>> oppMoves = moveList(table, oppFill);

        return (myMoves.size() - oppMoves.size() + getSafeMoves(myMoves, oppMoves) - getSafeMoves(oppMoves, myMoves));
    }

    private int getSafeMoves(ArrayList<Pair<Case, Case>> moves1, ArrayList<Pair<Case, Case>> moves2){
        int nb = 0;
        for (Pair<Case, Case> move1 : moves1) {
            boolean safe = true;
            for (Pair<Case, Case> move2 : moves2) {
                if(move1.getKey().equals(move2.getKey()) || move1.getKey().equals(move2.getValue()) || move1.getValue().equals(move2.getKey()) || move1.getValue().equals(move2.getValue())){
                    safe = false;
                    break;
                }
            }
            if(safe){
                nb++;
            }
        }
        return nb;
    }

    public static ArrayList<Pair<Case, Case>> moveList(Case[][] table, Fill fill) {
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
