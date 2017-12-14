package com.bootcamp.Diffusion.controllers;

import com.bootcamp.Diffusion.services.PlainteService;
import com.bootcamp.entities.Etape;
import com.bootcamp.entities.Plainte;
import com.bootcamp.entities.WorkFlow;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestController("PlainteController")
@RequestMapping("/plaintes")
@Api(value = "Plainte API", description = "Plainte API")
public class PlainteController {
    
    @Autowired
    PlainteService plainteService;

    // create une plainte,
    // avoir une ref pour suivre une plainte ,
    // envoi msg au cps
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new plainte", notes = "Create a new plainte")
    public ResponseEntity<Plainte> sePlaindre(Plainte plainte) throws FileNotFoundException, IOException, IOException {
        HttpStatus httpStatus = null;
        Plainte plainte1 = new Plainte();

        try {
            plainte1 = plainteService.sendPlainte(plainte);
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(plainte1, httpStatus);
    }

    // creer une Etape,

    @RequestMapping(method = RequestMethod.POST,value = "/etape")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create une etape de traitement de plainte", notes = "Create une etape de traitement de plainte")
    public ResponseEntity<Etape> creerEtape(Etape etape) throws FileNotFoundException, IOException, IOException {
        HttpStatus httpStatus = null;
        Etape etape1 = new Etape();

        try {
            etape1 = plainteService.createEtape(etape);
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(etape1, httpStatus);
    }

    //consulter une plainte a partir de la reference
    @RequestMapping(method = RequestMethod.GET, value = "/{reference}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "consulter une plainte apartir de la reference", notes = "consulter une plainte apartir de la reference")
    public ResponseEntity<Plainte> getPlaineByReference(@PathVariable(name = "reference") String reference) throws FileNotFoundException, IOException, IOException {
        HttpStatus httpStatus = null;
        Plainte plainte = new Plainte();

        try {
            plainte = plainteService.readPlainteByReference(reference);
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(plainte, httpStatus);
    }

    //consulter la liste des plaintes
    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "consulter toutes les plaintes ", notes = "consulter la liste des plaintes ")
    public ResponseEntity<List<Plainte>> getPlaintesList() throws FileNotFoundException, IOException, IOException {
        HttpStatus httpStatus = null;
        List<Plainte> plainteList = new ArrayList<>();

        try {
            plainteList = plainteService.readPlainte();
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<List<Plainte>>(plainteList, httpStatus);
    }

    //consulter la liste des etapes
    @RequestMapping(method = RequestMethod.GET,value = "/etapes/")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "consulter toutes les etapes ", notes = "consulter la liste des etapes ")
    public ResponseEntity<List<Etape>> getEtapesList() throws FileNotFoundException, IOException, IOException {
        HttpStatus httpStatus = null;
        List<Etape> etapeList = new ArrayList<>();

        try {
            etapeList = plainteService.readEtape();
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<List<Etape>>(etapeList, httpStatus);
    }
    //Suivre une plainte a partir d'une reference
    @RequestMapping(method = RequestMethod.GET, value = "suivre/{reference}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Suivre une plainte a partir d'une reference", notes = "Suivre une plainte a partir d'une reference")
    public ResponseEntity<List<WorkFlow>> suivreAvecReference(@PathVariable(name = "reference") String reference) throws SQLException {

        HttpStatus httpStatus = null;
        List<WorkFlow> workFlows = new ArrayList<>();

        try {
            Plainte plainte = plainteService.readPlainteByReference(reference);
            workFlows = plainteService.readWorkFlowByIdPlainte(plainte.getId());
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(workFlows, httpStatus);
    }

    //valider une plainte
    @RequestMapping(method = RequestMethod.PUT, value = "valider/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Valid a plainte", notes = "Valid a plainte")
    public ResponseEntity<Plainte> update(@PathVariable(name = "id") int id) {

        HttpStatus httpStatus = null;
        Plainte plainte = new Plainte();

        try {
            plainte = plainteService.changeEtat(id);
            plainte.setEtatValidation(true);
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(plainte, httpStatus);
    }

    // changer l'eatpe d'une plainte
    @RequestMapping(method = RequestMethod.POST, value = "/{idPlainte}/{idEtape}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "change level of a plainte", notes = "change level of a plainte")
    public ResponseEntity<List<WorkFlow>> setPlainteLevel(@PathVariable(name = "idPlainte") int idPlainte, @PathVariable(name = "idEtape") int idEtape) throws FileNotFoundException, IOException, IOException {
        HttpStatus httpStatus = null;
        List<WorkFlow> returWorkFlows = new ArrayList<>();
        Plainte plainte = new Plainte();

        try {
            WorkFlow workFlow = new WorkFlow();
            workFlow.setIdEtape(idEtape);
            workFlow.setIdPlainte(idPlainte);
            plainteService.createWorFlow(workFlow);

            returWorkFlows = plainteService.readWorkFlowByIdPlainte(idPlainte);
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(returWorkFlows, httpStatus);
    }



//    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Delete a plainte", notes = "Delete a plainte")
//    public void delete(@PathVariable(name = "id") int id) {
//
//        HttpStatus httpStatus = null;
//
//        try {
//            Plainte plainte = plainteService.delete(id);
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException exception){
//
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Read a plainte", notes = "Read a plainte")
//    public ResponseEntity<Plainte> read(@PathVariable(name = "id") int id) {
//
//        HttpStatus httpStatus = null;
//        Plainte plainte = new Plainte();
//        try {
//            plainte = plainteService.read(id);
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException exception){
//
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<Plainte>(plainte, httpStatus);
//    }
//
//    @RequestMapping(method = RequestMethod.GET)
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Read a plainte", notes = "Read a plainte")
//    public ResponseEntity<List<Plainte>> read() {
//
//        List<Plainte> plaintes = new ArrayList<Plainte>();
//        HttpStatus httpStatus = null;
//
//        try {
//            plaintes = plainteService.read();
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException e){
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<List<Plainte>>(plaintes, httpStatus);
//    }

}
