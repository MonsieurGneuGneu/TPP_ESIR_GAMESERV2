package TicTacToe;

import java.util.ArrayList;

public class MiniMaxAI {
    private Fill myFill;
    private Fill oppFill;

    public MiniMaxAI(Fill myFill){
        this.myFill = myFill;
        this.oppFill = myFill==Fill.o?Fill.x:Fill.o;
    }

    public Fill getMyFill(){
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
            bestScore = evaluate(table, myFill);
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

    private int evaluate(Case[][] table, Fill myFill){
        int score = 0;
        score += evaluateLine(table[0][0], table[0][1], table[0][2], myFill);  // row 0
        score += evaluateLine(table[1][0], table[1][1], table[1][2], myFill);  // row 1
        score += evaluateLine(table[2][0], table[2][1], table[2][2], myFill);  // row 2
        score += evaluateLine(table[0][0], table[1][0], table[2][0], myFill);  // col 0
        score += evaluateLine(table[0][1], table[1][1], table[2][1], myFill);  // col 1
        score += evaluateLine(table[0][2], table[1][2], table[2][2], myFill);  // col 2
        score += evaluateLine(table[0][0], table[1][1], table[2][2], myFill);  // diagonal
        score += evaluateLine(table[0][2], table[1][1], table[2][0], myFill);  // alternate diagonal
        return score;
    }

    private int evaluateLine(Case case1, Case case2, Case case3, Fill myFill) {
        int score = 0;
        Fill oppFill = myFill==Fill.o?Fill.x:Fill.o;

        if(case1.filledBy()==myFill){
            score = 1;
        } else if(case1.filledBy()==oppFill){
            score = -1;
        }

        if(case2.filledBy()==myFill){
            if(score == 1){
                score = 10;
            } else if(score == -1){
                return 0;
            }
            else {
                score = 1;
            }
        }else if(case2.filledBy() == oppFill){
            if(score == -1){
                score = -10;
            }else if(score == 1){
                return 0;
            }
            else{
                score = -1;
            }
        }

        if(case3.filledBy() == myFill){
            if(score > 0){
                score *= 10;
            }else if(score < 0){
                return 0;
            }else{
                score = 1;
            }
        }else if(case3.filledBy() == oppFill){
            if(score < 0){
                score *= 10;
            }else if(score > 0){
                return 0;
            }else{
                score = -1;
            }
        }
        return score;
    }

    private int[] winningPatterns = {
            0b111000000, 0b000111000, 0b000000111, // rows
            0b100100100, 0b010010010, 0b001001001, // cols
            0b100010001, 0b001010100               // diagonals
    };

    /** Returns true if thePlayer wins */
    private boolean hasWon(Case[][] table, Fill fill) {
        int pattern = 0b000000000;  // 9-bit pattern for the 9 cells
        for (int row = 0; row < table.length; ++row) {
            for (int col = 0; col < table[0].length; ++col) {
                if (table[row][col].filledBy() == fill) {
                    pattern |= (1 << (row * table[0].length + col));
                }
            }
        }
        for (int winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) return true;
        }
        return false;
    }

}


