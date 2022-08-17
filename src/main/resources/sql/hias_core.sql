-- CREATE SCHEMA HIAS;
-- DROP SCHEMA HIAS;
--Set up Client
-- drop schema hias cascade;
-- truncate table hias.claim restart identity cascade;

CREATE TABLE HIAS.USERS
(
    USER_NO             INTEGER GENERATED ALWAYS AS IDENTITY,
    USER_NAME           VARCHAR(2000) NOT NULL UNIQUE,
    PASSWORD            VARCHAR(2000) NOT NULL,
    CLIENT_NO           INTEGER,
    MEMBER_NO           INTEGER,
    EMPLOYEE_NO         INTEGER,
    SERVICE_PROVIDER_NO INTEGER,
    STATUS_CODE         VARCHAR(3)    DEFAULT 'ACT',
    IS_DELETED          BOOLEAN       DEFAULT FALSE,
    CREATED_BY          VARCHAR(2000) DEFAULT 'System',
    CREATED_ON          TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY         VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON         TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (USER_NO)
);

CREATE TABLE HIAS.ROLE
(
    ROLE_NO     INTEGER GENERATED ALWAYS AS IDENTITY,
    ROLE_NAME   VARCHAR(2000) NOT NULL,
    IS_DELETED  BOOLEAN       DEFAULT FALSE,
    CREATED_BY  VARCHAR(2000) DEFAULT 'System',
    CREATED_ON  TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (ROLE_NO)
);

CREATE TABLE HIAS.USER_ROLE
(
    USER_ROLE_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    USER_NO      INTEGER NOT NULL,
    ROLE_NO      INTEGER NOT NULL,
    IS_DELETED   BOOLEAN       DEFAULT FALSE,
    CREATED_BY   VARCHAR(2000) DEFAULT 'System',
    CREATED_ON   TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY  VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (USER_ROLE_NO),
    UNIQUE (USER_NO, ROLE_NO),
    CONSTRAINT FK_USER FOREIGN KEY (USER_NO) REFERENCES HIAS.USERS (USER_NO),
    CONSTRAINT FK_ROLE FOREIGN KEY (ROLE_NO) REFERENCES HIAS.ROLE (ROLE_NO)
);

--Set up Province
CREATE TABLE HIAS.PROVINCE
(
    PROVINCE_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    PROVINCE_NAME VARCHAR(2000) NOT NULL,
    IS_DELETED    BOOLEAN       DEFAULT FALSE,
    CREATED_BY    VARCHAR(2000) DEFAULT 'System',
    CREATED_ON    TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY   VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON   TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (PROVINCE_NO)
);

--Set up Province
CREATE TABLE HIAS.DISTRICT
(
    DISTRICT_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    DISTRICT_NAME VARCHAR(2000) NOT NULL,
    PROVINCE_NO   INTEGER,
    IS_DELETED    BOOLEAN       DEFAULT FALSE,
    CREATED_BY    VARCHAR(2000) DEFAULT 'System',
    CREATED_ON    TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY   VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON   TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (DISTRICT_NO),
    CONSTRAINT FK_PROVINCE FOREIGN KEY (PROVINCE_NO) REFERENCES HIAS.PROVINCE (PROVINCE_NO)
);

--Set up Client
CREATE TABLE HIAS.CLIENT
(
    CLIENT_NO    INTEGER GENERATED ALWAYS AS IDENTITY,
    CLIENT_NAME  VARCHAR(2000),
    CORPORATE_ID VARCHAR(2000),
    EMAIL        VARCHAR(2000),
    PHONE_NUMBER VARCHAR(2000),
    ADDRESS      VARCHAR(2000),
    START_DATE   TIMESTAMP,
    END_DATE     TIMESTAMP,
    STATUS_CODE  VARCHAR(3)    DEFAULT 'ACT',
    IS_DELETED   BOOLEAN       DEFAULT FALSE,
    CREATED_BY   VARCHAR(2000) DEFAULT 'System',
    CREATED_ON   TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY  VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (CLIENT_NO)
);

