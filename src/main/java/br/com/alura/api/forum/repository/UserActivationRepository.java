package br.com.alura.api.forum.repository;

import br.com.alura.api.forum.entity.UserActivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivationRepository extends JpaRepository<UserActivation, String> {

    UserActivation findByCode(String code);

    UserActivation findByUserId(String id);
}
