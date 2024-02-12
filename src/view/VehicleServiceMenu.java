package view;

import service.VehicleService;
import service.VehicleServiceImpl;
import config.GetTexts;

public class VehicleServiceMenu {
    public static void vehicleServiceMenu() {
        VehicleService vehicleService = new VehicleServiceImpl();
        int menuno = 0;
        System.out.println("1. 차량 등록 2. 차량 목록 조회 3. 차량 수정 4. 차량 삭제 5. 차량별 배차 내역 조회 6. 이전 메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 -> {
                vehicleService.addVehicle();
                vehicleServiceMenu();
            }
            case 2 -> {
                vehicleService.getVehicleList();
                vehicleServiceMenu();
            }
            case 3 -> {
                vehicleService.modifyVehicle();
                vehicleServiceMenu();
            }
            case 4 -> {
                vehicleService.removeVehicle();
                vehicleServiceMenu();
            }
            case 5 -> {
                vehicleService.getDispatchRecordsByVehicle();
                vehicleServiceMenu();
            }
            case 6 -> {
                MemberMenu.memberMainMenu();
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                vehicleServiceMenu();
            }
        }
    }
}
