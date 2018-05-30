package com.mycompany.myapp.web.rest.connect4;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.web.rest.connect4.*;

@RestController
//@Secured(AuthoritiesConstants.ADMIN)
@RequestMapping("/api")
public class RemoteCallConnect4 {
	
    Case[][] table;
    AI AI;
	
	@GetMapping("/connect4/init")
	public String init(@RequestParam(value = "startWidth", defaultValue = "7")int width,@RequestParam(value = "startHeight", defaultValue = "6")int height,@RequestParam(value = "difficulty", defaultValue = "1")int difficulty) {
		table = new Case[height][width];
        for(int i = 0;i<table.length;++i){
            for(int j = 0;j<table[0].length;++j){
                table[i][j] = new Case(i,j);
            }
        }
        AI = new AI(Fill.x,difficulty);

        JSONObject obj = new JSONObject();
        obj.put("board", getFills(table));

        return obj.toString();
	}
	
	/** 
	 * @param playLoc
	 * @return
	 * board + 
	 * state (as in other games)
	 * state = 4 if player played on a full column
	 */
	@GetMapping("/connect4/play")
	public String play(@RequestParam(value = "column")int playLoc) {
		
		int a = Main.minrow(table, playLoc);
		
		if(a==-1) {
			JSONObject obj = new JSONObject();
	        obj.put("board", getFills(table));
	        obj.put("state", 4);

	        return obj.toString();
		}
		table[a][playLoc].fill(Fill.o);//player plays
		
		int gameState = Main.detectEnd(table, table[a][playLoc], Fill.o);
        
        if(gameState==0){
            //AI plays
            Case c = AI.chooseCaseToFill(table);
            gameState = Main.detectEnd(table, c, Fill.x);
        }//else do nothing
		
        JSONObject obj = new JSONObject();
        obj.put("board", getFills(table));
        obj.put("state", gameState);

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
