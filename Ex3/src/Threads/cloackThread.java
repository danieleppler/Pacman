package Threads;

import GUI.MyGameGui;
import org.junit.platform.engine.support.descriptor.FileSystemSource;

import javax.swing.*;
import java.awt.*;

public class cloackThread implements Runnable{
    private MyGameGui mgg;
    private JLabel cloak;
    public cloackThread(MyGameGui mgg)
    {
        this.mgg=mgg;
        cloak=new JLabel("");
        cloak.setBackground(Color.red);
        this.mgg.getFrame().add(cloak);
    }
    @Override
    public void run() {
        while (this.mgg.getGuiObject().getGame().isRunning()){
            cloak.setText("time to end: " + String.valueOf((this.mgg.getGuiObject().getGame().timeToEnd()/1000)));
            cloak.setBackground(Color.red);
            //System.out.println("the clock is ticking");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }
    }
}
