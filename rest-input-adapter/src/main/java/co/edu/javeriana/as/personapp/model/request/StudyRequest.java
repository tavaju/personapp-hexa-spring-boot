package co.edu.javeriana.as.personapp.model.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRequest {
	private Integer personaCc;
	private Integer profesionId;
	private LocalDate fechaGraduacion;
	private String universidad;
	private String database;
}

