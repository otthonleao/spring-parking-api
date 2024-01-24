package dev.otthon.parkingapi.web.controller;

import dev.otthon.parkingapi.entity.Usuario;
import dev.otthon.parkingapi.service.UsuarioService;
import dev.otthon.parkingapi.web.dto.UsuarioCreateDTO;
import dev.otthon.parkingapi.web.dto.UsuarioResponseDTO;
import dev.otthon.parkingapi.web.dto.UsuarioSenhaDTO;
import dev.otthon.parkingapi.web.dto.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /*
    @RequestBody: é usado para extrair dados do corpo da requisição.
    @PathVariable: é usado para extrair valores de variáveis de modelo na URI da requisição.
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioCreateDTO createDTO) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDTO dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
        /*
        * Em alguns casos não é necessário enviar nada no body da response, como em um update de senha
        * Então utilizamos o tipo void e retornamos um noContent() que retorna um code 204 - No Content
        * */
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDTO(users));
    }
}
