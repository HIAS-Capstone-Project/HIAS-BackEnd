package com.hias.constant;

public class ChartQuery {

    public static final String MEMBER_AGE_CHART_QUERY = "SELECT t.key, COUNT(t.value) AS value from(\n" +
            "WITH ages AS (SELECT DATE_PART('year', NOW()::date) - DATE_PART('year', m.dob::date) AS age, m.client_no\n" +
            "FROM HIAS.member m)\n" +
            "SELECT 'Under 30' AS key, age AS value, client_no\n" +
            "FROM ages\n" +
            "WHERE age < 30 \n" +
            "UNION ALL\n" +
            "SELECT 'Between 30 And 50' AS key, age AS value, client_no\n" +
            "FROM ages\n" +
            "WHERE age >= 30 AND age <= 50\n" +
            "UNION ALL\n" +
            "SELECT 'Greater Than 50' AS key, age AS value, client_no\n" +
            "FROM ages\n" +
            "WHERE age > 50) t\n" +
            "WHERE 1 = 1 %s\n" +
            "GROUP BY key";

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
            "SELECT 'Settle' AS key, \n" +
            "    COUNT(c.claim_no) AS value\n" +
            "FROM HIAS.CLAIM c\n" +
            "WHERE c.status_code = 'SET' %s\n" +
            "GROUP BY c.status_code";

    public static final String FIND_ALL_STATISTICS = "SELECT 'member' AS key, COUNT(*) AS value FROM HIAS.member\n" +
            "UNION\n" +
            "SELECT 'claim', COUNT(*) FROM HIAS.claim\n" +
            "UNION\n" +
            "SELECT 'policy', COUNT(*) FROM HIAS.policy\n" +
            "UNION\n" +
            "SELECT 'business_sector', COUNT(*) FROM HIAS.business_sector";

    public static final String FIND_ALL_STATISTICS_ROLE_EMP = "SELECT 'policy' AS key, COUNT(DISTINCT(m.policy_no)) AS value FROM HIAS.member m\n" +
            "WHERE m.client_no IN(\n" +
            "           SELECT ec.client_no\n" +
            "           FROM HIAS.employee_client ec\n" +
            "           WHERE ec.employee_no = %s)\n" +
            "UNION\n" +
            "SELECT 'member', COUNT(m.member_no) FROM HIAS.member m\n" +
            "WHERE m.client_no IN(\n" +
            "           SELECT ec.client_no\n" +
            "           FROM HIAS.employee_client ec\n" +
            "           WHERE ec.employee_no = %s)\n" +
            "UNION         \n" +
            "SELECT 'claim', COUNT(c.claim_no) FROM HIAS.claim c\n" +
            "WHERE c.member_no IN (\n" +
            "    SELECT m.member_no FROM HIAS.member m\n" +
            "           WHERE m.client_no IN(\n" +
            "           SELECT ec.client_no\n" +
            "           FROM HIAS.employee_client ec\n" +
            "           WHERE ec.employee_no =%s)\n" +
            ")\n" +
            "UNION\n" +
            "SELECT 'business_sector', COUNT(c.business_sector_no) FROM HIAS.client_business_sector c\n" +
            "WHERE c.client_no IN(\n" +
            "           SELECT ec.client_no\n" +
            "           FROM HIAS.employee_client ec\n" +
            "           WHERE ec.employee_no = %s)";

    public static final String FIND_ALL_STATISTICS_ROLE_CLIENT = "SELECT 'member' AS key, COUNT(*) AS value \n" +
            "FROM HIAS.member m\n" +
            "WHERE m.client_no = %s\n" +
            "UNION\n" +
            "SELECT 'policy', COUNT(DISTINCT(m.policy_no)) \n" +
            "FROM HIAS.member m\n" +
            "WHERE m.client_no = %s\n" +
            "UNION\n" +
            "SELECT 'claim', COUNT(c.claim_no) \n" +
            "FROM HIAS.claim c\n" +
            "WHERE c.member_no IN (\n" +
            "    SELECT m.member_no \n" +
            "    FROM HIAS.member m\n" +
            "    WHERE m.client_no = %s )\n" +
            "UNION                \n" +
            "SELECT 'business_sector', COUNT(c.business_sector_no) \n" +
            "FROM HIAS.client_business_sector c\n" +
            "WHERE c.client_no = %s";

    public static final String PAYMENT_CHART_QUERY = "SELECT to_char(c.payment_date,'%s') key, SUM(payment_amount) value\n" +
            "FROM HIAS.claim c\n" +
            "WHERE c.status_code = 'SET' AND c.member_no IN(\n" +
            "    SELECT m.member_no\n" +
            "    FROM HIAS.member m\n" +
            "    WHERE 1 = 1 %s\n" +
            ") %s";
}
