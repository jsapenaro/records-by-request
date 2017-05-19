package com.opussolutionsgroup.recordsbyrequest.data;

public class Row {

	private String[] rowData;
	private String[] visibleData;
	private int requestID;

	public Row(String[] rowData, String[] visibleData, int requestID) {
		this.rowData = rowData;
		this.visibleData = visibleData;
		this.requestID = requestID;
	}

	private String[] getRowData() {
		return rowData;
	}

	private String[] getVisibleRowData() {
		return visibleData;
	}

	public int getRequestID() {
		return requestID;
	}
	
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

}
