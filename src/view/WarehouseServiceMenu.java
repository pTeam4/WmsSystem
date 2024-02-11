package view;

import service.StorageService;
import service.WarehouseService;
import service.WarehouseServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//창고관리 서비스
public class WarehouseServiceMenu {
    public static void warehouseServiceMenu()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        WarehouseService warehouseService = new WarehouseServiceImpl();
        int menuno = 0;
        System.out.println("1. 창고 등록 2. 창고 조회 3. 창고 현황 확인 4. 재고 업무 5. 이전 메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 ->{
                warehouseService.addWarehouse();
                warehouseServiceMenu();
            }
            case 2 ->{
                warehouseService.getWarehouse();
                warehouseServiceMenu();
            }
            case 3 ->{
                warehouseService.warehouseStatusList();
                warehouseServiceMenu();
            }
            case 4 ->{
                StockServiceMenu.stockServiceMenu();
            }
            case 5 ->{
                MemberMenu.memberMainMenu();
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                warehouseServiceMenu();
            }
        }
    }
}