package com.board.dto;

public class PagingDto {
	private int pageSize = 5;	// 한 페이지 당 게시글 수 
	private int rangeSize = 5;	// 한 블럭 당 페이지 수 - << 1 2 3 4 5 >>
	private int curPage = 1;
	private int curRange = 1;
	
	private int listCount;		// 총 게시글 수 
	private int pageCount;		// 총 페이지 수 
	private int rangeCount;		//	총 블럭 수 
	
	private int startPage = 1;
	private int endPage = 1;
	
	private int startIndex;	//	한 페이지에 보여줄 게시글의 첫번째 index  
	private int endIndex;	//	한 페이지에 보여줄 게시글의 마지막 index  
	private int prevPage;
	private int nextPage;
	
	
	public PagingDto(int curPage, int listCount) {
		// 현재 페이지, 총 게시물 수 받아와서 set 
		setCurPage(curPage);
		setListCount(listCount);
		
		setPageCount();		//	총 페이지 수 계산 
		setRangeCount();	//	총 블럭수 계산 
		
		// 블럭 세팅 
		rangeSetting(curPage);
		setStartIndex();
		setEndIndex();
	}
	
	public void rangeSetting(int curPage) {
		setCurRange();		//	현재 페이지 블럭 계산
		this.startPage = (curRange -1) * rangeSize + 1;
		this.endPage = startPage + rangeSize - 1;
		
		if(endPage > pageCount) {
			this.endPage = pageCount;
		}
		this.prevPage = curPage - 1;
		this.nextPage = curPage + 1;
	}

	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getRangeSize() {
		return rangeSize;
	}


	public void setRangeSize(int rangeSize) {
		this.rangeSize = rangeSize;
	}


	public int getCurPage() {
		return curPage;
	}


	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}


	public int getCurRange() {
		return curRange;
	}


	public void setCurRange() {
		this.curRange = (int)((curPage-1) / rangeSize) + 1;
	}

	 
	public int getListCount() {
		return listCount;
	}


	public void setListCount(int listCount) {
		this.listCount = listCount;
	}


	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount() {		// 총 페이지 수 계산  
		this.pageCount = (int)Math.ceil(listCount*1.0 / pageSize);
	}


	public int getRangeCount() {
		return rangeCount;
	}


	public void setRangeCount() {
		this.rangeCount = (int)Math.ceil(pageCount*1.0 / rangeSize);
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


	public int getStartIndex() {
		return startIndex;
	}


	public void setStartIndex() {
		this.startIndex = (curPage - 1)*pageSize;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex() {
		this.endIndex = startIndex + pageSize;
	}
	
	public int getPrevPage() {
		return prevPage;
	}


	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}


	public int getNextPage() {
		return nextPage;
	}


	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	
	
	@Override
	public String toString() {
		return "PagingVo [pageSize=" + pageSize + ", rangeSize=" + rangeSize + ", curPage=" + curPage + ", curRange="
				+ curRange + ", listCount=" + listCount + ", pageCount=" + pageCount + ", rangeCount=" + rangeCount
				+ ", startPage=" + startPage + ", endPage=" + endPage + ", startIndex=" + startIndex + ", prevPage="
				+ prevPage + ", nextPage=" + nextPage + "]";
	}
	
	
	
}