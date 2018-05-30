import { Injectable } from '@angular/core';

@Injectable()
export class GameinfoService {
  private game: string;
  private scoreIA: number;
  private scorePlayer: number;
  private scoreDraw: number;
  private gameFinished: boolean;
  private difficulty: string;

  constructor() { }

  newGame(game: string) {
    this.game = game;
    this.scoreIA = 0;
    this.scorePlayer = 0;
    this.scoreDraw = 0;
    this.gameFinished = false;
    this.difficulty = null;
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
    switch(this.difficulty) {
      case "easy" : return 0;
      case "medium": return 1;
      case "hard": return 2;
      case "very-hard": return 3;
      case "very-very-hard": return 4;
    }
  }
  setDifficulty(difficulty: string) {
    this.difficulty = difficulty;
  }
}
