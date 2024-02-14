package service;

import config.GetTexts;
import dao.ProductDao;
import dao.StockMovementDao;
import dao.UserDao;
import util.MovementStatus;
import util.UserManager;
import vo.Product;
import vo.StockMovement;
import vo.User;

import java.util.List;

public class StorageServiceImpl implements StorageService {
    @Override
    public void requestStorage() {
        Product product = new Product();
        ProductDao productDao = new ProductDao();

        System.out.print("상품 이름을 입력하세요: ");
        String name = GetTexts.getInstance().readLine();

        System.out.print("상품 브랜드을 입력하세요: ");
        String brand = GetTexts.getInstance().readLine();

        System.out.print("상품 분류를 입력하세요: ");
        String type = GetTexts.getInstance().readLine();

        System.out.print("상품 정가를 입력하세요: ");
        int price = Integer.parseInt(GetTexts.getInstance().readLine());

        System.out.print("상품 판매가를 입력하세요: ");
        int salePrice = Integer.parseInt(GetTexts.getInstance().readLine());

        System.out.print("상품 수량을 입력하세요: ");
        int quantity = Integer.parseInt(GetTexts.getInstance().readLine());

        product.setName(name);
        product.setBrand(brand);
        product.setType(type);
        product.setPrice(price);
        product.setSalePrice(salePrice);
        product.setQuantity(quantity);

        int productId = productDao.productInsert(product);

        if (productId != 0) {
            StockMovement stockMovement = new StockMovement();
            StockMovementDao stockMovementDao = new StockMovementDao();

            stockMovement.setProductId(productId);
            stockMovement.setUserId("test_user");
            stockMovement.setStatusCode(MovementStatus.REQUESTED.getCode());

            int smRow = stockMovementDao.stockMovementInsert(stockMovement);

            System.out.printf("입고 요청 %d건이 완료되었습니다.%n", smRow);
        }
    }

    @Override
    public void approveStorageRequest() {
        UserDao userDao = new UserDao();
        User user = UserManager.getInstance().getCurrentUser();

        if (user.getPermission() == 1) {
            StockMovementDao stockMovementDao = new StockMovementDao();

            List<StockMovement> stockMovements = stockMovementDao.stockMovementSelectByStatus(MovementStatus.REQUESTED.getCode());

            for (StockMovement stockMovement : stockMovements) System.out.println(stockMovement.toString());

            System.out.printf("""
                    메뉴를 선택하세요.
                    1. 일괄 승인 | 2. 개별 승인
                    """);

            int menuno = Integer.parseInt(GetTexts.getInstance().readLine());

            switch (menuno) {
                case 1 -> {
                    int rows = stockMovementDao.stockMovementUpdateAllStatus(
                            MovementStatus.APPROVED.getCode(), MovementStatus.REQUESTED.getCode()
                    );

                    System.out.printf("%d건의 입고 요청이 승인되었습니다.%n", rows);
                }
            }
        }
    }

    @Override
    public void createQrBarcode() {

    }

    @Override
    public void cancelStorageRequest() {
        StockMovementDao stockMovementDao = new StockMovementDao();

        // 모든 입고 요청을 출력
        stockMovementDao.printAllStockMovements();

        // 사용자로부터 취소할 입고 요청의 ID를 입력받음
        System.out.print("취소할 입고 요청의 ID를 입력하세요: ");
        int requestId = Integer.parseInt(GetTexts.getInstance().readLine());

        // 상태를 '취소됨'으로 변경
        int updatedRows = stockMovementDao.updateStockMovementStatus(requestId, MovementStatus.CANCELLED.getCode());

        // 변경된 행이 있을 경우 메시지 출력
        if (updatedRows > 0) {
            System.out.println("입고 요청이 취소되었습니다.");
        } else {
            System.out.println("입고 요청 취소에 실패했습니다.");
        }

    }

    @Override
    public void modifyStorageRequest() {

    }

    @Override
    public void designateStorageLocation() {

    }

    @Override
    public void designateStorageDate() {

    }

    @Override
    public void printStorageNotice() {

    }

    @Override
    public void storage() {

    }

    @Override
    public void storageStatus() {

    }

    @Override
    public void storageStatusByPeriod() {

    }

    @Override
    public void storageStatusMonthly() {

    }

    @Override
    public void getQrBarcode() {

    }
}
