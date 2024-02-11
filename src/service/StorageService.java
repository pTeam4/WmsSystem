package service;
//출고 관리
public interface StorageService {
    public void requestStorage();

    public void approveStorageRequest();

    public void createQrBarcode();

    public void cancelStorageRequest();

    public void modifyStorageRequest();

    public void designateStorageLocation();

    public void designateStorageDate();

    public void printStorageNotice();

    public void storage();

    public void storageStatus();

    public void storageStatusByPeriod();

    public void storageStatusMonthly();

    public void getQrBarcode();
}
