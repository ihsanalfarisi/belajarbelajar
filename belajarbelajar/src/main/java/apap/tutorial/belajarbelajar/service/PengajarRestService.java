package apap.tutorial.belajarbelajar.service;
import java.util.List;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import reactor.core.publisher.Mono;

public interface PengajarRestService {
    PengajarModel createPengajar(PengajarModel pengajar);
    List<PengajarModel> retrieveListPengajar();
    PengajarModel getPengajarByNo(Long noPengajar);
    PengajarModel updatePengajar(Long noPengajar, PengajarModel pengajarUpdate);
    void deletePengajar(String noPengajar);
    PengajarModel setJenisKelaminPengajarGenderizeAPI(Long noPengajar);
}
