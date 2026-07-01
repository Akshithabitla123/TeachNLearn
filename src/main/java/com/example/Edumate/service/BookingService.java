package com.example.Edumate.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Edumate.Enum.Status;
import com.example.Edumate.dto.BookingDTO;
import com.example.Edumate.dto.BookingResponseDTO;
import com.example.Edumate.dto.SkillResponseDTO;
import com.example.Edumate.dto.UserDTO;
import com.example.Edumate.model.Booking;
import com.example.Edumate.model.Skill;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.BookingRepo;
import com.example.Edumate.repository.SkillRepo;
import com.example.Edumate.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepo bookingRepo;
    private final UserRepo userRepo;
    private final SkillRepo skillRepo;
    private final RescheduleReqService service;
    private SkillResponseDTO mapSkill(Skill skill){

        SkillResponseDTO dto = new SkillResponseDTO(
        skill.getId(),
        skill.getTitle(),
        skill.getDescription(),
        skill.getCategory(),
        skill.getExperienceLevel(),
        skill.getPrice(),
        null
        );
        return dto;
    }
    private UserDTO mapUser(User user){
    return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getBio(),
            user.getRating(),
            user.getProfileImage(),
            user.getLinkedIn(),
            user.getGithub(),
            user.isVerified()
    );
}
    private BookingResponseDTO mapToDTO(Booking booking) {
        BookingResponseDTO dto=new BookingResponseDTO();
        dto.setId(booking.getId());
        dto.setMentor(mapUser(booking.getMentor()));
        dto.setStudent(mapUser(booking.getStudent())); 
        dto.setSkill(mapSkill(booking.getSkill()));
        dto.setSessionDate(booking.getSessionDate());
        dto.setStatus(booking.getStatus());
        dto.setMessage(booking.getMessage());
        dto.setMentorCompleted(booking.isMentorCompleted());
        dto.setStudentCompleted(booking.isStudentCompleted());
        dto.setReviewed(booking.isReviewed());
        return dto;
    }
    //create booking
    public BookingResponseDTO createBooking(BookingDTO dto){
        User student=userRepo.findById(dto.getStudentId()).
                orElseThrow(()->new RuntimeException("Student Not found"));
        User mentor=userRepo.findById(dto.getMentorId())
                .orElseThrow(()->new RuntimeException("Mentor not found"));
        if(!mentor.isVerified()){
            throw new RuntimeException("Mentor is not verified by the Admin");
        }
        Skill skill=skillRepo.findById(dto.getSkillId())
                .orElseThrow(()->new RuntimeException("Skill not found"));
        Optional<Booking> existingBooking=bookingRepo.findFirstByStudentIdAndSkillIdAndStatusInOrderBySessionDate(student.getId(), skill.getId(),
                List.of(Status.PENDING,Status.ACCEPTED,Status.RESCHEDULE_REQUESTED,Status.RESCHEDULED));
        if(existingBooking.isPresent()){
            throw new RuntimeException("Session already booked");
        }
        Booking booking=new Booking();
        booking.setStudent(student);
        booking.setMentor(mentor);
        booking.setSkill(skill);
        booking.setSessionDate(dto.getSessionDate());
        booking.setMessage(dto.getMessage());
        booking.setStatus(Status.PENDING);
        return mapToDTO(bookingRepo.save(booking));
    }
    //get bookings for mentor
    public List<BookingResponseDTO> getMentorBookings(Long mentorId){
        return bookingRepo.findByMentorId(mentorId)
        .stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
    }
    //get bookings by a student
    public List<BookingResponseDTO> getStudentBookingsByEmail(String email){
        User user=userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        return bookingRepo.findByStudentId(user.getId())
            .stream()
            .map(this::mapToDTO)
            .toList();
    }
    //update the status of the session
    //accepted and rejected-> mentor
    //completed should be by both mentor and student
    public BookingResponseDTO updateStatus(Long bookingId,Long userId,Status status){
        Booking booking=bookingRepo.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));
        Long mentorId=booking.getMentor().getId();
        //accept or reject only by mentor
       if(status==Status.ACCEPTED || status==Status.REJECTED){
            if(!mentorId.equals(userId)){
                throw new RuntimeException("Only mentor can accept/reject");
            }
            booking.setStatus(status);
       }
       else if(status==Status.RESCHEDULE_REQUESTED){
            if(booking.getStatus()==Status.COMPLETED || booking.getStatus()==Status.CANCELLED){
                throw new RuntimeException("Cannot reschedule");
            }
            service.createRequest(bookingId);
       }
        return mapToDTO(bookingRepo.save(booking));
    }
    public BookingResponseDTO markCompleted(Long bookingId,Long userId){
        Booking booking=bookingRepo.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));
        if(booking.getStatus()!=Status.ACCEPTED){
            throw new RuntimeException("Only accepted sessions can be completed");
        }
        Long mentorId=booking.getMentor().getId();
        Long studentId=booking.getStudent().getId();
        if(mentorId.equals(userId)){
            booking.setMentorCompleted(true);
        }
        else if(studentId.equals(userId)){
            booking.setStudentCompleted(true);
        }
        else{
            throw new RuntimeException("Unauthorized user");
        }
        if(booking.isMentorCompleted() && booking.isStudentCompleted()){
            booking.setStatus(Status.COMPLETED);
        }
        return mapToDTO(bookingRepo.save(booking));
    }
    //get booking details
    public BookingResponseDTO getBookingById(Long bookingId){
        Booking booking= bookingRepo.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));
        return mapToDTO(booking);
    }
    //get existing booking
    public BookingResponseDTO getExistingBooking(Long studentId,Long skillId){
       Booking booking= bookingRepo.findFirstByStudentIdAndSkillIdOrderBySessionDate(studentId, skillId)
        .orElse(null);
        if(booking==null)return null;
        return mapToDTO(booking);
    }
    //cancel booking (by the student)
    public BookingResponseDTO cancelBooking(Long bookingId,String email){
        Booking booking=bookingRepo.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));
        if(!booking.getStudent().getEmail().equals(email)){
            throw new RuntimeException("Unauthorized");
        }
        booking.setStatus(Status.CANCELLED);
        return mapToDTO(bookingRepo.save(booking));
    }

}
