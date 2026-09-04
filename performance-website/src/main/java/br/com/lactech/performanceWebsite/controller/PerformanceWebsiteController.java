package br.com.lactech.performanceWebsite.controller;

import br.com.lactech.performanceWebsite.dto.PerformanceWebsiteRequestDTO;
import br.com.lactech.performanceWebsite.dto.PerformanceWebsiteResponseDTO;
import br.com.lactech.performanceWebsite.service.PerformanceWebsiteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/performance-website")
public class PerformanceWebsiteController {

    @Autowired
    private PerformanceWebsiteService performanceWebsiteService;

    @GetMapping
    public ResponseEntity<List<PerformanceWebsiteResponseDTO>> getAllPerformanceWebsite(){

        List<PerformanceWebsiteResponseDTO> list = performanceWebsiteService.findAllPerformanceWebsite();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceWebsiteResponseDTO> getPerformanceWebsite(@PathVariable @Valid Long id){

        PerformanceWebsiteResponseDTO performanceWebsiteResponseDTO = performanceWebsiteService.findPerformanceWebsiteById(id);

        return ResponseEntity.ok(performanceWebsiteResponseDTO);
    }

    @PostMapping
    public ResponseEntity<PerformanceWebsiteResponseDTO> createPerformanceWebsite(@RequestBody PerformanceWebsiteRequestDTO inputDTO){

        PerformanceWebsiteResponseDTO performanceWebsiteResponseDTO = performanceWebsiteService.savePerformanceWebsite(inputDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand( performanceWebsiteResponseDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(performanceWebsiteResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable long id){
        performanceWebsiteService.deletePerformanceWebsiteById(id);

        return ResponseEntity.noContent().build();
    }
}
