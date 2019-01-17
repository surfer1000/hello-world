package ect;

public class PageMaker {
	private int pageSize=3; // 페이지당 레코드 수
	private int startPage; // 시작페이지
	
	private int endPage;
	private int currentPage=1; //현재페이지
	private int start; // 페이지 시작 레코드
	private int totalCount;
	private boolean prev; // 이전버튼 클릭
	private boolean next; // 다음 버클 클릭
	
	public PageMaker(int currentPage, int totalCount){
		this.currentPage=currentPage;
		this.totalCount=totalCount;
		start=(currentPage-1)*pageSize; //페이지 시작 레코드
		System.out.println(Math.ceil(currentPage/(double)pageSize));
		endPage=(int)(Math.ceil(currentPage/(double)pageSize)*pageSize);
		startPage=(endPage-pageSize)+1;
		int totalPage=(int)(Math.ceil(totalCount/(double)pageSize));
		if(endPage>totalPage) endPage=totalPage;
		
		prev=startPage==1?false:true;
		next=endPage>=totalPage?false:true;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
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
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
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
}
