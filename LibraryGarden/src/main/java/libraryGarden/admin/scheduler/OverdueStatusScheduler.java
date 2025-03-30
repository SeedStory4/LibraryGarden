package libraryGarden.admin.scheduler;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import libraryGarden.admin.mapper.AdminBookLoanMapper;

@Component
public class OverdueStatusScheduler {

    @Autowired
    private AdminBookLoanMapper adminBookLoanMapper;	
    
    // (cron 표현식은 필요에 따라 수정)
    @Scheduled(cron = "0 * * * * ?") // 1분마다 업데이트 되도록 설정
    public void updateOverdueStatus() {
        int updatedCount = adminBookLoanMapper.updateOverdueStatus();
        System.out.println("업데이트된 OVERDUE 레코드 수: " + updatedCount);
    }
    
    

}
