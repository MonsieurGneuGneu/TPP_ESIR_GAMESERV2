package com.mycompany.myapp.web.rest.hex;

import java.util.ArrayList;

public class Main {

//	public static void main(String[] args) {
//		int size = 14;
//		Case[][] table = new Case[size][size];
//		for (int i = 0; i < size; ++i) {
//			for (int j = 0; j < size; ++j) {
//				table[i][j] = new Case(i, j);
//			}
//		}
//		
//		//Le rouge essaye de lier une colonne
//		//Le bleu essaye de lier une ligne
//		
//		/* Initialisation of AI */
//       AI redAI = new RandomAI(size, Fill.r);
//       AI blueAI = new RandomAI(size, Fill.b);
//
//		System.out.println(printTable(table));
//		/* GAME LOOP */
//		while (true) {		
//			/* blueAI TURN */
//			blueAI.chooseCaseToFill(table);
//			System.out.println(printTable(table));
//			if(detectEnd(table)) {
//				System.out.println("Blue Player win");
//			}
//			
//			long time = System.nanoTime();
//			while(System.nanoTime()<time+1000000000) ;//wait 1s
//
//
//            /* redAI TURN */
//			redAI.chooseCaseToFill(table);
//			System.out.println(printTable(table));
//			if(detectEnd(table)) {
//				System.out.println("Blue Player win");
//			}
//            time = System.nanoTime();
//            while(System.nanoTime()<time+1000000000) ;//wait 1s
//			
//		}
//
//	}


    /**
     * A case(n,n)'s neighbors are :
     * n-1,n-1
     * n-1,n
     * n,n-1
     * n,n+1
     * n+1,n
     * n+1,n+1
     * 
     * @param loc
     * the place to check
     * @param table
     * game state
     * @return
     * arraylist of neighbors
     */
    private static ArrayList<Case> neighbors(Case loc, Case[][] table){
        ArrayList<Case> res = new ArrayList<Case>();

        if(loc.row()>0){
            res.add(table[loc.row()-1][loc.col()]);            
        }
        
        if(loc.col()>0){
            if(loc.row()>0){
                res.add(table[loc.row()-1][loc.col()-1]);
            }
            res.add(table[loc.row()][loc.col()-1]);
        }
        
        if(loc.col()<table.length-1){
            res.add(table[loc.row()][loc.col()+1]);
            if(loc.row()<table.length-1){
                res.add(table[loc.row()+1][loc.col()+1]);
            }
        }
        
        if(loc.row()<table.length-1){
            res.add(table[loc.row()+1][loc.col()]);
        }
        
        return res;
    }

    /**
     * marks all cases as "not visited" to get ready for a DFS
     * 
     * @param table
     * game state
     */
    private void initForSearch(Case[][] table){
        for(Case[] clist : table){
            for(Case c : clist){
                c.unmark();
            }
        }
    }
    
    /**
     * Checks game state
     * 
     * @param table
     * game state
     * @param playLoc
     * play location
     * @return
     * 0 if not finished
     * 1 if player won
     * 2 if player lost
     */
    private int detectEnd(Case[][] table,Case playLoc) {
        
        
		// TODO Auto-generated method stub
		return 0;
	}
    
    /**
     * Explores a connex component
     * 
     * @param table
     * game state
     */
    private void explore(Case[][] table){
        for(Case[] clist : table){
            for(Case c : clist){
                if(!c.marked()){
                    //check loc or row depending on player (r = top and bottom, b = sides) 
                }
            }
        }
    }
    
    private ArrayList<Case> findAWay(Case[][] table){
        ArrayList<Case> res = new ArrayList<Case>();
        
        initForSearch(table);
        //TODO
        return res;
        
    }
    
    /**
     * Depth First Search
     * @param table
     * game state
     * @param loc
     * start pos
     */
    private void DFS(Case[][] table,Case loc){
        loc.mark();
        for(Case a : neighbors(loc, table)){
            if(!a.marked()){
                DFS(table,a);
            }
        }
    }

	private static String printTable(Case[][] table) {
		StringBuilder res = new StringBuilder();
		String indent = " ";
		String haut = "";
		for(int i = 0; i < table.length;i++) {
			haut += " / "+ "\\";
		}
		
		String bas = "";
		for(int i = 0; i < table.length;i++) {
			bas += " \\ /";
		}
		res.append(indent+haut+"\n");
		for (int i = 0; i < table.length; i++) {
			res.append(indent+"|");
			for (int j = 0; j < table.length; j++) {
				switch (table[i][j].filledBy()) {
				case r:
					res.append(" r |");
					break;
				case b:
					res.append(" b |");
					break;
				case blank:
					res.append(" - |");
					break;
				default:
					break;
				}
			}
			res.append("\n");
			if(i< table.length-1) {
				res.append(indent+bas+ " \\"+"\n");
			}else {
				res.append(indent+bas);
			}
			indent += "  ";
		}
		return res.toString();
	}

	enum Fill {
		r, b, blank
	}

}
