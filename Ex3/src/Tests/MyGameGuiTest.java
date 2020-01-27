package Tests;

import GUI.MyGameGui;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

   class MyGameGuiTest {
    private MyGameGui mgg;

    @BeforeEach
    void init() throws JSONException, InterruptedException {
        this.mgg=new MyGameGui();
    }

    @org.junit.jupiter.api.Test
    void initGui() {
    }

    @org.junit.jupiter.api.Test
    void startPlaying() {
    }

    @Test
    void printFromDB()
    {
        this.mgg.setPlayerID(205487770);
        Integer[] arr={290,580,580,500,580,580,580,290,580,290,1140};
        this.mgg.setLevelMaxMoves(arr);
        this.mgg.printFromDB();
    }
}