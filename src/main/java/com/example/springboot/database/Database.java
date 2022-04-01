package com.example.springboot.database;

import com.example.springboot.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Configurable
//Now connect with mysql using JPA
/*
docker run -d --rm --name mysql-spring-boot-tutorial \
-e MYSQL_ROOT_PASSWORD=06091998 \
-e MYSQL_USER=giangvv \
-e MYSQL_PASSWORD=06091998 \
-e MYSQL_DATABASE=test_db \
-p 3308:3306 \
--volume mysql-spring-boot-tutorial-volume:/var/lib/mysql \
mysql:latest

mysql -h localhost -P 3308 --protocol=tcp -u hoangnd -p

* */
public class Database
{
    //logger
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository)
    {
        return new CommandLineRunner()
        {
            @Override
            public void run(String... args) throws Exception {
              //  Product productA = new Product("MacBook pro 16", 2021, 3469.9, "");
              //  Product productB = new Product("Iphone 13 pro max", 2021, 4000.9, "");

                //logger.info("insert data:" + productRepository.save(productA));
              //  logger.info("insert data:" + productRepository.save(productB));
            }
        };
    }

}
