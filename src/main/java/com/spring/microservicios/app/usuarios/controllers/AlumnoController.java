package com.spring.microservicios.app.usuarios.controllers;

import com.spring.microservicios.app.usuarios.models.entity.Alumno;
import com.spring.microservicios.app.usuarios.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AlumnoController {

    @Autowired
    private AlumnoService service;

    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Alumno> oAlumno = service.findById(id);
        if(oAlumno.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(oAlumno.get());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Alumno alumno){
        Alumno alumnoPersisted = service.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoPersisted);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Alumno alumno, @PathVariable Long id){
        Optional<Alumno> oAlumno = service.findById(id);
        if(oAlumno.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoToModify = oAlumno.get();
        alumnoToModify.setNombre(alumno.getNombre());
        alumnoToModify.setApellido(alumno.getApellido());
        alumnoToModify.setEmail(alumno.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoToModify));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
