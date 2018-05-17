import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static void main(String[] args){
		int size=3;
		Case[][] table = new Case[size][size];
		for(int i = 0;i<size;++i){
			for(int j = 0;j<size;++j){
				table[i][j]=new Case(i,j);
			}
		}

		/*GAME LOOP*/
		while(true){
			System.out.println(printTable(table));

			/*AI 1 TURN*/
			chooseCaseToFill(table,Fill.o).fill(Fill.o);

			System.out.println(printTable(table));

			long time = System.nanoTime();
			while(System.nanoTime()<time+1000000000) ;//wait 1s

			//detect win
			if(detectEnd(table))break;

			/*AI 2 TURN*/
			chooseCaseToFill(table,Fill.x).fill(Fill.x);

			System.out.println(printTable(table));

			time = System.nanoTime();
			while(System.nanoTime()<time+1000000000);//wait 1s

			//detect win
			if(detectEnd(table))break;
		}

	}

	private static boolean detectEnd(Case[][] table){
		boolean flagfull = true;

		int countx = 0;
		int counto = 0;

		/*row check*/
        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) flagfull = false;
                if (anATable.filledBy() == Fill.x) countx++;
                if (anATable.filledBy() == Fill.o) counto++;
            }
            if (countx == table.length) {
                System.out.println("x won!");
                return true;
            }
            if (counto == table.length) {
                System.out.println("o won!");
                return true;
            }
            countx = 0;
            counto = 0;
        }

		/*column check*/
		for(int j = 0;j<table.length;++j){
			for(int i = 0;i<table[j].length;++i){
				if(table[i][j].filledBy()==Fill.x)countx++;
				if(table[i][j].filledBy()==Fill.o)counto++;
			}
			if(countx==table.length){
				System.out.println("x won!");
				return true;
			}
			if(counto==table.length){
				System.out.println("o won!");
				return true;
			}
			countx = 0;
			counto = 0;
		}

		/*diag1 check*/
		for(int i = 0;i<table.length;++i){
			if(table[i][i].filledBy()==Fill.x)countx++;
			if(table[i][i].filledBy()==Fill.o)counto++;
		}
		if(countx==table.length){
			System.out.println("x won!");
			return true;
		}
		if(counto==table.length){
			System.out.println("o won!");
			return true;
		}
		countx=0;
		counto=0;

		/*diag2 check*/
		for(int i = 0;i<table.length;++i){
			if(table[i][table.length-i-1].filledBy()==Fill.x)countx++;
			if(table[i][table.length-i-1].filledBy()==Fill.o)counto++;
		}
		if(countx==table.length){
			System.out.println("x won!");
			return true;
		}
		if(counto==table.length){
			System.out.println("o won!");
			return true;
		}

		//useless
		countx=0;
		counto=0;

		if(flagfull)System.out.println("no winner!");
		return flagfull;
	}

	/**
	 *
	 * @param table
     * the table
	 * @return
	 * the case where AI plays, or a null case if it cannot play (conceed)
	 * or a -1/-1 case if game is a tie
	 */
	private static Case chooseCaseToFill(Case[][] table, Fill myFill){
		/* 1) checks if AI can win (plays if found)*/

        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) {
                    if (hasToPlayHereToWin(anATable, table, myFill)) {
                        return anATable;
                    }
                }
            }
        }
		/* 2) checks if foe is winning on his next turn (plays if found)*/

        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) {
                    if (hasToPlayHereToDef(anATable, table, myFill)) {
                        return anATable;
                    }
                }
            }
        }
        /* 3) checks if foe is able to create a fork on his next turn (plays if found)*/
        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) {
                    if (hasToPlayHereToMakeAFork(anATable, table, myFill)) {
                        return anATable;
                    }
                }
            }
        }
        /* 4) checks if opponent is able to create a fork on his next turn (plays if found)*/
        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) {
                    if (hasToPlayHereToPreventAFork(anATable, table, myFill)) {
                        return anATable;
                    }
                }
            }
        }
		/* 5) plays somewhere else (method yet to define, temporary = last free place)*/
		Case playLoc = table[table.length/2][table[table.length/2].length/2];
		if(playLoc.filledBy()==Fill.blank){
			return playLoc;
		}else{
			return playSomewhere(table);
		}
	}

	private static Case playSomewhere(Case[][] table){
	    ArrayList<Case> res = new ArrayList<Case>();
	    for(Case[] clist : table){
			for(Case c : clist){
				if(c.filledBy()==Fill.blank)res.add(c);
			}
		}
		return res.get(new Random().nextInt(res.size()));
	}

	/**
	 * Computes if the AI has to play on a case to avoid losing
	 *
	 * @param _case
	 * the case to check (assuming it's blank)
	 * @param table
	 * current state of the game
	 * @return
	 * true if the other cases of the row, col or diag all have the symbol of the foe
	 */
	private static boolean hasToPlayHereToDef(Case _case, Case[][] table, Fill myFill){
		Fill oppFill = myFill==Fill.o?Fill.x:Fill.o;

		/*checking the diag1 if case is on diag1*/
		int count = 0;
        if (checkDiag1(_case, table, oppFill, count)) return true;

        /*checking diag2 if case is on diag2*/
		count = 0;
        if (checkDiag2(_case, table, oppFill, count)) return true;

        /*checking the column*/
		count=0;
        for (Case[] aTable : table) {
            if (aTable[_case.col()].filledBy() == oppFill) ++count;
        }
		if(count==table.length-1) return true;

		/*checking the row*/
		count=0;
		for(int i = 0;i<table.length;++i){
			if(table[_case.row()][i].filledBy()==oppFill)++count;
		}
        return count == table.length - 1;

    }



    /**
	 * Computes if the AI has to play on a case to win
	 *
	 * @param _case
	 * the case to check (assuming it's blank)
	 * @param table
	 * current state of the game
	 * @return
	 * true if the other cases of the row, col or diag all have the symbol of the AI
	 */
	private static boolean hasToPlayHereToWin(Case _case, Case[][] table, Fill myFill){
		/*checking the diag1 if case is on diag1*/
		int count = 0;
        if (checkDiag1(_case, table, myFill, count)) return true;

        /*checking diag2 if case is on diag2*/
		count = 0;
        if (checkDiag2(_case, table, myFill, count)) return true;

        /*checking the column*/
		count=0;
        for (Case[] aTable : table) {
            if (aTable[_case.col()].filledBy() == myFill) ++count;
        }
		if(count==table.length-1) return true;

		/*checking the row*/
		count=0;
		for(int i = 0;i<table.length;++i){
			if(table[_case.row()][i].filledBy()==myFill)++count;
		}
        return count == table.length - 1;

    }

    private static boolean hasToPlayHereToMakeAFork(Case _case, Case[][] table, Fill myFill){
        table[_case.row()][_case.col()].fill(myFill);

	    /*number of way to win at the next turn*/
        int count = 0;

        count = getWinCount(table, myFill, count);
        table[_case.row()][_case.col()].fill(Fill.blank);

	    return count >= 2;
    }


    private static boolean hasToPlayHereToPreventAFork(Case _case, Case[][] table, Fill myFill){
        Fill oppFill = myFill==Fill.o?Fill.x:Fill.o;
        table[_case.row()][_case.col()].fill(oppFill);

        /*number of way to win at the next turn*/
        int count = 0;

        count = getWinCount(table, oppFill, count);
        table[_case.row()][_case.col()].fill(Fill.blank);

        return count >= 2;
    }

    private static int getWinCount(Case[][] table, Fill oppFill, int count) {
        for (Case[] aTable : table) {

            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) {
                    if (hasToPlayHereToWin(anATable, table, oppFill)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }


    private static boolean checkDiag2(Case _case, Case[][] table, Fill oppFill, int count) {
        if(_case.col()+_case.row()==table.length-1){
            for(int i = 0;i<table.length;++i){
                if(table[i][table.length-i-1].filledBy()==oppFill)++count;
            }
            return count == table.length - 1;
        }
        return false;
    }

    /**
     * Assuming table has same width and height
     *
     * @param _case
     * the case to check (assuming it's blank)
     * @param table
     * current state of the game
     * @return
     * true if this diag contains n-1 oppFill symbol
     */
    private static boolean checkDiag1(Case _case, Case[][] table, Fill oppFill, int count) {
        if(_case.col()==_case.row()){
            for(int i = 0;i<table.length;++i){
                if(table[i][i].filledBy()==oppFill)++count;
            }
            return count == table.length - 1;
        }
        return false;
    }

	/**
	 * Assuming table has same width and height
	 *
	 * @param table
	 * a square table
	 * @return
	 * content to print
	 */
	private static String printTable(Case[][] table){
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
		return res.toString();
	}
}


enum Fill{
	x,
	o,
	blank
}

class Case{
	private int row;
	private int col;
	private Fill filledBy;
	Case(int row, int col){
		this.row=row;
		this.col=col;
		this.filledBy=Fill.blank;
	}

	int row(){
		return row;
	}
	int col(){
		return col;
	}
	void fill(Fill fill){
		this.filledBy=fill;
	}
	Fill filledBy(){
		return filledBy;
	}
}