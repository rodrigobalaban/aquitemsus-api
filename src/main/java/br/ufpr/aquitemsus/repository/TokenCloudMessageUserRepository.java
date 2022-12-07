package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.TokenCloudMessageUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenCloudMessageUserRepository extends JpaRepository<TokenCloudMessageUser, Long> {
    TokenCloudMessageUser findByUserSusId(Long userId);
}
