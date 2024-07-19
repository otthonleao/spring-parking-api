package dev.otthon.parkingapi.service;

import dev.otthon.parkingapi.entity.Usuario;
import dev.otthon.parkingapi.enums.Role;
import dev.otthon.parkingapi.repository.UsuarioRepository;
import dev.otthon.parkingapi.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(String.format("Username '%s' já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true) //Indica para o Spring que esse método apenas faz consulta no DB.
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        // Verifica se a nova senha e a de confirmação são iguais
        if (!novaSenha.equals(confirmaSenha)) {
            throw new RuntimeException("Nova senha não confere com confirmação de senha.");
        }

        // Verifica se a senha atual é igual a cadastrada no banco
        Usuario user = buscarPorId(id);
        if(!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new RuntimeException("Sua senha não confere");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException((String.format("Usuário com '%s' não encontrado", username)))
        );
    }

    @Transactional(readOnly = true)
    public Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
