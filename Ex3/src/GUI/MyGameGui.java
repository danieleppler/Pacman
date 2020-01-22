package GUI;

import Data_Structure.*;
import Server.Game_Server;
import Threads.cloackThread;
import Threads.drawingThread;
import Threads.movingThread;
import algorithms.KML_Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferStrategy;
import java.net.http.HttpHeaders;
import java.util.*;
import java.util.List;

public class MyGameGui  {

    private gui_Object go;
    private JFrame frame;
    public KML_Logger kl;
    public String fruitAPath="";
    public String fruitBPath="";
    public String RobotPath="";

    public MyGameGui(int s) throws JSONException, InterruptedException {
        this.frame = new JFrame();
        this.frame.setLayout(new FlowLayout());
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.go = SingleGameCreator.create(s);
        this.kl=new KML_Logger(this);
    }
    public void setPaths(String fruita,String fruitb,String robot)
    {
        this.fruitAPath=fruita;
        this.fruitBPath=fruitb;
        this.RobotPath=robot;
    }
    public void initGame() throws JSONException, InterruptedException {
        this.go.game.startGame();
        System.out.println("the game had started");
        activateClock();
        drawingThread dt = new drawingThread(this);
        Thread t1 = new Thread(dt);
        t1.setName("drawing");
        t1.start();
        movingThread mt = new movingThread(this.go);
        Thread t2 = new Thread(mt);
        t2.setName("moving");
        t2.start();
    }

    public void printReasult() {
        double totalPoints = 0;
        for (robot r : this.go.getRobotsList()
        ) {
            totalPoints += r.getPoints();
        }
        JFrame gameOver = new JFrame();
        JOptionPane.showMessageDialog(gameOver, "The game has ended,your score is : " + totalPoints);
    }

    public void initGui() throws JSONException {
        //scaling the locations of the item to FIT our GUI
        //scaling the nodes location
        DGraph g = this.go.g;
        for (Map.Entry<Integer, node> entry : g.nodeCollection.entrySet()
        ) {
            String Position = entry.getValue().getInfo();
            StringTokenizer st = new StringTokenizer(Position, ",");
            String newPoisiton = "";
            String temp = st.nextToken();
            temp = temp.substring(10, temp.length());
            newPoisiton += Double.toString(scale(Double.parseDouble(temp), 35, 36, 50, 400)) + ',';
            newPoisiton += Double.toString(scale(Double.parseDouble(st.nextToken()), 32, 33, 0, 250)) + ',';
            newPoisiton += 0;
            entry.getValue().setInfo(newPoisiton);
        }
        drawBoard(24, 30, 115, 125, g);
    }

