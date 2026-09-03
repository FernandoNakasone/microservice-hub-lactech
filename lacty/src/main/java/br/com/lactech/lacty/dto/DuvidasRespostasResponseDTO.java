package  br.com.lactech.lacty.dto;

import br.com.lactech.lacty.entities.DuvidasRespostas;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DuvidasRespostasResponseDTO {

    private String resposta;


    DuvidasRespostasResponseDTO(DuvidasRespostas duvidasRespostas){
        resposta = duvidasRespostas.getResposta();
    }

}
