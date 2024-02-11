package service;
//입고 관리
public interface RetrievalService {
    public void getWaybill();

    public void requestRetrieval();

    public void approveRetrievalRequest();

    public void getRetrievalOrder();

    public void getRetrievalList();

    public void searchRetrievalGoods();

    public void addVehicleDispatching();

    public void addWaybill();

    public void getVehicleDispatching();

    public void cancelVehicleDispatching();

    public void modifyVehicleDispatching();

    public void modifyWaybill();
}
