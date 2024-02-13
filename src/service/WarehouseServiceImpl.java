package service;

import config.GetTexts;
import dao.WarehouseDao;
import vo.Warehouse;

public class WarehouseServiceImpl implements WarehouseService {
    @Override
    public void addWarehouse() {
        WarehouseDao warehouseDao = new WarehouseDao();
        Warehouse warehouse = new Warehouse();

        System.out.print("창고 이름을 입력하세요: ");
        String name = GetTexts.getInstance().readLine();

        System.out.print("창고 위치를 입력하세요: ");
        String location = GetTexts.getInstance().readLine();

        System.out.print("창고 종류를 입력하세요: ");
        String type = GetTexts.getInstance().readLine();

        warehouse.setName(name);
        warehouse.setLocation(location);
        warehouse.setType(type);

        warehouseDao.warehouseInsert(warehouse);
    }

    @Override
    public void getWarehouse() {

    }

    @Override
    public void warehouseStatusList() {

    }

    @Override
    public void getStock() {

    }

    @Override
    public void addPhysicalInventory() {

    }

    @Override
    public void modifyStockTaking() {

    }

    @Override
    public void removeStockTaking() {

    }

    @Override
    public void getStockTaking() {

    }

    @Override
    public void getStockByMajorCategory() {

    }

    @Override
    public void getStockByMiddleCategory() {

    }

    @Override
    public void getStockBySubcategory() {

    }

    @Override
    public void commmodityStatusList() {

    }

    @Override
    public void correspondentStatusList() {

    }
}
