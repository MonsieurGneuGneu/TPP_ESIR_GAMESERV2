package com.mycompany.myapp.web.rest.hex;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        int size = 4;
        Case[][] table = new Case[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                table[i][j] = new Case(i, j);
            }
        }

        // Le rouge essaye de lier une colonne
        // Le bleu essaye de lier une ligne

        /* Initialisation of AI */
        AI redAI = new RandomAI(size, Fill.r);
        AI blueAI = new RandomAI(size, Fill.b);

        System.out.println(printTable(table));
        /* GAME LOOP */
        while (true) {
            /* blueAI TURN */
            Case c = blueAI.chooseCaseToFill(table);
            System.out.println(printTable(table));
            if (detectEnd(table, c, true) == 1) {
                System.out.println("Blue Player win");
                break;
            }

            long time = System.nanoTime();
            while (System.nanoTime() < time + 1000000000)
                ;// wait 1s

            /* redAI TURN */
            c = redAI.chooseCaseToFill(table);
            System.out.println(printTable(table));
            if (detectEnd(table, c, false) == 2) {
                System.out.println("Red Player win");
                break;
            }
            time = System.nanoTime();
            while (System.nanoTime() < time + 1000000000)
                ;// wait 1s

        }

    }

    /**
     * A case(n,n)'s neighbors are : n-1,n-1 n-1,n n,n-1 n,n+1 n+1,n n+1,n+1
     * 
     * @param loc
     *            the place to check
     * @param table
     *            game state
     * @return arraylist of neighbors
     */
    private static ArrayList<Case> neighbors(Case loc, Case[][] table) {
        ArrayList<Case> res = new ArrayList<Case>();

        if (loc.row() > 0) {
            res.add(table[loc.row() - 1][loc.col()]);
        }

        if (loc.col() > 0) {
            if (loc.row() > 0) {
                res.add(table[loc.row() - 1][loc.col() - 1]);
            }
            res.add(table[loc.row()][loc.col() - 1]);
        }

        if (loc.col() < table.length - 1) {
            res.add(table[loc.row()][loc.col() + 1]);
            if (loc.row() < table.length - 1) {
                res.add(table[loc.row() + 1][loc.col() + 1]);
            }
        }

        if (loc.row() < table.length - 1) {
            res.add(table[loc.row() + 1][loc.col()]);
        }

        return res;
    }

    /**
     * marks all cases as "not visited" to get ready for a DFS
     * 
     * @param table
     *            game state
     */
    private static void initForSearch(Case[][] table) {
        for (Case[] clist : table) {
            for (Case c : clist) {
                c.unmark();
                c.setFather(new Case(-1, -1));
            }
        }
    }

    /**
     * Checks game state
     * 
     * @param table
     *            game state
     * @param playLoc
     *            play location
     * @param isPlayer
     *            true if player of playLoc is real player
     * @return 0 if not finished 1 if player won 2 if player lost
     */
    private static int detectEnd(Case[][] table, Case playLoc, boolean isPlayer) {
        Fill currPlayer = playLoc.filledBy();
        ArrayList<ArrayList<Case>> paths = new ArrayList<ArrayList<Case>>();
        if (currPlayer == Fill.r) {// links top and bottom
            for (int j = 0; j < table[0].length; ++j) {
                if (table[0][j].filledBy() == currPlayer) {
                    paths.add(findAWay(table, table[0][j], currPlayer));
                } else if (table[table.length - 1][j].filledBy() == currPlayer) {
                    paths.add(findAWay(table, table[table.length - 1][j], currPlayer));
                }
            }
        } else if (currPlayer == Fill.b) {// links sides
            for (int i = 0; i < table.length; ++i) {
                if (table[i][0].filledBy() == currPlayer) {
                    paths.add(findAWay(table, table[i][0], currPlayer));
                } else if (table[i][table.length - 1].filledBy() == currPlayer) {
                    paths.add(findAWay(table, table[i][table.length - 1], currPlayer));
                }
            }
        }
        if (isPlayer) {
            for (ArrayList<Case> path : paths) {
                if ((exists(path, new Predicate<Case>() {

                    @Override
                    public boolean test(Case c) {
                        return false;// TODO vérif qu'une case est sur un bord
                    }
                })) && (exists(path, new Predicate<Case>() {

                    @Override
                    public boolean test(Case c) {
                        return false;// TODO vérif qu'une case est sur l'autre
                                     // bord
                    }
                }))) {

                }
            }
        } else {
            for (ArrayList<Case> path : paths) {
                if ((exists(path, new Predicate<Case>() {

                    @Override
                    public boolean test(Case c) {
                        return false;// TODO vérif qu'une case est sur un bord
                    }
                })) && (exists(path, new Predicate<Case>() {

                    @Override
                    public boolean test(Case c) {
                        return false;// TODO vérif qu'une case est sur l'autre
                                     // bord
                    }
                }))) {

                }
            }
        }

        return 0;
    }

    private static <T> boolean exists(ArrayList<T> tlist, Predicate<T> p) {
        boolean res = false;
        for (T t : tlist) {
            res |= p.test(t);
        }
        return res;
    }

    private static <T> ArrayList<T> reverse(ArrayList<T> list) {
        ArrayList<T> res = new ArrayList<T>();

        for (int i = list.size() - 1; i >= 0; --i) {
            res.add(list.get(i));
        }
        return res;
    }

    /**
     * Checks if the player has won
     * 
     * @param table
     *            game state
     * @param endNode
     *            the outer node of the checked path
     * @return [null] if no way found else, win path
     */
    private static ArrayList<Case> findAWay(Case[][] table, Case endNode, Fill player) {
        ArrayList<Case> res = new ArrayList<Case>();

        initForSearch(table);
        explore(table, player);
        Case current = endNode;
        while (current.row() != -1 && current.col() != -1) {
            res.add(current);
            current = current.getFather();
        }

        return reverse(res);

    }

    /**
     * Fully explores a connex component
     * 
     * @param table
     *            game state
     * @param player
     *            last played Fill (r links top and bottom, b links sides)
     */
    private static void explore(Case[][] table, Fill player) {
        for (Case[] clist : table) {
            for (Case c : clist) {
                if (!c.marked()) {
                    // check loc or row depending on player
                    if (player == Fill.r) {
                        if (c.row() == table.length - 1 || c.row() == 0) {
                            if (c.filledBy() == Fill.r) {
                                c.setFather(new Case(-1, -1));
                                DFS(table, c);
                            }
                        }
                    } else {
                        if (c.col() == table[0].length - 1 || c.col() == 0) {
                            if (c.filledBy() == Fill.b) {
                                c.setFather(new Case(-1, -1));
                                DFS(table, c);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Depth First Search
     * 
     * @param table
     *            game state
     * @param loc
     *            start pos
     */
    private static void DFS(Case[][] table, Case loc) {
        loc.mark();
        for (Case a : neighbors(loc, table)) {
            if (!a.marked()) {
                a.setFather(loc);
                DFS(table, a);
            }
        }
    }

    private static String printTable(Case[][] table) {
        StringBuilder res = new StringBuilder();
        String indent = " ";
        String haut = "";
        for (int i = 0; i < table.length; i++) {
            haut += " / " + "\\";
        }

        String bas = "";
        for (int i = 0; i < table.length; i++) {
            bas += " \\ /";
        }
        res.append(indent + haut + "\n");
        for (int i = 0; i < table.length; i++) {
            res.append(indent + "|");
            for (int j = 0; j < table.length; j++) {
                switch (table[i][j].filledBy()) {
                case r:
                    res.append(" r |");
                    break;
                case b:
                    res.append(" b |");
                    break;
                case blank:
                    res.append(" - |");
                    break;
                default:
                    break;
                }
            }
            res.append("\n");
            if (i < table.length - 1) {
                res.append(indent + bas + " \\" + "\n");
            } else {
                res.append(indent + bas);
            }
            indent += "  ";
        }
        return res.toString();
    }

    enum Fill {
        r, b, blank
    }

}