--Set up Policy
CREATE TABLE HIAS.POLICY
(
    POLICY_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    POLICY_NAME VARCHAR(2000),
    POLICY_CODE VARCHAR(2000),
    START_DATE  TIMESTAMP,
    END_DATE    TIMESTAMP,
    REMARK      VARCHAR(2000),
    CLIENT_NO   INTEGER,
    STATUS_CODE VARCHAR(3)    DEFAULT 'ACT',
    IS_DELETED  BOOLEAN       DEFAULT FALSE,
    CREATED_BY  VARCHAR(2000) DEFAULT 'System',
    CREATED_ON  TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (POLICY_NO),
    CONSTRAINT FK_CLIENT FOREIGN KEY (CLIENT_NO) REFERENCES HIAS.CLIENT (CLIENT_NO)
);

--Set up Benefit
CREATE TABLE HIAS.BENEFIT
(
    BENEFIT_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    BENEFIT_NAME VARCHAR(2000),
    BENEFIT_CODE VARCHAR(2000),
    START_DATE   TIMESTAMP,
    END_DATE     TIMESTAMP,
    REMARK       VARCHAR(2000),
    STATUS_CODE  VARCHAR(3)    DEFAULT 'ACT',
    IS_DELETED   BOOLEAN       DEFAULT FALSE,
    CREATED_BY   VARCHAR(2000) DEFAULT 'System',
    CREATED_ON   TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY  VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (BENEFIT_NO)
);

--Set up Benefit Item Type
CREATE TABLE HIAS.BENEFIT_ITEM_TYPE
(
    BENEFIT_ITEM_TYPE_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    BENEFIT_ITEM_TYPE_CODE VARCHAR(2000),
    BENEFIT_ITEM_TYPE_NAME VARCHAR(2000),
    REMARK                 VARCHAR(2000),
    IS_DELETED             BOOLEAN       DEFAULT FALSE,
    CREATED_BY             VARCHAR(2000) DEFAULT 'System',
    CREATED_ON             TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY            VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON            TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (BENEFIT_ITEM_TYPE_NO)
);

--Set up Benefit Item Type
CREATE TABLE HIAS.BENEFIT_ITEM
(
    BENEFIT_ITEM_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    BENEFIT_ITEM_CODE VARCHAR(2000),
    BENEFIT_ITEM_NAME VARCHAR(2000),
    REMARK            VARCHAR(2000),
    BUDGET_AMOUNT     NUMERIC,
    BENEFIT_NO        INTEGER,
    IS_DELETED        BOOLEAN       DEFAULT FALSE,
    CREATED_BY        VARCHAR(2000) DEFAULT 'System',
    CREATED_ON        TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY       VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON       TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (BENEFIT_ITEM_NO),
    CONSTRAINT FK_BENEFIT FOREIGN KEY (BENEFIT_NO) REFERENCES HIAS.BENEFIT (BENEFIT_NO)
);

--Set up Policy Coverage
CREATE TABLE HIAS.POLICY_COVERAGE
(
    POLICY_COVERAGE_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    BENEFIT_NO         INTEGER,
    POLICY_NO          INTEGER,
    IS_DELETED         BOOLEAN       DEFAULT FALSE,
    CREATED_BY         VARCHAR(2000) DEFAULT 'System',
    CREATED_ON         TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY        VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON        TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (POLICY_COVERAGE_NO),
    CONSTRAINT FK_BENEFIT FOREIGN KEY (BENEFIT_NO) REFERENCES HIAS.BENEFIT (BENEFIT_NO),
    CONSTRAINT FK_POLICY FOREIGN KEY (POLICY_NO) REFERENCES HIAS.POLICY (POLICY_NO)
);

