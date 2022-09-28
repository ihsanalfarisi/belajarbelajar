package apap.tutorial.belajarbelajar.service;
import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.repository.PengajarDb;

public interface PengajarService {
    void addPengajar(PengajarModel pengajar);

    PengajarModel updatePengajar(PengajarModel pengajar);

    PengajarModel getPengajarByNoPengajar(Long noPengajar);

    void deletePengajar(PengajarModel pengajar);

    String checkUpdatePengajar(CourseModel course);

    String checkDeletePengajar(CourseModel course, PengajarModel pengajar);
}