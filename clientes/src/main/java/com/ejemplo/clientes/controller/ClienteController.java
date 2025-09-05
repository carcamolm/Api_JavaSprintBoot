package com.ejemplo.clientes.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import com.ejemplo.clientes.model.Cliente;
import com.ejemplo.clientes.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.obtenerTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        Optional<Cliente> clienteExistente = clienteService.obtenerPorId(id);

        if (clienteExistente.isPresent()) {
            Cliente cliente = clienteExistente.get();
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setTelefono(clienteActualizado.getTelefono());

            return ResponseEntity.ok(clienteService.crear(cliente));  // ←
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        return clienteService.crear(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.obtenerPorId(id);

        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
