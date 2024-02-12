package view;

import service.WarehouseService;
import service.WarehouseServiceImpl;
import config.GetTexts;

public class StockServiceMenu {
    public static void stockServiceMenu(){
        WarehouseService warehouseService = new WarehouseServiceImpl();
        int menuno = 0;
        System.out.println("1. 재고 조회 2. 재고 실사 목록 추가 3. 재고 실사 목록 수정 4. 재고 실사 목록 삭제 5. 재고 실사 목록 조회 6. 이전 메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 ->{
                warehouseService.getStock();
                stockServiceMenu();
            }
            case 2 ->{
                warehouseService.addPhysicalInventory();
                stockServiceMenu();
            }
            case 3 ->{
                warehouseService.modifyStockTaking();
                stockServiceMenu();
            }
            case 4 ->{
                warehouseService.removeStockTaking();
                stockServiceMenu();
            }
            case 5 ->{
                warehouseService.getStockTaking();
                stockServiceMenu();
            }
            case 6 ->{
                WarehouseServiceMenu.warehouseServiceMenu();
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                stockServiceMenu();
            }
        }
    }
}
