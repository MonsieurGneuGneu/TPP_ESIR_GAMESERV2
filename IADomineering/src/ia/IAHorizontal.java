package ia;

import ia.Main.Fill;

public class IAHorizontal {
	private static int height;
	private static int width;

	IAHorizontal(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * 
	 * @param vertical
	 * @param horizontal
	 * @return
	 */
	public Case[][] update(Case[][] vertical, Case[][] horizontal) {
		for (int i = 0; i < height - 1; i++) {
			for (int j = 0; j < width; j++) {
				if (vertical[i][j].filledBy() == Fill.v && i < height && j < width) {
					if (horizontal[i][j].filledBy() != Fill.h) {
						horizontal[i][j].fill(Fill.x);
					}
					if (j > 0) {
						if (horizontal[i][j - 1].filledBy() != Fill.h) {
							horizontal[i][j - 1].fill(Fill.x);
						}
					}
					if (horizontal[i + 1][j].filledBy() != Fill.h) {
						horizontal[i + 1][j].fill(Fill.x);
					}

					if (j > 0 && i > 0) {
						if (horizontal[i + 1][j - 1].filledBy() != Fill.h) {
							horizontal[i + 1][j - 1].fill(Fill.x);
						}
					}
				}
			}
		}
		return horizontal;
	}
	
	/**
	 * joue a l'endroit indiqué
	 * @param table : la table (ici horizontal)
	 * @param i : ligne
	 * @param j : colonne
	 * @return la table avec les changements
	 */
	public Case[][] playHere(Case[][] table, int i, int j) {
		table[i][j].fill(Fill.h);
		if (j > 0) {
			if (table[i][j - 1].filledBy() != Fill.v) {
				table[i][j - 1].fill(Fill.x);
			}
		}
		if (j < width - 1) {
			if (table[i][j + 1].filledBy() != Fill.v) {
				table[i][j + 1].fill(Fill.x);
			}
		}
		return table;
	}

	/**
	 * Choisis une case a remplir (ici rempli la premiere case vide disponible)
	 * @param table : la table horizontal
	 * @return la table avec les changements
	 */
	public Case[][] chooseCaseToFill(Case[][] table) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (table[i][j].filledBy() == Fill.blank) {
					return playHere(table, i, j);
				}
			}
		}
		return table;
	}
	
	/**
	 * Verifie si il n'y a plus de place dans la table
	 * @param table : la table (horizontal ici)
	 * @param size : la taille du plateau de jeu
	 * @return true si la table est entierement remplie de h ou de x
	 */
	public boolean noMoreMove(Case[][] table, int size) {
		int countNoBlank = 0;
		for (Case[] aTable : table) {
			for (Case anATable : aTable) {
				if (anATable.filledBy() != Fill.blank) {
					countNoBlank++;
				}
			}
		}
		return (countNoBlank == (size * (size - 1)));
	}

}
