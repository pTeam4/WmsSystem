package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WmsMainMenu {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public void mainmenu()
    {
        System.out.print(" /\\_/\\    /\\_/\\    /\\_/\\  \n" +
                "( o.o )  ( o.o )  ( o.o ) \n" +
                " > ^ <    > ^ <    > ^ <\n");
        System.out.println("야옹 창고 시스템에 오신 것을 환영합니다. 원하시는 메뉴를 선택해주세요");
        System.out.println("1. 회원가입 2. 로그인 3. 고객센터 4. 거래처관리 5. 운송장 조회 6. 견적신청");

        int menuno = 0;
    }
}
