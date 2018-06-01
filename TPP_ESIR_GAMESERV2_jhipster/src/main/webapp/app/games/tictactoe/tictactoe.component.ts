import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { SERVER_API_URL } from '../../app.constants';
import { BoardtttComponent } from '..';

@Component({
  selector: 'jhi-tictactoe',
  templateUrl: './tictactoe.component.html',
  styles: ['.game { margin: auto; max-width: 500px; max-height: 500px;}']
})
export class TictactoeComponent implements OnInit {
  @Input() startSize = 0;
  @ViewChild('board') 
  private boardComponent: BoardtttComponent;

  constructor() { }

  ngOnInit() {
  }

  restart() {
    if(this.boardComponent) {
      console.log("restart");
      this.boardComponent.reload(this.startSize);
    }
  }
}
