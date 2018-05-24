import { Component } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { SERVER_API_URL } from '../../app.constants';
import { CSRFService } from '../../shared/auth/csrf.service';

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
  private gameover = false;
  private winner = null;
  
  constructor(private http: HttpClient, private csrfService:CSRFService) {}
  
  ngOnInit() {
    for (let i = 0; i < 9; i++) {
      this.cells[i] = null;
    }

    this.init();
  }

  init() {
    for (let i = 0; i < 9; i++) {
      this.cells[i] = null;
    }
    this.turn = 'x';
    this.gameover = false;
    this.winner = null;
    // post request size of board (int)

    let headers = new HttpHeaders().set('Content-Type','application/x-www-form-urlencoded')
    .set('Accept', 'application/json')
    .set('Set-Cookie', 'XSRF-TOKEN='+this.csrfService.getCSRF());    

    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Set-Cookie' : 'X-CSRF-TOKEN='+this.csrfService.getCSRF()// + '; JSESSIONID='+this.getSessionID()
    }),
    };

    this.http.post<any>('http://localhost:8080/' + 'api/morpion/init', httpOptions).subscribe((response) => {
      if (response){
        console.log(response);
      }
    });
  }

  clickHandler(idx: number) {
    console.log(idx);
    if (!this.gameover && this.turn =='x') {
      console.log('sth set');
      if (this.cells[idx] === null) {
        // send post request to back with cell clicked
        this.clickOnCell(this.cells);
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

  clickOnCell(cells: string[]){
        let x;
        let y;

        cells.forEach((item, index) => {
          if (item != null) {
            x = index % 3;
            y = index / 3;
          }
        });
        let headers = new HttpHeaders().set('Content-Type','application/x-www-form-urlencoded')
        .set('Accept', 'application/json')
        .set('Set-Cookie', '<cookie-list>')
        .set('Set-Cookie', 'X-CSRF-TOKEN='+this.csrfService.getCSRF())
        .set('Set-Cookie', 'JSESSIONID='+this.getSessionID());

        let httpOptions = {
          headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Set-Cookie': 'X-CSRF-TOKEN='+this.csrfService.getCSRF() + '; JSESSIONID='+this.getSessionID()
        }),
          x: x,
          y: y
        };
        let data = 'x=' +  x +
        '&y=' + y;

        this.http.post<any>('http://localhost:8080/' + 'api/morpion/play', httpOptions).subscribe((response) => {
          if (response){
            console.log(response);
          }
        });
    }

    getSessionID() {
      var name = "JSESSIONID=";
      var ca = document.cookie.split(';');
      for(var i=0; i<ca.length; i++) {
          var c = ca[i];
          while (c.charAt(0)==' ') c = c.substring(1);
          if (c.indexOf(name) !== -1) return c.substring(name.length,c.length);
      }
      return "";
  }
} 