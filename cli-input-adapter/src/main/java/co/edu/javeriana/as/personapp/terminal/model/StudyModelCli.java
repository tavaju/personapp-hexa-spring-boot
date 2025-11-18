package co.edu.javeriana.as.personapp.terminal.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyModelCli {
	private Integer personaCc;
	private String personaNombre;
	private Integer profesionId;
	private String profesionNombre;
	private LocalDate fechaGraduacion;
	private String universidad;
}

