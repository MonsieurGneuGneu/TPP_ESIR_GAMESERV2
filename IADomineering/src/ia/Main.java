package ia;

public class Main {

	public static void main(String[] args) {
		int size = 5;
		Case[][] table = new Case[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				table[i][j] = new Case(i, j);
			}
		}

		/* Initialisation des deux tableau */
		Case[][] vertical = new Case[size - 1][size];
		Case[][] horizontal = new Case[size][size - 1];

		for (int i = 0; i < size - 1; ++i) {
			for (int j = 0; j < size; ++j) {
				vertical[i][j] = new Case(i, j);
			}
		}
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size - 1; ++j) {
				horizontal[i][j] = new Case(i, j);
			}
		}
		System.out.println(printTable(horizontal, vertical, table, size));
		/* GAME LOOP */
		while (true) {

			/* Initialisation of AI */
			IAHorizontal hai = new IAHorizontal(size, size - 1);
			IAVertical vai = new IAVertical(size - 1, size);
			
			/* AI V TURN */
			vertical = vai.chooseCaseToFill(vertical);
			System.out.println("--VAI--\n"+printTable(horizontal, vertical, table, size));
			if (vai.noMoreMove(vertical, size) && hai.noMoreMove(horizontal, size)) {
				System.out.println("Joueur vertical win");
				break;
			}
			horizontal = hai.update(vertical, horizontal);
			
			long time = System.nanoTime();
			while(System.nanoTime()<time+1000000000) ;//wait 1s
			
			/* AI H TURN */
			horizontal = hai.chooseCaseToFill(horizontal);
			System.out.println("--HAI--\n"+printTable(horizontal, vertical, table, size));
			if (vai.noMoreMove(vertical, size) && hai.noMoreMove(horizontal, size)) {
				System.out.println("Joueur horizontal win");
				break;
			}
			vertical = vai.update(vertical, horizontal);
			long time2 = System.nanoTime();
			while(System.nanoTime()<time2+1000000000) ;//wait 1s
			
		}

	}

	public static String printTable(Case[][] horizontal, Case[][] vertical, Case[][] table, int size) {
		StringBuilder res = new StringBuilder();
		/* On remplie le tableau a afficher avec les lignes horizontale */
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - 1; j++) {
				if (horizontal[i][j].filledBy() == Fill.h) {
					table[i][j].fill(Fill.h);
					table[i][j+1].fill(Fill.h);
				}
			}
		}

		/* On remplie le tableau a afficher avec les lignes vertical */
		for (int i = 0; i < size - 1; i++) {
			for (int j = 0; j < size; j++) {
				if (vertical[i][j].filledBy() == Fill.v) {
					table[i][j].fill(Fill.v);
					table[i+1][j].fill(Fill.v);
				}
			}
		}

		for (Case[] aTable : table) {
			for (Case anATable : aTable) {
				switch (anATable.filledBy()) {
				case h:
					res.append("h ");
					break;
				case v:
					res.append("v ");
					break;
				case blank:
					res.append("b ");
					break;
				default:
					break;
				}
			}
			res.append("\n");
		}
		return res.toString();
	}
	
	public static boolean detectEnd(Case[][] table) {
		for (Case[] aTable : table) {
			for (Case anATable : aTable) {
				if (anATable.filledBy() == Fill.blank) return false;
			}
		}
		return true;
	}

	public static String printTest(Case[][] table) {
		StringBuilder res = new StringBuilder();
		for (Case[] aTable : table) {
			for (Case anATable : aTable) {
				switch (anATable.filledBy()) {
				case h:
					res.append("h ");
					break;
				case v:
					res.append("v ");
					break;
				case x:
					res.append("x ");
					break;
				case blank:
					res.append("b ");
					break;
				default:
					break;
				}
			}
			res.append("\n");
		}
		return res.toString();
	}
	
	enum Fill {
		h, v, blank, x
	}

}
