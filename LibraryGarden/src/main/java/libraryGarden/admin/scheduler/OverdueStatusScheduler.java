package libraryGarden.admin.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import libraryGarden.admin.controller.AdminApprovalController;
import libraryGarden.admin.mapper.AdminBookLoanMapper;
import libraryGarden.user.mapper.BookReservationMapper;

@Component
public class OverdueStatusScheduler {

    @Autowired
    private AdminBookLoanMapper alm;
    
    @Autowired
    private BookReservationMapper arm;
    
	private static final Logger logger = LoggerFactory.getLogger(AdminApprovalController.class);
    
    // (cron 표현식은 필요에 따라 수정)
    @Scheduled(cron = "0 * * * * ?") // 1분마다 업데이트 되도록 설정
    public void updateOverdueStatus() {
        int updatedCount = alm.updateOverdueStatus();
        logger.debug("📝 updateOverdueStatus 실행됨 - 업데이트된 OVERDUE 수: {}", updatedCount);
    }
    
    // 매일 자정에 예약 픽업일이 7일 남은 도서의 상태를 "예약대기"로 업데이트
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateReservationWaitStatus() {
        int updatedCount = arm.updateBooksToWaitStatus();
        logger.debug("📚 updateReservationWaitStatus 실행됨 - 예약대기로 변경된 도서 수: {}", updatedCount);
    }
    
    

}
