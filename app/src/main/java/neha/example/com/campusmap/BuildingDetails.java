package neha.example.com.campusmap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by np6023 on 10/27/16.
 */
public class BuildingDetails {

    public ArrayList<HashMap<String,String>> myBuildList = new ArrayList<HashMap<String,String>>();

    public BuildingDetails(){
        HashMap<String,String> build1 = new HashMap<String,String>();
        HashMap<String,String> build2 = new HashMap<String,String>();
        HashMap<String,String> build3 = new HashMap<String,String>();
        HashMap<String,String> build4 = new HashMap<String,String>();
        HashMap<String,String> build5 = new HashMap<String,String>();
        HashMap<String,String> build6 = new HashMap<String,String>();

        build1.put("sname","KL");
        build1.put("name","KING LIBRARY");
        build1.put("address","Dr. Martin Luther King, Jr. Library, 150 East San Fernando Street, San Jose, CA 95112");
        build1.put("lat","37.335716");
        build1.put("lon","-121.885213");
        build1.put("ix","60");
        build1.put("iy","215");
        myBuildList.add(build1);

        build2.put("sname","EB");
        build2.put("name","ENGINEERING BUILDING");
        build2.put("address","San José State University Charles W. Davidson College of Engineering, 1 Washington Square, San Jose, CA 95112");
        build2.put("lat","37.337359");
        build2.put("lon","-121.881909");
        build2.put("ix","280");
        build2.put("iy","220");
        myBuildList.add(build2);

        build3.put("sname","YUH");
        build3.put("name","YOSHIHIRO UCHIDA HALL");
        build3.put("address","Yoshihiro Uchida Hall, San Jose, CA 95112");
        build3.put("lat","37.333492");
        build3.put("lon","-121.883756");
        build3.put("ix","50");
        build3.put("iy","360");
        myBuildList.add(build3);

        build4.put("sname","SU");
        build4.put("name","STUDENT UNION");
        build4.put("address","Student Union Building, San Jose, CA 95112");
        build4.put("lat","37.336361");
        build4.put("lon","-121.881282");
        build4.put("ix","280");
        build4.put("iy","270");
        myBuildList.add(build4);

        build5.put("sname","BBC");
        build5.put("name","BOCCARDO BUSINESS COMPLEX");
        build5.put("address","Boccardo Business Complex, San Jose, CA 95112");
        build5.put("lat","37.336530");
        build5.put("lon","-121.878717");
        build5.put("ix","390");
        build5.put("iy","320");
        myBuildList.add(build5);

        build6.put("sname","SPG");
        build6.put("name","SOUTH PARKING GARAGE");
        build6.put("address","San Jose State University South Garage, 330 South 7th Street, San Jose, CA 95112");
        build6.put("lat","37.333385");
        build6.put("lon","-121.880264");
        build6.put("ix","160");
        build6.put("iy","460");
        myBuildList.add(build6);
    }
}
