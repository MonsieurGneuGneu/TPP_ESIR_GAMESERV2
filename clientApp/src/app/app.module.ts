import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';


import { AppComponent } from './app.component';
import { GameListComponent } from './game-list/game-list.component';
import { GamesComponent } from './games/games.component';
import { MorpionComponent } from './games/morpion/morpion.component';
import {CellComponent} from './games/morpion/cell.component';
import {BoardComponent} from './games/morpion/board.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { GameDetailsComponent } from './game-details/game-details.component';
import { MatToolbarModule } from '@angular/material';
import {MatButtonModule} from '@angular/material/button';
import {MatDividerModule} from '@angular/material/divider';
import {MatSelectModule} from '@angular/material/select';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    GameListComponent,
    GamesComponent,
    MorpionComponent,
    CellComponent,
    BoardComponent,
    GameDetailsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    MatToolbarModule,
    MatButtonModule,
    MatDividerModule,
    MatSelectModule,
    ReactiveFormsModule,
    NoopAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
