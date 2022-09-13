package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.UserSus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSusRepository extends JpaRepository<UserSus, Long> {
}