--Set up Bank
CREATE TABLE HIAS.BANK
(
    BANK_NO     INTEGER GENERATED ALWAYS AS IDENTITY,
    BANK_NAME   VARCHAR(2000),
    IS_DELETED  BOOLEAN       DEFAULT FALSE,
    CREATED_BY  VARCHAR(2000) DEFAULT 'System',
    CREATED_ON  TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (BANK_NO)
);

--Set up Member
CREATE TABLE HIAS.MEMBER
(
    MEMBER_NO       INTEGER GENERATED ALWAYS AS IDENTITY,
    MEMBER_NAME     VARCHAR(2000),
    STAFF_ID        VARCHAR(2000),
    PHONE_NUMBER    VARCHAR(2000),
    BANK_ACCOUNT_NO VARCHAR(2000),
    EMAIL           VARCHAR(2000),
    ADDRESS         VARCHAR(2000),
    DOB             DATE,
    START_DATE      TIMESTAMP,
    END_DATE        TIMESTAMP,
    REMARK          VARCHAR(2000),
    CLIENT_NO       INTEGER,
    POLICY_NO       INTEGER,
    BANK_NO         INTEGER,
    DISTRICT_NO     INTEGER,
    HEALTH_CARD_NO  VARCHAR(2000),
    GENDER          VARCHAR(3)    DEFAULT 'O',
    STATUS_CODE     VARCHAR(3)    DEFAULT 'ACT',
    IS_DELETED      BOOLEAN       DEFAULT FALSE,
    CREATED_BY      VARCHAR(2000) DEFAULT 'System',
    CREATED_ON      TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY     VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON     TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (MEMBER_NO),
    CONSTRAINT FK_CLIENT FOREIGN KEY (CLIENT_NO) REFERENCES HIAS.CLIENT (CLIENT_NO),
    CONSTRAINT FK_POLICY FOREIGN KEY (POLICY_NO) REFERENCES HIAS.POLICY (POLICY_NO),
    CONSTRAINT FK_BANK FOREIGN KEY (BANK_NO) REFERENCES HIAS.BANK (BANK_NO),
    CONSTRAINT FK_DISTRICT FOREIGN KEY (DISTRICT_NO) REFERENCES HIAS.DISTRICT (DISTRICT_NO)
);


--Set up Department
CREATE TABLE HIAS.DEPARTMENT
(
    DEPARTMENT_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    DEPARTMENT_CODE VARCHAR(2000),
    DEPARTMENT_NAME VARCHAR(2000),
    IS_DELETED      BOOLEAN       DEFAULT FALSE,
    CREATED_BY      VARCHAR(2000) DEFAULT 'System',
    CREATED_ON      TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY     VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON     TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (DEPARTMENT_NO)
);

--Set up Employment Type
CREATE TABLE HIAS.EMPLOYMENT_TYPE
(
    EMPLOYMENT_TYPE_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    EMPLOYMENT_TYPE_CODE VARCHAR(2000),
    EMPLOYMENT_TYPE_NAME VARCHAR(2000),
    IS_DELETED           BOOLEAN       DEFAULT FALSE,
    CREATED_BY           VARCHAR(2000) DEFAULT 'System',
    CREATED_ON           TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY          VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON          TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (EMPLOYMENT_TYPE_NO)
);

--Set up Department Employment Type
CREATE TABLE HIAS.DEPARTMENT_EMPLOYMENT_TYPE
(
    DEPARTMENT_EMPLOYMENT_TYPE_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    EMPLOYMENT_TYPE_NO            INTEGER,
    DEPARTMENT_NO                 INTEGER,
    IS_DELETED                    BOOLEAN       DEFAULT FALSE,
    CREATED_BY                    VARCHAR(2000) DEFAULT 'System',
    CREATED_ON                    TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY                   VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON                   TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (DEPARTMENT_EMPLOYMENT_TYPE_NO)
);

