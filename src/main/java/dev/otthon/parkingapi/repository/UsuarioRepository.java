package dev.otthon.parkingapi.repository;

import dev.otthon.parkingapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
