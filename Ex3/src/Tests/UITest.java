package Tests;

import algorithms.UI;
import org.json.JSONException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UITest {
        private UI u;


        @Test
        void startGameTest() throws JSONException, InterruptedException {
            this.u=new UI(2);
            this.u.mg.setPaths("C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\coin.jpg","C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\cherry.png","C:\\Users\\danie\\IdeaProjects\\Ex3\\src\\taz.png");
            this.u.startGame();
        }
}