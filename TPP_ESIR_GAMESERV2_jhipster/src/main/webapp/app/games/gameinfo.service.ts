import { Injectable } from '@angular/core';

@Injectable()
export class GameinfoService {
  private game: string;
  private scoreIA: number = 0;
  private scorePlayer: number = 0;
  private scoreDraw: number = 0;
  private gameFinished: boolean;
  private difficulty: number;

  constructor() { }

  newGame(game: string) {
    if (this.game == game) {
      this.gameFinished = false;
    }
    else {
    this.game = game;
    this.scoreIA = 0;
    this.scorePlayer = 0;
    this.scoreDraw = 0;
    }
  }

  sendInfo(instantScore: number) {
    switch(instantScore) {
        case 1: this.scorePlayer++;
                break;
        case 2: this.scoreIA++;
                break;
        case 3: this.scoreDraw++;
                break;
    }
    this.gameFinished = true;
  }

  getGame(): string{
    return this.game;
  }
  getScoreAI(): number {
    return this.scoreIA;
  }
  getScorePlayer(): number {
    return this.scorePlayer;
  }
  getScoreDraw(): number{
    return this.scoreDraw;
  }
  getGameFinished(): boolean {
    return this.gameFinished;
  }
  getDifficulty(): number {
    return this.difficulty;
    /*switch(this.difficulty) {
      case "easy" : return 0;
      case "medium": return 1;
      case "hard": return 2;
      case "very-hard": return 3;
      case "very-very-hard": return 4;
    }*/
  }
  setDifficulty(difficulty: number) {
    this.difficulty = difficulty;
  }
  setGame(game: string) {
    this.game = game;
  }
}
