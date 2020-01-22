package Threads;

import Data_Structure.robot;
import GUI.MyGameGui;
import GUI.gui_Object;
import algorithms.KML_Logger;
import org.json.JSONException;
import org.junit.platform.engine.support.descriptor.FileSystemSource;
import utils.StdDraw;

import javax.print.DocFlavor;

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
                Thread.sleep(100);
                StdDraw.clear();

            } catch (InterruptedException | JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
