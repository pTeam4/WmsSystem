package service;
//거래처 관리
public interface VendorService {
    public void getFeeInfo();

    public void addVendor();

    public void getVendorAuthority();

    public void approveVendorAuthority();

    public void addFeeInfo();

    public void modifyFeeInfo();

    public void removeFeeInfo();

    public void getAllContracts();

    public void approveContract();

    public void cancelContract();

    public void getContractsByIndividual();

    public void manageContractCalendar();
}
