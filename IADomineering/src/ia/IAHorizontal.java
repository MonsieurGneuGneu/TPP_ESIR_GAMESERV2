package ia;

import ia.Main.Fill;

public class IAHorizontal {
	private static int height;
	private static int width;

	IAHorizontal(int height, int width) {
		this.height = height;
		this.width = width;
	}

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

	public Case[][] chooseCaseToFill(Case[][] table) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (table[i][j].filledBy() == Fill.blank) {
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
			}
		}

		return table;
	}

	private static Case[][] playSomewhere(Case[][] table) {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean hasToPlayHereToDef(Case anATable, Case[][] table) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean hasToPlayHereToWin(Case anATable, Case[][] table) {
		// TODO Auto-generated method stub
		return false;
	}
	
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
