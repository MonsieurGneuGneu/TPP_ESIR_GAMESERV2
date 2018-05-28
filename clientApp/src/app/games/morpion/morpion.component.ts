import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-morpion',
  templateUrl: './morpion.component.html',
  styleUrls: ['./morpion.component.css']
})
export class MorpionComponent implements OnInit {
  @Input() nbCases = 0;
  constructor() { }

  ngOnInit() {
  }

}
