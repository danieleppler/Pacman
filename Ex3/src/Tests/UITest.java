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
            this.u.startGame();
        }
}