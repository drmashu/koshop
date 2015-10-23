DROP TABLE account;
CREATE TABLE IF NOT EXISTS account (
  id          INT NOT NULL PRIMARY KEY,
  name        NVARCHAR2(100) NOT NULL,
  password    NVARCHAR2(20),
  role        INT
);
CREATE TABLE IF NOT EXISTS customer (
  id          INT NOT NULL PRIMARY KEY,
  postal_code NVARCHAR2(10),
  address     NVARCHAR2(100),
  phone       NVARCHAR2(20)
);
