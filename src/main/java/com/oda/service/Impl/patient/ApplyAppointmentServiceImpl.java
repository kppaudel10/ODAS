package com.oda.service.Impl.patient;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.ApplyDto;
import com.oda.enums.ApplyStatus;
import com.oda.model.patient.ApplyAppointment;
import com.oda.model.patient.Patient;
import com.oda.repo.patient.ApplyAppointmentRepo;
import com.oda.service.Impl.HospitalServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
@Service
@RequiredArgsConstructor
public class ApplyAppointmentServiceImpl {
    private final ApplyAppointmentRepo applyAppointmentRepo;
    private final HospitalServiceImpl hospitalService;

    private final PatientServiceImpl patientService;


    public ApplyAppointment save(ApplyDto apply)
    {
        ApplyAppointment applyAppointment = ApplyAppointment.builder()
                .id(apply.getId())
                .patient(AuthorizedUser.getPatient())
                .doctor(apply.getDoctor())
                .fromTime(apply.getFormTime())
                .applyStatus(ApplyStatus.PENDING)
                .applyDate(new Date())
                .toTime(apply.getToTime())
                .hospital(apply.getHospital()).build();
        //save into database
       ApplyAppointment applyAppointment1 = applyAppointmentRepo.save(applyAppointment);
        return applyAppointment1;
    }

    public List<ApplyAppointment> findAppointmentOfPatient(Integer patientId){
        return applyAppointmentRepo.findApplyAppointmentByPatientId(patientId);
    }

    public List<ApplyAppointment> findAppointmentForHospitalOfPending(Integer hospitalId){
        return applyAppointmentRepo.findApplyAppointmentByHospitalOfPending(hospitalId);
    }

    public List<ApplyAppointment> findAppointmentForHospitalOfBooked(Integer hospitalId){
        return applyAppointmentRepo.findApplyAppointmentByHospitalOfBooked(hospitalId);
    }

    public ApplyAppointment findById(Integer id){
        return applyAppointmentRepo.findById(id).get();
    }

    public ApplyAppointment update(ApplyAppointment applyAppointment){
        //update status
        applyAppointment.setApplyStatus(ApplyStatus.BOOKED);
        applyAppointmentRepo.save(applyAppointment);
        return applyAppointment;
    }

    /*
    this is method return list of appointment that is for him/her
    only booked appointment will display
     */
    public List<ApplyAppointment> findAppointmentThatIsBooked(){
        return applyAppointmentRepo.findApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId());
    }

    public List<ApplyAppointment> findAppointMentByPatientId(String deatils){
        //find patient details
        Patient patient= patientService.findPatientByContactDetails(deatils);
        if(patient !=null){
            return applyAppointmentRepo.findApplyAppointmentByPatientId(patient.getId());
        }else {
            return null;
        }
    }

    public void deleteBYId(Integer integer){
        applyAppointmentRepo.deleteById(integer);
    }

}
