package service;

import util.GetTexts;
import dao.StockDao;
import dao.WarehouseDao;
import dto.StockInfo;
import dto.WarehouseInfo;
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

        System.out.print("창고 종류를 선택하세요: ");
        System.out.println("1. 일반 | 2. 식품 | 3. 의류");

        int typeNo = Integer.parseInt(GetTexts.getInstance().readLine());
        String type = "";

        switch (typeNo) {
            case 1 -> type = "일반 창고";
            case 2 -> type = "식품 창고";
            case 3 -> type = "의류 창고";
            default -> System.out.println("잘못된 입력입니다.");
        }

        warehouse.setName(name);
        warehouse.setLocation(location);
        warehouse.setType(type);

        int row = warehouseDao.warehouseInsert(warehouse);
        System.out.printf("창고 %d개가 등록되었습니다.%n", row);

        getAllWarehouse();
    }

    @Override
    public void getWarehouse() {
        getWarehouseSubMenu();
    }

    private void getWarehouseSubMenu() {
        System.out.println("1. 전체 조회 | 2. 지역별 조회");
        int menuno = Integer.parseInt(GetTexts.getInstance().readLine());

        switch (menuno) {
            case 1 -> {
                getAllWarehouse();
            }
            case 2 -> getWarehouseByLocation();
        }

        System.out.println("1. 창고 정보 | 2. 나가기");
        int subMenuNo = Integer.parseInt(GetTexts.getInstance().readLine());

        switch (subMenuNo) {
            case 1 -> getWarehouseInfo();
            case 2 -> {
                return;
            }
        }
    }

    private void getWarehouseInfo() {
        WarehouseDao warehouseDao = new WarehouseDao();
        Warehouse warehouse;

        System.out.print("창고 ID를 입력하세요: ");
        int warehouseId = Integer.parseInt(GetTexts.getInstance().readLine());

        List<WarehouseInfo> warehouseInfoList = warehouseDao.warehouseSelectWithStock(warehouseId);

        if (warehouseInfoList.isEmpty()) {
            warehouse = warehouseDao.warehouseSelectOne(warehouseId);

            printWarehouseInfo(warehouse);

            System.out.println("창고가 비어있습니다.");
            System.out.println(
                    "-------------------------------\n"
            );

            warehouseEditMenu(warehouseId);
        } else {
            warehouse = new Warehouse();
            warehouse.setId(warehouseInfoList.get(0).getWarehouseId());
            warehouse.setName(warehouseInfoList.get(0).getWarehouseName());
            warehouse.setLocation(warehouseInfoList.get(0).getWarehouseLocation());
            warehouse.setType(warehouseInfoList.get(0).getWarehouseType());

            printWarehouseInfo(warehouse);

            printStockInWarehouse(warehouseInfoList);

            warehouseEditMenu(warehouseId);
        }
    }

    private void printWarehouseInfo(Warehouse warehouse) {
        System.out.println(
                "\n-------------------------------"
        );
        System.out.printf("""
                            ID : %d
                            Name : %s
                            Location : %s
                            Type : %s
                            """,
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getType());
        System.out.println(
                "-------------------------------"
        );
    }

    private void printStockInWarehouse(List<WarehouseInfo> warehouseInfoList) {
        int totalQuantity = 0;

        System.out.printf(
                "%-20s%-20s%n", "Product Name", "Quantity"
        );
        System.out.println(
                "-------------------------------"
        );

        for (WarehouseInfo warehouseInfo : warehouseInfoList) {
            totalQuantity += warehouseInfo.getStockQuantity();

            System.out.printf("%-20s%-20d%n",
                    warehouseInfo.getProductName(),
                    warehouseInfo.getStockQuantity());
        }
        System.out.println(
                "-------------------------------"
        );
        System.out.printf("총 재고량 : %d%n", totalQuantity);
        System.out.println(
                "-------------------------------\n"
        );
    }

    private void warehouseEditMenu(int warehouseId) {
        System.out.println("1. 창고 삭제 | 2. 창고 수정 | 3. 나가기");
        int menuNo = Integer.parseInt(GetTexts.getInstance().readLine());

        switch (menuNo) {
            case 1 -> {
                WarehouseDao warehouseDao = new WarehouseDao();
                int row = warehouseDao.warehouseDelete(warehouseId);

                System.out.printf("창고 %d개가 삭제되었습니다.", row);

                getAllWarehouse();
            }
        }
    }

    private void getWarehouseByLocation() {
        WarehouseDao warehouseDao = new WarehouseDao();

        System.out.print("지역을 입력하세요: ");
        String location = GetTexts.getInstance().readLine();

        List<Warehouse> warehouseList = warehouseDao.warehouseSelectByLocation(location);

        if (!warehouseList.isEmpty()) {
            System.out.println(
                    "\n-----------------------------------------------------------------------"
            );
            System.out.printf(
                    "%-6s%-20s%-20s%n", "ID", "Name", "Type"
            );
            System.out.println(
                    "-----------------------------------------------------------------------"
            );
            for (Warehouse warehouse : warehouseList) {
                System.out.printf(
                        "%-6s%-20s%-20s%n",
                        warehouse.getId(),
                        warehouse.getName(),
                        warehouse.getType()
                );
            }
            System.out.println(
                    "-----------------------------------------------------------------------"
            );
        } else System.out.println("조회된 창고가 없습니다.");
    }

    public void getAllWarehouse() {
        WarehouseDao warehouseDao = new WarehouseDao();
        List<Warehouse> warehouses = warehouseDao.warehouseSelect();

        System.out.println(
                "\n-------------------------------------------------------------------------------------------"
        );
        System.out.printf(
                "%-6s%-20s%-20s%-20s%n", "ID", "Name", "Location", "Type"
        );
        System.out.println(
                "-------------------------------------------------------------------------------------------"
        );
        for (Warehouse warehouse : warehouses) {
            System.out.printf(
                    "%-6s%-20s%-20s%-20s%n",
                    warehouse.getId(),
                    warehouse.getName(),
                    warehouse.getLocation(),
                    warehouse.getType()
            );
        }
        System.out.println(
                "-------------------------------------------------------------------------------------------"
        );
    }

    @Override
    public void warehouseStatusList() {

    }

    @Override
    public void getStock() {
        getAllWarehouse();

        System.out.print("재고를 조회할 창고 ID를 입력하세요: ");
        int warehouseId = Integer.parseInt(GetTexts.getInstance().readLine());

        StockDao stockDao = new StockDao();
        List<StockInfo> stockInfoList = stockDao.stockSelectInfo(warehouseId);

        printStockList(stockInfoList);
    }

    private void printStockList(List<StockInfo> stockInfoList) {
        int totalQuantity = 0;

        System.out.println(
                "\n-------------------------------------------------------------------------------------------"
        );
        System.out.printf(
                "%-6s%-25s%-20s%-20s%-25s%n", "ID", "Product Name", "Quantity", "Warehouse Name", "Warehouse Location"
        );
        System.out.println(
                "-------------------------------------------------------------------------------------------"
        );
        for (StockInfo stockInfo : stockInfoList) {
            totalQuantity += stockInfo.getStockQuantity();

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
        System.out.printf("총 재고량 : %d%n", totalQuantity);
        System.out.println(
                "-------------------------------------------------------------------------------------------\n"
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
