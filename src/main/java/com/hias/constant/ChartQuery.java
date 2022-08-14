package com.hias.constant;

public class ChartQuery {
    public static final String MEMBER_LOCATION_CHART_QUERY = "SELECT province_name key, COUNT(member_name) value " +
            "FROM HIAS.DISTRICT d INNER JOIN HIAS.PROVINCE p ON d.province_no = p.province_no " +
            "INNER JOIN HIAS.MEMBER m ON d.district_no = m.district_no " +
            "WHERE 1 = 1 %s" +
            "GROUP BY province_name";

    public static final String MEMBER_ONBOARD_CHART_QUERY = "SELECT EXTRACT(YEAR FROM start_date) AS key, COUNT(member_no) value\n" +
            "FROM HIAS.member %s\n" +
            "GROUP BY EXTRACT(YEAR FROM start_date)\n" +
            "ORDER BY key";
}
