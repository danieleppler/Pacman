package Threads;

import GUI.MyGameGui;
import org.junit.platform.engine.support.descriptor.FileSystemSource;

import javax.swing.*;

public class cloackThread implements Runnable{
    private MyGameGui mgg;
    private JLabel cloak;
    public cloackThread(MyGameGui mgg)
    {
        this.mgg=mgg;
        cloak=new JLabel("");
        this.mgg.getFrame().add(cloak);
    }
    @Override
    public void run() {
        while (this.mgg.getGuiObject().getGame().isRunning()){
            cloak.setText(String.valueOf((this.mgg.getGuiObject().getGame().timeToEnd()/1000)));
            //System.out.println("the clock is ticking");
            }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
