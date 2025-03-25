package libraryGarden.admin.scheduler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import libraryGarden.admin.mapper.AdminBookLoanMapper;

@Component
public class OverdueStatusScheduler {

    @Autowired
    private AdminBookLoanMapper adminBookLoanMapper;	
    
    // 매일 자정에 실행 (cron 표현식은 필요에 따라 수정)
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateOverdueStatus() {
        int updatedCount = adminBookLoanMapper.updateOverdueStatus();
        System.out.println("업데이트된 OVERDUE 레코드 수: " + updatedCount);
    }
    
    

}
