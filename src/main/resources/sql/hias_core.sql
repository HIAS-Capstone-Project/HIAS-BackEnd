CREATE SCHEMA HIAS;
-- DROP SCHEMA HIAS;
--Set up Client

CREATE TABLE HIAS.USERS
(
    USER_NO     INTEGER GENERATED ALWAYS AS IDENTITY,
    USER_NAME   VARCHAR(2000) NOT NULL UNIQUE,
    PASSWORD    VARCHAR(2000) NOT NULL,
    CLIENT_NO   INTEGER,
    MEMBER_NO   INTEGER,
    EMPLOYEE_NO INTEGER,
    STATUS_CODE VARCHAR(3)    DEFAULT 'ACT',
    IS_DELETED  BOOLEAN       DEFAULT FALSE,
    CREATED_BY  VARCHAR(2000) DEFAULT 'System',
    CREATED_ON  TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (USER_NO)
);

CREATE TABLE HIAS.ROLE
(
    ROLE_NO     INTEGER GENERATED ALWAYS AS IDENTITY,
    ROLE_NAME   VARCHAR(2000) NOT NULL,
    IS_DELETED  BOOLEAN       DEFAULT FALSE,
    CREATED_BY  VARCHAR(2000) DEFAULT 'System',
    CREATED_ON  TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (ROLE_NO)
);

CREATE TABLE HIAS.USER_ROLE
(
    USER_ROLE_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    USER_NO      INTEGER NOT NULL,
    ROLE_NO      INTEGER NOT NULL,
    IS_DELETED   BOOLEAN       DEFAULT FALSE,
    CREATED_BY   VARCHAR(2000) DEFAULT 'System',
    CREATED_ON   TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY  VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (USER_ROLE_NO),
    UNIQUE (USER_NO, ROLE_NO),
    CONSTRAINT FK_USER FOREIGN KEY (USER_NO) REFERENCES HIAS.USERS (USER_NO),
    CONSTRAINT FK_ROLE FOREIGN KEY (ROLE_NO) REFERENCES HIAS.ROLE (ROLE_NO)
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
    CREATED_ON   TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY  VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     DEFAULT CURRENT_DATE,
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
    CREATED_ON  TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON TIMESTAMP     DEFAULT CURRENT_DATE,
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
    CREATED_ON   TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY  VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (BENEFIT_NO)
);

--Set up Policy Coverage
CREATE TABLE HIAS.POLICY_COVERAGE
(
    POLICY_COVERAGE_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    BENEFIT_NO         INTEGER,
    POLICY_NO          INTEGER,
    BUDGET_AMOUNT      NUMERIC,
    IS_DELETED         BOOLEAN       DEFAULT FALSE,
    CREATED_BY         VARCHAR(2000) DEFAULT 'System',
    CREATED_ON         TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY        VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON        TIMESTAMP     DEFAULT CURRENT_DATE,
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
    CREATED_ON  TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (BANK_NO)
);

--Set up Member
CREATE TABLE HIAS.MEMBER
(
    MEMBER_NO    INTEGER GENERATED ALWAYS AS IDENTITY,
    MEMBER_NAME  VARCHAR(2000),
    STAFF_ID     VARCHAR(2000),
    PHONE_NUMBER VARCHAR(2000),
    EMAIL        VARCHAR(2000),
    START_DATE   TIMESTAMP,
    END_DATE     TIMESTAMP,
    REMARK       VARCHAR(2000),
    CLIENT_NO    INTEGER,
    POLICY_NO    INTEGER,
    BANK_NO      INTEGER,
    STATUS_CODE  VARCHAR(3)    DEFAULT 'ACT',
    IS_DELETED   BOOLEAN       DEFAULT FALSE,
    CREATED_BY   VARCHAR(2000) DEFAULT 'System',
    CREATED_ON   TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY  VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (MEMBER_NO),
    CONSTRAINT FK_CLIENT FOREIGN KEY (CLIENT_NO) REFERENCES HIAS.CLIENT (CLIENT_NO),
    CONSTRAINT FK_POLICY FOREIGN KEY (POLICY_NO) REFERENCES HIAS.POLICY (POLICY_NO),
    CONSTRAINT FK_BANK FOREIGN KEY (BANK_NO) REFERENCES HIAS.BANK (BANK_NO)
);


--Set up Department
CREATE TABLE HIAS.DEPARTMENT
(
    DEPARTMENT_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    DEPARTMENT_NAME VARCHAR(2000),
    IS_DELETED      BOOLEAN       DEFAULT FALSE,
    CREATED_BY      VARCHAR(2000) DEFAULT 'System',
    CREATED_ON      TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY     VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON     TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (DEPARTMENT_NO)
);

