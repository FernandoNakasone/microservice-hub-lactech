package  br.com.lactech.lacty.controller;

import  br.com.lactech.lacty.dto.DuvidasRespostasRequestDTO;
import  br.com.lactech.lacty.dto.DuvidasRespostasResponseDTO;
import  br.com.lactech.lacty.service.DuvidasRespostasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lacty")
public class DuvidasRespostasController {


    @Autowired
    private DuvidasRespostasService duvidasRespostasService;

    @PostMapping("/perguntar")
    public ResponseEntity<DuvidasRespostasResponseDTO> perguntar(@RequestBody DuvidasRespostasRequestDTO pergunta){

        DuvidasRespostasResponseDTO resposta = duvidasRespostasService.processarPergunta(pergunta);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping
    public ResponseEntity<List<DuvidasRespostasResponseDTO>> getAllPerguntasRespostas(){

        List<DuvidasRespostasResponseDTO> list = duvidasRespostasService.findAllPerguntasRespostas();

        return ResponseEntity.ok(list);
    }

}
