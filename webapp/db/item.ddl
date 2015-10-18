CREATE TABLE item (
  id          NVARCHAR2(20)  NOT NULL PRIMARY KEY,
  name        NVARCHAR2(100) NOT NULL,
  price       INT            NOT NULL,
  description NVARCHAR2(1024)
);
CREATE TABLE items (
  uid     NVARCHAR2(256) NOT NULL,
  item_id NVARCHAR2(20)  NOT NULL,
  count   INT            NOT NULL,
  PRIMARY KEY (uid, itemId)
);
CREATE TABLE item_image (
  item_id NVARCHAR2(20) NOT NULL,
  index   TINYINT       NOT NULL,
  image   IMAGE,
  PRIMARY KEY (item_id, index)
);