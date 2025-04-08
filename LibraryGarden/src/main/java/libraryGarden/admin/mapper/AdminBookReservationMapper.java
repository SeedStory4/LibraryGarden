package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.LoanVo;
import libraryGarden.domain.ReservationDto;

public interface AdminBookReservationMapper {
	
	public int bookReservationTotalCount(HashMap<String,Object> hm);
	public ArrayList<ReservationDto> bookReservationSelectAll(HashMap<String,Object> hm);
	public int bookTotalCount(HashMap<String,Object> hm);
	public ArrayList<LibraryBookDto> bookSelectAll(HashMap<String,Object> hm);
	public List<HashMap<String, String>> getOverduePeriodsByUser(String userNumber); // 예약 연체 조회
    // 신규 메소드: 해당 도서(lbidx)의 예약 pickupDate 조회
    public List<ReservationDto> getReservationsByBook(int lbidx);
    // 신규 메소드: 해당 도서(lbidx)의 대출(loanDate, dueDate) 정보 조회 (LoanVo 사용)
    public List<LoanVo> getLoansByBook(int lbidx);
    
}
