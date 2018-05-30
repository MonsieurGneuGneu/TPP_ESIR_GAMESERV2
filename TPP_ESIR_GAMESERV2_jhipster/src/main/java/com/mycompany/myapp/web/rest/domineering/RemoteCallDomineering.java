package com.mycompany.myapp.web.rest.domineering;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.web.rest.domineering.Case;
import com.mycompany.myapp.web.rest.domineering.Main.Fill;

@RestController
//@Secured(AuthoritiesConstants.ADMIN)
@RequestMapping("/api")
public class RemoteCallDomineering {
    
    Case[][] table;
    AI AI;
    
    /**
     * Assuming the playLoc is currently blank
     * 
     * @param playLoc
     *   a length-2 array containing coordinates of the play location of the player
     * @return
     *   a JSON containing the new table and the state of the game :
     *   0 : not finished
     *   1 : player win
     *   2 : player lose
     */
    @GetMapping(path="/domineering/play", produces={"application/JSON"})
    public String playerMove(@RequestParam(value = "x1") int x1, @RequestParam(value = "y1") int y1, @RequestParam(value = "x2") int x2, @RequestParam(value = "y2") int y2){
        
        AI.play(table, new Pair<Case, Case>(table[x1][y1],table[x2][y2]), Fill.v);//player move
        
        int gameState = Main.detectEnd(table, AI, true);
        
        if(gameState==0){
            //AI plays
            AI.chooseCaseToFill(table);
            gameState = Main.detectEnd(table, AI, false);
        }//else do nothing

        JSONObject obj = new JSONObject();
        obj.put("board", getFills(table));
        obj.put("state", gameState);

        return obj.toString();
        //return "{board: "+JSONify(getFills(table))+", state: "+gameState+"}";
    }
    
    @GetMapping(path="/domineering/canPlay", produces={"application/JSON"})
    public String canPlay(){
        JSONObject obj = new JSONObject();
        int count = 0;
        List<JSONObject> board = new ArrayList<JSONObject>();
        for(int i = 0;i<table.length;++i){
            for(int j = 0;j<table[0].length;++j){
                if(table[i][j].filledBy()==Fill.blank){//check if neighbours are blank too
                    if(i!=0 && table[i-1][j].filledBy()==Fill.blank){
                        JSONObject objl = new JSONObject();
                        objl.put("x1",(i-1));
                        objl.put("x2", j);
                        objl.put("y1", i);
                        objl.put("y2", j);
                        board.add(objl);
                        ++count;
                    }
                    if(i!=table.length-1 && table[i+1][j].filledBy()==Fill.blank){
                        JSONObject objr = new JSONObject();
                        objr.put("x1",i);
                        objr.put("x2", j);
                        objr.put("y1", i+1);
                        objr.put("y2", j);
                        board.add(objr);
                        ++count;
                    }
                }
            }
        }
        obj.put("moves", board);

        return obj.toString();
        //return "{board: "+JSONify(getFills(table))+", state: "+gameState+"}";
    }
    
    /**
     * @return
     *   the table in a JSON form
     */
    @GetMapping(path="/domineering/init", produces={"application/JSON"})
    public String init(@RequestParam(value = "startSize", defaultValue = "5") int startSize,@RequestParam(value = "difficulty", defaultValue = "1") int difficulty){
        table = new Case[startSize][startSize];
        for(int i = 0;i<startSize;++i){
            for(int j = 0;j<startSize;++j){
                table[i][j] = new Case(i,j);
            }
        }
        if(difficulty>0){            
            AI = new LessStupidAI(Fill.h,difficulty);
        }else{
            AI = new StupidAI(Fill.h);
        }

        JSONObject obj = new JSONObject();
        obj.put("board", getFills(table));

        return obj.toString();
    }
    
    /**
     * table to fills
     */
    private String[][] getFills(Case[][] table){
        String[][] fills = new String[table.length][table[0].length];
        for(int i = 0;i<table.length;++i){
            for(int j = 0;j<table[i].length;++j){
                fills[i][j] = table[i][j].filledBy().toString();
            }
        }
        return fills;
    }
}
