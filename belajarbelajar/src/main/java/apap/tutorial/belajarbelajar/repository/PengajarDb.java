package apap.tutorial.belajarbelajar.repository;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PengajarDb extends JpaRepository<PengajarModel, Long>{
    Optional<PengajarModel> findByNoPengajar(Long noPengajar);

    @Query("SELECT c FROM PengajarModel c WHERE c.noPengajar = :noPengajar")
    Optional<PengajarModel> findByPengajarUsingQuery(@Param("noPengajar") Long noPengajar);
}

