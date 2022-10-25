USE `AdvertisementDB`;

INSERT INTO `Role`
 (`id`, `name`) VALUES
 (1, 'USER'),
 (2, 'MODERATOR'),
 (3, 'ADMIN');

 INSERT INTO `User`
 (`id`, `username`, `password`, `status`, `name`, `phone`, `email`, `registered_since`, `about`, `money`) VALUES
-- password: typicalpassword
 (1, 'usedreamless', '$2a$12$JcbePW2uCcxkcod0nE.0YOqF83BVJmDMAnlSG8Lb.AZTYVU6swtOW', 'ALIVE', 'Andrey', '9912345678',
 'email@example.com', '2021-06-18 14:34:09', 'Hello, World!', 10000000),
-- password: admin
 (2, 'admin', '$2a$12$ywkYoOZ.KpsdxWU2O4kMM.0j9aC.mwIsBpcr54ThOLgaC5FTxVPc2', 'ALIVE', 'Vasiliy', NULL,
  'email@test.com', '2012-04-20 10:00:00', 'I have nothing to tell you', 200000000),
--  password: baduser
 (3, 'unknown_user123', '$2a$12$YEm2NsvAQ6xKmI02Oe4qSu9pmX3x6GIcH47oSm.CIr97KjICdXWY.', 'BLOCKED', 'Georgy', NULL,
   'mammoth@example.com', '2022-09-01 08:45:09', 'Hey', 500000),
--  password: moderatorpassword
 (4, 'moderator_user', '$2a$12$FcPrd84Lz8Uwglxc74Ldl.x9SZowKEKNK048tY2d2VfDgHS7RRkU2', 'ALIVE', 'Viktorya', NULL,
   'moderator@example.com', '2022-09-01 08:45:09', 'Hello!', 5000000);

 INSERT INTO `User2Role`
 (`user_id`, `role_id`) VALUES
 (1, 1),
 (2, 3),
 (3, 1),
 (4, 2);

 INSERT INTO `Advertisement`
 (`id`, `name`, `status`, `created_at`, `about`, `user_id`) VALUES
 (1, 'Buy premium wristwatches cheap', 'ALIVE', '2021-06-18 16:00:00', 'Cool wristwatches!', 1),
 (2, 'Rent office in Moscow just in one day!', 'ALIVE', '2021-06-18 18:00:00', 'Comfortable, spacious offices with fast rent', 1),
 (3, 'Order fake sim-cards absolutely safe', 'BLOCKED', '2022-09-01 09:00:00', 'Totally safe!', 3);

 INSERT INTO `Product`
 (`id`, `name`, `cost`, `available`, `ad_id`) VALUES
 (1, 'Premium Wristwatches', 1500000, 100, 1),
 (2, 'Office in Moscow', 10000000, 67, 2),
 (3, 'Fake sim-card', 50000, 586, 3);

 INSERT INTO `AdvertisementRating`
 (`id`, `value`, `promoted_value`, `payment_period`) VALUES
 (1, 450, 450, NULL),
 (2, 500, 500, NULL),
 (3, 150, 150, NULL);

 INSERT INTO `AdvertisementRate`
 (`id`, `user_id`, `ad_id`, `value`) VALUES
 (1, 2, 1, 500),
 (2, 3, 1, 400),
 (3, 2, 2, 500),
 (4, 1, 3, 200),
 (5, 2, 3, 100);

 INSERT INTO `Purchase`
 (`id`, `purchased_at`, `count`, `purchaser_id`, `product_id`) VALUES
 (1, '2022-09-01 10:00:00', 4, 2, 1),
 (2, '2022-09-01 11:10:42', 1, 2, 2),
 (3, '2022-09-01 11:40:42', 1, 1, 3);

 INSERT INTO `Comment`
 (`id`, `content`, `added_at`, `user_id`, `ad_id`) VALUES
 (1, 'Scam', '2022-09-01 11:50:42', 1, 3),
 (2, 'Good wristwatches!', '2022-09-01 10:30:00', 2, 1),
 (3, 'Spacious office and fast rent, as said in the advertisement. Good one!', '2022-09-01 11:20:00', 2, 2);

 INSERT INTO `Message`
 (`id`, `content`, `sent_at`, `sender_id`, `receiver_id`) VALUES
 (1, 'Hello! Do you have any discounts?', '2022-09-01 09:00:00', 2, 1),
 (2, 'Sorry, but for now, we can not provide any discounts.', '2022-09-01 10:00:00', 1, 2);