--Set up Employee
CREATE TABLE HIAS.EMPLOYEE
(
    EMPLOYEE_NO        INTEGER GENERATED ALWAYS AS IDENTITY,
    EMPLOYEE_NAME      VARCHAR(2000),
    EMPLOYEE_ID        VARCHAR(2000),
    EMAIL              VARCHAR(2000),
    ADDRESS            VARCHAR(2000),
    PHONE_NUMBER       VARCHAR(2000),
    DOB                DATE,
    DEPARTMENT_NO      INTEGER,
    EMPLOYMENT_TYPE_NO INTEGER,
    START_DATE         TIMESTAMP,
    END_DATE           TIMESTAMP,
    GENDER             VARCHAR(3)    DEFAULT 'O',
    STATUS_CODE        VARCHAR(3)    DEFAULT 'ACT',
    IS_DELETED         BOOLEAN       DEFAULT FALSE,
    CREATED_BY         VARCHAR(2000) DEFAULT 'System',
    CREATED_ON         TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY        VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON        TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (EMPLOYEE_NO),
    CONSTRAINT FK_DEPARTMENT FOREIGN KEY (DEPARTMENT_NO) REFERENCES HIAS.DEPARTMENT (DEPARTMENT_NO),
    CONSTRAINT FK_EMPLOYMENT_TYPE FOREIGN KEY (DEPARTMENT_NO) REFERENCES HIAS.EMPLOYMENT_TYPE (EMPLOYMENT_TYPE_NO)
);

--Set up Employee Client
CREATE TABLE HIAS.EMPLOYEE_CLIENT
(
    EMPLOYEE_CLIENT_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    EMPLOYEE_NO        INTEGER,
    CLIENT_NO          INTEGER,
    IS_DELETED         BOOLEAN       DEFAULT FALSE,
    CREATED_BY         VARCHAR(2000) DEFAULT 'System',
    CREATED_ON         TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY        VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON        TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (EMPLOYEE_CLIENT_NO),
    CONSTRAINT FK_EMPLOYEE FOREIGN KEY (EMPLOYEE_NO) REFERENCES HIAS.EMPLOYEE (EMPLOYEE_NO),
    CONSTRAINT FK_CLIENT FOREIGN KEY (CLIENT_NO) REFERENCES HIAS.CLIENT (CLIENT_NO)
);

--Set up Service Provider
CREATE TABLE HIAS.SERVICE_PROVIDER
(
    SERVICE_PROVIDER_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    SERVICE_PROVIDER_ID   VARCHAR(2000),
    SERVICE_PROVIDER_NAME VARCHAR(2000),
    EMAIL                 VARCHAR(2000),
    ADDRESS               VARCHAR(2000),
    PHONE_NUMBER          VARCHAR(2000),
    START_DATE            TIMESTAMP,
    END_DATE              TIMESTAMP,
    STATUS_CODE           VARCHAR(3)    DEFAULT 'ACT',
    IS_DELETED            BOOLEAN       DEFAULT FALSE,
    CREATED_BY            VARCHAR(2000) DEFAULT 'System',
    CREATED_ON            TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY           VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON           TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (SERVICE_PROVIDER_NO)
);

