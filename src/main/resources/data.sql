INSERT INTO campaings (c_id, c_name, c_status_id, c_start_date, c_end_date) VALUES (1, 'First test Campaign', 0, '2019-06-18 00:00:00', '2019-07-18 00:00:00');


INSERT INTO ads (ad_id, ad_name, ad_status_id, ad_asset_url, campaign_id) VALUES (1, 'first test ad', 0, 'asset url', 1);
INSERT INTO ads (ad_id, ad_name, ad_status_id, ad_asset_url, campaign_id) VALUES (2, 'second test ad', 1, 'asset2 url', 1);


INSERT INTO platform_ads (ad_id, platform_id) VALUES (2, 1);
INSERT INTO platform_ads (ad_id, platform_id) VALUES (2, 2);

INSERT INTO platform_ads (ad_id, platform_id) VALUES (1, 0);
INSERT INTO platform_ads (ad_id, platform_id) VALUES (1, 2);