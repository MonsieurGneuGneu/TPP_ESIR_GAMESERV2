import { Component, OnInit, Input } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { SERVER_API_URL } from '../../app.constants';
import { GameinfoService } from '../gameinfo.service';

@Component({
  selector: 'jhi-boardc4',
  templateUrl: './boardc4.component.html',
  styles: [`
  .clearfix {
    overflow: auto;
 }

 .clearfix::after {
    content: "";
    clear: both;
    display: table;
}
 .container{
    margin: auto;
    height: 450;
 }  
 `]
})

export class Boardc4Component implements OnInit {
  private widthSize: number = 7;
  private heightSize: number = 6;
  private game = "Connect 4";
  private cells: string[] = []; // local table startSize&startSize, copy of back
  private turn: string;
  private state: number;
  private firstClick: number;

  constructor(private http: HttpClient, private gameinfoService: GameinfoService) { }

 reload() {
   this.ngOnInit();
 }
 
 ngOnInit() {
   for (let i = 0; i < this.widthSize * this.heightSize; i++) {
     this.cells[i] = 'null';
   }

   this.init();
 }

 init() {
    let difficulty = this.gameinfoService.getDifficulty();
    let params = new HttpParams()
            .set('difficulty',String(difficulty));

    // post request to init with posibly size of board
    this.http.get<any>(SERVER_API_URL + '/api/connect4/init', { params: params }).subscribe((response) => {
      if (response){
        //console.log(response);
        for (let i = 0; i < this.cells.length; i++) {
          this.cells[i] = null;
        }
      }
    });
    this.gameinfoService.newGame(this.game);
    this.turn = 'o';
    this.state = 0;
  }

  clickHandler(idx: number) {
    console.log(idx);
    if ((this.state == 0) && this.turn =='o') {
        let column = idx%this.widthSize;
        this.clickOnColumn(column);
        console.log(column);
    }
  }
 
  clickOnColumn(column: number) {
       let params = new HttpParams()
           .set('column', String(column));
 
       this.http.get<any>(SERVER_API_URL+'/api/connect4/play', {params: params})
       .subscribe(response => { 
        response.state;
        // display new board
        if (response.state != 4) {
          this.state = response.state;
          for (let i in response.board) {
            //console.log("row: "+Number(i));
            let row = response.board[Number(i)];
            for (let j in response.board[i]) {
              //console.log("col: "+Number(j));
              let cell = response.board[Number(i)][Number(j)];
              //console.log("cell: "+cell);
              if (cell != "blank") {
                let idx = ((this.heightSize - Number(i) - 1) * this.widthSize) + Number(j);
                this.cells[idx] = cell;
              }
            }
          }
          // refresh score
          if (response.state != 0) {
            this.gameinfoService.sendInfo(response.state);
          }
        }
       });
    }

}
