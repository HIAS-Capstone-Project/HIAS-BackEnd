package com.hias.constant;

public class ChartQuery {
    public static final String MEMBER_LOCATION_CHART_QUERY = "SELECT province_name key, COUNT(member_name) value " +
            "FROM HIAS.DISTRICT d INNER JOIN HIAS.PROVINCE p ON d.province_no = p.province_no " +
            "INNER JOIN HIAS.MEMBER m ON d.district_no = m.district_no " +
            "WHERE 1 = 1 %s" +
            "GROUP BY province_name";

    public static final String MEMBER_ONBOARD_CHART_QUERY = "SELECT EXTRACT(YEAR FROM start_date) AS key, COUNT(member_no) value\n" +
            "FROM HIAS.member m \n" +
            "WHERE 1 = 1 %s \n" +
            "GROUP BY EXTRACT(YEAR FROM start_date) " +
            "ORDER BY key";

    public static final String CLAIM_STATUS_CHART_QUERY = "SELECT c.status_code key, COUNT(c.claim_no) value\n" +
            "FROM HIAS.claim c\n" +
            "WHERE c.member_no IN(\n" +
            "    SELECT m.member_no\n" +
            "    FROM HIAS.member m\n" +
            "    WHERE 1 = 1 %s\n" +
            ")\n" +
            "GROUP BY c.status_code";
}
