import { Component } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { SERVER_API_URL } from '../../app.constants';
import { Observable } from 'rxjs/Observable';

@Component({
 selector: 'jhi-my-board',
 template: `
 <div class=container>
 <div class="clearfix">
 <jhi-my-cell *ngFor="let cell of cells | slice: 0: 3; let i = index" [value]="cell" (userClick)=clickHandler(i)></jhi-my-cell><br>
 </div>

<div class="clearfix">
  <jhi-my-cell *ngFor="let cell of cells | slice: 3: 6; let i = index" [value]="cell" (userClick)=clickHandler(i+3)></jhi-my-cell><br>
</div>
<div class="clearfix">
  <jhi-my-cell *ngFor="let cell of cells | slice: 6; let i = index" [value]="cell" (userClick)=clickHandler(i+6)></jhi-my-cell><br>
</div>
 <div *ngIf="winner">
   winner is {{winner}}
 </div>
 </div>
 `,
 styles: [
   `.clearfix::after {
   content: "";
   clear: both;
   display: table;
}
   .container{
     margin: auto;
   }  
   `
 ]
})
export class BoardComponent {
 private cells: string[] = []; // local table 3*3, copy of back
 private turn: string = 'x';
 private state: number;
 private winner = null;
 private startSize: number = 3;

 constructor(private http: HttpClient) {}

 ngOnInit() {
   for (let i = 0; i < this.startSize * this.startSize; i++) {
     this.cells[i] = null;
   }

   this.init();
 }

 init() {
  let params = new HttpParams()
          .set('startSize', String(this.startSize));

   // post request to init with posibly size of board
  this.http.get<any>('/api/morpion/init', { params: params }).subscribe((response) => {
    if (response){
      //console.log(response);
      for (let i = 0; i < this.cells.length; i++) {
        this.cells[i] = null;
      }
      
     }
  });

    this.turn = 'x';
    this.state = 0;
    this.winner = null;
 }

 clickHandler(idx: number) {
   //console.log(idx);
   if ((this.state == 0) && this.turn =='x') {
     if (this.cells[idx] === null) {
       // send post request to back end with the cell clicked
       this.clickOnCell(idx);
       // this.changeTurn(); // --> I.A's turn in back end
     }

   }
 }

 changeTurn() {
   if (this.turn === 'x') {
     this.turn = 'o';
   } else {
     this.turn = 'x';
   }
 }

 clickOnCell(cell:number) {
      let params = new HttpParams()
          .set('x', String(Math.floor(cell/this.startSize)))
          .set('y', String(cell%this.startSize));

      this.http.get<any>(SERVER_API_URL+'/api/morpion/play', {params: params})
      .subscribe(response => { 
        this.state = response.state;

        if (this.state == 1) {
          this.winner = "You";
        }
        else if (this.state == 2) {
          this.winner = "A.I";
        }
        else if (this.state == 3) {
          this.winner = "neither you nor A.I";
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