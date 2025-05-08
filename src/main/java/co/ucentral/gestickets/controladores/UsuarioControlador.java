package co.ucentral.gestickets.controladores;

import co.ucentral.gestickets.dto.UsuarioDto;
import co.ucentral.gestickets.persistencia.entidades.Usuario;
import co.ucentral.gestickets.servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;
import java.security.Principal;


@AllArgsConstructor
@Controller
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    // Página principal después de iniciar sesión
    @GetMapping("/inicio")
    public String paginaInicio() {
        return "inicio"; // Asegúrate de tener inicio.html
    }

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioDto());
        return "registro"; // registro.html en templates
    }

    // Procesar registro
    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute("usuario") UsuarioDto usuarioDto, Principal principal) {
        if (principal != null) {
            // Si el usuario ya está logueado, redirigir a la página de inicio
            return "redirect:/usuarios/inicio";
        }
        usuarioServicio.registrar(usuarioDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // (opcional) API para registrar usuario desde frontend con JS o Postman
    @PostMapping("/api/registrar")
    @ResponseBody
    public String registrarDesdeApi(@RequestBody UsuarioDto usuarioDto) {
        usuarioServicio.registrar(usuarioDto);
        return "Usuario registrado correctamente desde API";
    }

    // (opcional) Obtener todos los usuarios (solo si lo necesitas)
    @GetMapping("/")
    @ResponseBody
    public List<Usuario> obtenerTodos() {
        return usuarioServicio.obtenerTodos();
    }
}