    private void activateClock() throws InterruptedException {
        this.frame.setVisible(true);
        cloackThread ct = new cloackThread(this);
        Thread t3 = new Thread(ct);
        t3.setName("clock ticking");
        t3.start();
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void drawPanel() {
        Button b1 = new Button("<---p1");
        b1.addActionListener(this::actionPerformed);
        Button b2 = new Button("p1--->");
        b2.addActionListener(this::actionPerformed);
        this.frame.add(b1);
        this.frame.add(b2);
        if (this.go.getRobotsList().size() == 2) {
            Button b3 = new Button("<---p2");
            b3.addActionListener(this::actionPerformed);
            Button b4 = new Button("p2--->");
            b4.addActionListener(this::actionPerformed);
            this.frame.add(b3);
            this.frame.add(b4);
        } else if (this.go.getRobotsList().size() == 3) {
            Button b3 = new Button("<---p2");
            b3.addActionListener(this::actionPerformed);
            Button b4 = new Button("p2--->");
            b4.addActionListener(this::actionPerformed);
            this.frame.add(b3);
            this.frame.add(b4);
            Button b5 = new Button("<---p3");
            b5.addActionListener(this::actionPerformed);
            Button b6 = new Button("p3--->");
            b6.addActionListener(this::actionPerformed);
            this.frame.add(b5);
            this.frame.add(b6);
        }
        this.frame.setVisible(true);
    }

    public void drawBoard(int minHeight, int maxHeight, int minWidth, int maxWidth, DGraph g) throws JSONException {
        //starting to draw
        StdDraw.setYscale(minHeight, maxHeight);
        StdDraw.setXscale(minWidth, maxWidth);
        //drawing nodes
        for (Map.Entry<Integer, node> entry : g.nodeCollection.entrySet()
        ) {
            StdDraw.setPenColor(Color.blue);
            StdDraw.setPenRadius(0.020);
            String temp = entry.getValue().getInfo();
            String[] point = new String[]{"", "", ""};
            int j = 0;
            for (int i = 10; i < temp.length(); i++) {
                if (temp.charAt(i) == ',')
                    j++;
                else point[j] += temp.charAt(i);
            }
            StdDraw.point(Double.parseDouble(point[0]), Double.parseDouble(point[1]));
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(Color.red);
            StdDraw.text(Double.parseDouble(point[0]), Double.parseDouble(point[1]), String.valueOf(entry.getKey()));
        }
        ArrayList<edge> drawnedEdges = new ArrayList<>();
        //drawing edges
        for (Map.Entry<Integer[], edge> entry : g.edgeCollection.entrySet()
        ) {
            boolean isDrawned = false;
            Iterator<edge> it = drawnedEdges.iterator();
            while (it.hasNext()) {
                edge temp = it.next();
                if (entry.getKey()[0] == temp.getDest())
                    if (entry.getKey()[1] == temp.getSrc()) {
                        isDrawned = true;
                        break;
                    }
            }
            StdDraw.setPenRadius(0.002);
            StdDraw.setPenColor(Color.green);
            dataStructure.node_data src = g.nodeCollection.get(entry.getKey()[0]);
            dataStructure.node_data dest = g.nodeCollection.get(entry.getKey()[1]);
            String temp1 = src.getInfo();
            String[] point1 = new String[]{"", "", ""};
            int j = 0;
            for (int i = 10; i < temp1.length(); i++) {
                if (temp1.charAt(i) == ',')
                    j++;
                else point1[j] += temp1.charAt(i);
            }
            String temp2 = dest.getInfo();
            String[] point2 = new String[]{"", "", ""};
            int k = 0;
            for (int i = 10; i < temp2.length(); i++) {
                if (temp2.charAt(i) == ',')
                    k++;
                else point2[k] += temp2.charAt(i);
            }
            Point3D point3 = new Point3D(Double.parseDouble(point1[0]), Double.parseDouble(point1[1]), 0);
            Point3D point4 = new Point3D(Double.parseDouble(point2[0]), Double.parseDouble(point2[1]), 0);
            String weight = String.valueOf(entry.getValue().getWeight());
            if (isDrawned == false) {
                StdDraw.line(point3.x(), point3.y(), point4.x(), point4.y());
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(Color.RED);
                StdDraw.text(point4.x() - point3.x(), point4.y() - point3.y(), weight);
                drawnedEdges.add(entry.getValue());
            }
        }
        drawFruitsAndRobots();
    }

    public void drawFruitsAndRobots() throws JSONException {
        drawFruits();
        int robotNumber = 0;
        for (String temp:this.go.getGame().getRobots()
             ) {
            robot r=new robot(temp);
            this.kl.placeMarkList+=this.kl.addPlaceMark("robot",r.getLocation());
        }
        for (robot r : this.go.getRobotsList()
        ) {
            drawRobot(r, robotNumber);
            robotNumber++;
        }
    }

    private void drawFruits() throws JSONException {
        for (String f:this.go.getGame().getFruits()
             ) {
            fruit f2=new fruit(f);
            if (f2.getType()==1)
                this.kl.placeMarkList+=this.kl.addPlaceMark("fruitA",f2.getLocation());
            else   this.kl.placeMarkList+=this.kl.addPlaceMark("fruitB",f2.getLocation());
        }
        ArrayList<fruit> updatedFruitList = SingleGameCreator.findingCurEdge(this.go.game);
        int i = 0;
        while (i < updatedFruitList.size()) {
            fruit temp = updatedFruitList.get(i);
            i++;
            if (temp.getType() == 1){
                StdDraw.picture(temp.getLocation().x(), temp.getLocation().y(), fruitAPath, 0.5, 0.09);}
            else{
                StdDraw.picture(temp.getLocation().x(), temp.getLocation().y(), fruitBPath, 0.7, 0.2);}
        }
        this.go.setFruitList(updatedFruitList);
    }

    private void drawRobot(robot r, int n) throws JSONException {
        //robot location parsing
        String robotToParse = this.go.game.getRobots().get(n);
        String robotLocationX = "";
        String robotLocationY = "";
        int count = 1;
        int posStarting = 0;
        int j = 0;
        while (j < robotToParse.length()) {
            if (robotToParse.charAt(j) == 'p')
                if (robotToParse.charAt(j + 1) == 'o')
                    posStarting = j + 6;
            j++;
        }
        robotToParse = robotToParse.substring(posStarting, robotToParse.length());
        for (int i = 0; i < robotToParse.length(); i++) {
            if (robotToParse.charAt(i) == '"')
                break;
            if (robotToParse.charAt(i) != ',') {
                if (count == 1)
                    robotLocationX += robotToParse.charAt(i);
                else if (count == 2) robotLocationY += robotToParse.charAt(i);
                else if (count == 3)
                    break;
            } else count++;
        }

        ArrayList<robot> updatedRobots = new ArrayList<>();
        int i = 0;
        while (i < this.go.game.getRobots().size()) {
            robot temp = new robot(this.go.game.getRobots().get(i));
            double x = scale(temp.getLocation().x(), 35, 36, 50, 400);
            double y = scale(temp.getLocation().y(), 32, 33, 0, 250);
            StdDraw.picture(r.getLocation().x(), r.getLocation().y(), RobotPath, 0.8, 0.2);
            temp.setLocation(new Point3D(x, y, 0));
            updatedRobots.add(temp);
            i++;
        }
        this.go.setRobotList(updatedRobots);
    }

    static double scale(double data, double r_min, double r_max, double t_min, double t_max) {

        double res = ((data - r_min) / (r_max - r_min)) * (t_max - t_min) + t_min;
        return res;
    }

    public void actionPerformed(ActionEvent e) {
        String click = e.getActionCommand();
        if (click.equals("p1--->")) {
            robot r = this.go.getRobotsList().get(0);
            while (this.go.game.chooseNextEdge(r.getId(), r.getCurrNodeDest()) != -1) {
            }
            HashMap<Integer, node> neighbors = new HashMap<>();
            Collection<dataStructure.edge_data> robotEdges = this.go.g.getE(r.getCurrNode());
            Collection<dataStructure.node_data> nodes = this.go.g.getV();
            //finding all the neighbors of the robot
            for (dataStructure.node_data n : nodes) {
                for (dataStructure.edge_data ed : robotEdges
                ) {
                    if (n.getTag() == ed.getDest())
                        neighbors.put(n.getKey(), (node) n);
                }
            }
            double max = 0;
            int rightestNode = 0;
            for (Map.Entry<Integer, node> n : neighbors.entrySet()
            ) {
                if (n.getValue().getLocation().x() > max) {
                    max = n.getValue().getLocation().x();
                    rightestNode = n.getKey();
                }
            }
            r.setCurrNodeDest(rightestNode);
            this.go.game.chooseNextEdge(r.getId(), r.getCurrNodeDest());
            r.setCurrNode(rightestNode);
            while (this.go.game.chooseNextEdge(r.getId(), r.getCurrNodeDest()) != -1) {
            }
            r.setCurrNodeDest(-1);
        } else if (click.equals("<---p1")) {
            robot r = this.go.getRobotsList().get(0);
            // while (this.go.game.chooseNextEdge(r.getId(),r.getCurrNodeDest())==-1){}
            HashMap<Integer, node> neighbors = new HashMap<>();
            Collection<dataStructure.edge_data> robotEdges = this.go.g.getE(r.getCurrNode());
            Collection<dataStructure.node_data> nodes = this.go.g.getV();
            //finding all the neighbors of the robot
            for (dataStructure.node_data n : nodes) {
                for (dataStructure.edge_data ed : robotEdges
                ) {
                    if (n.getTag() == ed.getDest())
                        neighbors.put(n.getKey(), (node) n);
                }
            }
            double min = Integer.MAX_VALUE;
            int leftesttNode = 0;
            for (Map.Entry<Integer, node> n : neighbors.entrySet()
            ) {
                if (n.getValue().getLocation().x() < min) {
                    min = n.getValue().getLocation().x();
                    leftesttNode = n.getKey();
                }
            }
            r.setCurrNodeDest(leftesttNode);
            this.go.game.chooseNextEdge(r.getId(), r.getCurrNodeDest());
            r.setCurrNode(leftesttNode);
            r.setCurrNodeDest(-1);
            // frame.repaint();
        } else if (click.equals("p2--->")) {
            robot r = this.go.getRobotsList().get(1);
            // while (this.go.game.chooseNextEdge(r.getId(),r.getCurrNodeDest())==-1){}
            HashMap<Integer, node> neighbors = new HashMap<>();
            Collection<dataStructure.edge_data> robotEdges = this.go.g.getE(r.getCurrNode());
            Collection<dataStructure.node_data> nodes = this.go.g.getV();
            //finding all the neighbors of the robot
            for (dataStructure.node_data n : nodes) {
                for (dataStructure.edge_data ed : robotEdges
                ) {
                    if (n.getTag() == ed.getDest())
                        neighbors.put(n.getKey(), (node) n);
                }
            }
            double min = Integer.MAX_VALUE;
            int leftesttNode = 0;
            for (Map.Entry<Integer, node> n : neighbors.entrySet()
            ) {
                if (n.getValue().getLocation().x() < min) {
                    min = n.getValue().getLocation().x();
                    leftesttNode = n.getKey();
                }
            }
            r.setCurrNodeDest(leftesttNode);
            this.go.game.chooseNextEdge(r.getId(), r.getCurrNodeDest());
            r.setCurrNode(leftesttNode);
            r.setCurrNodeDest(-1);
        } else if (click.equals("<---p2")) {
            robot r = this.go.getRobotsList().get(1);
            // while (this.go.game.chooseNextEdge(r.getId(),r.getCurrNodeDest())==-1){}
            HashMap<Integer, node> neighbors = new HashMap<>();
            Collection<dataStructure.edge_data> robotEdges = this.go.g.getE(r.getCurrNode());
            Collection<dataStructure.node_data> nodes = this.go.g.getV();
            //finding all the neighbors of the robot
            for (dataStructure.node_data n : nodes) {
                for (dataStructure.edge_data ed : robotEdges
                ) {
                    if (n.getTag() == ed.getDest())
                        neighbors.put(n.getKey(), (node) n);
                }
            }
            double min = Integer.MAX_VALUE;
            int leftesttNode = 0;
            for (Map.Entry<Integer, node> n : neighbors.entrySet()
            ) {
                if (n.getValue().getLocation().x() < min) {
                    min = n.getValue().getLocation().x();
                    leftesttNode = n.getKey();
                }
            }
            r.setCurrNodeDest(leftesttNode);
            this.go.game.chooseNextEdge(r.getId(), r.getCurrNodeDest());
            r.setCurrNode(leftesttNode);
            r.setCurrNodeDest(-1);
        } else if (click.equals("p3--->")) {
            robot r = this.go.getRobotsList().get(2);
            // while (this.go.game.chooseNextEdge(r.getId(),r.getCurrNodeDest())==-1){}
            HashMap<Integer, node> neighbors = new HashMap<>();
            Collection<dataStructure.edge_data> robotEdges = this.go.g.getE(r.getCurrNode());
            Collection<dataStructure.node_data> nodes = this.go.g.getV();
            //finding all the neighbors of the robot
            for (dataStructure.node_data n : nodes) {
                for (dataStructure.edge_data ed : robotEdges
                ) {
                    if (n.getTag() == ed.getDest())
                        neighbors.put(n.getKey(), (node) n);
                }
            }
            double min = Integer.MAX_VALUE;
            int leftesttNode = 0;
            for (Map.Entry<Integer, node> n : neighbors.entrySet()
            ) {
                if (n.getValue().getLocation().x() < min) {
                    min = n.getValue().getLocation().x();
                    leftesttNode = n.getKey();
                }
            }
            r.setCurrNodeDest(leftesttNode);
            this.go.game.chooseNextEdge(r.getId(), r.getCurrNodeDest());
            r.setCurrNode(leftesttNode);
            r.setCurrNodeDest(-1);
        } else if (click.equals("<---p3")) {
            robot r = this.go.getRobotsList().get(2);
            // while (this.go.game.chooseNextEdge(r.getId(),r.getCurrNodeDest())==-1){}
            HashMap<Integer, node> neighbors = new HashMap<>();
            Collection<dataStructure.edge_data> robotEdges = this.go.g.getE(r.getCurrNode());
            Collection<dataStructure.node_data> nodes = this.go.g.getV();
            //finding all the neighbors of the robot
            for (dataStructure.node_data n : nodes) {
                for (dataStructure.edge_data ed : robotEdges
                ) {
                    if (n.getTag() == ed.getDest())
                        neighbors.put(n.getKey(), (node) n);
                }
            }
            double min = Integer.MAX_VALUE;
            int leftesttNode = 0;
            for (Map.Entry<Integer, node> n : neighbors.entrySet()
            ) {
                if (n.getValue().getLocation().x() < min) {
                    min = n.getValue().getLocation().x();
                    leftesttNode = n.getKey();
                }
            }
            r.setCurrNodeDest(leftesttNode);
            this.go.game.chooseNextEdge(r.getId(), r.getCurrNodeDest());
            r.setCurrNode(leftesttNode);
            r.setCurrNodeDest(-1);
            //   frame.repaint();
        }
    }


    public gui_Object getGuiObject() {
        return this.go;
    }
}
