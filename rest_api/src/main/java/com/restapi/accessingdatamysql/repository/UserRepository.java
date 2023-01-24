package com.restapi.accessingdatamysql.repository;

import org.springframework.data.repository.CrudRepository;
import com.restapi.accessingdatamysql.domain.User;

public interface UserRepository  extends CrudRepository <User, Integer>{

}
