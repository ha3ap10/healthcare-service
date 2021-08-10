import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

public class MedicalServiceImplTest {

    @Test
    public void testCheckBloodPressure() {
        PatientInfo patientInfo = new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

        PatientInfoRepository patientInfoFileRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoFileRepository.getById(any()))
                .thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);

        BloodPressure currentPressure = new BloodPressure(80, 120);
        medicalService.checkBloodPressure("patient", currentPressure);

        Mockito.verify(sendAlertService, Mockito.times(1)).send(any());
    }

    @Test
    public void testCheckTemperature() {
        PatientInfo patientInfo = new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

        PatientInfoRepository patientInfoFileRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoFileRepository.getById(any()))
                .thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);

        BigDecimal currentTemperature = new BigDecimal("34.9");
        medicalService.checkTemperature("patient", currentTemperature);

        Mockito.verify(sendAlertService, Mockito.times(1)).send(any());
    }

    @Test
    public void testNoMessage() {
        PatientInfo patientInfo = new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

        PatientInfoRepository patientInfoFileRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoFileRepository.getById(any()))
                .thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);

        BloodPressure currentPressure = new BloodPressure(120, 80);
        medicalService.checkBloodPressure("patient", currentPressure);

        BigDecimal currentTemperature = new BigDecimal("36.65");
        medicalService.checkTemperature("patient", currentTemperature);

        Mockito.verify(sendAlertService, Mockito.times(0)).send(any());

    }
}
