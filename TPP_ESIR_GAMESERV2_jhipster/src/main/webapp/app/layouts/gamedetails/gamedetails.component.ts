import { Component, OnInit } from '@angular/core';
import { GameinfoService } from '../../games/gameinfo.service';

@Component({
  selector: 'jhi-gamedetails',
  templateUrl: './gamedetails.component.html',
  styleUrls: ['./gamedetails.component.css']
})
export class GamedetailsComponent implements OnInit {
  private difficulty: number = 0;

  constructor(private gameinfoService: GameinfoService) { }

  ngOnInit() {
    this.changeDifficulty();
  }

  changeDifficulty() {
    console.log(this.difficulty);
    this.gameinfoService.setDifficulty(this.difficulty);
  }
}
