package service;
//창고 관리
public interface WarehouseService {
    public void addWarehouse();

    public void getWarehouse();
    public void getWarehouseByLocation();

    public void warehouseStatusList();

    public void getStock();

    public void addPhysicalInventory();

    public void modifyStockTaking();

    public void removeStockTaking();

    public void getStockTaking();
    public void getStockByMajorCategory();
    public void getStockByMiddleCategory();
    public void getStockBySubcategory();
    public void commmodityStatusList();
    public void correspondentStatusList();
}