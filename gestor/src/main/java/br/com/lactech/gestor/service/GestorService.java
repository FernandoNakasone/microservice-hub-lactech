package br.com.lactech.gestor.service;

import br.com.lactech.gestor.dto.GestorLoginRequestDTO;
import br.com.lactech.gestor.dto.GestorLoginResponseDTO;
import br.com.lactech.gestor.dto.GestorRequestDTO;
import br.com.lactech.gestor.dto.GestorResponseDTO;
import br.com.lactech.gestor.entities.Gestor;
import br.com.lactech.gestor.exceptions.ResourceNotFoundException;
import br.com.lactech.gestor.repository.GestorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GestorService {

    @Autowired
    private GestorRepository gestorRepository;

    @Transactional(readOnly = true)
    public List<GestorResponseDTO> findAllGestores(){

        return gestorRepository.findAll().stream().map(GestorResponseDTO :: new).toList();

    }

    @Transactional(readOnly = true)
    public GestorResponseDTO findGestorByEmail(String email){

        Gestor gestor = gestorRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado. email:" + email));

        return new GestorResponseDTO(gestor);

    }

    @Transactional
    public GestorResponseDTO saveGestor(GestorRequestDTO inputDTO){

        Gestor gestor = new Gestor();
        copyDtoToGestor(inputDTO, gestor);
        gestor = gestorRepository.save(gestor);

        return new GestorResponseDTO(gestor);
    }

    @Transactional
    public GestorResponseDTO updateGestor(String email, GestorRequestDTO inputDto){

        try {
            Gestor gestor = gestorRepository.getReferenceById(email);
            copyDtoToGestor(inputDto,gestor);
            gestor = gestorRepository.save(gestor);
            return new GestorResponseDTO(gestor);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado. email:" + email);
        }
    }

    @Transactional
    public void deleteGestorById(String email){
        if(!gestorRepository.existsById(email)){
            throw new ResourceNotFoundException("Recurso não encontrado. email:" + email);
        }
        gestorRepository.deleteById(email);
    }

    @Transactional
    public boolean login(GestorLoginRequestDTO request) {

        return gestorRepository
                .findByEmailAndSenha(request.email(), request.senha())
                .isPresent();
    }

    private void copyDtoToGestor(GestorRequestDTO inputDTO, Gestor gestor) {

        gestor.setEmail(inputDTO.getEmail());
        gestor.setSenha(inputDTO.getSenha());

    }

}