--Set up Employee
CREATE TABLE HIAS.EMPLOYEE
(
    EMPLOYEE_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    EMPLOYEE_NAME VARCHAR(2000),
    EMPLOYEE_ID   VARCHAR(2000),
    EMAIL         VARCHAR(2000),
    DEPARTMENT_NO INTEGER,
    START_DATE    TIMESTAMP,
    END_DATE      TIMESTAMP,
    STATUS_CODE   VARCHAR(3)    DEFAULT 'ACT',
    IS_DELETED    BOOLEAN       DEFAULT FALSE,
    CREATED_BY    VARCHAR(2000) DEFAULT 'System',
    CREATED_ON    TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY   VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON   TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (EMPLOYEE_NO),
    CONSTRAINT FK_DEPARTMENT FOREIGN KEY (DEPARTMENT_NO) REFERENCES HIAS.DEPARTMENT (DEPARTMENT_NO)
);

--Set up Business Employee
CREATE TABLE HIAS.BUSINESS_EMPLOYEE
(
    BUSINESS_EMPLOYEE_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    EMPLOYEE_NO          INTEGER NOT NULL UNIQUE,
    IS_DELETED           BOOLEAN       DEFAULT FALSE,
    CREATED_BY           VARCHAR(2000) DEFAULT 'System',
    CREATED_ON           TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY          VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON          TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (BUSINESS_EMPLOYEE_NO)
);

--Set up Employee Client
CREATE TABLE HIAS.EMPLOYEE_CLIENT
(
    EMPLOYEE_CLIENT_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    BUSINESS_EMPLOYEE_NO INTEGER,
    CLIENT_NO            INTEGER,
    IS_DELETED           BOOLEAN       DEFAULT FALSE,
    CREATED_BY           VARCHAR(2000) DEFAULT 'System',
    CREATED_ON           TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY          VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON          TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (EMPLOYEE_CLIENT_NO),
    CONSTRAINT FK_BUSINESS_EMPLOYEE FOREIGN KEY (BUSINESS_EMPLOYEE_NO) REFERENCES HIAS.BUSINESS_EMPLOYEE (BUSINESS_EMPLOYEE_NO),
    CONSTRAINT FK_CLIENT FOREIGN KEY (CLIENT_NO) REFERENCES HIAS.CLIENT (CLIENT_NO)
);

--Set up Claim Processor
CREATE TABLE HIAS.CLAIM_PROCESSOR
(
    CLAIM_PROCESSOR_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    EMPLOYEE_NO        INTEGER NOT NULL UNIQUE,
    IS_DELETED         BOOLEAN       DEFAULT FALSE,
    CREATED_BY         VARCHAR(2000) DEFAULT 'System',
    CREATED_ON         TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY        VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON        TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (CLAIM_PROCESSOR_NO)
);

--Set up Employee
CREATE TABLE HIAS.ACCOUNTANT
(
    ACCOUNTANT_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    EMPLOYEE_NO   INTEGER NOT NULL UNIQUE,
    IS_DELETED    BOOLEAN       DEFAULT FALSE,
    CREATED_BY    VARCHAR(2000) DEFAULT 'System',
    CREATED_ON    TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY   VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON   TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (ACCOUNTANT_NO)
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
    IS_DELETED            BOOLEAN       DEFAULT FALSE,
    CREATED_BY            VARCHAR(2000) DEFAULT 'System',
    CREATED_ON            TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY           VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON           TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (SERVICE_PROVIDER_NO)
);

--Set up Claim
CREATE TABLE HIAS.CLAIM
(
    CLAIM_NO            INTEGER GENERATED ALWAYS AS IDENTITY,
    CLAIM_ID            VARCHAR(2000),
    VISIT_DATE          TIMESTAMP,
    SUBMITTED_DATE      TIMESTAMP,
    APPROVED_DATE       TIMESTAMP,
    PAYMENT_DATE        TIMESTAMP,
    REMARK              VARCHAR(2000),
    CLAIM_AMOUNT        NUMERIC,
    SERVICE_PROVIDER_NO INTEGER,
    MEMBER_NO           INTEGER,
    BENEFIT_NO          INTEGER,
    STATUS_CODE         VARCHAR(3)    DEFAULT 'DRA',
    IS_DELETED          BOOLEAN       DEFAULT FALSE,
    CREATED_BY          VARCHAR(2000) DEFAULT 'System',
    CREATED_ON          TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY         VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON         TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (CLAIM_NO),
    CONSTRAINT FK_MEMBER FOREIGN KEY (MEMBER_NO) REFERENCES HIAS.MEMBER (MEMBER_NO),
    CONSTRAINT FK_BENEFIT FOREIGN KEY (BENEFIT_NO) REFERENCES HIAS.BENEFIT (BENEFIT_NO)
);

