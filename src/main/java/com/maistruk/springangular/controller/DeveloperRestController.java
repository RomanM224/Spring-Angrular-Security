package com.maistruk.springangular.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maistruk.springangular.model.Developer;
import com.maistruk.springangular.model.Role;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestController {
    
    private List<Developer> DEVELOPERS = Stream.of(
            new Developer(1L, "Ivan", "Ivanov"),
            new Developer(2L, "Sergej", "Sergejev"),
            new Developer(3L, "Petr", "Petrov")
            ).collect(Collectors.toList());
    
    @GetMapping
 //   @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public List<Developer> getAll(){
        
        return DEVELOPERS;
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public Developer getById(@PathVariable Long id) {
        
        return DEVELOPERS.stream().filter(developre -> developre.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Developer create(@RequestBody Developer developer) {
        this.DEVELOPERS.add(developer);
        return developer;
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        this.DEVELOPERS.removeIf(developer -> developer.getId().equals(id));
    }
    

}
