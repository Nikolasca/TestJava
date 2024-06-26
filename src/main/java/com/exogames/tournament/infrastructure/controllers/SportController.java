package com.exogames.tournament.infrastructure.controllers;

import com.exogames.tournament.application.servicesImpl.SportServiceImpl;
import com.exogames.tournament.domain.dtos.CreateSportDto;
import com.exogames.tournament.domain.dtos.SportDto;
import com.exogames.tournament.domain.dtos.UpdateSportDto;
import com.exogames.tournament.infrastructure.exceptions.CountryNotFoundException;
import com.exogames.tournament.infrastructure.exceptions.ExistentSportException;
import com.exogames.tournament.infrastructure.exceptions.SportNotFoundException;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/sports")
public class SportController {

    private final SportServiceImpl service;

    public SportController(SportServiceImpl service) {
        this.service = service;
    }

    @GetMapping("") // GET ALL SPORTS
    public ResponseEntity<List<SportDto>> getAllSports(){
        List<SportDto> sports = service.getAllSports();
        return new ResponseEntity<>(sports, HttpStatus.OK);
    }

    @GetMapping("/{id}") // GET SPORT
    public ResponseEntity<?> getSport(@PathVariable String id){
        try{
            SportDto sport = service.getSportById(id);
            return new ResponseEntity<>(sport, HttpStatus.OK);
        }catch (SportNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("") // CREATE NEW SPORT
    public ResponseEntity<?> createSport(@RequestPart("sport") CreateSportDto createSportDto, @RequestPart("image")MultipartFile icon){
        try{
            SportDto sport = service.createSport(createSportDto, icon);
            return new ResponseEntity<>(sport, HttpStatus.CREATED);
        }catch (ExistentSportException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }catch (SportNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("") // UPDATE SPORT
    public ResponseEntity<?> updateSport(@RequestPart("sport") UpdateSportDto updateSportDto, @RequestPart("image") MultipartFile icon){
        try{
            SportDto sport = service.updateSport(updateSportDto, icon);
            return new ResponseEntity<>(sport, HttpStatus.OK);
        }catch (SportNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/deactivate") // DEACTIVATE SPORT
    public ResponseEntity<?> deactivateSport(@RequestParam String id){
        try {
            SportDto sport = service.deactivateSport(id);
            return new ResponseEntity<>(sport, HttpStatus.OK);
        }catch (SportNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/activate") // ACTIVATE SPORT
    public ResponseEntity<?> activateSport(@RequestParam String id){
        try{
            SportDto sport = service.activateSport(id);
            return new ResponseEntity<>(sport, HttpStatus.OK);
        }catch (SportNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") // DELETE SPORT
    public ResponseEntity<?> deleteSport(@PathVariable String id){
        try{
            service.deleteSport(id);
            return ResponseEntity.ok().build();
        }catch (CountryNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
