CREATE TABLE account (
  id          NVARCHAR2(20)  NOT NULL PRIMARY KEY,
  name        NVARCHAR2(100) NOT NULL,
  password    NVARCHAR2(20),
  postal_code NVARCHAR2(10),
  address     NVARCHAR2(100),
  phone       NVARCHAR2(20)
);
