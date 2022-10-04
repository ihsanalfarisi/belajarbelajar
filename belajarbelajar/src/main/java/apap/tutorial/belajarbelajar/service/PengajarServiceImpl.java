package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.repository.PengajarDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PengajarServiceImpl implements PengajarService{
    @Autowired
    PengajarDb pengajarDb;

    @Override
    public void addPengajar(PengajarModel pengajar) {
        pengajarDb.save(pengajar);
    }

    @Override
    public PengajarModel updatePengajar(PengajarModel pengajar) {
        pengajarDb.save(pengajar);
        return pengajar;
    }

    @Override
    public PengajarModel getPengajarByNoPengajar(Long noPengajar) {
        Optional<PengajarModel> pengajar = pengajarDb.findByPengajarUsingQuery(noPengajar);
        if (pengajar.isPresent()) {
            return pengajar.get();
        } else return null;
    }

    @Override
    public void deletePengajar(PengajarModel pengajar) {
        pengajarDb.delete(pengajar);
    }

    @Override
    public List<PengajarModel> getListPengajar() {
        return pengajarDb.findAll();
    }

    public String checkUpdatePengajar(CourseModel course) {
        if (course.getTanggalBerakhir().isBefore(LocalDateTime.now())) return "form-update-pengajar";
        else return "error-update-pengajar";
    }

    public String checkDeletePengajar(CourseModel course, PengajarModel pengajar) {
        if (course.getTanggalBerakhir().isBefore(LocalDateTime.now())){
            deletePengajar(pengajar);
            return "form-delete-pengajar";
        } else return "error-delete-pengajar";
    }
}