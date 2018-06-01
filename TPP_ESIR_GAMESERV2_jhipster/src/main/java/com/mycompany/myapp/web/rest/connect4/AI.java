package com.mycompany.myapp.web.rest.connect4;

import java.util.ArrayList;
import java.util.Random;

public class AI {
    private Fill myFill;
    private Fill oppFill;
    private int depth;

    public AI(Fill myFill, int depth){
        this.myFill = myFill;
        this.oppFill = myFill==Fill.x?Fill.o:Fill.x;
        this.depth = depth;
    }

    public Fill getMyFill() {
        return myFill;
    }

    /**
     *
     * @param table
     *            the table
     * @return the case where AI plays, or a null case if it cannot play
     *         (conceed) or a -1/-1 case if game is a tie
     */
    protected Case chooseCaseToFill(Case[][] table) {
        int col = minimax(depth, table, myFill)[1];
        int row = Main.minrow(table, col);
        if(row<0 || col<0){
            for(col = 0;col<table[0].length;col++){
                row = Main.minrow(table, col);
                if(row != -1){
                    play(table, row, col, myFill);
                    return(table[row][col]);
                }
            }
        }else{
            play(table, row, col, myFill);
        }
        return(table[row][col]);
    }

    private int[] minimax(int depth, Case[][] table, Fill testingFill){
        int bestScore = (myFill == testingFill) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestCol = -1;

        if(depth == 0){
            bestScore = evaluate(table);
        }
        else {
            for(int col = 0;col < table[0].length;col++){
                int row = Main.minrow(table, col);
                if(row != -1){
                    table[row][col].fill(testingFill);
                    if(myFill==testingFill){
                        currentScore = minimax(depth-1, table, oppFill)[0];
                        if(currentScore > bestScore){
                            bestScore=currentScore;
                            bestCol = col;
                        }
                    } else {
                        currentScore = minimax(depth-1, table, myFill)[0];
                        if(currentScore < bestScore){
                            bestScore=currentScore;
                            bestCol = col;
                        }
                    }
                    table[row][col].fill(Fill.blank);
                }
            }
        }
        return new int[] {bestScore, bestCol};
    }

    private int evaluate(Case[][] table){
        int value = 0;
        for(int col = 0;col<table[0].length;col++){
            for(int row = 0;row<table.length;row++){
                if(table[row][col].filledBy() == Fill.blank){
                    break;
                }else{
                    if(table[row][col].filledBy() == myFill){
                        value+=evaluateToken(table, row, col);
                    }else{
                        value-=evaluateToken(table, row, col);
                    }
                }
            }
        }
        return value;
    }

    private int evaluateToken(Case[][] table, int row, int col){
        int value = 0;
        for(int i = -1;i<2;i++){
            for(int j = -1;j<2;j++){
                if(j!=0 && i!=0){
                    value += evaluateLine(table, row, col, i, j);
                }
            }
        }
        return value;
    }

    private int evaluateLine(Case[][] table, int row, int col, int i, int j) {
        Fill tokenFill = table[row][col].filledBy();
        int value = 1;
        for(int k=1;k<4;k++){
            if(row+i*k < 0 || col+j*k < 0 || row+i*k >= table.length || col+j*k >= table[0].length){
                return 0;
            }
            if (table[row + i * k][col + j * k].filledBy() == tokenFill) {
                value++;
            }else if(table[row + i * k][col + j * k].filledBy() != Fill.blank){
                return 0;
            }else {
                /*for (int l = col + j * k; l >= 0; l--) {
                    if (table[row + i * k][l].filledBy() == Fill.blank) {
                        value--;
                    }
                }*/
            }
        }
        if(value==4) return 100;
        if(value<0)return 0;
        return value;
    }

    private static void play(Case[][] table, int row, int col, Fill fill) {
        table[row][col].fill(fill);
    }
}
