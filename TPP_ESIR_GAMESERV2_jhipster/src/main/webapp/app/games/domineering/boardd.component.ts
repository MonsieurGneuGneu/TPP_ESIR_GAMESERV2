import { Component, OnInit, Input } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { SERVER_API_URL } from '../../app.constants';
import { first } from 'rxjs/operators';

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
  @Input() startSize;
  private difficulty: number = 4;
  private cells: string[] = []; // local table startSize&startSize, copy of back
  private turn: string;
  private state: number;
  private winner = null;
  private firstClick: number;

  constructor(private http: HttpClient) { }

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
    let params = new HttpParams()
            .set('startSize', String(this.startSize))
            .set('difficulty',String(this.difficulty));

    // post request to init with posibly size of board
    this.http.get<any>(SERVER_API_URL + '/api/domineering/init', { params: params }).subscribe((response) => {
      if (response){
        //console.log(response);
        for (let i = 0; i < this.cells.length; i++) {
          this.cells[i] = null;
        }
        
      }
    });
    console.log(this.cells);

    this.turn = 'v';
    this.state = 0;
    this.winner = null;
  }
  
  goodMove(x1, y1, x2, y2, move): boolean {
    return (x1 == move.x1) && (y1 == move.y1) && (x2 == move.x2) && (y2 == move.y2);
  }

  clickHandler(idx: number) {
    console.log(idx);
    if ((this.state == 0) && this.turn =='v') {
      // verfify if first cell clicked
      if (this.firstClick) {
        // playable?
        // get possible moves
        this.http.get<any>(SERVER_API_URL + '/api/domineering/canPlay').subscribe((response) => {
          if (response){
            console.log(response);
            let x1 = this.firstClick/this.startSize;
            let y1 = this.firstClick%this.startSize;
            let x2 = idx/this.startSize;
            let y2 = idx%this.startSize;

            for (let i in response.moves) {
              let move = response.moves[i];
              if(this.goodMove(x1, y1, x1, y2, move)){
                console.log("good move!");
                this.clickOnCell(x1, y1, x2, y2);
                this.firstClick = null;
              }
            }
          }
        });  
      }

      else {
        this.firstClick = idx;
      }
      
      
      //this.clickOnCell(idx);
    }
  }
 
  clickOnCell(x1:number, y1:number, x2:number, y2:number) {
       let params = new HttpParams()
           .set('x1', String(x1))
           .set('y2', String(y2))
           .set('x1', String(x2))
           .set('y2', String(y2));
 
       this.http.get<any>(SERVER_API_URL+'/api/domineering/play', {params: params})
       .subscribe(response => { 
         this.state = response.state;
 
         if (this.state == 1) {
           this.winner = "You";
         }
         else if (this.state == 2) {
           this.winner = "A.I";
         }
 
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
         //console.log(this.cells);
         //console.log(response);
       });
    }

}
