import {Component, OnInit, Input} from '@angular/core';
import { CellComponent } from './cell.component';
@Component({
  selector: 'jhi-my-board',
  templateUrl: './board.component.html',
  styles: [
      `.clearfix::after {
      content: "";
      clear: both;
      display: table;
    }

    .container {
      margin: auto;
      width: 25em;
      height: 25em;
    }
    `
  ]
})
export class BoardComponent implements OnInit {
  cells: string[] = []; // local table, copy of back
  turn = 'x';
  gameover = false;
  winner = null;
  @Input() nbCells = 0;
  ngOnInit() {
    for (let i = 0; i < this.nbCells * this.nbCells; i++) {
      this.cells[i] = null;
    }
  }

  init() {
    for (let i = 0; i < this.nbCells * this.nbCells; i++) {
      this.cells[i] = null;
    }
    this.turn = 'x';
    this.gameover = false;
    this.winner = null;
  }

  clickHandler(idx: number) {
    console.log(idx);
    if (!this.gameover) {
      console.log('sth set');
      if (this.cells[idx] === null) {
        this.cells[idx] = this.turn;
        this.checkWinner();
        this.changeTurn();
      }

    }
  }

  changeTurn() {
    if (this.turn === 'x') {
      this.turn = 'o';
    } else {
      this.turn = 'x';
    }
  }

  checkWinner() {
    const lines = [
      [0, 1, 2],
      [3, 4, 5],
      [6, 7, 8],
      [0, 3, 6],
      [1, 4, 7],
      [2, 5, 8],
      [0, 4, 8],
      [2, 4, 6]
    ];
    for (const line of lines) {
      if (this.cells[line[0]] === this.cells[line[1]] && this.cells[line[1]] === this.cells[line[2]] && this.cells[line[0]] !== null) {
        this.gameover = true;
        this.winner = this.cells[line[0]];
        return;
      }
    }

    let occupy = 0;
    this.cells.forEach((e) => {
      occupy += (e !== null ? 1 : 0);
    });
    if (occupy === 9) {
      console.log('tie');
      this.gameover = true;
      this.winner = 'tie';
    }
  }
}
