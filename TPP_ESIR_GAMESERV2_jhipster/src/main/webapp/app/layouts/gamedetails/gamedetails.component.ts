import { Component, OnInit } from '@angular/core';
// import { TictactoeComponent } from '../../games'

@Component({
  selector: 'jhi-gamedetails',
  templateUrl: './gamedetails.component.html',
  styleUrls: ['./gamedetails.component.css']
})
export class GamedetailsComponent implements OnInit {
  game: string;
  constructor() { }

  ngOnInit() {
    this.game = "Tic tac toe";
  }

  
}
