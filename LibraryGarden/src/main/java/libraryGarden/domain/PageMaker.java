package libraryGarden.domain;

import org.springframework.stereotype.Component;

@Component
public class PageMaker {

	private int displayPageNum = 5;
	private int startPage;
	private int endPage;
	private int totalCount;
	
	private boolean prev;
	private boolean next;
	
	private SearchCriteria scri;	

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {  // �� �Խù��� ����� �޴� �޼ҵ�
		this.totalCount = totalCount;
		calcData();  // ������ ��� ����Ʈ ��ȣ�� ��Ÿ���ֱ� ���� ����
		
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public SearchCriteria getScri() {
		return scri;
	}

	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}
	
	private void calcData() {
		
		// 1. �⺻������ 1�������� 10���� ��Ÿ���� �����Ѵ�  (�������׺���̼ǿ���)
		endPage = (int)(Math.ceil(scri.getPage() / (double)displayPageNum) * displayPageNum);  // ��� �ø�ó�� �ϴ� �޼ҵ� ceil()
		// Math.ceil(scri.getPage() / (double)displayPageNum -> ���� �������� �ѹ��� �����ִ� ������ ���� ������ ���° ������ �׺���̼����� Ȯ���Ѵ�.
		// Math.ceil(scri.getPage() / (double)displayPageNum) * displayPageNum -> ������ �׺���̼ǿ� �ѹ��� �����ִ� ������ ���� ���ؼ� ������ �������� ���Ѵ�.
		// (int)(Math.ceil(scri.getPage() / (double)displayPageNum) * displayPageNum) -> int������ ����ȯ
		
		// 2. endPage�� �����Ǿ����� ������������ ����
		startPage = (endPage - displayPageNum) + 1;
		// endPage - displayPageNum -> ������ ���������� �ѹ��� �����ִ� ������ ���� ���� ���� ������ �׺���̼��� ������ �������� Ȯ���Ѵ�.
		// (endPage - displayPageNum) + 1 -> ���� ������ �׺���̼��� ������ �������� 1�� ���ؼ� ���� ������ �׺���̼��� ó�� �������� ���Ѵ�.
		
		// 3. ���� �Խù����� ���� endPage�� ���ϰڴ�
		int tempEndPage = (int)(Math.ceil(totalCount / (double)scri.getPerPageNum()));
		// totalCount / (double)scri.getPerPageNum() -> �� �Խù����� �� �������� ���̴� �Խù� ���� ������ ��ü ���������� ���Ѵ�.
		// Math.ceil(totalCount / (double)scri.getPerPageNum()) -> ���� �Խù� ����(������)�� ������ ������ �Ҽ��� �ø� �Ѵ�.
		// (int)(Math.ceil(totalCount / (double)scri.getPerPageNum()))) -> int������ ����ȯ

		// 4. ������ endPage�� ���� endPage�� ���ؼ� ���� endPage�� ���Ѵ�
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		// endPage���� ����endPage(tempEndPage)�� ������ endPage�� ����endPage(tempEndPage)�� �����Ѵ�.
		
		// 5. ����, ���� ��ư �����
		prev = (startPage == 1? false : true);  // ���׿����� ���
		// ������������ 1�̸� ������ư�� ���ش�.
		
		next = (endPage * scri.getPerPageNum() >= totalCount ? false : true);
		// ������ �������� �� �������� ���̴� �Խù� ���� ���� ���� ��ü �Խù������� ũ�ų� ������ ���� �������� �Խ����� ������ �������̹Ƿ�
		// ������ư�� ���ش�.
	}
	
}
