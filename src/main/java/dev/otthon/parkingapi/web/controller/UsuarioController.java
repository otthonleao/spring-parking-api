package dev.otthon.parkingapi.web.controller;

import dev.otthon.parkingapi.entity.Usuario;
import dev.otthon.parkingapi.service.UsuarioService;
import dev.otthon.parkingapi.web.dto.UsuarioCreateDTO;
import dev.otthon.parkingapi.web.dto.UsuarioResponseDTO;
import dev.otthon.parkingapi.web.dto.UsuarioSenhaDTO;
import dev.otthon.parkingapi.web.dto.mapper.UsuarioMapper;
import dev.otthon.parkingapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /*
    @RequestBody: é usado para extrair dados do corpo da requisição.
    @PathVariable: é usado para extrair valores de variáveis de modelo na URI da requisição.
     */


    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário com email e senha",
                responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "E-mail já cadastrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
                })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioCreateDTO createDTO) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }




    @Operation(summary = "Recuperar usuário pelo ID", description = "Requisição exige um Bearer Token. Acesso restrito ao ADMIN ou CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENTE') AND #id == authentication.principal.id )")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(user));
    }



    @Operation(summary = "Atualizar senha", description = "Requisição exige um Bearer Token. Acesso restrito ao ADMIN ou CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "A senha não confere", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDTO dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
        /*
        * Em alguns casos não é necessário enviar nada no body da response, como em um update de senha
        * Então utilizamos o tipo void e retornamos um noContent() que retorna um code 204 - No Content
        * */
    }



    @Operation(summary = "Listar todos os usuários", description = "Requisição exige um Bearer Token. Acesso restrito ao ADMIN ou CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                        content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class)))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDTO(users));
    }
}
