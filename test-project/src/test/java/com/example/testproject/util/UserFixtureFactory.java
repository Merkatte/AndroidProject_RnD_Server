package com.example.testproject.util;

import com.example.testproject.domain.user.entity.AppUser;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.time.LocalDate;

import static org.jeasy.random.FieldPredicates.*;

public class UserFixtureFactory {

    static public EasyRandom get(LocalDate firstDate, LocalDate lastDate){

        var idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(AppUser.class));

        var params = new EasyRandomParameters()
                .excludeField(idPredicate)
                .dateRange(firstDate, lastDate);

        return new EasyRandom(params);
    }
}
