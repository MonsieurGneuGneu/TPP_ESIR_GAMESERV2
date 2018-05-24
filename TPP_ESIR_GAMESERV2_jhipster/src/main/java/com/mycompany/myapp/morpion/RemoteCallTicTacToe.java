package com.mycompany.myapp.morpion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/morpion")
public class RemoteCallTicTacToe {
    
    /**
     * @param playLoc
     *   a length-2 array containing coordinates of the play location of the player
     * @return
     *   a JSON containing the new table and the state of the game :
     *   0 : not finished
     *   1 : player win
     *   2 : player lose
     *   3 : draw
     */
    @GetMapping(value="/play",produces={"application/JSON"})
    public String playerMove(@RequestParam("playLoc") int[] playLoc){
        return "";
    }
    
    /**
     * 
     */
    @GetMapping(value="/init",produces={"application/JSON"})
    public String init(@RequestParam(value = "startSize", defaultValue = "3") int startSize){
        return "";
        
    }
    
    private String JSONify(String s){
        return "{\n\n}";//TODO
    }
}
