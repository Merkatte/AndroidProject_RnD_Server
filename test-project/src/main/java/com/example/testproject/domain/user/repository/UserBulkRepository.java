package com.example.testproject.domain.user.repository;

import com.example.testproject.domain.user.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserBulkRepository {
    final private String TABLE = "app_user";
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void bulkInsert(List<AppUser> users){
        var sql = String.format("""
                INSERT INTO `%s` (username, email, password, full_name, birth, phone_number, created_at)
                VALUES (:username, :email, :password, :fullName, :birth, :phoneNumber, :createdAt)
                """, TABLE);

        SqlParameterSource[] params = users
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }
}
