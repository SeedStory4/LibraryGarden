package libraryGarden.domain;

public class ApprovalVo {

	private int aidx;
	private int uidx;
	private int rqidx;
	private int bidx;
	private String rejectionReason;
	private String approvalDate;
	private String regDate;
	private String modify;
	private String delyn;
	
	public int getAidx() {
		return aidx;
	}
	public void setAidx(int aidx) {
		this.aidx = aidx;
	}
	public int getUidx() {
		return uidx;
	}
	public void setUidx(int uidx) {
		this.uidx = uidx;
	}
	public int getRqidx() {
		return rqidx;
	}
	public void setRqidx(int rqidx) {
		this.rqidx = rqidx;
	}
	public int getBidx() {
		return bidx;
	}
	public void setBidx(int bidx) {
		this.bidx = bidx;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getModify() {
		return modify;
	}
	public void setModify(String modify) {
		this.modify = modify;
	}
	public String getIpdelyn() {
		return delyn;
	}
	public void setIpdelyn(String ipdelyn) {
		this.delyn = ipdelyn;
	}
	
}
