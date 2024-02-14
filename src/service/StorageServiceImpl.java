package service;

import config.GetTexts;
import dao.ProductDao;
import dao.StockMovementDao;
import util.MovementStatus;
import vo.Product;
import vo.StockMovement;

public class StorageServiceImpl implements StorageService {
    @Override
    public void requestStorage() {
  /*
         상품 정보들을 입력 받는다.
         입력한 상품 정보들을 이용하여 product 테이블에 삽입한다..
         product 테이블에 데이터가 정상적으로 삽입되면 입출고 요청 테이블에 정보를 삽입한다.(메소드를 분리하여 실행)
         */
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

    }

    @Override
    public void createQrBarcode() {

    }

    @Override
    public void cancelStorageRequest() {
        StockMovementDao stockMovementDao = new StockMovementDao();

        // 모든 입고 요청을 출력
        stockMovementDao.stockMovementsSelect();

        // 사용자로부터 취소할 입고 요청의 ID를 입력받음
        System.out.print("취소할 입고 요청의 ID를 입력하세요: ");
        int requestId = Integer.parseInt(GetTexts.getInstance().readLine());

        // 상태를 '취소됨=ID'으로 변경
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
        ProductDao productDao = new ProductDao();
        StockMovementDao stockMovementDao = new StockMovementDao();

        // 모든 입고 요청 출력
        stockMovementDao.stockMovementsSelect();

        System.out.print("수정할 상품의 ID를 입력하세요: ");
        int productId = Integer.parseInt(GetTexts.getInstance().readLine());

        // 사용자로부터 수정할 상품의 정보 입력 받기
        Product product = new Product();
        System.out.print("새로운 상품 이름을 입력하세요: ");
        product.setName(GetTexts.getInstance().readLine());
        System.out.print("새로운 상품 브랜드를 입력하세요: ");
        product.setBrand(GetTexts.getInstance().readLine());
        System.out.print("새로운 상품 분류를 입력하세요: ");
        product.setType(GetTexts.getInstance().readLine());
        System.out.print("새로운 상품 정가를 입력하세요: ");
        product.setPrice(Integer.parseInt(GetTexts.getInstance().readLine()));
        System.out.print("새로운 상품 판매가를 입력하세요: ");
        product.setSalePrice(Integer.parseInt(GetTexts.getInstance().readLine()));
        System.out.print("새로운 상품 수량을 입력하세요: ");
        product.setQuantity(Integer.parseInt(GetTexts.getInstance().readLine()));

        int updatedProductRows = productDao.productUpdate(productId, product);

        if (updatedProductRows > 0) {
            System.out.println("상품 정보가 수정되었습니다.");
        } else {
            System.out.println("상품 정보 수정에 실패했습니다.");
        }

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
