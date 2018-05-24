import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-gamesbar',
  templateUrl: './gamesbar.component.html',
  styles: [``]
})
export class GamesbarComponent implements OnInit {

  tictactoeSelected: boolean;
  constructor() { }

  ngOnInit() {
  }

  gotoTictactoe() {
    this.tictactoeSelected = true;
  }
}
