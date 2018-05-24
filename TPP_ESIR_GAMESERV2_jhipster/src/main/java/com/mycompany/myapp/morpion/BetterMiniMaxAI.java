package com.mycompany.myapp.morpion;

import java.util.ArrayList;
import java.util.Arrays;


class BetterMiniMaxAI {
    private Fill myFill;
    private Fill oppFill;
    private long[] winningPatterns;

    BetterMiniMaxAI(Fill myFill, int size){
        this.myFill = myFill;
        this.oppFill = myFill==Fill.o?Fill.x:Fill.o;
        winningPatterns = new long[size*2+2];
        generateWinningPatterns(size);

        System.out.println(Arrays.toString(winningPatterns));
    }

    Fill getMyFill(){
        return myFill;
    }

    Case chooseCaseToFill(Case[][] table){
        int[] result = minimax(2, table, myFill);
        return table[result[1]][result[2]];
    }

    private int[] minimax(int depth, Case[][] table, Fill testingFill){
        ArrayList<Case> possiblemove = possibleMoves(table);

        int bestScore = (myFill == testingFill) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if(depth == 0){
            bestScore = evaluate(table);
        }
        else {
            for(Case casee : possiblemove){
                table[casee.row()][casee.col()].fill(testingFill);
                if(myFill==testingFill){
                    currentScore = minimax(depth-1, table, oppFill)[0];
                    if(currentScore > bestScore){
                        bestScore=currentScore;
                        bestRow=casee.row();
                        bestCol=casee.col();
                    }
                } else {
                    currentScore = minimax(depth-1, table, myFill)[0];
                    if(currentScore < bestScore){
                        bestScore=currentScore;
                        bestRow=casee.row();
                        bestCol=casee.col();
                    }
                }

                table[casee.row()][casee.col()].fill(Fill.blank);
            }
        }

        return new int[] {bestScore, bestRow, bestCol};
    }

    private ArrayList<Case> possibleMoves(Case[][] table) {
        ArrayList<Case> moves = new ArrayList<>();

        if (hasWon(table, Fill.o) || hasWon(table, Fill.x)) {
            return moves;
        }

        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) moves.add(anATable);
            }
        }
        return moves;
    }

    private int evaluate(Case[][] table){
        int score = 0;
        Case[] diag1 = new Case[table.length];
        Case[] diag2 = new Case[table.length];
        for(int i = 0;i < table.length;i++){
            score += evaluateLine(table[i]);
            Case[] line = new Case[table.length];
            for (int j = 0;j < table[0].length;j++){
                line[j]=(table[j][i]);
            }
            score += evaluateLine(line);
            diag1[i]=(table[i][i]);
            diag2[i]=(table[i][table.length-i-1]);
        }
        score += evaluateLine(diag1);
        score += evaluateLine(diag2);
        return score;
    }

    private int evaluateLine(Case[] line) {
        int score = 0;
        Fill oppFill = myFill==Fill.o?Fill.x:Fill.o;

        for(int i = 0;i < line.length;i++){
            if(line[i].filledBy()==myFill){
                if(score > 0){
                    score *= 10;
                }else if(score < 0){
                    return 0;
                }else{
                    score = 1;
                }
            } else if(line[i].filledBy()==oppFill){
                if(score < 0){
                    score *= 10;
                }else if(score > 0){
                    return 0;
                }else{
                    score = -1;
                }
            }
        }
        return score;
    }



    private void generateWinningPatterns(int size){
        int nbPatterns = 0;
        long col = 0b0;
        long row = 0b0;
        long diag1 = 0b0;
        long diag2 = 0b0;


        for(int i = 0;i < size;i++){
            row = row << 1;
            row = row|0b1;

            col = col << size;
            col = col|0b1;

            diag1 = diag1 << (size+1);
            diag1 = diag1|0b1;

            diag2 = diag2|0b1;
            diag2 = diag2 << (size-1);
        }

        for(int i = 0;i < size;i++){
            long winningPattern = row;
            winningPattern = winningPattern << (i*size);
            winningPatterns[nbPatterns] = winningPattern;
            nbPatterns++;

            winningPattern = col;
            winningPattern = winningPattern << i;
            winningPatterns[nbPatterns] = winningPattern;
            nbPatterns++;
        }

        winningPatterns[nbPatterns] = diag1;
        nbPatterns++;
        winningPatterns[nbPatterns] = diag2;
    }

    /** Returns true if thePlayer wins */
    private boolean hasWon(Case[][] table, Fill fill) {
        long pattern = 0b000000000;  // 9-bit pattern for the 9 cells
        for (int row = 0; row < table.length; ++row) {
            for (int col = 0; col < table[0].length; ++col) {
                if (table[row][col].filledBy() == fill) {
                    pattern |= (1 << (row * table[0].length + col));
                }
            }
        }
        for (long winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) return true;
        }
        return false;
    }

}


