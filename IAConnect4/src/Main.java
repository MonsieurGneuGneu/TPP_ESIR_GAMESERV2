import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int width = 7;
        int height = 6;
        Case[][] table = new Case[height][width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                table[i][j] = new Case(i, j);
            }
        }

        /* GAME LOOP */
        while (true) {
            System.out.println(printTable(table));

            /* AI 1 TURN */
            Case c = chooseCaseToFill(table, Fill.o);
            c.fill(Fill.o);

            System.out.println(printTable(table));

            long time = System.nanoTime();
            while (System.nanoTime() < time + 1000000000)
                ;// wait 1s

            // detect win
            if (detectEnd(table,c))
                break;

            /* AI 2 TURN */
            c = chooseCaseToFill(table, Fill.x);
            c.fill(Fill.x);

            System.out.println(printTable(table));

            time = System.nanoTime();
            while (System.nanoTime() < time + 1000000000)
                ;// wait 1s

            // detect win
            if (detectEnd(table,c))
                break;
        }

    }

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
     */
    private static boolean detectEnd(Case[][] table, Case lastpos) {
        boolean flagfull = isFull(table);

        int count = 0;
        
        /* horizontal check */
        for (int col = 0; col < table[0].length; ++col) {
            if (table[lastpos.row()][col].filledBy() == lastpos.filledBy())
                count++;
            else
                count = 0;
            if (count >= 4)
                return true;
        }

        /* vertical check */
        for (int row=0;row<table.length;++row)
        {
            if (table[row][lastpos.col()].filledBy()==lastpos.filledBy())
                ++count;
            else
                count=0;

            if (count>=4)
                return true;
        } 
        
        /* diag\ check */
      // top-left to bottom-right - lower diagonals
        //TODO
        
        
      // top-left to bottom-right - upper diagonals
        //TODO
        
        
        /* diag/ check */
     // bottom-left to top-right - lower diagonals
        for( int rowStart = 0; rowStart < table.length - 4; rowStart++){
            count = 0;
            int row, col;
            for( row = rowStart, col = 0; row < table.length && col < table[0].length; row++, col++ ){
                if(table[row][col].filledBy()==lastpos.filledBy()){
                    count++;
                    if(count >= 4) return true;
                }
                else {
                    count = 0;
                }
            }
        }

        // bottom-left to top-right - upper diagonals
        for(int colStart = 1; colStart < table[0].length - 4; colStart++){
            count = 0;
            int row, col;
            for( row = 0, col = colStart; row < table.length && col < table[0].length; row++, col++ ){
                if(table[row][col].filledBy()==lastpos.filledBy()){
                    count++;
                    if(count >= 4) return true;
                }
                else {
                    count = 0;
                }
            }
        }
        
        
        if (flagfull)
            System.out.println("no winner!");
        return flagfull;
    }

    /**
     *
     * @param table
     *            the table
     * @return the case where AI plays, or a null case if it cannot play
     *         (conceed) or a -1/-1 case if game is a tie
     */
    private static Case chooseCaseToFill(Case[][] table, Fill myFill) {
        /* 1) checks if AI can win (plays if found) */

        /* 2) checks if foe is winning on his next turn (plays if found) */

        /*
         * 3) checks if AI is able to create a fork on his next turn (plays if
         * found)
         */

        /*
         * 4) checks if opponent is able to create a fork on his next turn
         * (plays if found)
         */

        /*
         * 5) plays somewhere else (method yet to define, temporary = last free
         * place)
         */
        return playSomewhere(table);
    }

    /**
     * Computes the minheight in this row of the table
     * 
     * @param table
     *            the state of the game
     * @param row
     *            the row to check
     * @return -1 if not possible to play on a column, else the minheight in
     *         this column
     */
    private static int minrow(Case[][] table, int col) {

        for (int row = 0; row < table.length; ++row) {
            if (table[row][col].filledBy() == Fill.blank)
                return row;
        }
        return -1;

    }

    private static Case playSomewhere(Case[][] table) {
        ArrayList<Case> res = new ArrayList<>();
        for (int col = 0; col < table[0].length; ++col) {
            res.add(table[minrow(table, col)][col]);
        }
        return res.get(new Random().nextInt(res.size()));// TODO minmax
    }

    /**
     * Computes if the AI has to play on a case to avoid losing
     *
     * @param _case
     *            the case to check (assuming it's blank)
     * @param table
     *            current state of the game
     * @return true if the other cases of the row, col or diag all have the
     *         symbol of the foe
     */
    private static boolean hasToPlayHereToDef(Case _case, Case[][] table, Fill myFill) {
        Fill oppFill = myFill == Fill.o ? Fill.x : Fill.o;

        return false;
    }

    /**
     * Computes if the AI has to play on a case to win
     *
     * @param _case
     *            the case to check (assuming it's blank)
     * @param table
     *            current state of the game
     * @return true if the other cases of the row, col or diag all have the
     *         symbol of the AI
     */
    private static boolean hasToPlayHereToWin(Case _case, Case[][] table, Fill myFill) {

        return false;
    }

    private static boolean hasToPlayHereToMakeAFork(Case _case, Case[][] table, Fill myFill) {
        // TODO check that the player CAN play on the winning place after the
        // fork
        return false;
    }

    private static boolean hasToPlayHereToPreventAFork(Case _case, Case[][] table, Fill myFill) {
        Fill oppFill = myFill == Fill.o ? Fill.x : Fill.o;
        // TODO check that the player CAN play on the winning place after the
        // fork
        return false;
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