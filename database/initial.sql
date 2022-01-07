DO $$BEGIN
    CREATE TABLE IF NOT EXISTS addressesdatabase (id SERIAL PRIMARY KEY, street VARCHAR(30) NOT NULL, "streetNumber" INT NOT NULL, "flatNumber" INT NOT NULL, "postalCode" VARCHAR(10) NOT NULL, country VARCHAR(20) NOT NULL, city VARCHAR(20) NOT NULL);
    ALTER TABLE addressesdatabase ADD CONSTRAINT addressesdatabase_id_unique UNIQUE (id);
    CREATE TABLE IF NOT EXISTS customersdatabase (email VARCHAR(30) PRIMARY KEY, id INT NOT NULL, "name" VARCHAR(20) NOT NULL, surname VARCHAR(20) NOT NULL, "password" VARCHAR(400) NOT NULL, CONSTRAINT fk_customersdatabase_id_id FOREIGN KEY (id) REFERENCES addressesdatabase(id) ON DELETE RESTRICT ON UPDATE RESTRICT);
    ALTER TABLE customersdatabase ADD CONSTRAINT customersdatabase_email_unique UNIQUE (email);
    CREATE TABLE IF NOT EXISTS productsdatabase (id SERIAL PRIMARY KEY, "name" VARCHAR(100) NOT NULL, price DOUBLE PRECISION NOT NULL, image VARCHAR(100) NOT NULL, category VARCHAR(50) NOT NULL, description VARCHAR(300) NOT NULL, producer VARCHAR(50) NOT NULL, "size" VARCHAR(5) NOT NULL, color VARCHAR(50) NOT NULL, popularity INT NOT NULL, quantity INT NOT NULL, "productCode" INT NOT NULL);
    ALTER TABLE productsdatabase ADD CONSTRAINT productsdatabase_id_unique UNIQUE (id);
    CREATE TABLE IF NOT EXISTS cartsdatabase ("customerEmail" VARCHAR(30), "productId" INT, quantity INT NOT NULL, CONSTRAINT pk_CartsDatabase PRIMARY KEY ("customerEmail", "productId"), CONSTRAINT fk_cartsdatabase_customeremail_email FOREIGN KEY ("customerEmail") REFERENCES customersdatabase(email) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT fk_cartsdatabase_productid_id FOREIGN KEY ("productId") REFERENCES productsdatabase(id) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT check_CartsDatabase_0 CHECK (quantity > 0));
    CREATE TABLE IF NOT EXISTS ordersdatabase (id SERIAL PRIMARY KEY, "customerEmail" VARCHAR(30) NOT NULL, "addressId" INT NOT NULL, "totalQuantity" INT NOT NULL, "totalPrice" DOUBLE PRECISION NOT NULL, "date" VARCHAR(30) NOT NULL, CONSTRAINT fk_ordersdatabase_customeremail_email FOREIGN KEY ("customerEmail") REFERENCES customersdatabase(email) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT fk_ordersdatabase_addressid_id FOREIGN KEY ("addressId") REFERENCES addressesdatabase(id) ON DELETE CASCADE ON UPDATE CASCADE);
    ALTER TABLE ordersdatabase ADD CONSTRAINT ordersdatabase_id_unique UNIQUE (id);
    CREATE TABLE IF NOT EXISTS orderdetailsdatabase ("orderId" INT, "productId" INT, quantity INT NOT NULL, price DOUBLE PRECISION NOT NULL, CONSTRAINT pk_OrderDetailsDatabase PRIMARY KEY ("orderId", "productId"), CONSTRAINT fk_orderdetailsdatabase_orderid_id FOREIGN KEY ("orderId") REFERENCES ordersdatabase(id) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT fk_orderdetailsdatabase_productid_id FOREIGN KEY ("productId") REFERENCES productsdatabase(id) ON DELETE CASCADE ON UPDATE CASCADE);

    INSERT INTO addressesdatabase (street, "streetNumber", "flatNumber", "postalCode", country, city) VALUES
        ('Polk Street', 24, 10, 10-101, 'United States', 'San Francisco'),
        ('Wall street', 13, 17, 71-912, 'United States', 'New York'),
        ('Carnaby Street', 206, 39, 21-791, 'United Kingdom', 'London'),
        ('Carnaby Street', 206, 39, 21-791, 'United Kingdom', 'London');

    INSERT INTO customersdatabase (email, id, name, surname, password) VALUES
        ('john.smith@example.com', 1, 'John', 'Smith', 'password'),
        ('agatha.clarke@example.com', 3, 'Agatha', 'Clarke', 'Password123');

    INSERT INTO productsdatabase (name, price, image, category, description, producer, size, color, popularity, quantity, "productCode") VALUES
        ('Dr. Martens 1460', 700, 'test', 'Hi-tops', 'The 1460 is the original Dr. Martens boot. Its instantly recognizable DNA looks like this: 8 eyes, classic Dr. Martens Smooth leather, grooved sides, a heel-loop, yellow stitching, and a comfortable, air-cushioned sole.', 'Dr. Martens', 42, 'black', 0, 3, 1),
        ('Dr. Martens 1460', 700, 'test', 'Hi-tops', 'The 1460 is the original Dr. Martens boot. Its instantly recognizable DNA looks like this: 8 eyes, classic Dr. Martens Smooth leather, grooved sides, a heel-loop, yellow stitching, and a comfortable, air-cushioned sole.', 'Dr. Martens', 41, 'black', 0, 1, 1),
        ('Dr. Martens 1460', 700, 'test', 'Hi-tops', 'The 1460 is the original Dr. Martens boot. Its instantly recognizable DNA looks like this: 8 eyes, classic Dr. Martens Smooth leather, grooved sides, a heel-loop, yellow stitching, and a comfortable, air-cushioned sole.', 'Dr. Martens', 40, 'black', 0, 2, 1),
        ('Dr. Martens 1460', 700, 'test', 'Hi-tops', 'The 1460 is the original Dr. Martens boot. Its instantly recognizable DNA looks like this: 8 eyes, classic Dr. Martens Smooth leather, grooved sides, a heel-loop, yellow stitching, and a comfortable, air-cushioned sole.', 'Dr. Martens', 42, 'purple', 0, 2, 1),
        ('Dr. Martens 1460', 700, 'test', 'Hi-tops', 'The 1460 is the original Dr. Martens boot. Its instantly recognizable DNA looks like this: 8 eyes, classic Dr. Martens Smooth leather, grooved sides, a heel-loop, yellow stitching, and a comfortable, air-cushioned sole.', 'Dr. Martens', 45, 'purple', 0, 2, 1),
        ('Dr. Martens 1461', 600, 'test', 'Ankle shoes', 'These 1461 shoes stay true to the six-decade-old silhouette. The shoes sit on a rugged air-cushioned sole, reinforced with our signature yellow welt stitching — just as they have since the start.', 'Dr. Martens', 40, 'black', 0, 1, 2),
        ('Dr. Martens 1461', 600, 'test', 'Ankle shoes', 'These 1461 shoes stay true to the six-decade-old silhouette. The shoes sit on a rugged air-cushioned sole, reinforced with our signature yellow welt stitching — just as they have since the start.', 'Dr. Martens', 42, 'black', 0, 5, 2),
        ('Dr. Martens 1461', 600, 'test', 'Ankle shoes', 'These 1461 shoes stay true to the six-decade-old silhouette. The shoes sit on a rugged air-cushioned sole, reinforced with our signature yellow welt stitching — just as they have since the start.', 'Dr. Martens', 46, 'black', 0, 2, 2),
        ('Celine loafer with triomphe chain in polished bull', 5000, 'test', 'Ankle shoes', 'Bulky toe shape, chunky triomphe gold chain on the apron fringes. Oversized celine signature under the rubber outsole, hand-stitched apron.', 'Celine', 41, 'black', 0, 1, 3),
        ('Celine loafer with triomphe chain in polished bull', 5000, 'test', 'Ankle shoes', 'Bulky toe shape, chunky triomphe gold chain on the apron fringes. Oversized celine signature under the rubber outsole, hand-stitched apron.', 'Celine', 43, 'black', 0, 2, 3),
        ('Celine loafer with triomphe chain in polished bull', 5000, 'test', 'Ankle shoes', 'Bulky toe shape, chunky triomphe gold chain on the apron fringes. Oversized celine signature under the rubber outsole, hand-stitched apron.', 'Celine', 44, 'black', 0, 1, 3),
        ('Balmain bicolor ivory and black smooth', 3000, 'test', 'Hi-tops', 'Black smooth leather and ivory and black monogram jacquard Phil Ranger ankle boots Laces and zip fastening on top, embossed black Balmain monogram on ankle, rubber outer soles.', 'Balmain', 44, 'black', 0, 2, 4),
        ('Balmain bicolor ivory and black smooth', 3000, 'test', 'Hi-tops', 'Black smooth leather and ivory and black monogram jacquard Phil Ranger ankle boots Laces and zip fastening on top, embossed black Balmain monogram on ankle, rubber outer soles.', 'Balmain', 42, 'black', 0, 1, 4),
        ('Balmain white calfskin and neoprene B-Court sneakers', 1200, 'test', 'Sneakers', 'White calfskin and neoprene B-Court sneakers White laces on front, tongue with leather panel and Balmain logo, white TPU sole with tone-on-tone Balmain logo.', 'Balmain', 41, 'white', 0, 3, 5),
        ('Balmain white calfskin and neoprene B-Court sneakers', 1200, 'test', 'Sneakers', 'White calfskin and neoprene B-Court sneakers White laces on front, tongue with leather panel and Balmain logo, white TPU sole with tone-on-tone Balmain logo.', 'Balmain', 43, 'white', 0, 1, 5),
        ('Balmain white calfskin and neoprene B-Court sneakers', 1200, 'test', 'Sneakers', 'White calfskin and neoprene B-Court sneakers White laces on front, tongue with leather panel and Balmain logo, white TPU sole with tone-on-tone Balmain logo.', 'Balmain', 44, 'white', 0, 1, 5),
        ('CDG play x Converse', 800, 'test', 'Sneakers', 'Black and cream lace-up sneakers from Comme des Garçons Play X Converse. Printed heart and logo at sides. Brown and cream rubber sole.', 'Converse', 39, 'grey', 0, 6, 6),
        ('CDG play x Converse', 800, 'test', 'Sneakers', 'Black and cream lace-up sneakers from Comme des Garçons Play X Converse. Printed heart and logo at sides. Brown and cream rubber sole.', 'Converse', 40, 'grey', 0, 3, 6),
        ('CDG play x Converse', 800, 'test', 'Sneakers', 'Black and cream lace-up sneakers from Comme des Garçons Play X Converse. Printed heart and logo at sides. Brown and cream rubber sole.', 'Converse', 41, 'grey', 0, 1, 6),
        ('CDG play x Converse', 800, 'test', 'Sneakers', 'Black and cream lace-up sneakers from Comme des Garçons Play X Converse. Printed heart and logo at sides. Brown and cream rubber sole.', 'Converse', 42, 'grey', 0, 4, 6),
        ('CDG play x Converse', 800, 'test', 'Sneakers', 'Black and cream lace-up sneakers from Comme des Garçons Play X Converse. Printed heart and logo at sides. Brown and cream rubber sole.', 'Converse', 43, 'grey', 0, 2, 6),
        ('CDG play x Converse', 800, 'test', 'Sneakers', 'Black and cream lace-up sneakers from Comme des Garçons Play X Converse. Printed heart and logo at sides. Brown and cream rubber sole.', 'Converse', 40, 'black', 0, 5, 6),
        ('CDG play x Converse', 800, 'test', 'Sneakers', 'Black and cream lace-up sneakers from Comme des Garçons Play X Converse. Printed heart and logo at sides. Brown and cream rubber sole.', 'Converse', 42, 'black', 0, 10, 6);

    INSERT INTO cartsdatabase ("customerEmail", "productId", quantity) VALUES
        ('john.smith@example.com', 23, 1),
        ('agatha.clarke@example.com', 3, 1);

    INSERT INTO ordersdatabase ("customerEmail", "addressId", "totalQuantity", "totalPrice", date) VALUES
        ('john.smith@example.com', 2, 2, 2000, '2020-01-13T17:09:42.411'),
        ('agatha.clarke@example.com', 4, 1, 5000, '2021-05-10T12:10:12.114');

    INSERT INTO orderdetailsdatabase ("orderId", "productId", quantity, price) VALUES
        (1, 20, 1, 800),
        (1, 15, 1, 1200),
        (2, 10, 1, 5000);
END $$;
