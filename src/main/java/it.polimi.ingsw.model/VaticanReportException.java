package it.polimi.ingsw.model;
/**
 *Exception called when the increment of a Faith Track Position causes a Vatican Report
 */
public class VaticanReportException extends Exception{
    private final int reportId;
    public VaticanReportException(int i){
        reportId=i;
    }
    public int getReportId(){
        return  reportId;
    }
}
