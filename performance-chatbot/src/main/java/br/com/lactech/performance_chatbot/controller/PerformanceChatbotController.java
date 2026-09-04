package br.com.lactech.performance_chatbot.controller;

import br.com.lactech.performance_chatbot.dto.PerformanceChatbotRequestDTO;
import br.com.lactech.performance_chatbot.dto.PerformanceChatbotResponseDTO;
import br.com.lactech.performance_chatbot.service.PerformanceChatbotService;
import jakarta.servlet.ServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/performance-chatbot")
public class PerformanceChatbotController {

    @Autowired
    private PerformanceChatbotService performanceChatbotService;

    @GetMapping
    public ResponseEntity<List<PerformanceChatbotResponseDTO>> getAllPerformancesChatbots(){
        List<PerformanceChatbotResponseDTO> list = performanceChatbotService.findAllPerformanceChatbots();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{cpf}")
    public  ResponseEntity<List<PerformanceChatbotResponseDTO>> getAllPerformancesChatbotsByCpf(@PathVariable @Valid String cpf){
        List<PerformanceChatbotResponseDTO> list = performanceChatbotService.findAllPerformanceChatbotsByCpf(cpf);

        return ResponseEntity.ok(list);
    }

    @PostMapping
    public void salvarInteracao(@RequestBody PerformanceChatbotRequestDTO dto){
        performanceChatbotService.savePerformanceChatbot(dto);
    }

}
