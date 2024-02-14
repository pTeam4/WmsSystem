package service;

import config.GetTexts;
import dao.WarehouseDao;
import vo.Warehouse;
import java.util.List;

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
        WarehouseDao warehouseDao = new WarehouseDao();
        List<Warehouse> warehouses = warehouseDao.warehouseSelect();

        System.out.println("\n = = = = = 창고 리스트 = = = = = \n");

        for (Warehouse warehouse : warehouses) {
            System.out.printf("""
                    창고 아이디: %d
                    창고 이름: %s
                    창고 위치: %s
                    창고 종류: %s%n
                    """, warehouse.getId(), warehouse.getName(), warehouse.getLocation(), warehouse.getType());
        }

        System.out.println("= = = = = = = = = = = = = = = \n");
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