--Set up Claim Document
CREATE TABLE HIAS.CLAIM_DOCUMENT
(
    CLAIM_DOCUMENT_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    FILE_URL          VARCHAR(2000),
    CLAIM_NO          INTEGER,
    IS_DELETED        BOOLEAN       DEFAULT FALSE,
    CREATED_BY        VARCHAR(2000) DEFAULT 'System',
    CREATED_ON        TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY       VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON       TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (CLAIM_DOCUMENT_NO),
    CONSTRAINT FK_CLAIM FOREIGN KEY (CLAIM_NO) REFERENCES HIAS.CLAIM (CLAIM_NO)
);


--Set up Claim Request History
CREATE TABLE HIAS.CLAIM_REQUEST_HISTORY
(
    CLAIM_REQUEST_HISTORY_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    CLAIM_NO                 INTEGER,
    CLAIM_PROCESSOR_NO       INTEGER,
    IS_DELETED               BOOLEAN       DEFAULT FALSE,
    CREATED_BY               VARCHAR(2000) DEFAULT 'System',
    CREATED_ON               TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY              VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON              TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (CLAIM_REQUEST_HISTORY_NO),
    CONSTRAINT FK_CLAIM FOREIGN KEY (CLAIM_NO) REFERENCES HIAS.CLAIM (CLAIM_NO),
    CONSTRAINT FK_CLAIM_PROCESSOR FOREIGN KEY (CLAIM_NO) REFERENCES HIAS.CLAIM_PROCESSOR (CLAIM_PROCESSOR_NO)
);

--Set up Claim Payment
CREATE TABLE HIAS.CLAIM_PAYMENT
(
    CLAIM_PAYMENT_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    CLAIM_NO         INTEGER,
    ACCOUNTANT_NO    INTEGER,
    STATUS_CODE      VARCHAR(3)    DEFAULT 'PAY',
    IS_DELETED       BOOLEAN       DEFAULT FALSE,
    CREATED_BY       VARCHAR(2000) DEFAULT 'System',
    CREATED_ON       TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY      VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON      TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (CLAIM_PAYMENT_NO),
    CONSTRAINT FK_CLAIM FOREIGN KEY (CLAIM_NO) REFERENCES HIAS.CLAIM (CLAIM_NO),
    CONSTRAINT FK_ACCOUNTANT FOREIGN KEY (ACCOUNTANT_NO) REFERENCES HIAS.ACCOUNTANT (ACCOUNTANT_NO)
);

--Set up Liscense
CREATE TABLE HIAS.LISCENSE
(
    LISCENSE_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    LISCENSE_NAME VARCHAR(2000),
    LABEL         VARCHAR(2000),
    IS_DELETED    BOOLEAN       DEFAULT FALSE,
    CREATED_BY    VARCHAR(2000) DEFAULT 'System',
    CREATED_ON    TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY   VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON   TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (LISCENSE_NO)
);


--Set up Benefit Liscense
CREATE TABLE HIAS.BENEFIT_LISCENSE
(
    BENEFIT_LISCENSE_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    LISCENSE_NAME       VARCHAR(2000),
    LABEL               VARCHAR(2000),
    IS_DELETED          BOOLEAN       DEFAULT FALSE,
    CREATED_BY          VARCHAR(2000) DEFAULT 'System',
    CREATED_ON          TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY         VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON         TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (BENEFIT_LISCENSE_NO)
);

--Set up Benefit Liscense
CREATE TABLE HIAS.BENEFIT_LISCENSE
(
    BENEFIT_LISCENSE_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    LISCENSE_NAME       VARCHAR(2000),
    LABEL               VARCHAR(2000),
    IS_DELETED          BOOLEAN       DEFAULT FALSE,
    CREATED_BY          VARCHAR(2000) DEFAULT 'System',
    CREATED_ON          TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY         VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON         TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (BENEFIT_LISCENSE_NO)
);

--Set up Client Health Card Format
CREATE TABLE HIAS.HEALTH_CARD_FORMAT
(
    HEALTH_CARD_FORMAT_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    PREFIX                VARCHAR(2000),
    CLIENT_NO             INTEGER,
    IS_DELETED            BOOLEAN       DEFAULT FALSE,
    CREATED_BY            VARCHAR(2000) DEFAULT 'System',
    CREATED_ON            TIMESTAMP     DEFAULT CURRENT_DATE,
    MODIFIED_BY           VARCHAR(2000) DEFAULT 'System',
    MODIFIED_ON           TIMESTAMP     DEFAULT CURRENT_DATE,
    PRIMARY KEY (HEALTH_CARD_FORMAT_NO),
    CONSTRAINT FK_HEALTH_CARD_FORMAT FOREIGN KEY (CLIENT_NO) REFERENCES HIAS.CLIENT (CLIENT_NO)
);