package br.com.lactech.performanceWebsite.service;

import br.com.lactech.performanceWebsite.dto.PerformanceWebsiteRequestDTO;
import br.com.lactech.performanceWebsite.dto.PerformanceWebsiteResponseDTO;
import br.com.lactech.performanceWebsite.entities.PerformanceWebsite;
import br.com.lactech.performanceWebsite.exceptions.ResourceNotFoundException;
import br.com.lactech.performanceWebsite.repositories.PerformanceWebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PerformanceWebsiteService {

    @Autowired
    private PerformanceWebsiteRepository performanceWebsiteRepository;

    @Transactional(readOnly = true)
    public List<PerformanceWebsiteResponseDTO> findAllPerformanceWebsite(){
        return performanceWebsiteRepository.findAll().stream().map(PerformanceWebsiteResponseDTO :: new).toList();
    }

    @Transactional(readOnly = true)
    public PerformanceWebsiteResponseDTO findPerformanceWebsiteById(Long id){

        PerformanceWebsite performanceWebsite = performanceWebsiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado. ID:" + id));

        return new PerformanceWebsiteResponseDTO(performanceWebsite);

    }

    @Transactional
    public PerformanceWebsiteResponseDTO savePerformanceWebsite(PerformanceWebsiteRequestDTO inputDTO){

        PerformanceWebsite performanceWebsite = new PerformanceWebsite();
        copyDtoToPerformanceWebsite(inputDTO,performanceWebsite);
        performanceWebsite = performanceWebsiteRepository.save(performanceWebsite);

        return new PerformanceWebsiteResponseDTO(performanceWebsite);

    }

    @Transactional
    public void deletePerformanceWebsiteById(Long id){
        if(!performanceWebsiteRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID:" + id);
        }
        performanceWebsiteRepository.deleteById(id);

    }

    private void copyDtoToPerformanceWebsite(PerformanceWebsiteRequestDTO inputDTO, PerformanceWebsite performanceWebsite) {

        performanceWebsite.setUrl(inputDTO.getUrl());
        performanceWebsite.setData(inputDTO.getData());
        performanceWebsite.setTempoPermanenciaMs(inputDTO.getTempoPermanenciaMs());
        performanceWebsite.setTempoCarregamentoMs(inputDTO.getTempoCarregamentoMs());
        performanceWebsite.setTipoDispositivo(inputDTO.getTipoDispositivo());
        performanceWebsite.setNavegador(inputDTO.getNavegador());
        performanceWebsite.setSistemaOperacional(inputDTO.getSistemaOperacional());

    }

}
