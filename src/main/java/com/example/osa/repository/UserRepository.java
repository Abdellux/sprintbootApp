package com.example.osa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import com.example.osa.entity.UserEntity;

@Repository
@Service
public interface UserRepository extends CrudRepository<UserEntity, Long>{
    UserEntity findById(long id);
    UserEntity findByUsername(String username);
}

