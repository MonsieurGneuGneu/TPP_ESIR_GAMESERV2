package ia;

import ia.Main.Fill;

public class Case {
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
