package gameClient;

import GUI.MyGameGui;
import org.json.JSONException;

public class UI {
    public MyGameGui mg;

    public UI(int l) throws JSONException, InterruptedException {
        mg=new MyGameGui(l,false,205487770);
        mg.initGui();
        mg.drawPanel();
    }

    public void startGame() throws JSONException, InterruptedException {
        mg.initGame();
    }
}