--Set up Claim
CREATE TABLE HIAS.CLAIM
(
    CLAIM_NO                  INTEGER GENERATED ALWAYS AS IDENTITY,
    CLAIM_ID                  VARCHAR(2000),
    VISIT_DATE                TIMESTAMP,
    ADMISSION_FROM_DATE       TIMESTAMP,
    ADMISSION_TO_DATE         TIMESTAMP,
    SUBMITTED_DATE            TIMESTAMP,
    MEDICAL_EXAMINATION_DATE  TIMESTAMP,
    BUSINESS_EXAMINATION_DATE TIMESTAMP,
    APPROVED_DATE             TIMESTAMP,
    PAYMENT_DATE              TIMESTAMP,
    REMARK                    VARCHAR(2000),
    DESCRIPTION               VARCHAR(2000),
    MEDICAL_ADDRESS           VARCHAR(2000),
    BUSINESS_APPRAISAL_BY     VARCHAR(2000),
    MEDICAL_APPRAISAL_BY      VARCHAR(2000),
    CLAIM_AMOUNT              NUMERIC,
    SERVICE_PROVIDER_NO       INTEGER,
    MEMBER_NO                 INTEGER,
    BENEFIT_NO                INTEGER,
    STATUS_CODE               VARCHAR(3)    DEFAULT 'DRA',
    RECORD_SOURCE             VARCHAR(3)    DEFAULT 'M',
    STATUS_REASON_CODE        VARCHAR(10),
    IS_DELETED                BOOLEAN       DEFAULT FALSE,
    CREATED_BY                VARCHAR(2000) DEFAULT 'System',
    CREATED_ON                TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY               VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON               TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (CLAIM_NO),
    CONSTRAINT FK_MEMBER FOREIGN KEY (MEMBER_NO) REFERENCES HIAS.MEMBER (MEMBER_NO)
--     CONSTRAINT FK_BENEFIT_ITEM FOREIGN KEY (BENEFIT_ITEM_NO) REFERENCES HIAS.BENEFIT_ITEM (BENEFIT_ITEM_NO)
);

--Set up Claim Document
CREATE TABLE HIAS.CLAIM_DOCUMENT
(
    CLAIM_DOCUMENT_NO  INTEGER GENERATED ALWAYS AS IDENTITY,
    FILE_URL           VARCHAR(2000),
    PATH_FILE          VARCHAR(2000),
    FILE_NAME          VARCHAR(2000),
    ORIGINAL_FILE_NAME VARCHAR(2000),
    CLAIM_NO           INTEGER,
    LICENSE_NO         INTEGER,
    IS_DELETED         BOOLEAN       DEFAULT FALSE,
    CREATED_BY         VARCHAR(2000) DEFAULT 'System',
    CREATED_ON         TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY        VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON        TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (CLAIM_DOCUMENT_NO),
    CONSTRAINT FK_CLAIM FOREIGN KEY (CLAIM_NO) REFERENCES HIAS.CLAIM (CLAIM_NO),
    CONSTRAINT FK_LICENSE FOREIGN KEY (LICENSE_NO) REFERENCES HIAS.LICENSE (LICENSE_NO)
);


--Set up Claim Request History
CREATE TABLE HIAS.CLAIM_REQUEST_HISTORY
(
    CLAIM_REQUEST_HISTORY_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    CLAIM_NO                 INTEGER,
    EMPLOYEE_NO              INTEGER,
    IS_DELETED               BOOLEAN       DEFAULT FALSE,
    CREATED_BY               VARCHAR(2000) DEFAULT 'System',
    CREATED_ON               TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY              VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON              TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (CLAIM_REQUEST_HISTORY_NO),
    CONSTRAINT FK_CLAIM FOREIGN KEY (CLAIM_NO) REFERENCES HIAS.CLAIM (CLAIM_NO),
    CONSTRAINT FK_EMPLOYEE FOREIGN KEY (EMPLOYEE_NO) REFERENCES HIAS.EMPLOYEE (EMPLOYEE_NO)
);

--Set up Claim Payment
CREATE TABLE HIAS.CLAIM_PAYMENT
(
    CLAIM_PAYMENT_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    CLAIM_NO         INTEGER,
    EMPLOYEE_NO      INTEGER,
    PAYMENT_AMOUNT   NUMERIC,
    STATUS_CODE      VARCHAR(3)    DEFAULT 'PAY',
    IS_DELETED       BOOLEAN       DEFAULT FALSE,
    CREATED_BY       VARCHAR(2000) DEFAULT 'System',
    CREATED_ON       TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY      VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON      TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (CLAIM_PAYMENT_NO),
    CONSTRAINT FK_CLAIM FOREIGN KEY (CLAIM_NO) REFERENCES HIAS.CLAIM (CLAIM_NO),
    CONSTRAINT FK_EMPLOYEE FOREIGN KEY (EMPLOYEE_NO) REFERENCES HIAS.EMPLOYEE (EMPLOYEE_NO)
);

