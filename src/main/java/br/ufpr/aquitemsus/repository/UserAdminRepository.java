package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.UserAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAdminRepository extends JpaRepository<UserAdmin, Long> {
    Page<UserAdmin> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT CASE WHEN count(u)> 0 THEN true ELSE false END FROM UserAdmin u INNER JOIN u.allowedEstablishments as e WHERE u.email = :email AND e.id = :idEstablishment")
    boolean haveEstablishmentPermission(String email, Long idEstablishment);
}
