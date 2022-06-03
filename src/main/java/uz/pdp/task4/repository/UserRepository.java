package uz.pdp.task4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.task4.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    @Modifying
    @Query(nativeQuery = true,
            value = "update users set is_enabled=case when is_enabled=false then true " +
                    "when is_enabled=true then false end where users.id=:id")
    void changeUserStatus(Long id);

    @Modifying
    @Query(nativeQuery = true,
            value = "update users set is_enabled=false where id in (:userIds)")
    void blockAllUsers(List<Long> userIds);

}
