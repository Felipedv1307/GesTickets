package co.ucentral.gestickets.controladores;

import co.ucentral.gestickets.persistencia.entidades.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import co.ucentral.gestickets.persistencia.entidades.Solicitud;
import co.ucentral.gestickets.persistencia.repositorios.SolicitudRepositorio;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketControlador {

    @Autowired
    private SolicitudRepositorio solicitudRepositorio;

    @GetMapping("/create")
    public String crearTicket() {
        return "Ticket creado por usuario con rol USER";
    }

    @GetMapping("/assign")
    public String asignarTicket() {
        return "Ticket asignado por usuario con rol GESTOR";
    }

    @GetMapping("/all")
    public Object verTodosLosTickets(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "Debes iniciar sesión.";
        }

        if (usuario.getRol() != Usuario.Rol.ADMINISTRADOR) {
            return "Acceso denegado. Solo los ADMIN pueden ver todos los tickets.";
        }

        List<Solicitud> solicitudes = solicitudRepositorio.findAll();
        return solicitudes;
    }
    @PostMapping("/create")
    public Solicitud crearTicket(@RequestBody Solicitud solicitud, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            throw new RuntimeException("Debes iniciar sesión.");
        }

        solicitud.setEstado("PENDIENTE"); // Estado inicial por defecto
        return solicitudRepositorio.save(solicitud);
    }
}