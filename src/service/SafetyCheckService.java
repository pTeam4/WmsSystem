package service;
//안전점검관리
public interface SafetyCheckService {
    public void addSafetyCheck();

    public void getSafetyCheckRecords();

    public void modifySafetyCheckRecord();

    public void removeSafetyCheckRecord();

    public void recordTakeFollowUpAction();
}
