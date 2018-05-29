package ia;

import com.mycompany.myapp.web.rest.hex.Main.Fill;

class Case {
	private int row;
	private int col;
	private Fill filledBy;
	private boolean mark;
	private Case father;
	
	Case(int row, int col) {
		this.row = row;
		this.col = col;
		this.filledBy = Fill.blank;
		mark = false;
		father = null;
	}

	int row() {
		return row;
	}
	
	void setFather(Case c){
	    father = c;
	}
	
	Case getFather(){
	    return father;
	}
	
	void unmark(){
	    mark = false;
	}
	void mark(){
	    mark = true;
	}
	boolean marked(){
	    return mark;
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

	boolean equals(Case case2){
	    return(this.row == case2.row && this.col == case2.col());
    }
}
