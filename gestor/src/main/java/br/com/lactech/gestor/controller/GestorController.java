package br.com.lactech.gestor.controller;

import br.com.lactech.gestor.dto.GestorLoginRequestDTO;
import br.com.lactech.gestor.dto.GestorLoginResponseDTO;
import br.com.lactech.gestor.dto.GestorRequestDTO;
import br.com.lactech.gestor.dto.GestorResponseDTO;
import br.com.lactech.gestor.service.GestorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/gestores")
public class GestorController {

    @Autowired
    private GestorService gestorService;

    @GetMapping
    public ResponseEntity<List<GestorResponseDTO>> getAllGestor(){

        List<GestorResponseDTO> list = gestorService.findAllGestores();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{email}")
    public ResponseEntity<GestorResponseDTO> getGestorByEmail(@PathVariable @Valid String email){

        GestorResponseDTO gestorResponseDTO = gestorService.findGestorByEmail(email);

        return ResponseEntity.ok(gestorResponseDTO);
    }

    @PostMapping
    public ResponseEntity<GestorResponseDTO> createGestor(@RequestBody @Valid GestorRequestDTO inputDTO){

        GestorResponseDTO gestor = gestorService.saveGestor(inputDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{email}")
                .buildAndExpand( gestor.getEmail())
                .toUri();

        return ResponseEntity.created(uri).body(gestor);
    }

    @PutMapping("/{email}")
    public ResponseEntity<GestorResponseDTO> updateGestor(@PathVariable String email, @RequestBody @Valid GestorRequestDTO inputDTO){

        GestorResponseDTO gestor = gestorService.updateGestor(email,inputDTO);

        return ResponseEntity.ok(gestor);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteGestor(@PathVariable String email){

        gestorService.deleteGestorById(email);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(
            @RequestBody GestorLoginRequestDTO request
    ) {
        return ResponseEntity.ok(gestorService.login(request));
    }
}
