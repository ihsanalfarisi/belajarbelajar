package apap.tutorial.belajarbelajar.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.rest.CourseDetail;
import apap.tutorial.belajarbelajar.rest.GenderizeDetail;
import apap.tutorial.belajarbelajar.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tutorial.belajarbelajar.repository.PengajarDb;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PengajarRestServiceImpl implements PengajarRestService{

    private final WebClient webClient;

    public PengajarRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.genderizeUrl).build();
    }
    @Autowired
    private PengajarDb pengajarDb;

    @Override
    public PengajarModel createPengajar(PengajarModel pengajar){
        return pengajarDb.save(pengajar);
    }

    @Override
    public PengajarModel getPengajarByNo(Long noPengajar){
        Optional<PengajarModel> pengajar = pengajarDb.findByNoPengajar(noPengajar);
        if (pengajar.isPresent()){
            return pengajar.get();
        }
        else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public PengajarModel updatePengajar(Long noPengajar, PengajarModel pengajarUpdate){
        PengajarModel pengajar = getPengajarByNo(noPengajar);
        pengajar.setNamaPengajar(pengajarUpdate.getNamaPengajar());
        pengajar.setIsPengajarUniversitas(pengajarUpdate.getIsPengajarUniversitas());
        return pengajarDb.save(pengajar);
    }

    @Override
    public List<PengajarModel> retrieveListPengajar(){
        return pengajarDb.findAll();
    }

    @Override
    public void deletePengajar(String noPengajar){
        PengajarModel pengajar = getPengajarByNo(Long.parseLong(noPengajar));
        if (pengajar != null){
            pengajarDb.delete(pengajar);
        }else{
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public PengajarModel setJenisKelaminPengajarGenderizeAPI(Long noPengajar) {
        PengajarModel pengajar = getPengajarByNo(noPengajar);
        CourseModel course = pengajar.getCourse();
        if (!isClosed(course.getTanggalDimulai(), course.getTanggalBerakhir())) {
            throw new UnsupportedOperationException();
        }
        String namaDepanPengajar = pengajar.getNamaPengajar().split(" ")[0];
        Mono<GenderizeDetail> responseAPI = this.webClient.get().uri("/?name=" +
                namaDepanPengajar).retrieve().bodyToMono(GenderizeDetail.class);
        boolean genderBool = responseAPI.block().getGender().equals("female");
        pengajar.setJenisKelamin(genderBool);
        updatePengajar(noPengajar, pengajar);
        return getPengajarByNo(noPengajar);

    }

    public boolean isClosed(LocalDateTime tanggalDimulai, LocalDateTime tanggalBerakhir){
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(tanggalDimulai) || now.isAfter(tanggalBerakhir)){
            return true;
        }
        return false;
    }

}