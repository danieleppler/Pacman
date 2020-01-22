package Tests;

import GUI.MyGameGui;
import algorithms.automatation;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class automatationTest {
    private automatation a;
//8,9,11
    @Test
    void startGame() throws JSONException, InterruptedException {
        int l=21;
        a=new automatation(l,"C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\elements\\");
        a.startGame(l);
}
}