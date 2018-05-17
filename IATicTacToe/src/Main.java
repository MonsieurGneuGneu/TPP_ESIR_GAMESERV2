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
			while(System.nanoTime()<time+1000000000);//wait 1s
			
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
		for(int i = 0;i<table.length;++i){
			for(int j = 0;j<table[i].length;++j){
				if(table[i][j].filledBy()==Fill.blank)flagfull=false;
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
		if(flagfull)System.out.println("no winner!");
		return flagfull;
	}

	/**
	 * 
	 * @param table
	 * @return
	 * the case where AI plays, or a null case if it cannot play (conceed)
	 * or a -1/-1 case if game is a tie
	 */
	public static Case chooseCaseToFill(Case[][] table,Fill myFill){
		Case playLoc = table[0][0];
		for(int i = 0;i<table.length;++i){
			for(int j = 0;j<table[i].length;++j){
				if(table[i][j].filledBy()==Fill.blank){
					/* 1) checks if AI can win (plays if found)*/
					if(hasToPlayHereToWin(table[i][j], table, myFill)){
						return table[i][j]; 
					}
					/* 2) checks if foe is winning on his next turn (plays if found)*/
					if(hasToPlayHereToDef(table[i][j], table, myFill)){
						return table[i][j]; 
					}
					/* 3) plays somewhere else (method yet to define, temporary = last free place)*/
					playLoc = table[i][j];
				}				
			}	
		}
		return playLoc;
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
	public static boolean hasToPlayHereToDef(Case _case,Case[][] table,Fill myFill){
		Fill oppFill = myFill==Fill.o?Fill.x:Fill.o;
		
		/*checking the diag1 if case is on diag1*/
		int count = 0;
		if(_case.col()==_case.row()){
			for(int i = 0;i<table.length;++i){
				if(table[i][i].filledBy()==oppFill)++count;
			}
			if(count==table.length-1) return true;
		}
		
		/*checking diag2 if case is on diag2*/
		count = 0;
		if(_case.col()+_case.row()==table.length){
			for(int i = 0;i<table.length;++i){
				if(table[i][table.length-i-1].filledBy()==oppFill)++count;						
			}
			if(count==table.length-1) return true;
		}
		
		/*checking the column*/
		count=0;
		for(int i = 0;i<table.length;++i){
			if(table[i][_case.col()].filledBy()==oppFill)++count;
		}
		if(count==table.length-1) return true;
		
		/*checking the row*/
		count=0;
		for(int i = 0;i<table.length;++i){
			if(table[_case.row()][i].filledBy()==oppFill)++count;
		}
		if(count==table.length-1) return true;
		
		return false;
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
	public static boolean hasToPlayHereToWin(Case _case,Case[][] table,Fill myFill){		
		/*checking the diag1 if case is on diag1*/
		int count = 0;
		if(_case.col()==_case.row()){
			for(int i = 0;i<table.length;++i){
				if(table[i][i].filledBy()==myFill)++count;
			}
			if(count==table.length-1) return true;
		}
		
		/*checking diag2 if case is on diag2*/
		count = 0;
		if(_case.col()+_case.row()==table.length){
			for(int i = 0;i<table.length;++i){
				if(table[i][table.length-i-1].filledBy()==myFill)++count;						
			}
			if(count==table.length-1) return true;
		}
		
		/*checking the column*/
		count=0;
		for(int i = 0;i<table.length;++i){
			if(table[i][_case.col()].filledBy()==myFill)++count;
		}
		if(count==table.length-1) return true;
		
		/*checking the row*/
		count=0;
		for(int i = 0;i<table.length;++i){
			if(table[_case.row()][i].filledBy()==myFill)++count;
		}
		if(count==table.length-1) return true;
		
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
	public static String printTable(Case[][] table){
		String res = "";
		for(int i = 0;i<table.length;++i){
			for(int j = 0;j<table[i].length;++j){
				switch(table[i][j].filledBy()){
				case o:res+="o";
					break;
				case x:res+="x";
					break;
				case blank:res+=" ";
					break;
				default:
					break;
				}
				res+="|";
			}
			res+="\n";
		}
		return res;
	}
}


enum Fill{
	x,
	o,
	blank;
}

class Case{
	private int row;
	private int col;
	private Fill filledBy;
	public Case(int row, int col){
		this.row=row;
		this.col=col;
		this.filledBy=Fill.blank;
	}
	
	public int row(){
		return row;
	}
	public int col(){
		return col;
	}
	public void fill(Fill fill){
		this.filledBy=fill;
	}
	public Fill filledBy(){
		return filledBy;
	}
}