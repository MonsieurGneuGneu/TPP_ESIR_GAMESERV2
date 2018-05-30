import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { BoarddComponent } from '..';

@Component({
  selector: 'jhi-domineering',
  templateUrl: './domineering.component.html',
  styles: ['.game { margin: auto; max-width: 450px; max-height: 450px;}']
})
export class DomineeringComponent implements OnInit {
  @Input() startSize = 0;
  @ViewChild('board') 
  private boardComponent: BoarddComponent;

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
