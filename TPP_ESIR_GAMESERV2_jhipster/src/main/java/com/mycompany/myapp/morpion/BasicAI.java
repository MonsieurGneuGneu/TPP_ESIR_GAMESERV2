package com.mycompany.myapp.morpion;

import java.util.ArrayList;
import java.util.Random;

class BasicAI {
    private Fill myFill;

    BasicAI(Fill myFill){
        this.myFill = myFill;
    }

    Fill getMyFill(){
        return myFill;
    }

    Case chooseCaseToFill(Case[][] table){

        /* 1) checks if AI can win (plays if found)*/
        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) {
                    if (hasToPlayHereToWin(anATable, table, myFill)) {
                        return anATable;
                    }
                }
            }
        }

        /* 2) checks if foe is winning on his next turn (plays if found)*/
        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) {
                    if (hasToPlayHereToDef(anATable, table, myFill)) {
                        return anATable;
                    }
                }
            }
        }

        /* 3) checks if foe is able to create a fork on his next turn (plays if found)*/
        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) {
                    if (hasToPlayHereToMakeAFork(anATable, table, myFill)) {
                        return anATable;
                    }
                }
            }
        }

        /* 4) checks if opponent is able to create a fork on his next turn (plays if found)*/
        for (Case[] aTable : table) {
            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) {
                    if (hasToPlayHereToPreventAFork(anATable, table, myFill)) {
                        return anATable;
                    }
                }
            }
        }

        /* 5) plays somewhere else (method yet to define, temporary = last free place)*/
        Case playLoc = table[table.length/2][table[table.length/2].length/2];
        if(playLoc.filledBy()==Fill.blank){
            return playLoc;
        }else{
            return playSomewhere(table);
        }
    }


    private static Case playSomewhere(Case[][] table){

        ArrayList<Case> res = new ArrayList<>();
        for(Case[] clist : table){
            for(Case c : clist){
                if(c.filledBy()==Fill.blank)res.add(c);
            }
        }
        if(table.length == 3 && res.size()==table.length*table.length-1){
            return table[0][0];
        }else{
            return res.get(new Random().nextInt(res.size()));
        }


    }



    /**
     * Computes if the AI has to play on a case to avoid losing
     *
     * @param _case
     * the case to check (assuming it's blank)
     * @param table
     * current state of the game
     * @return
     * true if the other cases of the row, col or diag all have the symbol of the foe
     */
    private static boolean hasToPlayHereToDef(Case _case, Case[][] table, Fill myFill){
        Fill oppFill = myFill==Fill.o?Fill.x:Fill.o;

        /*checking the diag1 if case is on diag1*/
        int count = 0;
        if (checkDiag1(_case, table, oppFill, count)) return true;

        /*checking diag2 if case is on diag2*/
        count = 0;
        if (checkDiag2(_case, table, oppFill, count)) return true;

        /*checking the column*/
        count=0;
        for (Case[] aTable : table) {
            if (aTable[_case.col()].filledBy() == oppFill) ++count;
        }
        if(count==table.length-1) return true;

        /*checking the row*/
        count=0;
        for(int i = 0;i<table.length;++i){
            if(table[_case.row()][i].filledBy()==oppFill)++count;
        }
        return count == table.length - 1;

    }



    /**
     * Computes if the AI has to play on a case to win
     *
     * @param _case
     * the case to check (assuming it's blank)
     * @param table
     * current state of the game
     * @return
     * true if the other cases of the row, col or diag all have the symbol of the AI
     */
    private static boolean hasToPlayHereToWin(Case _case, Case[][] table, Fill myFill){
        /*checking the diag1 if case is on diag1*/
        int count = 0;
        if (checkDiag1(_case, table, myFill, count)) return true;

        /*checking diag2 if case is on diag2*/
        count = 0;
        if (checkDiag2(_case, table, myFill, count)) return true;

        /*checking the column*/
        count=0;
        for (Case[] aTable : table) {
            if (aTable[_case.col()].filledBy() == myFill) ++count;
        }
        if(count==table.length-1) return true;

        /*checking the row*/
        count=0;
        for(int i = 0;i<table.length;++i){
            if(table[_case.row()][i].filledBy()==myFill)++count;
        }
        return count == table.length - 1;

    }

    private static boolean hasToPlayHereToMakeAFork(Case _case, Case[][] table, Fill myFill){
        table[_case.row()][_case.col()].fill(myFill);

        /*number of way to win at the next turn*/
        int count = 0;

        count = getWinCount(table, myFill, count);
        table[_case.row()][_case.col()].fill(Fill.blank);

        return count >= 2;
    }


    private static boolean hasToPlayHereToPreventAFork(Case _case, Case[][] table, Fill myFill){
        Fill oppFill = myFill==Fill.o?Fill.x:Fill.o;
        table[_case.row()][_case.col()].fill(oppFill);

        /*number of way to win at the next turn*/
        int count = 0;

        count = getWinCount(table, oppFill, count);
        table[_case.row()][_case.col()].fill(Fill.blank);

        return count >= 2;
    }

    private static int getWinCount(Case[][] table, Fill oppFill, int count) {
        for (Case[] aTable : table) {

            for (Case anATable : aTable) {
                if (anATable.filledBy() == Fill.blank) {
                    if (hasToPlayHereToWin(anATable, table, oppFill)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }


    private static boolean checkDiag2(Case _case, Case[][] table, Fill oppFill, int count) {
        if(_case.col()+_case.row()==table.length-1){
            for(int i = 0;i<table.length;++i){
                if(table[i][table.length-i-1].filledBy()==oppFill)++count;
            }
            return count == table.length - 1;
        }
        return false;
    }

    /**
     * Assuming table has same width and height
     *
     * @param _case
     * the case to check (assuming it's blank)
     * @param table
     * current state of the game
     * @return
     * true if this diag contains n-1 oppFill symbol
     */
    private static boolean checkDiag1(Case _case, Case[][] table, Fill oppFill, int count) {
        if(_case.col()==_case.row()){
            for(int i = 0;i<table.length;++i){
                if(table[i][i].filledBy()==oppFill)++count;
            }
            return count == table.length - 1;
        }
        return false;
    }
}
