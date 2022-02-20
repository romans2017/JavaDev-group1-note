package ua.goit.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    User findUserByName(String userName);

    boolean existsByNameIgnoreCase(String userName);

    boolean existsByNameIgnoreCaseAndIdIsNot(String userName, UUID id);

    boolean existsByRoles_Id(UUID id);
}