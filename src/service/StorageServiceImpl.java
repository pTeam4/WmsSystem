package service;

import config.GetTexts;
import dao.*;
import util.MovementStatus;

import util.UserManager;
import vo.*;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static dao.QrBarcodeDao.saveQrCodeImage;

public class StorageServiceImpl implements StorageService {
    //qrcode용 필드 객체들 2개 있어요
    private String generateQrCodeContent(Stock stock, StockMovement stockMovement) {
        return "Warehouse ID: " + stock.getWarehouseId() + "\nProduct ID: " + stockMovement.getProductId() + "\nUser ID: " + stockMovement.getUserId() + "\nRequest Datetime: " + stockMovement.getRequestDatetime() + "\nApproved Datetime: " + stockMovement.getApprovedDatetime();
    }

    private Blob convertBase64ToBlob(String base64Image) {
        try {
            // Convert Base64 encoded string to byte array
            byte[] byteArray = Base64.getDecoder().decode(base64Image);

            // Converting byte array to Blob
            return new SerialBlob(byteArray);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

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
        User user = UserManager.getInstance().getCurrentUser();

        if (user.getPermission() == 1) {
            StockMovementDao stockMovementDao = new StockMovementDao();
            List<StockMovement> stockMovements = stockMovementDao.stockMovementSelectByStatus(MovementStatus.REQUESTED.getCode());

            printStockMovement(stockMovements);

            approveStorageRequestSubMenu(stockMovementDao);
        }
    }

    private void approveStorageRequestSubMenu(StockMovementDao stockMovementDao) {
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

    private void printStockMovement(List<StockMovement> stockMovements) {
        System.out.println(
                "-----------------------------------------------------------------------------------------------------"
        );
        System.out.printf(
                "%-6s%-6s%-20s%-6s%-25s%-25s%n",
                "id",
                "Product ID",
                "User ID",
                "Status",
                "Request Date Time",
                "Approved Date Time"
        );
        System.out.println(
                "-----------------------------------------------------------------------------------------------------"
        );
        for (StockMovement stockMovement : stockMovements) {
            System.out.printf(
                    "%-6s%-6s%-20s%-6s%-25s%-25s%n",
                    stockMovement.getId(),
                    stockMovement.getProductId(),
                    stockMovement.getUserId(),
                    stockMovement.getStatusCode(),
                    stockMovement.getRequestDatetime(),
                    stockMovement.getApprovedDatetime()
            );
        }
        System.out.println(
                "-----------------------------------------------------------------------------------------------------"
        );
    }

    @Override
    public void createQrBarcode() {
        StockMovementDao stockMovementDao = new StockMovementDao();
        StockDao stockDao = new StockDao();

        System.out.println("QR 코드를 생성 할 Product ID를 입력하세요.:");
        int productId = Integer.parseInt(GetTexts.getInstance().readLine());

        List<Stock> stocks = stockDao.getAllStocks(productId);
        List<StockMovement> stockMovements = stockMovementDao.getAllStockMovements(productId);
        if (!stocks.isEmpty() && !stockMovements.isEmpty()) {
            for (Stock stock : stocks) {
                for (StockMovement stockMovement : stockMovements) {
                    String qrCodeContent = generateQrCodeContent(stock, stockMovement);

                    String base64Image = QrBarcodeDao.generateQrCode(qrCodeContent);

                    if (base64Image != null) {
                        // Convert Base64 string to Blob
                        Blob barcodeData = convertBase64ToBlob(base64Image);

                        // Saving QrBarcode object to the database
                        QrBarcode qrBarcode = new QrBarcode();
                        qrBarcode.setProduct_id(productId);
                        qrBarcode.setBarcodeData(barcodeData);
                        qrBarcode.setCreationDate(new Date());

                        // Call your database service to save qrBarcode object to the database
                        QrBarcodeDao.saveQrBarcode(qrBarcode);
                        System.out.println("QR 코드 생성이 완료되었습니다.");
                    } else {
                        System.out.println("Failed to generate QR code image.");
                    }
                }
            }
        } else {
            System.out.println("No data found for the provided Product ID.");
        }
        String filePath = "./qrcode"+productId+".png";
        QrBarcodeDao.saveQrCodeImage(productId, filePath);
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
        ProductDao productDao = new ProductDao();
        StockMovementDao stockMovementDao = new StockMovementDao();

        // 모든 입고 요청 출력
        stockMovementDao.printAllStockMovements();

        System.out.print("수정할 상품의 ID를 입력하세요: ");
        int productId = Integer.parseInt(GetTexts.getInstance().readLine());

        // 입력받은 productId로 상품의 상태가 IR이 아닌 경우 수정 불가능하다고 막는 메서드 필요.
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
        StockMovementDao stockMovementDao = new StockMovementDao();
        List<StockMovement> storageNow = stockMovementDao.getStockMovementsNow();
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf(
                "%-6s%-25s%-20s%-20s%-16s%-16s%n",
                "id",
                "product_id",
                "user_id",
                "status_code",
                "Request_time",
                "Approved_time"
        );
        System.out.println("-------------------------------------------------------------------------------------------------");

        for (StockMovement s : storageNow){
            System.out.printf(
                    "%-6s%-25s%-20s%-20s%-16s%-16s%n",
                    s.getId(),
                    s.getProductId(),
                    s.getUserId(),
                    s.getStatusCode(),
                    s.getRequestDatetime(),
                    s.getApprovedDatetime()
            );
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
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
