import { Component, OnInit } from '@angular/core';
import { GameinfoService } from '../../games/gameinfo.service';

@Component({
  selector: 'jhi-footerdetails',
  templateUrl: './footerdetails.component.html',
  styleUrls: ['./footerdetails.component.css']
})
export class FooterdetailsComponent implements OnInit {

  constructor(private gameinfoService: GameinfoService) { }

  ngOnInit() {
  }

  goToTictactoe() {
    this.gameinfoService.setGame("Tic tac toe");
    window.scrollTo(0,0);
  }

  goToDomineering() {
    this.gameinfoService.setGame("Domineering");
    window.scrollTo(0,0);
  }

  goToConnect4() {
    this.gameinfoService.setGame("Connect 4");
    window.scrollTo(0,0);
  }
}
