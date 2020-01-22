package algorithms;

import GUI.MyGameGui;
import org.json.JSONException;

public class UI {
    MyGameGui mg;

    public UI(int l) throws JSONException, InterruptedException {
        mg=new MyGameGui(l);
        mg.drawPanel();
    }

    public void startGame() throws JSONException, InterruptedException {
        mg.initGame();
        mg.printReasult();
    }
}
