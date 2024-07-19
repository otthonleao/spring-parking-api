package dev.otthon.parkingapi.entity;

import dev.otthon.parkingapi.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_usuarios", schema = "PUBLIC")
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENTE;

    @CreatedDate /* AUDITORIA */
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;

    @LastModifiedDate /* AUDITORIA */
    @Column(name = "DATA_MODIFICACAO")
    private LocalDateTime dataModificacao;

    @CreatedBy /* AUDITORIA */
    @Column(name = "CRIADO_POR")
    private String criadoPor;

    @LastModifiedBy /* AUDITORIA */
    @Column(name = "MODIFICADO_POR")
    private String modificadoPor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                '}';
    }
}
