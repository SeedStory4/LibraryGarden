package libraryGarden.domain;

public class OverdueVo {
	
    private int oidx;         // 연체 ID
    private int lidx;         // 대출/반납 ID 
    private int uidx;         // 유저 ID 
    private String status;    // 연체 상태 (Y/N)
    private String startDate; // 연체 시작일
    private String endDate;   // 연체 종료일
    
    
	public int getOidx() {
		return oidx;
	}
	public void setOidx(int oidx) {
		this.oidx = oidx;
	}
	public int getLidx() {
		return lidx;
	}
	public void setLidx(int lidx) {
		this.lidx = lidx;
	}
	public int getUidx() {
		return uidx;
	}
	public void setUidx(int uidx) {
		this.uidx = uidx;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
