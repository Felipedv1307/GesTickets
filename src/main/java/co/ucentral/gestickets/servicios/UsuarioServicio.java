package co.ucentral.gestickets.servicios;

import co.ucentral.gestickets.dto.UsuarioDto;
import co.ucentral.gestickets.persistencia.entidades.Usuario;
import co.ucentral.gestickets.persistencia.repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final BCryptPasswordEncoder passwordEncoder;

    public Usuario crear(UsuarioDto usuarioDto) {
        Usuario usuario = Usuario.builder()
                .nombreCompleto(usuarioDto.getNombreCompleto())
                .nacionalidad(usuarioDto.getNacionalidad())
                .username(usuarioDto.getUsername())
                .password(passwordEncoder.encode(usuarioDto.getPassword()))
                .build();

        return usuarioRepositorio.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepositorio.findAll();
    }

    public UsuarioDto autenticar(UsuarioDto usuarioDto) {
        Optional<Usuario> optional = usuarioRepositorio.findByUsername(usuarioDto.getUsername());

        log.info("Intentando autenticar usuario: {}", usuarioDto.getUsername());
        optional.ifPresent(value -> log.debug("Clave codificada en BDD: {}", value.getPassword()));

        if (optional.isPresent() && passwordEncoder.matches(usuarioDto.getPassword(), optional.get().getPassword())) {
            return new UsuarioDto(
                    optional.get().getNombreCompleto(),
                    optional.get().getNacionalidad(),
                    optional.get().getUsername(),
                    "");
        } else {
            return null;
        }
    }
    public void registrar(UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setNacionalidad(dto.getNacionalidad());
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword())); // Encriptar
        usuarioRepositorio.save(usuario);
    }

}