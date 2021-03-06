SET MODE PostgreSQL;

DROP TABLE IF EXISTS  campaings;
CREATE TABLE campaings
(
  c_id         SERIAL PRIMARY KEY,
  c_name       VARCHAR(50) UNIQUE NOT NULL,
  c_status_id  SMALLINT           NOT NULL,
  c_start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  c_end_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

DROP TABLE IF EXISTS  ads;
CREATE TABLE ads
(
  ad_id          SERIAL PRIMARY KEY,
  ad_name        VARCHAR(50) UNIQUE NOT NULL,
  ad_status_id   SMALLINT           NOT NULL,
  ad_asset_url   VARCHAR(50)        NOT NULL,
  ad_campaign_id INT                NOT NULL
);

DROP TABLE IF EXISTS  platform_ads;
CREATE TABLE platform_ads
(
  ad_id       INT NOT NULL,
  platform_id INT NOT NULL
);