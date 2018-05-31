import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Boardc4Component } from '..';

@Component({
  selector: 'jhi-connect4',
  templateUrl: './connect4.component.html',
  styles: ['.game { margin: auto; max-width: 450px; max-height: 450px;}']
})
export class Connect4Component implements OnInit {
  private startSize = 0;
  @ViewChild('board') 
  private boardComponent: Boardc4Component;

  constructor() { }

  ngOnInit() {
  }

  restart() {
    if(this.boardComponent) {
      console.log("restart");
      this.boardComponent.reload();
    }
  }
}
