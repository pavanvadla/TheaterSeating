package main.com.theaterseating.model;

public class TheaterRequest {

    private String groupName;
    private int groupTickets;
    private boolean isProcessed;
    private int rowNumber;
    private int sectionNumber;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupTickets() {
        return groupTickets;
    }

    public void setGroupTickets(int groupTickets) {
        this.groupTickets = groupTickets;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }
    
    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }
    
}
