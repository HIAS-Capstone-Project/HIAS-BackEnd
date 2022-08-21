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
    CLAIM_NO                INTEGER GENERATED ALWAYS AS IDENTITY,
    CLAIM_ID                VARCHAR(2000),
    VISIT_DATE              TIMESTAMP,
    ADMISSION_FROM_DATE     TIMESTAMP,
    ADMISSION_TO_DATE       TIMESTAMP,
    SUBMITTED_DATE          TIMESTAMP,
    MEDICAL_APPRAISAL_DATE  TIMESTAMP,
    BUSINESS_APPRAISAL_DATE TIMESTAMP,
    APPROVED_DATE           TIMESTAMP,
    PAYMENT_DATE            TIMESTAMP,
    CANCELED_DATE           TIMESTAMP,
    REJECTED_DATE           TIMESTAMP,
    REMARK                  VARCHAR(2000),
    DESCRIPTION             VARCHAR(2000),
    MEDICAL_ADDRESS         VARCHAR(2000),
    BUSINESS_APPRAISAL_BY   INTEGER,
    MEDICAL_APPRAISAL_BY    INTEGER,
    APPROVED_BY             INTEGER,
    PAID_BY                 INTEGER,
    REJECT_REASON           VARCHAR(2000),
    CLAIM_AMOUNT            NUMERIC,
    PAYMENT_AMOUNT          NUMERIC,
    SERVICE_PROVIDER_NO     INTEGER,
    MEMBER_NO               INTEGER,
    BENEFIT_NO              INTEGER,
    STATUS_CODE             VARCHAR(3)    DEFAULT 'DRA',
    RECORD_SOURCE           VARCHAR(3)    DEFAULT 'M',
    STATUS_REASON_CODE      VARCHAR(10),
    IS_DELETED              BOOLEAN       DEFAULT FALSE,
    CREATED_BY              VARCHAR(2000) DEFAULT 'System',
    CREATED_ON              TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY             VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON             TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (CLAIM_NO),
    CONSTRAINT FK_MEMBER FOREIGN KEY (MEMBER_NO) REFERENCES HIAS.MEMBER (MEMBER_NO),
    CONSTRAINT FK_BENEFIT FOREIGN KEY (BENEFIT_NO) REFERENCES HIAS.BENEFIT (BENEFIT_NO)
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
    BUSINESS_SECTOR_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    BUSINESS_SECTOR_NAME VARCHAR(2000),
    IS_DELETED           BOOLEAN       DEFAULT FALSE,
    CREATED_BY           VARCHAR(2000) DEFAULT 'System',
    CREATED_ON           TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY          VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON          TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    PRIMARY KEY (BUSINESS_SECTOR_NO)
);

--Set up Client Business Sector
CREATE TABLE HIAS.CLIENT_BUSINESS_SECTOR
(
    CLIENT_BUSINESS_SECTOR_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    BUSINESS_SECTOR_NO        INTEGER,
    CLIENT_NO                 INTEGER,
    IS_DELETED                BOOLEAN       DEFAULT FALSE,
    CREATED_BY                VARCHAR(2000) DEFAULT 'System',
    CREATED_ON                TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    MODIFIED_BY               VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON               TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh'),
    CONSTRAINT FK_CLI_BUS_CLI FOREIGN KEY (CLIENT_NO) REFERENCES HIAS.CLIENT (CLIENT_NO),
    CONSTRAINT FK_CLI_BUS_BUS FOREIGN KEY (BUSINESS_SECTOR_NO) REFERENCES HIAS.BUSINESS_SECTOR (BUSINESS_SECTOR_NO),
    PRIMARY KEY (CLIENT_BUSINESS_SECTOR_NO)
);


------------------------------------------------INSERT DATA---------------------------------------------------
******BUSINESS_SECTOR********
TRUNCATE TABLE HIAS.business_sector RESTART IDENTITY CASCADE;
INSERT INTO HIAS.business_sector(business_sector_name) VALUES ('Giáo dục');
INSERT INTO HIAS.business_sector(business_sector_name) VALUES ('Y tế');
INSERT INTO HIAS.business_sector(business_sector_name) VALUES ('Công nghệ');
INSERT INTO HIAS.business_sector(business_sector_name) VALUES ('Đầu tư');
INSERT INTO HIAS.business_sector(business_sector_name) VALUES ('Xuất nhập khẩu');
INSERT INTO HIAS.business_sector(business_sector_name) VALUES ('Thực phẩm');
INSERT INTO HIAS.business_sector(business_sector_name) VALUES ('Thời trang');
INSERT INTO HIAS.business_sector(business_sector_name) VALUES ('Ô tô');
INSERT INTO HIAS.business_sector(business_sector_name) VALUES ('Bank');


