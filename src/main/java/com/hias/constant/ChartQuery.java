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

    public static final String POLICY_BY_USAGE = "SELECT p.policy_name key, COUNT(m.member_no) value \n" +
            "FROM HIAS.policy p INNER JOIN HIAS.member m ON p.policy_no = m.policy_no\n" +
            "WHERE 1 = 1 %s\n" +
            "GROUP BY p.policy_name";

    public static final String BUSINESS_SECTOR = "SELECT bs.business_sector_name AS key, SUM(CASE WHEN cbs.client_no IS NULL THEN 0 ELSE 1 END) AS value\n" +
            "FROM HIAS.BUSINESS_SECTOR bs LEFT OUTER JOIN HIAS.CLIENT_BUSINESS_SECTOR cbs ON bs.business_sector_no = cbs.business_sector_no\n" +
            "GROUP BY key";

    public static final String APR_VIO_REJ_LEG = "SELECT (CASE WHEN c.status_reason_code LIKE 'RE001' THEN 'Lack of lisence' ELSE 'Violation' END) AS key, \n" +
            "    COUNT(c.claim_no) AS value\n" +
            "FROM HIAS.CLAIM c\n" +
            "WHERE c.status_code = 'REJ' %s \n" +
            "GROUP BY c.status_reason_code\n" +
            "UNION\n" +
            "SELECT 'Approve' AS key, \n" +
            "    COUNT(c.claim_no) AS value\n" +
            "FROM HIAS.CLAIM c\n" +
            "WHERE c.status_code = 'APR' %s\n" +
            "GROUP BY c.status_code";
}
