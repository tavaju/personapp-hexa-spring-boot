package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.terminal.model.StudyModelCli;

@Mapper
public class StudyMapperCli {

	public StudyModelCli fromDomainToAdapterCli(Study study) {
		StudyModelCli studyModelCli = new StudyModelCli();
		if (study.getPerson() != null) {
			studyModelCli.setPersonaCc(study.getPerson().getIdentification());
			studyModelCli.setPersonaNombre(study.getPerson().getFirstName() + " " + study.getPerson().getLastName());
		}
		if (study.getProfession() != null) {
			studyModelCli.setProfesionId(study.getProfession().getIdentification());
			studyModelCli.setProfesionNombre(study.getProfession().getName());
		}
		studyModelCli.setFechaGraduacion(study.getGraduationDate());
		studyModelCli.setUniversidad(study.getUniversityName());
		return studyModelCli;
	}
}

