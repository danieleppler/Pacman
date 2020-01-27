package Tests;

import gameClient.UI;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

class UITest {
        private UI u;


        @Test
        void startGameTest() throws JSONException, InterruptedException {
            this.u=new UI(20);
            u.mg.setPaths("C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\images\\coin.jpg","C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\images\\cherry.png","C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\images\\taz.png","C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\images\\node.png");
            this.u.startGame();
            Thread.currentThread().join();
        }
}