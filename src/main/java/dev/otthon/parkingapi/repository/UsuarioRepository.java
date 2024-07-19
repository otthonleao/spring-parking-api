package dev.otthon.parkingapi.repository;

import dev.otthon.parkingapi.entity.Usuario;
import dev.otthon.parkingapi.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u.role FROM Usuario u WHERE u.username LIKE :username")
    Role findRoleByUsername(String username);

}
