package service;

import util.GetTexts;

import dao.VehicleDao;
import util.UserManager;
import vo.*;
import dao.ShippingOrdersDao;
import dao.ShippingOrdersDetailDao;
import dao.WaybillDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RetrievalServiceImpl implements RetrievalService {
    private ShippingOrdersDetailDao shippingOrdersDetailDao = new ShippingOrdersDetailDao();
    private ShippingOrdersDao shippingOrdersDao = new ShippingOrdersDao();
    private WaybillDao waybillDao = new WaybillDao();
    private VehicleDao vehicleDao = new VehicleDao();
    UserManager userManager;
    private User currentUser;

    public RetrievalServiceImpl() {
        userManager = UserManager.getInstance();
        currentUser = userManager.getCurrentUser();
    }

    /**
     * 운송장 조회하기
     */
    @Override
    public void getWaybill() {
        /** 운송장 리스트 출력 */
        List<Waybill> waybillList = waybillDao.getWaybillList();
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf(
                "%-6s%-25s%-20s%-20s%-16s%n",
                "id",
                "shipping_orders_id",
                "vehicle_num",
                "departure_date",
                "arrival_date"
        );
        System.out.println("--------------------------------------------------------------------------------------");
        for (Waybill waybill : waybillList) {
            System.out.printf(
                    "%-6s%-25s%-20s%-20s%-16s%n",
                    waybill.getId(),
                    waybill.getShippingOrdersId(),
                    waybill.getVehicleNum(),
                    waybill.getDepartureDate(),
                    waybill.getArrivalDate()
            );
        }
        System.out.println("--------------------------------------------------------------------------------------");

        /** 출력할 운송장 선택 */
        System.out.println("출력할 운송장의 출고지시서 번호를 입력하세요.");
        int shippingOrdersId = Integer.parseInt(GetTexts.getInstance().readLine());
        Waybill waybill = waybillDao.getWaybillByShippingOrdersId(shippingOrdersId);
        System.out.println("운송장을 출력합니다.");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf(
                "%-6s%-20s%-10s%-25s%-16s%n",
                "id",
                "shipping_orders_id",
                "vehicle_num",
                "departure_date",
                "arrival_date"
        );
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf(
                "%-6s%-20s%-10s%-25s%-16s%n",
                waybill.getId(),
                waybill.getShippingOrdersId(),
                waybill.getVehicleNum(),
                waybill.getDepartureDate(),
                waybill.getArrivalDate()
        );
        System.out.println("--------------------------------------------------------------------------------------");
        //GetTexts.getInstance().close();
    }

    /**
     * 출고 요청
     */
    @Override
    public void requestRetrieval() {
        ShippingOrders shippingOrders = new ShippingOrders();

        System.out.println("-----출고 지시서 정보 입력-----");
        System.out.println("사용자 아이디 입력 : "); // 어떻게 할 지 물어보기
        String userId = GetTexts.getInstance().readLine();
        shippingOrders.setUserId(userId);
        System.out.println("배송 주소 입력 : ");
        String deliveryAddress = GetTexts.getInstance().readLine();
        shippingOrders.setDeliveryAddress(deliveryAddress);
        try {
            System.out.println("주문일 입력 : ");
            String dateInput = GetTexts.getInstance().readLine();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = simpleDateFormat.parse(dateInput);
            shippingOrders.setOrderDate(orderDate);

            System.out.println("배송일 입력 : ");
            dateInput = GetTexts.getInstance().readLine();
            Date deliveryDate = simpleDateFormat.parse(dateInput);
            shippingOrders.setDeliveryDate(deliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int shippingOrdersId = shippingOrdersDao.requestRetrieval(shippingOrders);
        requestShippingOrdersDetail(shippingOrdersId);

    }

    @Override
    public void requestShippingOrdersDetail(int shippingOrdersId) {
        ShippingOrdersDetail shippingOrdersDetail = new ShippingOrdersDetail();

        System.out.println("-----상세 출고 지시서 정보 입력-----");
        // 위에서 생성한 출고 지시서 아이디 입력
        shippingOrdersDetail.setShippingOrdersId(shippingOrdersId);
        System.out.println("상품 번호를 입력하세요. ");
        int productId = Integer.parseInt(GetTexts.getInstance().readLine());
        shippingOrdersDetail.setProductId(productId);
        System.out.println("수량을 입력하세요.");
        int quantity = Integer.parseInt(GetTexts.getInstance().readLine());
        shippingOrdersDetail.setQuantity(quantity);

        shippingOrdersDetailDao.requestRetrieval(shippingOrdersDetail);
        System.out.println("출고 요청이 완료되었습니다.");
    }

    /**
     * 출고요청 승인
     */
    @Override
    public void approveRetrievalRequest() {
        if (currentUser.getPermission() == 1 || currentUser.getPermission() == 4) {
            List<ShippingOrders> awaitedShippingOrdersList = shippingOrdersDao.getAwaitedShippingOrdersList();   // 승인 대기 중인 출고지시서 리스트 출력
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.printf(
                    "%-6s%-15s%-20s%-15s%-15s%-15s%-15s%n",
                    "id",
                    "user_id",
                    "delivery_address",
                    "order_date",
                    "delivery_date",
                    "status",
                    "approved_status"
            );
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            for (ShippingOrders shippingOrders : awaitedShippingOrdersList) {
                System.out.printf(
                        "%-6s%-15s%-20s%-15s%-15s%-15s%-15s%n",
                        shippingOrders.getId(),
                        shippingOrders.getUserId(),
                        shippingOrders.getDeliveryAddress(),
                        shippingOrders.getOrderDate(),
                        shippingOrders.getDeliveryDate(),
                        shippingOrders.getStatus(),
                        shippingOrders.getApprovedStatus()
                );
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------");

            System.out.println("승인할 출고지시서의 번호를 입력하세요.");
            int shippingOrdersId = Integer.parseInt(GetTexts.getInstance().readLine());
            shippingOrdersDao.approveRetrievalRequest(shippingOrdersId);
            System.out.println("해당 출고지시서가 승인되었습니다.");
            //GetTexts.getInstance().close();
        } else {
            System.out.println("접근 권한이 없습니다.");
        }
    }

    /**
     * 출고지시서 조회
     */
    @Override
    public void getRetrievalOrder() {
        if (currentUser.getPermission() == 1 || currentUser.getPermission() == 2 || currentUser.getPermission() == 4) {
            System.out.print("조회할 출고지시서 번호를 입력하세요 : ");
            int shippingOrdersId = Integer.parseInt(GetTexts.getInstance().readLine());
            System.out.println("출고지시서를 조회합니다.");
            ShippingOrders shippingOrders = shippingOrdersDao.getShippingOrder(shippingOrdersId);
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            System.out.printf(
                    "%-6s%-15s%-15s%-15s%-15s%-15s%-15s%n",
                    "id",
                    "user_id",
                    "delivery_address",
                    "order_date",
                    "delivery_date",
                    "status",
                    "approved_status"
            );
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            System.out.printf(
                    "%-6s%-15s%-15s%-15s%-15s%-15s%-15s%n",
                    shippingOrders.getId(),
                    shippingOrders.getUserId(),
                    shippingOrders.getDeliveryAddress(),
                    shippingOrders.getOrderDate(),
                    shippingOrders.getDeliveryDate(),
                    shippingOrders.getStatus(),
                    shippingOrders.getApprovedStatus()
            );
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            //GetTexts.getInstance().close();
        } else {
            System.out.println("접근 권한이 없습니다.");
        }
    }

    /**
     * 출고 리스트 조회
     */
    @Override
    public void getRetrievalList() {
        if (currentUser.getPermission() == 1 || currentUser.getPermission() == 2 || currentUser.getPermission() == 4) {
            System.out.println("출고지시서 목록을 조회합니다.");
            List<ShippingOrders> shippingOrdersList = shippingOrdersDao.getShippingOrdersList();   // 승인 완료된 출고지시서 리스트 출력
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.printf(
                    "%-6s%-15s%-20s%-15s%-15s%-15s%-15s%n",
                    "id",
                    "user_id",
                    "delivery_address",
                    "order_date",
                    "delivery_date",
                    "status",
                    "approved_status"
            );
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            for (ShippingOrders shippingOrders : shippingOrdersList) {
                System.out.printf(
                        "%-6s%-15s%-20s%-15s%-15s%-15s%-15s%n",
                        shippingOrders.getId(),
                        shippingOrders.getUserId(),
                        shippingOrders.getDeliveryAddress(),
                        shippingOrders.getOrderDate(),
                        shippingOrders.getDeliveryDate(),
                        shippingOrders.getStatus(),
                        shippingOrders.getApprovedStatus()
                );
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("접근 권한이 없습니다.");
        }
    }

    /**
     * 출고 상품 검색
     */
    @Override
    public void searchRetrievalGoods() {
        if (currentUser.getPermission() == 1 || currentUser.getPermission() == 2 || currentUser.getPermission() == 4) {
            System.out.println("검색할 상품의 상품 번호를 입력하세요.");
            int productId = Integer.parseInt(GetTexts.getInstance().readLine());
            List<ShippingOrdersDetail> shippingOrdersDetailList = shippingOrdersDetailDao.getShippingOrdersDetailByProductId(productId);
            System.out.println("-------------------------------------------------------------");
            System.out.printf(
                    "%-6s%-25s%-16s%-16s%n",
                    "id",
                    "shipping_orders_id",
                    "product_id",
                    "quantity"
            );
            System.out.println("-------------------------------------------------------------");
            for (ShippingOrdersDetail shippingOrdersDetail : shippingOrdersDetailList) {
                System.out.printf(
                        "%-6s%-25s%-16s%-16s%n",
                        shippingOrdersDetail.getId(),
                        shippingOrdersDetail.getShippingOrdersId(),
                        shippingOrdersDetail.getProductId(),
                        shippingOrdersDetail.getQuantity()
                );
            }
            System.out.println("-------------------------------------------------------------");
            //GetTexts.getInstance().close();
        } else {
            System.out.println("접근 권한이 없습니다.");
        }
    }

    /**
     * 배차 등록
     */
    @Override
    public void addVehicleDispatching(String vehicleNum) {
        if (currentUser.getPermission() == 1 || currentUser.getPermission() == 4) {
            vehicleDao.addVehicleDispatching(vehicleNum);
            System.out.println("배차가 등록되었습니다.");
        } else {
            System.out.println("접근 권한이 없습니다.");
        }
    }

    /**
     * 운송장 등록 : 일반적으로 출고 요청 승인 후에 배차를 진행하기 때문에 운송장에 넣었다.
     */
    @Override
    public void addWaybill() {
        if (currentUser.getPermission() == 1 || currentUser.getPermission() == 4) {
            getRetrievalList(); // 출고 지시서 리스트 출력
            System.out.println("운송장을 만들 출고 지시서 번호를 입력하세요.");
            int shippingOrderId = Integer.parseInt(GetTexts.getInstance().readLine());

            getVehicleDispatching(); // 출고 차량 리스트 출력
            System.out.println("출고할 차량 번호를 입력하세요.");
            String vehicleNum = GetTexts.getInstance().readLine();
            addVehicleDispatching(vehicleNum);

            System.out.println("출고 날짜를 입력하세요. (yyyy-MM-dd 형식)");
            String inputDate = GetTexts.getInstance().readLine();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date utilDepartureDate = simpleDateFormat.parse(inputDate);
                java.sql.Date departureDate = new java.sql.Date(utilDepartureDate.getTime());
                waybillDao.addWaybill(shippingOrderId, vehicleNum, departureDate);
                System.out.println("운송장이 등록되었습니다.");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //GetTexts.getInstance().close();
        } else {
            System.out.println("접근 권한이 없습니다.");
        }
    }


    /**
     * 배차 조회
     */
    @Override
    public void getVehicleDispatching() {
        if (currentUser.getPermission() == 1 || currentUser.getPermission() == 4) {
            List<Vehicle> vehicleList = vehicleDao.getVehicleList();
            System.out.println("----------------------------------------------------------------------");
            System.out.printf(
                    "%-15s%-20s%-15s%-15s%n",
                    "num",
                    "delivery_driver_id",
                    "type",
                    "telephone"
            );
            System.out.println("----------------------------------------------------------------------");
            for (Vehicle vehicle : vehicleList) {
                System.out.printf(
                        "%-15s%-20s%-15s%-15s%n",
                        vehicle.getNum(),
                        vehicle.getDeliveryDriverId(),
                        vehicle.getType(),
                        vehicle.getTelephone()
                );
            }
            System.out.println("----------------------------------------------------------------------");
        } else {
            System.out.println("접근 권한이 없습니다.");
        }
    }

    /**
     * 배차 취소
     */
    @Override
    public void cancelVehicleDispatching() {
        if (currentUser.getPermission() == 1 || currentUser.getPermission() == 4) {
            /** 운송장 리스트 출력 */
            // 출고지시서 만으로는 아직 배차가 되지 않았기 때문에 운송장 리스트를 출력해와야 한다.
            List<Waybill> waybillList = waybillDao.getWaybillList();
            System.out.println("--------------------------------------");
            System.out.printf(
                    "%-6s%-20s%-10s%-25s%-16s%n",
                    "id",
                    "shipping_orders_id",
                    "vehicle_num",
                    "departure_date",
                    "arrival_date"
            );
            System.out.println("--------------------------------------");
            for (Waybill waybill : waybillList) {
                System.out.printf(
                        "%-6s%-20s%-10s%-25s%-16s%n",
                        waybill.getId(),
                        waybill.getShippingOrdersId(),
                        waybill.getVehicleNum(),
                        waybill.getDepartureDate(),
                        waybill.getArrivalDate()
                );
            }
            System.out.println("--------------------------------------");

            /** 취소할 운송장을 출고지시서 번호로 선택 */
            System.out.println("배차 취소할 출고지시서 번호를 입력하세요.");
            int shippingOrderId = Integer.parseInt(GetTexts.getInstance().readLine());

            vehicleDao.cancelVehicleDispatchingByWaybillId(shippingOrderId);
            System.out.println("배차가 취소되었습니다.");
            //GetTexts.getInstance().close();
        } else {
            System.out.println("접근 권한이 없습니다.");
        }
    }

    /**
     * 배차 수정
     */
    @Override
    public void modifyVehicleDispatching() {
        if (currentUser.getPermission() == 1 || currentUser.getPermission() == 4) {
            // 운송장 리스트 출력
            // 출고지시서 만으로는 아직 배차가 되지 않았기 때문에 운송장 리스트를 출력해와야 한다.
            List<Waybill> waybillList = waybillDao.getWaybillList();
            System.out.println("--------------------------------------");
            System.out.printf(
                    "%-6d%-20d%-10s%-25s%-16s%n",
                    "id",
                    "shipping_orders_id",
                    "vehicle_num",
                    "departure_date",
                    "arrival_date"
            );
            System.out.println("--------------------------------------");
            for (Waybill waybill : waybillList) {
                System.out.printf(
                        "%-6d%-20d%-10s%-25s%-16s%n",
                        waybill.getId(),
                        waybill.getShippingOrdersId(),
                        waybill.getVehicleNum(),
                        waybill.getDepartureDate(),
                        waybill.getArrivalDate()
                );
            }
            System.out.println("--------------------------------------");

            System.out.println("배차 수정할 출고지시서 번호를 입력하세요.");
            int shippingOrdersId = Integer.parseInt(GetTexts.getInstance().readLine());

            vehicleDao.getVehicleList();// 배차 리스트 출력
            System.out.println("수정할 배차를 선택해주세요.");
            String vehicleNum = GetTexts.getInstance().readLine();

            vehicleDao.modifyVehicleDispatchingByWaybillId(shippingOrdersId, vehicleNum);
            System.out.println("배차가 수정되었습니다.");

            //GetTexts.getInstance().close();
        } else {
            System.out.println("접근 권한이 없습니다.");
        }
    }


    /**
     * 운송장 수정
     */
    @Override
    public void modifyWaybill() {
        if (currentUser.getPermission() == 1 || currentUser.getPermission() == 4) {
            Waybill waybill = new Waybill();
            List<Waybill> waybillList = waybillDao.getWaybillList(); // 운송장 리스트 출력
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.printf(
                    "%-6s%-25s%-20s%-20s%-16s%n",
                    "id",
                    "shipping_orders_id",
                    "vehicle_num",
                    "departure_date",
                    "arrival_date"
            );
            System.out.println("--------------------------------------------------------------------------------------");
            for (Waybill w : waybillList) {
                System.out.printf(
                        "%-6s%-25s%-20s%-20s%-16s%n",
                        w.getId(),
                        w.getShippingOrdersId(),
                        w.getVehicleNum(),
                        w.getDepartureDate(),
                        w.getArrivalDate()
                );
            }
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("수정할 운송장 번호를 입력하세요.");
            int waybillId = Integer.parseInt(GetTexts.getInstance().readLine());
            waybill.setId(waybillId);

            List<ShippingOrders> awaitedShippingOrdersList = shippingOrdersDao.getShippingOrdersList();   // 승인 완료된 출고지시서 리스트 출력
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.printf(
                    "%-6s%-15s%-20s%-15s%-15s%-15s%-15s%n",
                    "id",
                    "user_id",
                    "delivery_address",
                    "order_date",
                    "delivery_date",
                    "status",
                    "approved_status"
            );
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            for (ShippingOrders shippingOrders : awaitedShippingOrdersList) {
                System.out.printf(
                        "%-6s%-15s%-20s%-15s%-15s%-15s%-15s%n",
                        shippingOrders.getId(),
                        shippingOrders.getUserId(),
                        shippingOrders.getDeliveryAddress(),
                        shippingOrders.getOrderDate(),
                        shippingOrders.getDeliveryDate(),
                        shippingOrders.getStatus(),
                        shippingOrders.getApprovedStatus()
                );
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.println("출고 지시서 번호를 입력하세요.");
            int shippingOrdersId = Integer.parseInt(GetTexts.getInstance().readLine());
            waybill.setShippingOrdersId(shippingOrdersId);

            List<Vehicle> vehicleList = vehicleDao.getVehicleList();
            System.out.println("----------------------------------------------------------------------");
            System.out.printf(
                    "%-15s%-20s%-15s%-15s%n",
                    "num",
                    "delivery_driver_id",
                    "type",
                    "telephone"
            );
            System.out.println("----------------------------------------------------------------------");
            for (Vehicle vehicle : vehicleList) {
                System.out.printf(
                        "%-15s%-20s%-15s%-15s%n",
                        vehicle.getNum(),
                        vehicle.getDeliveryDriverId(),
                        vehicle.getType(),
                        vehicle.getTelephone()
                );
            }
            System.out.println("----------------------------------------------------------------------");
            System.out.println("차량 번호를 입력하세요.");
            String vehicleNum = GetTexts.getInstance().readLine();
            waybill.setVehicleNum(vehicleNum);
            try {
                System.out.println("(yyyy-MM-dd) 형식으로 출발일을 입력하세요.");
                String inputDate = GetTexts.getInstance().readLine();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date departureDate = simpleDateFormat.parse(inputDate);
                waybill.setDepartureDate(departureDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            waybillDao.modifyWaybill(waybill);
            System.out.println("운송장이 수정되었습니다.");
            //GetTexts.getInstance().close();
        } else {
            System.out.println("접근 권한이 없습니다.");
        }
    }
}
