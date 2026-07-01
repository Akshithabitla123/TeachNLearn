package com.example.Edumate.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Edumate.Enum.Status;
import com.example.Edumate.dto.BookingDTO;
import com.example.Edumate.dto.BookingResponseDTO;
import com.example.Edumate.model.Booking;
import com.example.Edumate.model.Skill;
import com.example.Edumate.model.User;
import com.example.Edumate.repository.BookingRepo;
import com.example.Edumate.repository.SkillRepo;
import com.example.Edumate.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private BookingRepo bookingRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private SkillRepo skillRepo;
    @Mock
    private RescheduleReqService service;
    @InjectMocks
    private BookingService bookingService;

    //test1-> booking created successfully
    @Test
    void createBooking_Successfully(){
        BookingDTO dto=new BookingDTO();
        dto.setStudentId(13L);
        dto.setMentorId(15L);
        dto.setSkillId(14L);
        dto.setSessionDate(LocalDateTime.now());

        User student=new User();
        student.setId(13L);

        User mentor=new User();
        mentor.setId(15L);
        mentor.setVerified(true);

        Skill skill=new Skill();
        skill.setId(14L);
        when(userRepo.findById(13L)).thenReturn(Optional.of(student));
        when(userRepo.findById(15L)).thenReturn(Optional.of(mentor));
        when(skillRepo.findById(14L)).thenReturn(Optional.of(skill));
        
        when(bookingRepo.findFirstByStudentIdAndSkillIdAndStatusInOrderBySessionDate(anyLong(),anyLong(),anyList())).
            thenReturn(Optional.empty());

        Booking saved=new Booking();
        saved.setId(20L);
        saved.setStudent(student);
        saved.setMentor(mentor);
        saved.setSkill(skill);
        saved.setStatus(Status.PENDING);

        when(bookingRepo.save(any(Booking.class))).thenReturn(saved);

        BookingResponseDTO result=bookingService.createBooking(dto);
        assertEquals(Status.PENDING,result.getStatus());
        verify(bookingRepo).save(any(Booking.class));
    }

    //test2-> mentor not verified
    @Test
    void createBooking_MentorNotVerified(){
        BookingDTO dto=new BookingDTO();
        dto.setStudentId(13L);
        dto.setMentorId(15L);
        User student=new User();

        User mentor=new User();
        mentor.setVerified(false);

        when(userRepo.findById(13L)).thenReturn(Optional.of(student));
        when(userRepo.findById(15L)).thenReturn(Optional.of(mentor));
        RuntimeException ex=assertThrows(RuntimeException.class,()->bookingService.createBooking(dto));
        assertEquals("Mentor is not verified by the Admin",ex.getMessage());
        verify(bookingRepo,never()).save(any());
    }

    //test3-> booking already eists
    @Test
    void createBooking_AlreadyExists(){
        BookingDTO dto=new BookingDTO();
        dto.setStudentId(13L);
        dto.setMentorId(15L);
        dto.setSkillId(14L);

        User student=new User();
        student.setId(13L);

        User mentor=new User();
        mentor.setId(15L);
        mentor.setVerified(true);

        Skill skill=new Skill();
        skill.setId(14L);
        when(userRepo.findById(13L)).thenReturn(Optional.of(student));
        when(userRepo.findById(15L)).thenReturn(Optional.of(mentor));
        when(skillRepo.findById(14L)).thenReturn(Optional.of(skill));
        
        when(bookingRepo.findFirstByStudentIdAndSkillIdAndStatusInOrderBySessionDate(anyLong(),anyLong(),anyList())).
            thenReturn(Optional.of(new Booking()));
        RuntimeException ex=assertThrows(RuntimeException.class,()->bookingService.createBooking(dto));
        assertEquals("Session already booked",ex.getMessage());
        verify(bookingRepo,never()).save(any());
    }

    //test4-> mentor accepts booking
    @Test
    void updateStatus_AcceptBooking(){
        User mentor=new User();
        mentor.setId(15L);

        User student=new User();
        student.setId(13L);

        Skill skill=new Skill();
        skill.setId(14L);
        Booking booking=new Booking();
        booking.setMentor(mentor);
        booking.setStudent(student);
        booking.setSkill(skill);
        booking.setStatus(Status.PENDING);
        
        when(bookingRepo.findById(13L)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        BookingResponseDTO dto=bookingService.updateStatus(13L,15L,Status.ACCEPTED);
        assertEquals(Status.ACCEPTED,dto.getStatus());
        verify(bookingRepo).save(booking);
    }

    //test5-> others cannot accept (~mentor)
    @Test
    void updateStatus_ErrorAcceptBooking(){
        User mentor=new User();
        mentor.setId(12L);
        Booking booking=new Booking();
        booking.setMentor(mentor);
        
        when(bookingRepo.findById(13L)).thenReturn(Optional.of(booking));
        RuntimeException ex=assertThrows(RuntimeException.class,()->bookingService.updateStatus(13L,20L,Status.ACCEPTED));
        assertEquals("Only mentor can accept/reject",ex.getMessage());
        verify(bookingRepo,never()).save(any());
    }

    //test6-> mentor complete
    @Test
    void markCompleted_ByMentor(){
        User mentor=new User();
        mentor.setId(15L);

        User student=new User();
        student.setId(13L);
        Skill skill=new Skill();
        skill.setId(14L);
        Booking booking=new Booking();
        booking.setMentor(mentor);
        booking.setStudent(student);
        booking.setStatus(Status.ACCEPTED);
        booking.setSkill(skill);
        when(bookingRepo.findById(20L)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        BookingResponseDTO dto=bookingService.markCompleted(20L,15L);
        assertEquals(true,dto.isMentorCompleted());
        assertEquals(false,dto.isStudentCompleted());
        verify(bookingRepo).save(any(Booking.class));
    }

    //test7-> student complete
    @Test
    void markCompleted_ByStudent(){
        User mentor=new User();
        mentor.setId(15L);

        User student=new User();
        student.setId(13L);
        Skill skill=new Skill();
        skill.setId(14L);
        Booking booking=new Booking();
        booking.setMentor(mentor);
        booking.setStudent(student);
        booking.setStatus(Status.ACCEPTED);
        booking.setSkill(skill);
        when(bookingRepo.findById(20L)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        BookingResponseDTO dto=bookingService.markCompleted(20L,13L);
        assertEquals(false,dto.isMentorCompleted());
        assertEquals(true,dto.isStudentCompleted());
        verify(bookingRepo).save(any(Booking.class));
    }

    //test8-> both complete
    @Test
    void markCompleted_Both(){
        User mentor=new User();
        mentor.setId(15L);

        User student=new User();
        student.setId(13L);
        Skill skill=new Skill();
        skill.setId(14L);
        Booking booking=new Booking();
        booking.setMentor(mentor);
        booking.setStudent(student);
        booking.setStatus(Status.ACCEPTED);
        booking.setSkill(skill);
        booking.setMentorCompleted(true);
        when(bookingRepo.findById(20L)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        BookingResponseDTO dto=bookingService.markCompleted(20L,13L);
        assertEquals(Status.COMPLETED,dto.getStatus());
        assertEquals(true,dto.isMentorCompleted());
        assertEquals(true,dto.isStudentCompleted());
    }

    //test9-> cancel booking successfully
    @Test
    void cancelBooking_Successfully(){
        User student=new User();
        student.setId(13L);
        student.setEmail("student@gmail.com");

        User mentor=new User();
        mentor.setId(15L);
        Skill skill=new Skill();
        skill.setId(14L);
        Booking booking=new Booking();
        booking.setMentor(mentor);
        booking.setStudent(student);
        booking.setStatus(Status.ACCEPTED);
        booking.setSkill(skill);
        when(bookingRepo.findById(20L)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);
        BookingResponseDTO dto=bookingService.cancelBooking(20L,"student@gmail.com");
        assertEquals(Status.CANCELLED,dto.getStatus());
        verify(bookingRepo).save(any(Booking.class));
    }

    //test10-> cancelling by unauthorized user
    @Test
    void cancelBooking_unauthorized(){
        User student=new User();
        student.setEmail("student@gmail.com");

        Booking booking=new Booking();
        booking.setStudent(student);
        when(bookingRepo.findById(20L)).thenReturn(Optional.of(booking));
        RuntimeException ex=assertThrows(RuntimeException.class,()->bookingService.cancelBooking(20L,"abc@gmail.com"));
        assertEquals("Unauthorized",ex.getMessage());
        verify(bookingRepo,never()).save(any());
    }

    //test11-> mentor rejects booking
    @Test
    void updateStatus_RejectBooking(){
        User mentor = new User();
        mentor.setId(15L);
        User student = new User();
        student.setId(13L);

        Skill skill = new Skill();
        skill.setId(14L);

        Booking booking = new Booking();
        booking.setMentor(mentor);
        booking.setStudent(student);
        booking.setSkill(skill);

        when(bookingRepo.findById(20L)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any())).thenReturn(booking);
        BookingResponseDTO dto=bookingService.updateStatus(20L,15L,Status.REJECTED);
        assertEquals(Status.REJECTED,dto.getStatus());
    }

    //test12-> reschedule request success
    @Test
    void updateStatus_RequestReschedule(){
        User mentor = new User();
        mentor.setId(15L);

        User student = new User();
        student.setId(13L);

        Skill skill = new Skill();
        skill.setId(14L);

        Booking booking = new Booking();

        booking.setMentor(mentor);
        booking.setStudent(student);
        booking.setSkill(skill);

        booking.setStatus(Status.ACCEPTED);

        when(bookingRepo.findById(20L)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any())).thenReturn(booking);
        bookingService.updateStatus(20L,15L,Status.RESCHEDULE_REQUESTED);
        verify(service).createRequest(20L);
        verify(bookingRepo).save(any());
    }

    //test 13-> cannot reschedule completed booking
    @Test
    void updateStatus_CannotRescheduleCompleted() {
        User mentor = new User();
        mentor.setId(15L);

        User student = new User();
        student.setId(13L);
        Booking booking = new Booking();
        booking.setMentor(mentor);
        booking.setStudent(student);
        booking.setStatus(Status.COMPLETED);
        when(bookingRepo.findById(20L)).thenReturn(Optional.of(booking));
        RuntimeException ex=assertThrows(RuntimeException.class,() -> bookingService.updateStatus( 20L,15L,Status.RESCHEDULE_REQUESTED));
        assertEquals("Cannot reschedule",ex.getMessage());
        verify(service,never()).createRequest(anyLong());
    }

    //test14-> cannot reschedule cancelled bookings
     @Test
    void updateStatus_CannotRescheduleCancelled() {
        User mentor = new User();
        mentor.setId(15L);

        User student = new User();
        student.setId(13L);
        Booking booking = new Booking();
        booking.setMentor(mentor);
        booking.setStudent(student);
        booking.setStatus(Status.CANCELLED);
        when(bookingRepo.findById(20L)).thenReturn(Optional.of(booking));
        RuntimeException ex=assertThrows(RuntimeException.class,() -> bookingService.updateStatus( 20L,15L,Status.RESCHEDULE_REQUESTED));
        assertEquals("Cannot reschedule",ex.getMessage());
        verify(service,never()).createRequest(anyLong());
    }

    //test 15-> booking not found for markcomplete
    @Test
    void markCompleted_BookinngNotFound(){
        when(bookingRepo.findById(20L)).thenReturn(Optional.empty());
        RuntimeException ex=assertThrows(RuntimeException.class,()->bookingService.markCompleted(20L,15L));
        assertEquals("Booking not found", ex.getMessage());
    }
    
}
