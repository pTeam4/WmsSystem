package service;

import config.GetTexts;
import vo.ShippingOrders;

public class RetrievalServiceImpl implements RetrievalService {
    @Override
    public void getWaybill() {
        System.out.println("운송장을 출력합니다.");
        /** dao 로 운송장 정보 불러오기*/
    }

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

    @Override
    public void getRetrievalOrder() {
        System.out.print("출고지시서 번호를 입력하세요 : ");
        String number = GetTexts.getInstance().readLine();
        System.out.println("출고지시서를 조회합니다.");

        GetTexts.getInstance().close();
    }

    @Override
    public void getRetrievalList() {
        System.out.println("출고지시서 목록을 조회합니다.");
    }

    @Override
    public void searchRetrievalGoods() {
    }

    @Override
    public void addVehicleDispatching() {

    }

    @Override
    public void addWaybill() {

    }

    @Override
    public void getVehicleDispatching() {

    }

    @Override
    public void cancelVehicleDispatching() {

    }

    @Override
    public void modifyVehicleDispatching() {

    }

    @Override
    public void modifyWaybill() {

    }
}
