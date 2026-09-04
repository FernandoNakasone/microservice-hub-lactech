package br.com.lactech.lacty.service;

import br.com.lactech.lacty.dto.UsuarioRequestDTO;
import br.com.lactech.lacty.dto.UsuarioResponseDTO;
import br.com.lactech.lacty.entities.Usuario;
import br.com.lactech.lacty.exceptions.DatabaseException;
import br.com.lactech.lacty.exceptions.ResourceNotFoundException;
import br.com.lactech.lacty.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAllUsuarios(){
        return usuarioRepository.findAll().stream().map(UsuarioResponseDTO:: new).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findUsuarioByCpf(String cpf){

        Usuario usuario = usuarioRepository.findById(cpf).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. cpf:" + cpf)
        );
        return new UsuarioResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO saveUsuario(UsuarioRequestDTO inputDTO){

        Usuario usuario = new Usuario();
        copyDtoToUsuario(inputDTO,usuario);
        usuario = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuario);
    }


    @Transactional
    public UsuarioResponseDTO updateUsuario(String cpf, UsuarioRequestDTO inputDTO){

        try{
            Usuario usuario = usuarioRepository.getReferenceById(cpf);
            copyDtoToUsuario(inputDTO,usuario);
            usuario = usuarioRepository.save(usuario);
            return new UsuarioResponseDTO(usuario);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado. cpf:" + cpf);
        }
    }

    @Transactional
    public void deleteUsuarioById(String cpf){
        if(!usuarioRepository.existsById(cpf)){
            throw new ResourceNotFoundException("Recurso não encontrado. cpf:" + cpf);
        }
        usuarioRepository.deleteById(cpf);
    }

    private void copyDtoToUsuario(UsuarioRequestDTO inputDTO, Usuario usuario) {

        if (usuario.getCpf() == null) {
            usuario.setCpf(inputDTO.getCpf());
        }
        usuario.setIdade(inputDTO.getIdade());
        usuario.setCep(inputDTO.getCep());
        usuario.setNumero(inputDTO.getNumero());
        usuario.setAtivo(inputDTO.getAtivo());

    }

}