--Set up License
CREATE TABLE HIAS.LICENSE
(
    LICENSE_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    LICENSE_NAME VARCHAR(2000),
    LABEL        VARCHAR(2000),
    REMARK       VARCHAR(2000),
    FILE_URL     VARCHAR(2000),
    IS_DELETED   BOOLEAN       DEFAULT FALSE,
    CREATED_BY   VARCHAR(2000) DEFAULT 'System',
    CREATED_ON   TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY  VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (LICENSE_NO)
);


--Set up Benefit License
CREATE TABLE HIAS.BENEFIT_LICENSE
(
    BENEFIT_LICENSE_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    LICENSE_NO         INTEGER,
    BENEFIT_NO         INTEGER,
    IS_DELETED         BOOLEAN       DEFAULT FALSE,
    CREATED_BY         VARCHAR(2000) DEFAULT 'System',
    CREATED_ON         TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY        VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON        TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    CONSTRAINT FK_BENEFIT FOREIGN KEY (BENEFIT_NO) REFERENCES HIAS.BENEFIT (BENEFIT_NO),
    CONSTRAINT FK_LICENSE FOREIGN KEY (LICENSE_NO) REFERENCES HIAS.LICENSE (LICENSE_NO),
    PRIMARY KEY (BENEFIT_LICENSE_NO)
);

--Set up Client Health Card Format
CREATE TABLE HIAS.HEALTH_CARD_FORMAT
(
    HEALTH_CARD_FORMAT_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    PREFIX                VARCHAR(2000),
    CLIENT_NO             INTEGER,
    IS_DELETED            BOOLEAN       DEFAULT FALSE,
    CREATED_BY            VARCHAR(2000) DEFAULT 'System',
    CREATED_ON            TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY           VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON           TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (HEALTH_CARD_FORMAT_NO),
    CONSTRAINT FK_HEALTH_CARD_FORMAT FOREIGN KEY (CLIENT_NO) REFERENCES HIAS.CLIENT (CLIENT_NO)
);

--Set up Business Sector
CREATE TABLE HIAS.BUSINESS_SECTOR
(
    BUSINESS_SECTOR_NO            INTEGER GENERATED ALWAYS AS IDENTITY,
    BUSINESS_SECTOR_NAME          VARCHAR(2000),
    IS_DELETED                    BOOLEAN       DEFAULT FALSE,
    CREATED_BY                    VARCHAR(2000) DEFAULT 'System',
    CREATED_ON                    TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY                   VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON                   TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (BUSINESS_SECTOR_NO)
);

--Set up Client Business Sector
CREATE TABLE HIAS.CLIENT_BUSINESS_SECTOR
(
    CLIENT_BUSINESS_SECTOR_NO     INTEGER GENERATED ALWAYS AS IDENTITY,
    BUSINESS_SECTOR_NO            INTEGER,
    CLIENT_NO                     INTEGER,
    IS_DELETED                    BOOLEAN       DEFAULT FALSE,
    CREATED_BY                    VARCHAR(2000) DEFAULT 'System',
    CREATED_ON                    TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY                   VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON                   TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    CONSTRAINT FK_CLI_BUS_CLI FOREIGN KEY (CLIENT_NO) REFERENCES HIAS.CLIENT (CLIENT_NO),
    CONSTRAINT FK_CLI_BUS_BUS FOREIGN KEY (BUSINESS_SECTOR_NO) REFERENCES HIAS.BUSINESS_SECTOR (BUSINESS_SECTOR_NO),
    PRIMARY KEY (CLIENT_BUSINESS_SECTOR_NO)
);