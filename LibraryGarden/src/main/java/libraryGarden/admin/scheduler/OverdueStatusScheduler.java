package libraryGarden.admin.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import libraryGarden.admin.mapper.AdminBookLoanMapper;
import libraryGarden.admin.mapper.AdminBookReservationMapper;

@Component
public class OverdueStatusScheduler {

    @Autowired
    private AdminBookLoanMapper adminBookLoanMapper;
    
    @Autowired
    private AdminBookReservationMapper adminBookReservationMapper;
    
    // (cron 표현식은 필요에 따라 수정)
    @Scheduled(cron = "0 * * * * ?") // 1분마다 업데이트 되도록 설정
    public void updateOverdueStatus() {
        int updatedCount = adminBookLoanMapper.updateOverdueStatus();
        System.out.println("업데이트된 OVERDUE 레코드 수: " + updatedCount);
    }
    
    // 매일 자정에 예약 픽업일이 7일 남은 도서의 상태를 "예약대기"로 업데이트
    @Scheduled(cron = "0 * * * * ?") // 1분 마다 일단 변경함
    public void updateReservationWaitStatus() {
        int updatedCount = adminBookReservationMapper.updateBooksToWaitStatus();
        System.out.println("예약대기로 변경된 도서 수: " + updatedCount);
    }
    
    

}
