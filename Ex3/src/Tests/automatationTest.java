package Tests;

import gameClient.automatation;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

class automatationTest {
    private automatation a;
    @Test
    void startGame() throws JSONException, InterruptedException {
        int l=0;
        long id=205487770;
        a=new automatation(l,"C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\Data\\",id);
        a.mg.setPaths("C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\images\\coin.jpg","C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\images\\cherry.png","C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\images\\taz.png","C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\images\\node.png");
        a.startGame(l);
}
}