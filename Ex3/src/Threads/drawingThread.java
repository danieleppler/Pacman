package Threads;

import GUI.MyGameGui;
import GUI.StdDraw;
import org.json.JSONException;

public class drawingThread implements Runnable {
    private MyGameGui go;

    public drawingThread(MyGameGui g) {
        this.go = g;
    }

    @Override
    public void run() {
        //StdDraw.clear();
        while (this.go.getGuiObject().getGame().isRunning()) {
            try {
                this.go.drawFruitsAndRobots();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // System.out.println("drawing");
            try {
                Thread.sleep(100);
                this.go.drawBoard(24, 30, 115, 125, this.go.getGuiObject().getGraph());
                Thread.sleep(70);
                StdDraw.clear();

            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        go.printReasult();
    }
}
