DELETE FROM routes;
DELETE FROM trips;
DELETE FROM bookings;
INSERT INTO "routes" ("id","departure_city","arrival_city","transport_type") VALUES (1,'CITY_A','CITY_B','BUS');
INSERT INTO "routes" ("id","departure_city","arrival_city","transport_type") VALUES (2,'CITY_C','CITY_D','AIRLINE');
INSERT INTO "trips" ("id","arrival_time","max_seats","departure_time","price","route_id") VALUES (1,1736067187898,10,1735746787898,5008.0,1);
INSERT INTO "trips" ("id","arrival_time","max_seats","departure_time","price","route_id") VALUES (2,1735872787916,144,1735735987916,15915.0,2);

INSERT INTO "bookings" ("id","email","first_name","last_name","refund_code","seat_number","trip_id") VALUES (2,'andrey2@test.com','Andrey','Vasilyev','480305cbe5534b888dbebfeda8507df9',7,1);