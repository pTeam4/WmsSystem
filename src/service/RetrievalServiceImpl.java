package service;

import config.GetTexts;
import dao.VehicleDao;
import vo.ShippingOrders;
import dao.ShippingOrdersDao;
import dao.ShippingOrdersDetailDao;
import dao.WaybillDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RetrievalServiceImpl implements RetrievalService {
    private ShippingOrdersDetailDao shippingOrdersDetailDao = new ShippingOrdersDetailDao();
    private ShippingOrdersDao shippingOrdersDao = new ShippingOrdersDao();
    private WaybillDao waybillDao = new WaybillDao();
    private VehicleDao vehicleDao = new VehicleDao();

    /**
     * 운송장 조회하기
     */
    @Override
    public void getWaybill() {
        /** 운송장 리스트 출력 */
        waybillDao.getWaybillList();
        System.out.println("출력할 운송장의 출고지시서 번호를 입력하세요.");
        int input = Integer.parseInt(GetTexts.getInstance().readLine());
        System.out.println("운송장을 출력합니다.");
        waybillDao.getWaybillByShippingOrdersId();
    }

    /**
     * 출고 요청
     */
    @Override
    public void requestRetrieval() {
        ShippingOrders shippingOrder = new ShippingOrders();
        /** set 함수로 출고 신청 양식 작성해서 보내기*/

        boolean flag = approveRetrievalRequest();
        if (flag == true) {
            System.out.println("출고 신청을 승인받았습니다.");
            /** 작성한 shippingOrder 를 dao 로 db에 집어넣기.*/
        } else {
            System.out.println("출고 신청이 거부되었습니다.");
            /** 이전 메뉴로 돌아가기*/
        }
    }

    /**
     * 출고요청 승인
     */
    @Override
    public boolean approveRetrievalRequest() {
        boolean flag = false;
        System.out.print("출고를 승인하시겠습니까 ? [ y / n ] ");
        try {
            String answer = GetTexts.getInstance().readLine();
            if (answer.equalsIgnoreCase("y")) {
                flag = true;
                System.out.println("출고를 승인합니다");
                /** shippingOrder 작성 권한 부여*/
            } else if (answer.equalsIgnoreCase("n")) {
                System.out.println("출고를 거부합니다.");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("다시 입력해주십시오.");
            approveRetrievalRequest();
        }
        GetTexts.getInstance().close();
        return flag;
    }

    /**
     * 출고지시서 조회
     */
    @Override
    public void getRetrievalOrder() {
        System.out.print("출고지시서 번호를 입력하세요 : ");
        String number = GetTexts.getInstance().readLine();
        System.out.println("출고지시서를 조회합니다.");

        GetTexts.getInstance().close();
    }

    /**
     * 출고리스트 조회
     */
    @Override
    public void getRetrievalList() {
        System.out.println("출고지시서 목록을 조회합니다.");
        shippingOrdersDao.getShippingOrdersList();
    }

    /**
     * 출고 상품 검색
     */
    @Override
    public void searchRetrievalGoods() {
        System.out.println("검색할 상품의 상품 번호를 입력하세요.");
        int input = Integer.parseInt(GetTexts.getInstance().readLine());
        /** input 으로 Shipping_Orders_Detail 테이블 검색하기 */
        System.out.println("출력 양식");
        shippingOrdersDetailDao.getShippingOrdersDetailByProductId(input);
        GetTexts.getInstance().close();
    }

    /**
     * 배차 등록
     */
    @Override
    public void addVehicleDispatching() {
        System.out.println("");

    }

    /**
     * 운송장 등록 : 일반적으로 출고 요청 승인 후에 배차를 진행하기 때문에 운송장에 넣었다.
     */
    @Override
    public void addWaybill() {
        getRetrievalList(); // 출고 지시서 리스트 출력
        System.out.println("운송장을 만들 출고 지시서 번호를 입력하세요.");
        int shippingOrderId = Integer.parseInt(GetTexts.getInstance().readLine());

        getVehicleDispatching(); // 출고 차량 리스트 출력
        System.out.println("출고할 차량 번호를 입력하세요.");
        String vehicleNum = GetTexts.getInstance().readLine();

        System.out.println("출고 날짜를 입력하세요. (yyyy-MM-dd 형식)");
        String inputDate = GetTexts.getInstance().readLine();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date utilDepartureDate = simpleDateFormat.parse(inputDate);
            java.sql.Date departureDate = new java.sql.Date(utilDepartureDate.getTime());
            waybillDao.addWaybill(shippingOrderId, vehicleNum, departureDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GetTexts.getInstance().close();
    }


    /**
     * 배차 조회
     */
    @Override
    public void getVehicleDispatching() {
        vehicleDao.getVehicleList();
    }

    /**
     * 배차 취소
     */
    @Override
    public void cancelVehicleDispatching() {
        waybillDao.getWaybillList();    // 운송장 리스트 출력
        // 출고지시서 만으로는 아직 배차가 되지 않았기 때문에 운송장 리스트를 출력해와야 한다.
        System.out.println("배차 취소할 출고지시서 번호를 입력하세요.");
        int shippingOrderId = Integer.parseInt(GetTexts.getInstance().readLine());
        vehicleDao.cancelVehicleDispatchingByWaybillId(shippingOrderId);
        GetTexts.getInstance().close();
    }

    /**
     * 배차 수정
     */
    @Override
    public void modifyVehicleDispatching() {

    }

    /**
     * 운송장 조회
     */
    @Override
    public void modifyWaybill() {

    }
}
