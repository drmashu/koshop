//DROP TABLE item;
CREATE TABLE IF NOT EXISTS item (
  id          NVARCHAR2(20)  NOT NULL PRIMARY KEY,
  name        NVARCHAR2(100) NOT NULL,
  price       INT            NOT NULL,
  description NVARCHAR2(1024)
);
//DROP TABLE items;
CREATE TABLE IF NOT EXISTS items (
  uid     NVARCHAR2(256) NOT NULL,
  item_id NVARCHAR2(20)  NOT NULL,
  count   INT            NOT NULL,
  PRIMARY KEY (uid, item_id)
);
//DROP TABLE item_image;
CREATE TABLE IF NOT EXISTS item_image (
  item_id NVARCHAR2(20) NOT NULL,
  `index`   TINYINT       NOT NULL,
  image   IMAGE,
  contentType VARCHAR2(256),
  PRIMARY KEY (item_id, `index`)
);