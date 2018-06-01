package com.mycompany.myapp.web.rest.domineering;

import java.util.ArrayList;

import com.mycompany.myapp.web.rest.domineering.Main.Fill;

public interface AI {
    public ArrayList<Pair<Case,Case>> moveList(Case[][] table,Fill fill);
    Fill getMyFill();
    public void chooseCaseToFill(Case[][] table);
    public void play(Case[][] table, Pair<Case,Case> move,Fill fill);
}
