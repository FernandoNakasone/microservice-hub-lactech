package  br.com.lactech.lacty.dto;

import br.com.lactech.lacty.entities.DuvidasRespostas;
import jakarta.persistence.Column;
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

    private Long id;
    private String duvida;
    private String resposta;
    private LocalDateTime data;


    public DuvidasRespostasResponseDTO(DuvidasRespostas duvidasRespostas){

        id = duvidasRespostas.getId();
        duvida = duvidasRespostas.getDuvida();
        resposta = duvidasRespostas.getResposta();
        data = duvidasRespostas.getData();
    }

}
