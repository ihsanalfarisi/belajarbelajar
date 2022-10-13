package apap.tutorial.belajarbelajar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(value={"course"}, allowSetters = true)
@Table(name = "pengajar")
public class PengajarModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noPengajar;

    @NotNull
    @Size(max = 30)
    @Column(name = "nama_pengajar", nullable = false)
    private String namaPengajar;

    @NotNull
    @Column(name = "is_pengajar_universitas", nullable = false)
    private Boolean isPengajarUniversitas;

    @Column(name="jenis_kelamin")
    private Boolean jenisKelamin;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "code", referencedColumnName = "code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CourseModel course;
}