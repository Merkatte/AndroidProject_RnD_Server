package com.example.testproject.domain.user;

import com.example.testproject.domain.user.entity.AppUser;
import com.example.testproject.domain.user.repository.UserBulkRepository;
import com.example.testproject.util.UserFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserBulkRepository userBulkRepository;
    @Test
    public void bulkInsert() {

//        List<String> appUserRoles = new ArrayList<>();
//        appUserRoles.add("ROLE_CLIENT");
//        System.out.println(appUserRoles);

        var easyRandom = UserFixtureFactory.get(
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2023, 1, 1)
        );
        System.out.println(easyRandom);
        var stopWatch = new StopWatch();
        stopWatch.start();
        var users = IntStream.range(0, 10)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(AppUser.class))
                .toList();
        System.out.println(users);

        stopWatch.stop();
        System.out.println("객체 생성 시간 " + stopWatch.getTotalTimeSeconds());

        var queryWatch = new StopWatch();
        queryWatch.start();
        userBulkRepository.bulkInsert(users);
        queryWatch.stop();
        System.out.println("DB INSERT 시간 " + queryWatch.getTotalTimeSeconds());
    }

}
