import { Component, OnInit, Input } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { SERVER_API_URL } from '../../app.constants';
import { GameinfoService } from '../gameinfo.service';

@Component({
  selector: 'jhi-boardd',
  templateUrl: './boardd.component.html',
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
 }  
 `]
})
export class BoarddComponent implements OnInit {
  @Input() startSize: number;
  private game = "Domineering";
  private cells: string[] = []; // local table startSize&startSize, copy of back
  private turn: string;
  private state: number;
  private firstClick: number;

  constructor(private http: HttpClient, private gameinfoService: GameinfoService) { }

 reload(startSize: number) {
   this.startSize = startSize;
   this.ngOnInit();
 }
 
 ngOnInit() {
   for (let i = 0; i < this.startSize * this.startSize; i++) {
     this.cells[i] = 'null';
   }

   this.init();
 }

 init() {
    let difficulty = this.gameinfoService.getDifficulty();
    let params = new HttpParams()
            .set('startSize', String(this.startSize))
            .set('difficulty',String(difficulty));

    // post request to init with posibly size of board
    this.http.get<any>(SERVER_API_URL + '/api/domineering/init', { params: params }).subscribe((response) => {
      if (response){
        //console.log(response);
        for (let i = 0; i < this.cells.length; i++) {
          this.cells[i] = null;
        }
        
      }
    });
    this.gameinfoService.newGame(this.game);
    this.turn = 'v';
    this.state = 0;
  }

  clickHandler(idx: number) {
    // console.log(idx);
    if ((this.state == 0) && this.turn =='v') {
      // verfify if first cell clicked
      if (this.firstClick != null) {
        // playable?
        // ask the good moves
        this.http.get<any>(SERVER_API_URL + '/api/domineering/canPlay').subscribe((response) => {
          if (response){
            // console.log(response);
            let x1 = Math.floor(this.firstClick/this.startSize);
            let y1 = this.firstClick%this.startSize;
            // console.log("firstclick: ",x1, y1);
            let x2 = Math.floor(idx/this.startSize);
            let y2 = idx%this.startSize;
            // console.log("secondclick: ",x2, y2);

            // check if good move
            for (let i in response.moves) {
              let move = response.moves[i];
              // good move (not depending of which cell was clicked first)
              if((x1 == move.x1) && (y1 == move.y1) && (x2 == move.x2) && (y2 == move.y2) ||
                        (x2 == move.x1) && (y2 == move.y1) && (x1 == move.x2) && (y1 == move.y2)){
                // AI is long when high difficulty, this is to accelerate the rending
                this.cells[x1 * this.startSize + y1] = 'v';
                this.cells[x2 * this.startSize + y2] = 'v';
                this.turn = 'h';
                // send info
                this.clickOnCell(x1, y1, x2, y2);
                // leave loop
                break;
              }
            }
            // reinitalise firstClick either if good or bad move
            this.firstClick = null;
          }
        });  
      } else {
        this.firstClick = idx;
      }
    }
  }
 
  clickOnCell(x1:number, y1:number, x2:number, y2:number) {
       let params = new HttpParams()
           .set('x1', String(x1))
           .set('y1', String(y1))
           .set('x2', String(x2))
           .set('y2', String(y2));
 
       this.http.get<any>(SERVER_API_URL+'/api/domineering/play', {params: params})
       .subscribe(response => { 
        this.state = response.state;
        // display new board
         for (let i in response.board) {
           //console.log("row: "+Number(i));
           let row = response.board[Number(i)];
           for (let j in response.board[i]) {
             //console.log("col: "+Number(j));
             let cell = response.board[Number(i)][Number(j)];
             //console.log("cell: "+cell);
             if (cell != "blank") {
               let idx = Number(i) * this.startSize + Number(j);
               this.cells[idx] = cell;
             }
           }
         }
         this.turn = 'v';
         // refresh score
         if (response.state != 0) {
          this.gameinfoService.sendInfo(response.state);
         }
       });
    }

}
