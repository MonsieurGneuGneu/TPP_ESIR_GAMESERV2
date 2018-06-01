package TicTacToe;

public class Main {
    
	public static void main(String[] args){
		int size=3;
		Case[][] table = new Case[size][size];
		for(int i = 0;i<size;++i){
			for(int j = 0;j<size;++j){
				table[i][j]=new Case(i,j);
			}
		}
        BetterMiniMaxAI AI1 = new BetterMiniMaxAI(Fill.o, size);
        BetterMiniMaxAI AI2 = new BetterMiniMaxAI(Fill.x, size);

		/*GAME LOOP*/
		while(true){
			System.out.println(printTable(table));


			/*AI 1 TURN*/
			AI1.chooseCaseToFill(table).fill(AI1.getMyFill());

			System.out.println(printTable(table));

			long time = System.nanoTime();
			while(System.nanoTime()<time+1000000000) ;//wait 1s

			//detect win
			if(detectEnd(table))break;

			/*AI 2 TURN*/
            AI2.chooseCaseToFill(table).fill(AI2.getMyFill());

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


		if(flagfull)System.out.println("no winner!");
		return flagfull;
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