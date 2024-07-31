package com.revature.RevConnect.repositories;

import com.revature.RevConnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserID(int id);
    User findByUsername(String username);

        @Modifying
        @Query(value = "UPDATE Users SET bio = ?2 WHERE user_id = ?1",nativeQuery = true)
        void updateBio(Integer userID,String bio);
}
