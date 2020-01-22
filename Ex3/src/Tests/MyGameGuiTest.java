package Tests;

import GUI.MyGameGui;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class MyGameGuiTest {
    private MyGameGui mgg;

    @BeforeEach
    void init() throws JSONException, InterruptedException {
        for (int i=22;i<24;i++)
            this.mgg=new MyGameGui(i);
        System.exit(0);
    }

    @org.junit.jupiter.api.Test
    void initGui() {
    }

    @org.junit.jupiter.api.Test
    void startPlaying() {
    }
}