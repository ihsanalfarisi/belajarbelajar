package apap.tutorial.belajarbelajar.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import apap.tutorial.belajarbelajar.service.PengajarRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import apap.tutorial.belajarbelajar.model.PengajarModel;

@RestController
@RequestMapping("/api/v1")
public class PengajarRestController {

    @Autowired
    private PengajarRestService pengajarRestService;

    @PostMapping(value = "/pengajar")
    private PengajarModel createPengajar(@Valid @RequestBody PengajarModel pengajar, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );
        }else {
            return pengajarRestService.createPengajar(pengajar);
        }
    }

    @PutMapping(value = "/pengajar/{noPengajar}")
    private PengajarModel updatePengajar(@PathVariable("noPengajar") String noPengajar, @RequestBody PengajarModel pengajar) {
        try {
            return pengajarRestService.updatePengajar(Long.parseLong(noPengajar), pengajar);
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pengajar dengan no " + noPengajar + " not found"
            );
        }
    }

    @GetMapping(value = "/pengajar/{noPengajar}")
    private PengajarModel retrievePengajar(@PathVariable("noPengajar") String noPengajar){
        try {
            return pengajarRestService.getPengajarByNo(Long.parseLong(noPengajar));
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pengajar dengan no " + noPengajar + " not found"
            );
        }
    }

    @GetMapping(value = "/list-pengajar")
    private List<PengajarModel> retrieveListPengajar(){
        return pengajarRestService.retrieveListPengajar();
    }

    @DeleteMapping(value = "/pengajar/{noPengajar}")
    private ResponseEntity deletePengajar(@PathVariable("noPengajar") String noPengajar){
        try {
            pengajarRestService.deletePengajar(noPengajar);
            return ResponseEntity.ok("Pengajar with no " + noPengajar + " has been deleted succesfully");
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ("Pengajar with no" + noPengajar + " not found"
            ));
        }catch (UnsupportedOperationException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Pengajar cannot be deleted"
            );
        }
    }

    @GetMapping("/pengajar/jenis-kelamin/{noPengajar}")
    private PengajarModel setJenisKelaminGenderizeAPI(@PathVariable("noPengajar") Long noPengajar) {
        try {
            return pengajarRestService.setJenisKelaminPengajarGenderizeAPI(noPengajar);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nomor pengajar " + noPengajar.toString() + " not found"
            );
        } catch (UnsupportedOperationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Course is still open"
            );
        }
    }
}