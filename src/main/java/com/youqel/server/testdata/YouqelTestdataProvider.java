package com.youqel.server.testdata;

import javax.annotation.PostConstruct;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 99)
public class YouqelTestdataProvider {

    @Autowired
    private Flyway flyway;


    @PostConstruct
    public void migrate() throws Exception {

        flyway.migrate();
    }



}
