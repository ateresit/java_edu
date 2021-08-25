INSERT INTO `categories` (`name`)
    VALUE ('Mobile'), ('Computer'), ('Software'), ('Accessories'), ('Misc');
GO

INSERT INTO `products` (`title`, `cost`, `description`, `category_id`)
    VALUE ('iPhone X', '75000', 'Super phone', '1'),
        ('iPhone 7', '35000', 'Mobile phone', '1');
GO
