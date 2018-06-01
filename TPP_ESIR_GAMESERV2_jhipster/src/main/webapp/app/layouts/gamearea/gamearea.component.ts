import { Component, OnInit } from '@angular/core';
import { GameinfoService } from '../../games/gameinfo.service';

@Component({
  selector: 'jhi-gamearea',
  templateUrl: './gamearea.component.html',
  styleUrls: ['./gamearea.component.css']
})
export class GameareaComponent implements OnInit {

  constructor(private gameinfoService: GameinfoService) { }

  ngOnInit() {
  }

}
