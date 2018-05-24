package ia;

import ia.Main.Fill;

public class IAVertical {
	private static int height;
	private static int width;

	IAVertical(int height, int width) {
		this.height = height;
		this.width = width;
	}

	public Case[][] update(Case[][] vertical, Case[][] horizontal) {
		for (int i = 0; i < height + 1; i++) {
			for (int j = 0; j < width - 1; j++) {
				if (horizontal[i][j].filledBy() == Fill.h && i < height && j < width) {

					if (vertical[i][j].filledBy() != Fill.v) {
						vertical[i][j].fill(Fill.x);
					}
					if (vertical[i][j + 1].filledBy() != Fill.v) {
						vertical[i][j + 1].fill(Fill.x);
					}

					if (i > 0) {
						if (vertical[i - 1][j].filledBy() != Fill.v) {
							vertical[i - 1][j].fill(Fill.x);
						}
					}
					if (i > 0 && j < width - 1) {
						if (vertical[i - 1][j + 1].filledBy() != Fill.v) {
							vertical[i - 1][j + 1].fill(Fill.x);
						}
					}
				}
			}
		}
		return vertical;
	}

	/**
	 * Choisis une case a remplir (ici rempli la premiere case vide disponible)
	 * @param table : la table vertical
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
	 * joue a l'endroit indiqué
	 * @param table : la table (ici vertical)
	 * @param i : ligne
	 * @param j : colonne
	 * @return la table avec les changements
	 */
	public Case[][] playHere(Case[][] table, int i, int j) {
		table[i][j].fill(Fill.v);
		if (i > 0) {
			if (table[i - 1][j].filledBy() != Fill.v) {
				table[i - 1][j].fill(Fill.x);
			}
		}
		if (i < height - 1) {
			if (table[i + 1][j].filledBy() != Fill.v) {
				table[i + 1][j].fill(Fill.x);
			}
		}
		return table;
	}
	
	/**
	 * Verifie si il n'y a plus de place dans la table
	 * @param table : la table (vertical ici)
	 * @param size : la taille du plateau de jeu
	 * @return true si la table est entierement remplie de v ou de x
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
