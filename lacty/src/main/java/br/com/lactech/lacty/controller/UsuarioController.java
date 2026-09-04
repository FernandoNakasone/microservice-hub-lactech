package br.com.lactech.lacty.controller;

import br.com.lactech.lacty.dto.UsuarioRequestDTO;
import br.com.lactech.lacty.dto.UsuarioResponseDTO;
import br.com.lactech.lacty.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios(){
        List<UsuarioResponseDTO> list = usuarioService.findAllUsuarios();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable String cpf){
         UsuarioResponseDTO usuarioResponseDTO = usuarioService.findUsuarioByCpf(cpf);

        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@RequestBody @Valid UsuarioRequestDTO inputDTO){
         UsuarioResponseDTO usuarioResponseDTO = usuarioService.saveUsuario(inputDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{cpf}")
                .buildAndExpand( usuarioResponseDTO.getCpf())
                .toUri();

        return ResponseEntity.created(uri).body(usuarioResponseDTO);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(@PathVariable String cpf, @RequestBody @Valid UsuarioRequestDTO inputDTO){
         UsuarioResponseDTO usuarioResponseDTO = usuarioService.updateUsuario(cpf, inputDTO);

        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String cpf){
        usuarioService.deleteUsuarioById(cpf);

        return ResponseEntity.noContent().build();
    }

}
