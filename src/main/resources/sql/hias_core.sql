CREATE SCHEMA HIAS;
-- DROP SCHEMA HIAS;
--Set up Client

CREATE TABLE HIAS.USERS
(
    USER_NO     INTEGER GENERATED ALWAYS AS IDENTITY,
    USER_NAME   VARCHAR(2000) NOT NULL UNIQUE,
    PASSWORD    VARCHAR(2000) NOT NULL,
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
    START_DATE   TIMESTAMP,
    END_DATE     TIMESTAMP,
    STATUS_CODE  VARCHAR(3)             DEFAULT 'ACT',
    IS_DELETED   boolean                DEFAULT false,
    CREATED_BY   VARCHAR(2000) NOT NULL DEFAULT 'System',
    CREATED_ON   TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    MODIFIED_BY  VARCHAR(2000) NOT NULL DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
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
    STATUS_CODE VARCHAR(3)    NOT NULL DEFAULT 'ACT',
    IS_DELETED  varchar(1)    NOT NULL DEFAULT '0',
    CREATED_BY  VARCHAR(2000) NOT NULL DEFAULT 'System',
    CREATED_ON  TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    MODIFIED_BY VARCHAR(2000) NOT NULL DEFAULT 'System',
    MODIFIED_ON TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
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
    STATUS_CODE  VARCHAR(3)             DEFAULT 'ACT',
    IS_DELETED   varchar(1)    NOT NULL DEFAULT '0',
    CREATED_BY   VARCHAR(2000) NOT NULL DEFAULT 'System',
    CREATED_ON   TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    MODIFIED_BY  VARCHAR(2000) NOT NULL DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY (BENEFIT_NO)
);

--Set up Policy Coverage
CREATE TABLE HIAS.POLICY_COVERAGE
(
    POLICY_COVERAGE_NO INTEGER GENERATED ALWAYS AS IDENTITY,
    BENEFIT_NO         INTEGER,
    POLICY_NO          INTEGER,
    BUDGET_AMOUNT      NUMERIC,
    IS_DELETED         varchar(1)    NOT NULL DEFAULT '0',
    CREATED_BY         VARCHAR(2000) NOT NULL DEFAULT 'System',
    CREATED_ON         TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    MODIFIED_BY        VARCHAR(2000) NOT NULL DEFAULT 'System',
    MODIFIED_ON        TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY (POLICY_COVERAGE_NO),
    CONSTRAINT FK_BENEFIT FOREIGN KEY (BENEFIT_NO) REFERENCES HIAS.BENEFIT (BENEFIT_NO),
    CONSTRAINT FK_POLICY FOREIGN KEY (POLICY_NO) REFERENCES HIAS.POLICY (POLICY_NO)
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
    STATUS_CODE  VARCHAR(3)             DEFAULT 'ACT',
    IS_DELETED   varchar(1)    NOT NULL DEFAULT '0',
    CREATED_BY   VARCHAR(2000) NOT NULL DEFAULT 'System',
    CREATED_ON   TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    MODIFIED_BY  VARCHAR(2000) NOT NULL DEFAULT 'System',
    MODIFIED_ON  TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY (MEMBER_NO),
    CONSTRAINT FK_CLIENT FOREIGN KEY (CLIENT_NO) REFERENCES HIAS.CLIENT (CLIENT_NO),
    CONSTRAINT FK_POLICY FOREIGN KEY (POLICY_NO) REFERENCES HIAS.POLICY (POLICY_NO)
);


--Set up Department
CREATE TABLE HIAS.DEPARTMENT
(
    DEPARTMENT_NO   INTEGER GENERATED ALWAYS AS IDENTITY,
    DEPARTMENT_NAME VARCHAR(2000),
    IS_DELETED      varchar(1)    NOT NULL DEFAULT '0',
    CREATED_BY      VARCHAR(2000) NOT NULL DEFAULT 'System',
    CREATED_ON      TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    MODIFIED_BY     VARCHAR(2000) NOT NULL DEFAULT 'System',
    MODIFIED_ON     TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
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
    STATUS_CODE   VARCHAR(3),
    IS_DELETED    varchar(1)    NOT NULL DEFAULT '0',
    CREATED_BY    VARCHAR(2000) NOT NULL DEFAULT 'System',
    CREATED_ON    TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    MODIFIED_BY   VARCHAR(2000) NOT NULL DEFAULT 'System',
    MODIFIED_ON   TIMESTAMP     NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY (EMPLOYEE_NO),
    CONSTRAINT FK_DEPARTMENT FOREIGN KEY (DEPARTMENT_NO) REFERENCES DEPARTMENT (DEPARTMENT_NO)
);