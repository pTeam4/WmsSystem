package service;

import java.util.List;

//입고 관리
public interface RetrievalService {
    public void getWaybill();

    public void requestRetrieval();

    public boolean approveRetrievalRequest();

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
