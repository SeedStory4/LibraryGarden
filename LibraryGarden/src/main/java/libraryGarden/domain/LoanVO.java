package libraryGarden.domain;

public class LoanVO {
	
    private int lidx;
    private int lbidx;
    private int uidx;
    private String loanDate;
    private String dueDate;
    private String returnDate;
    private String status;
    private String extended;
    private String regdate;
    private String modify;
    private String delyn;
    
	public int getLidx() {
		return lidx;
	}
	public void setLidx(int lidx) {
		this.lidx = lidx;
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
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExtended() {
		return extended;
	}
	public void setExtended(String extended) {
		this.extended = extended;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
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
