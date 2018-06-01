package com.mycompany.myapp.web.rest.connect4;

import java.util.ArrayList;
import java.util.Random;

public class Main {
//    public static void main(String[] args) {
//        int width = 7;
//        int height = 6;
//        Case[][] table = new Case[height][width];
//        for (int i = 0; i < height; ++i) {
//            for (int j = 0; j < width; ++j) {
//                table[i][j] = new Case(i, j);
//            }
//        }
//
//        /* GAME LOOP */
//        while (true) {
//
//            /* AI 1 TURN */
//            Case c = chooseCaseToFill(table, Fill.o);
//            c.fill(Fill.o);
//
//            System.out.println(printTable(table));
//
//            long time = System.nanoTime();
////            while (System.nanoTime() < time + 1000000000)
//                ;// wait 1s
//
//            // detect win
//            if (detectEnd(table, c, Fill.o))
//                break;
//
//            /* AI 2 TURN */
//            c = chooseCaseToFill(table, Fill.x);
//            c.fill(Fill.x);
//
//            System.out.println(printTable(table));
//
//            time = System.nanoTime();
////            while (System.nanoTime() < time + 1000000000)
//                ;// wait 1s
//
//            // detect win
//            if (detectEnd(table, c, Fill.x))
//                break;
//        }
//
//    }

    private static boolean isFull(Case[][] table) {
        boolean res = true;
        for (Case[] clist : table) {
            for (Case c : clist) {
                if (c.filledBy() == Fill.blank)
                    res = false;
            }
        }
        return res;
    }

    /**
     *
     * @param table
     *            game state
     * @param lastpos
     *            the last placed token
     * @return
     * 0 : not finished
     * 1 : player win
     * 2 : player lose
     * 3 : draw
     */
    static int detectEnd(Case[][] table, Case lastpos, Fill player) {
        if (isFull(table)) {
            System.out.println("no winner!");
            return 3;//XXX maybe it is possible that the last move is a winning one?
        }

        int count = 0;

        /* horizontal check */
        for (int col = 0; col < table[0].length; ++col) {
            if (table[lastpos.row()][col].filledBy() == lastpos.filledBy())
                count++;
            else
                count = 0;

            if (count >= 4) {
                System.out.println(player + " won!");
                if(player==Fill.x) {//AI
                	
                	return 2;
                }else {
                	return 1;
                }
            }
        }

        count = 0;

        /* vertical check */
        for (int row = 0; row < table.length; ++row) {
            if (table[row][lastpos.col()].filledBy() == lastpos.filledBy())
                ++count;
            else
                count = 0;

            if (count >= 4) {
                System.out.println(player + " won!");
                if(player==Fill.x) {//AI
                	
                	return 2;
                }else {
                	return 1;
                }
            }
        }

        count = 0;

        /* diag\ check */
        // top-left to bottom-right - lower diagonals
        for (int rowStart = table.length - 1; rowStart >= 4; --rowStart) {
            count = 0;
            int row, col;
            for (row = rowStart, col = 0; row >= 0 && col < table[0].length; --row, ++col) {
                if (table[row][col].filledBy() == lastpos.filledBy()) {
                    count++;
                    if (count >= 4) {
                        System.out.println(player + " won!");
                        if(player==Fill.x) {//AI
                        	
                        	return 2;
                        }else {
                        	return 1;
                        }
                    }
                } else {
                    count = 0;
                }
            }
        }

        count = 0;

        // top-left to bottom-right - upper diagonals
        for (int colStart = table.length - 1; colStart >= 4; --colStart) {
            count = 0;
            int row, col;
            for (row = 0, col = colStart; row < table.length && col >= 0; ++row, --col) {
                if (table[row][col].filledBy() == lastpos.filledBy()) {
                    count++;
                    if (count >= 4) {
                        System.out.println(player + " won!");
                        if(player==Fill.x) {//AI
                        	
                        	return 2;
                        }else {
                        	return 1;
                        }
                    }
                } else {
                    count = 0;
                }
            }
        }

        count = 0;

        /* diag/ check */
        // bottom-left to top-right - lower diagonals
        for (int rowStart = 0; rowStart < table.length - 4; rowStart++) {
            count = 0;
            int row, col;
            for (row = rowStart, col = 0; row < table.length && col < table[0].length; row++, col++) {
                if (table[row][col].filledBy() == lastpos.filledBy()) {
                    count++;
                    if (count >= 4) {
                        System.out.println(player + " won!");
                        if(player==Fill.x) {//AI
                        	
                        	return 2;
                        }else {
                        	return 1;
                        }
                    }
                } else {
                    count = 0;
                }
            }
        }

        count = 0;

        // bottom-left to top-right - upper diagonals
        for (int colStart = 1; colStart < table[0].length - 4; colStart++) {
            count = 0;
            int row, col;
            for (row = 0, col = colStart; row < table.length && col < table[0].length; row++, col++) {
                if (table[row][col].filledBy() == lastpos.filledBy()) {
                    count++;
                    if (count >= 4) {
                        System.out.println(player + " won!");
                        if(player==Fill.x) {//AI
                        	
                        	return 2;
                        }else {
                        	return 1;
                        }
                    }
                } else {
                    count = 0;
                }
            }
        }

        return 0;
    }



    /**
     * Computes the minheight in this row of the table
     *
     * @param table
     *            the state of the game
     * @param col
     *            the row to check
     * @return -1 if not possible to play on a column, else the minheight in
     *         this column
     */
    protected static int minrow(Case[][] table, int col) {
        if(col < 0){
            return -1;
        }
        for (int row = 0; row < table.length; ++row) {
            if (table[row][col].filledBy() == Fill.blank)
                return row;
        }
        return -1;
    }


    /**
     *
     * @param table
     *            the game state
     * @return content to print
     */
    private static String printTable(Case[][] table) {
        StringBuilder res = new StringBuilder();
        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                switch (anATable.filledBy()) {
                case o:
                    res.append("o");
                    break;
                case x:
                    res.append("x");
                    break;
                case blank:
                    res.append(" ");
                    break;
                default:
                    break;
                }
                res.append("|");
            }
            res.append("\n");
        }

        return join(reverse(res.toString().split("\n")));
    }

    private static String join(String[] data) {
        String res = "";
        for (String d : data) {
            res += d + "\n";
        }
        return res;
    }

    private static String[] reverse(String[] validData) {
        String[] res = new String[validData.length];

        for (int i = 0; i < validData.length; i++) {
            res[res.length - i - 1] = validData[i];
        }
        return res;
    }
}

enum Fill {
    x, o, blank
}

class Case {
    private int row;
    private int col;
    private Fill filledBy;

    Case(int row, int col) {
        this.row = row;
        this.col = col;
        this.filledBy = Fill.blank;
    }

    int row() {
        return row;
    }

    int col() {
        return col;
    }

    void fill(Fill fill) {
        this.filledBy = fill;
    }

    Fill filledBy() {
        return filledBy;
    }
}
