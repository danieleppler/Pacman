package gameClient;

import Data_Structure.*;
import GUI.MyGameGui;
import GUI.Point3D;
import GUI.SingleGameCreator;
import GUI.gui_Object;
import Server.Game_Server;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.Map;

public class KML_Logger {
    private MyGameGui mg;
    public String placeMarkList="";


    public KML_Logger(MyGameGui go) throws JSONException {
        this.mg=go;
    }

    public String writeKml(int level,String path_Name,String placeMarks) throws JSONException {
        String kmlConverted="";
        String start="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n<Document>"
               + "<name>"+level+"</name>"
        +"<open>"+level+"</open>";
        String style="<Style id=\"robot\">\n" +
                "  <IconStyle>\n" +
                "    <scale>1.3</scale>\n" +
                "    <Icon>\n" +
                "      <href>"+this.mg.RobotPath+"</href>\n" +
                "    </Icon>\n" +
                "  </IconStyle>\n" +
                "</Style>\n"+
                "<Style id=\"fruitA\">\n" +
                "  <IconStyle>\n" +
                "    <scale>1.3</scale>\n" +
                "    <Icon>\n" +
                "      <href>"+this.mg.fruitAPath+"</href>\n" +
                "    </Icon>\n" +
                "  </IconStyle>\n" +
                "</Style>\n"+
                "<Style id=\"fruitB\">\n" +
                "  <IconStyle>\n" +
                "    <scale>1.3</scale>\n" +
                "    <Icon>\n" +
                "      <href>"+this.mg.fruitBPath+"</href>\n" +
                "    </Icon>\n" +
                "  </IconStyle>\n" +
                "</Style>\n"+
                "<Style id=\"node\">\n" +
                "  <IconStyle>\n" +
                "    <scale>1.3</scale>\n" +
                "    <Icon>\n" +
                "      <href>"+this.mg.nodePath+"</href>\n" +
                "    </Icon>\n" +
                "  </IconStyle>\n" +
                "</Style>\n";
        String endging=" </Document>\n" +
                "</kml>\n";
        String temp2=this.mg.getGuiObject().getGame().getGraph();
        DGraph temp3=new DGraph(temp2);
        for (Map.Entry<Integer,node> entry:temp3.nodeCollection.entrySet()
             ) {
            placeMarks+=addPlaceMark("node",entry.getValue().getLocation());
        }
        kmlConverted=start+style+placeMarks+endging;
        try
        {
            String temp=path_Name+level+".kml";
            File file = new File(temp);
            PrintWriter pw = new PrintWriter(file);
            pw.write(kmlConverted);
            pw.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("could not find the specific file");
        }
        return kmlConverted;
    }

    public String addPlaceMark(String type, Point3D objectLocation)
    {
        String placeMark="<Placemark>\r\n" +
                "      <TimeStamp>\r\n" +
                "        <when>"+LocalTime.now()+"</when>\r\n" +
                "      </TimeStamp>\r\n" +
                "      <styleUrl>#"+type+"</styleUrl>\r\n" +
                "      <Point>\r\n" +
                "        <coordinates>"+objectLocation.x()+","+objectLocation.y()+"</coordinates>\r\n" +
                "      </Point>\r\n" +
                "    </Placemark>\r\n";
        return placeMark;
    }
}
