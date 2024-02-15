package service;

import config.GetTexts;
import dao.StockDao;
import dao.WarehouseDao;
import dto.StockInfo;
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

        int row = warehouseDao.warehouseInsert(warehouse);
        System.out.printf("창고 %d개가 등록되었습니다.%n", row);

        getWarehouse();
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
        getWarehouse();

        System.out.print("재고를 조회할 창고 ID를 입력하세요: ");
        int warehouseId = Integer.parseInt(GetTexts.getInstance().readLine());

        StockDao stockDao = new StockDao();
        List<StockInfo> stockInfoList = stockDao.stockSelect(warehouseId);

        System.out.println(
                "-------------------------------------------------------------------------------------------"
        );
        System.out.printf(
                "%-6s%-25s%-20s%-20s%-25s%n", "ID", "Product Name", "Quantity", "Warehouse Name", "Warehouse Location"
        );
        System.out.println(
                "-------------------------------------------------------------------------------------------"
        );
        for (StockInfo stockInfo : stockInfoList) {
            System.out.printf(
                    "%-6s%-25s%-20s%-20s%-25s%n",
                    stockInfo.getStockId(),
                    stockInfo.getProductName(),
                    stockInfo.getStockQuantity(),
                    stockInfo.getWarehouseName(),
                    stockInfo.getWarehouseLocation()
            );
        }
        System.out.println(
                "-------------------------------------------------------------------------------------------"
        );
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
