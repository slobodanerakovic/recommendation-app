-- data sql
insert into application."user" values (100, 0, now(), 'MORNING', 'MASTERCARD', 'MACBOOK', 'MIDDLE_AGE', 'ADMIN', 'RS', 'MALE', 55);
insert into application."user" values (999, 0, now(), 'NIGHT', 'AMEX', 'PC_LINUX', 'YOUNG', 'USER', 'US', 'FEMALE', 33);
insert into application."user" values (777, 0, now(), 'NIGHT', 'VISA', 'IOS_IPAD', 'CHILD', 'USER', 'DE', 'MALE', 33);
insert into application."user" values (888, 0, now(), 'DAY', 'AMEX', 'IOS_IPAD', 'YOUNG', 'USER', 'DE', 'FEMALE', 33);
insert into application."user" values (666, 0, now(), 'NIGHT', 'VISA', 'WINDOWS_PHONE', 'YOUNG_SENIOR', 'USER', 'US', 'MALE', 33);
insert into application."user" values (555, 0, now(), 'DAY', 'MASTERCARD', 'IOS_IPAD', 'YOUNG_SENIOR', 'USER', 'US', 'FEMALE', 33);
insert into application."user" values (444, 0, now(), 'NIGHT', 'VISA', 'IOS_IPAD', 'CHILD', 'USER', 'US', 'FEMALE', 13);

insert into application.product values (101, 0, now(), 'BALL', 23, 'A really BIG ball', 'ACTIVE');
insert into application.product values (102, 0, now(), 'FLOWER', 453, 'A really beautiful flower', 'ACTIVE');
insert into application.product values (103, 0, now(), 'HOUSE', 98, 'A really BIG house', 'ACTIVE');
insert into application.product values (104, 0, now(), 'CAR', 654, 'A really BIG car', 'ACTIVE');
insert into application.product values (105, 0, now(), 'CAT', 23424, 'A really BIG cat', 'ACTIVE');

insert into application.user_tracking_behaviour values (101, 0, now(), 101, 999, 'VIEWED');
insert into application.user_tracking_behaviour values (102, 0, now(), 102, 777, 'BOUGHT');
insert into application.user_tracking_behaviour values (103, 0, now(), 103, 444, 'COMMENTED');
insert into application.user_tracking_behaviour values (104, 0, now(), 104, 666, 'BOUGHT_COMMENTED');
insert into application.user_tracking_behaviour values (105, 0, now(), 105, 888, 'VIEWED_BOUGHT');
insert into application.user_tracking_behaviour values (106, 0, now(), 105, 555, 'VIEWED_BOUGHT');
insert into application.user_tracking_behaviour values (107, 0, now(), 105, 555, 'VIEWED');

insert into application.product_popularity values (105, 0, now(), 104, 'C_GOOD_RATING', 2015, 3);
insert into application.product_popularity values (106, 0, now(), 104, 'E_TOP_RATING', 2014, 1);
insert into application.product_popularity values (107, 0, now(), 101, 'D_VERY_GOOD_RATING', 2012, 4);
insert into application.product_popularity values (108, 0, now(), 102, 'D_VERY_GOOD_RATING', 2015, 3);
insert into application.product_popularity values (109, 0, now(), 103, 'E_TOP_RATING', 2011, 3);
insert into application.product_popularity values (110, 0, now(), 101, 'A_UNRATED', 2016, 5);
insert into application.product_popularity values (111, 0, now(), 104, 'A_UNRATED', 2017, 7);
insert into application.product_popularity values (112, 0, now(), 101, 'C_GOOD_RATING', 2016, 7);
insert into application.product_popularity values (113, 0, now(), 102, 'E_TOP_RATING', 2011, 3);
insert into application.product_popularity values (114, 0, now(), 101, 'B_LOW_RATING', 2015, 3);
insert into application.product_popularity values (116, 0, now(), 105, 'E_TOP_RATING', 2013, 1);
insert into application.product_popularity values (115, 0, now(), 102, 'E_TOP_RATING', 2015, 1);
insert into application.product_popularity values (117, 0, now(), 102, 'B_LOW_RATING', 2014, 3);
insert into application.product_popularity values (118, 0, now(), 105, 'B_LOW_RATING', 2014, 5);
insert into application.product_popularity values (119, 0, now(), 101, 'E_TOP_RATING', 2015, 11);
insert into application.product_popularity values (120, 0, now(), 101, 'C_GOOD_RATING', 2012, 7);
insert into application.product_popularity values (121, 0, now(), 102, 'E_TOP_RATING', 2011, 1);
insert into application.product_popularity values (122, 0, now(), 103, 'D_VERY_GOOD_RATING', 2011, 9);
