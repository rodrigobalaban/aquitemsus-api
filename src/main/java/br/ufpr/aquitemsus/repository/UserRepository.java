package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    Page<User> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