**********CLIENT**********
TRUNCATE TABLE HIAS.client RESTART IDENTITY CASCADE;
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('Fsoft', '12345', 'fsoft@gmail.com', '0904111111', 'Hà Nội', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('FEdu', '23456', 'fedu@gmail.com', '0904222222', 'Hà Nội', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('FTele', '34567', 'ftele@gmail.com', '0904333333', 'Hà Nội', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('FRetail', '45678', 'fretail@gmail.com', '0904444444', 'Hà Nội', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('Vinfast', '56789', 'vinfast@gmail.com', '0904555555', 'Hà Nội', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('Vinhome', '67891', 'vinhome@gmail.com', '0904666666', 'Hà Nội', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('Vinsmart', '78910', 'vinsmart@gmail.com', '0904777777', 'Hà Nội', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('HAGL', '891011', 'hagl@gmail.com', '0904888888', 'Buôn Ma Thuật', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('Gucci', '910111', 'gucci@gmail.com', '0904999999', 'Hồ Chí Minh', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('VinEdu', '101112', 'vinedu@gmail.com', '0904101010', 'Hà Nội', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('Viettel', '111213', 'viettel@gmail.com', '0904121212', 'Hà Nội', 'ACT');
INSERT INTO HIAS.client(client_name, corporate_id, email, phone_number, address, status_code) VALUES
    ('TPBank', '121314', 'tpbank@gmail.com', '0904131313', 'Hà Nội', 'ACT');


*********HEALTH_CARD_FORMAT**********************
TRUNCATE TABLE HIAS.health_card_format RESTART IDENTITY CASCADE;
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('12345-', 1);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('23456-', 2);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('34567-', 3);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('45678-', 4);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('56789-', 5);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('67891-', 6);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('78910-', 7);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('891011-', 8);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('910111-', 9);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('101112-', 10);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('111213-', 11);
INSERT INTO HIAS.health_card_format(prefix, client_no) VALUES
    ('121314-', 12);


********CLIENT_BUSINESS_SECTOR****************
TRUNCATE TABLE HIAS.client_business_sector RESTART IDENTITY CASCADE;
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (1, 3);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (2, 1);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (3, 3);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (4, 7);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (5, 8);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (6, 6);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (7, 3);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (8, 3);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (9, 6);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (10, 7);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (11, 3);
INSERT INTO HIAS.client_business_sector (client_no, business_sector_no) VALUES (12, 9);


******SERVICE_PROVIDER*************************
TRUNCATE TABLE HIAS.service_provider RESTART IDENTITY CASCADE;
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_123456', 'Bạch Mai', 'bachmai@gmail.com', 'Hà Nội', '0908111111', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_234567', 'Thủ Đức', 'thuduc@gmail.com', 'Hà Nội', '0908222222', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_345678', 'Thể Thao', 'thethao@gmail.com', 'Hà Nội', '0908333333', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_456789', 'Chợ Rẫy', 'choray@gmail.com', 'Hồ Chí Minh', '0908444444', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_567891', 'C Đà Nẵng', 'cdanang@gmail.com', 'Đà Nẵng', '0908555555', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_678910', 'Châm Cứu Trung Uơng', 'chamcuutrunguong@gmail.com', 'Hà Nội', '0908666666', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_789101', 'Đa Khoa TW Cần Thơ', 'twcantho@gmail.com', 'Cần Thơ', '0908777777', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_891011', 'Đa Khoa TW Thái Nguyên', 'twthainguyen@gmail.com', 'Thái Nguyên', '0908888888', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_910111', 'Trung Ương Huế', 'trunguonghue@gmail.com', 'Huế', '0908999999', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_101112', 'Răng Hàm Mặt TW TP. Hồ Chí Minh', 'twhcm@gmail.com', 'Hồ Chí Minh', '0908101010', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_111213', 'Tâm thần Trung ương 1', 'tamthantrunguong1@gmail.com', 'Hồ Chí Minh', '0908121212', '2020-01-01', '2025-01-01', 'ACT');
INSERT INTO HIAS.service_provider (service_provider_id, service_provider_name, email, address, phone_number, start_date, end_date, status_code)
VALUES ('sp_121314', 'Việt Nam - Cuba Đồng Hới', 'vncb@gmail.com', 'Đồng Hới', '0908131313', '2020-01-01', '2025-01-01', 'ACT');

