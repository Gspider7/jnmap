CREATE TABLE IF NOT EXISTS TBL_VULNERABILITY
(
    ID                        VARCHAR(64) PRIMARY KEY NOT NULL,
    CVE_ID                    VARCHAR(64),
    NAME                      VARCHAR(255),
    PUBLISHED                 VARCHAR(20),
    MODIFIED                  VARCHAR(20),
    SOURCE                    LONGVARCHAR,
    SEVERITY                  VARCHAR(20),
    VULNERABILITY_TYPE        VARCHAR(20),
    THR_TYPE                  VARCHAR(20),
    VULNERABILITY_DESC        LONGVARCHAR,
    VULNERABILITY_SOLUTION    LONGVARCHAR,
    CNNVD_MATCH_RULES         LONGVARCHAR,
    CNNVD_REFS                LONGVARCHAR,
    CVE_MATCH_RULES           LONGVARCHAR,
    CVE_REFS                  LONGVARCHAR,
    CVSS_BASE_SCORE           INT,
    CVSS_EXPLOITABILITY_SCORE INT,
    CVSS_IMPACT_SCORE         INT,
    PRODUCTS                  LONGVARCHAR
);

CREATE INDEX IF NOT EXISTS VULNERABILITY_CVE_ID_INDEX ON TBL_VULNERABILITY (CVE_ID);
CREATE INDEX IF NOT EXISTS VULNERABILITY_NAME_INDEX ON TBL_VULNERABILITY (NAME);
CREATE INDEX IF NOT EXISTS VULNERABILITY_PUBLISHED_INDEX ON TBL_VULNERABILITY (PUBLISHED);

CREATE TABLE IF NOT EXISTS TBL_NMAP_SCAN_RESULT
(
    ID           VARCHAR(64) PRIMARY KEY NOT NULL,
    SCAN_ID      VARCHAR(64),
    SCAN_PLAN_ID VARCHAR(64),
    TEMPLATE_ID  VARCHAR(64),
    SCAN_TIME    VARCHAR(32),
    IPV4_ADDRESS VARCHAR(32),
    IPV6_ADDRESS VARCHAR(32),
    MAC_ADDRESS  VARCHAR(32),
    HOSTNAME     VARCHAR(32),
    PORT_INFOS   LONGVARCHAR,
    SYSTEM_INFO  LONGVARCHAR,
    SCRIPT_INFOS LONGVARCHAR,
    CREATE_TIME  TIMESTAMP
);

CREATE INDEX IF NOT EXISTS NMAP_SCAN_RESULT_SCAN_ID_INDEX ON TBL_NMAP_SCAN_RESULT (SCAN_ID);
CREATE INDEX IF NOT EXISTS NMAP_SCAN_SCAN_PLAN_ID_INDEX ON TBL_NMAP_SCAN_RESULT (SCAN_PLAN_ID);
CREATE INDEX IF NOT EXISTS NMAP_SCAN_SCAN_IPV4_ADDRESS_INDEX ON TBL_NMAP_SCAN_RESULT (IPV4_ADDRESS);
CREATE INDEX IF NOT EXISTS NMAP_SCAN_SCAN_TEMPLATE_ID_INDEX ON TBL_NMAP_SCAN_RESULT (TEMPLATE_ID);


CREATE TABLE IF NOT EXISTS TBL_USER
(
    ID            IDENTITY        PRIMARY KEY NOT NULL,
    USERNAME      VARCHAR(64),
    PASSWORD      VARCHAR(64)
);

CREATE UNIQUE INDEX IF NOT EXISTS USER_INDEX_USERNAME ON TBL_USER (USERNAME);

CREATE TABLE IF NOT EXISTS TBL_SCAN
(
    ID            VARCHAR(64)     PRIMARY KEY NOT NULL,
    NAME          VARCHAR(64),
    TARGET        VARCHAR(1024),
    CREATE_TIME   TIMESTAMP
);

CREATE TABLE IF NOT EXISTS TBL_SYSTEM_LOG
(
    ID            IDENTITY        PRIMARY KEY NOT NULL,
    OPERATION     VARCHAR(64),
    USERNAME      VARCHAR(64),
    STATUS        INT,
    OPERATION_TIME   TIMESTAMP
);

CREATE INDEX IF NOT EXISTS SYSTEM_LOG_INDEX_USERNAME_TIME ON TBL_SYSTEM_LOG (USERNAME, OPERATION_TIME);
