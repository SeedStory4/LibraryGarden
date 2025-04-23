package libraryGarden.domain;

public class ReservationVo {
	
    private int ridx;           // 예약 인덱스
    private int lbidx;          // 도서 인덱스
    private int uidx;           // 사용자 인덱스
    private String regDate; // 예약 날짜
    private String pickupDate;      // 픽업 날짜
    private String status;        // 상태
    private String dueDate;         // 반납 예정일
    private String modify;          // 수정일
    private String delyn;         // 삭제 여부
    
    
    
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getRidx() {
		return ridx;
	}
	public void setRidx(int ridx) {
		this.ridx = ridx;
	}
	public int getLbidx() {
		return lbidx;
	}
	public void setLbidx(int lbidx) {
		this.lbidx = lbidx;
	}
	public int getUidx() {
		return uidx;
	}
	public void setUidx(int uidx) {
		this.uidx = uidx;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getModify() {
		return modify;
	}
	public void setModify(String modify) {
		this.modify = modify;
	}
	public String getDelyn() {
		return delyn;
	}
	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}  

}
