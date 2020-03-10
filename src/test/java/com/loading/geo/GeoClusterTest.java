package com.loading.geo;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 *
 * @author Lo_ading
 * @version 1.0.0
 * @date 2020/3/10
 */
public class GeoClusterTest {

    @Test
    public void dbscan() {
        String param = "40.263855\t116.625509\n" +
                "40.277043\t116.652108\n" +
                "40.266317\t116.631658\n" +
                "40.277169\t116.651286\n" +
                "40.276841\t116.650922\n" +
                "37.574134\t118.552578\n" +
                "40.27812\t116.649567\n" +
                "40.276552\t116.650055\n" +
                "40.276616\t116.650054\n" +
                "40.263698\t116.62527\n" +
                "40.276609\t116.649974\n" +
                "38.526571\t114.977288\n" +
                "38.522857\t114.975468\n" +
                "41.221535\t123.060864\n" +
                "38.522738\t114.975672\n" +
                "34.840778\t111.210388\n" +
                "38.513323\t114.955268\n" +
                "35.728333\t115.250091\n" +
                "38.522506\t114.976507\n" +
                "34.855482\t118.024524\n" +
                "38.522753\t114.975738\n" +
                "38.522607\t114.976167\n" +
                "38.522788\t114.975825\n" +
                "38.523417\t114.973947\n" +
                "35.040168\t109.485067";

        List<GeoPoint> geoPoints = new ArrayList<>();
        for(String geoStr : param.split("\n")){
            String[] geo = geoStr.split("\t");
            GeoPoint geoPoint = new GeoPoint(Double.parseDouble(geo[0]), Double.parseDouble(geo[1]));
            geoPoints.add(geoPoint);
        }

        System.out.println(geoPoints);

        List<List<GeoPoint>> cluster = GeoCluster.dbscan(geoPoints, 5000, 1);

        System.out.println(cluster);
    }

}
