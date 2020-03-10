package com.loading.geo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * desc: Geo聚集 - DBScan算法
 * <p>
 * 参考逻辑 https://img-blog.csdnimg.cn/20190104224539972.png#pic_center
 *
 * @author Lo_ading
 * @version 1.0.0
 * @date 2020/3/9
 */
public class GeoCluster {

    /**
     * 地球赤道半径（千米）
     */
    private final static double EARTH_RADIUS = 6378.137;

    /**
     * 执行聚集
     *
     * @param points 点集合
     * @param eps  半径(米)
     * @param minPts  半径内最小包含点数
     * @return 聚集结果
     */
    public static List<List<GeoPoint>> dbscan(List<GeoPoint> points, double eps, int minPts) {

        Map<GeoPoint, Integer> pointsToCluster = new HashMap<>();
        List<GeoPoint> visitedPoints = new ArrayList<>();
        int clusterNum = 1;
        for (GeoPoint point : points) {

            //选择一个unvisited点P
            if (!visitedPoints.contains(point)) {

                //标记P为已访问
                visitedPoints.add(point);

                //寻找P点eps范围内的所有点集合N
                List<GeoPoint> nearPoints = getNearPoints(point, points, eps);

                //添加一个聚集C
                pointsToCluster.put(point, clusterNum);

                //如果点P附近的点集合N大于minPts则继续向下查询
                if (nearPoints.size() > minPts) {

                    //遍历P集合N中unvisited点p
                    for (int i = 0; i < nearPoints.size(); i++) {
                        GeoPoint nearPoint = nearPoints.get(i);
                        if (!visitedPoints.contains(nearPoint)) {

                            //设置点p已访问
                            visitedPoints.add(nearPoint);

                            //寻找p点eps范围内的所有点集合
                            List<GeoPoint> nearNearPoints = getNearPoints(nearPoint, points, eps);

                            //如果点p附近的点集合大于minPts，将这些点加入到集合N
                            if (nearNearPoints.size() >= minPts) {
                                nearPoints.addAll(nearNearPoints);
                            }
                        }

                        //如果点还不是任何聚集成员，把点加入聚集并移除噪点标示
                        if (!pointsToCluster.containsKey(nearPoint)) {
                            pointsToCluster.put(nearPoint, clusterNum);
                        }
                    }
                }
                clusterNum++;
            }
        }

        //输出结果
        Map<Integer, List<GeoPoint>> clusterToPoint = new HashMap<>();
        for (Map.Entry<GeoPoint, Integer> entry : pointsToCluster.entrySet()) {
            if (!clusterToPoint.containsKey(entry.getValue())) {
                clusterToPoint.put(entry.getValue(), new ArrayList<>());
            }
            clusterToPoint.get(entry.getValue()).add(entry.getKey());
        }
        return new ArrayList<>(clusterToPoint.values());
    }

    private static List<GeoPoint> getNearPoints(GeoPoint centerPoint, List<GeoPoint> points, double eps) {
        List<GeoPoint> nearPoints = new ArrayList<>();
        for (GeoPoint p : points) {
            double distance = getDistance(centerPoint, p);
            if (distance <= eps) {
                nearPoints.add(p);
            }
        }
        return nearPoints;
    }

    private static double getDistance(GeoPoint pointA, GeoPoint pointB) {
        double radLat1 = rad(pointA.getLat());
        double radLat2 = rad(pointB.getLat());
        double a = radLat1 - radLat2;
        double b = rad(pointA.getLon()) - rad(pointB.getLon());
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
