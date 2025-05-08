package co.ucentral.gestickets.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDto {
    private String nombreCompleto;
    private String nacionalidad;
    private String username;
    private String password;
}


