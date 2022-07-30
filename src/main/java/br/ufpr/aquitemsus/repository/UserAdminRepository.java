package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.UserAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAdminRepository extends JpaRepository<UserAdmin, Long> {
    Page<UserAdmin> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
