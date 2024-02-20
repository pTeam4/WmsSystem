package util;

public enum MovementStatus {
    REQUESTED("IR"), // 입고 요청
    CANCELLED("ID"), // 입고 취소
    APPROVED("IC"),  // 입고 승인 완료
    RELEASED("OR"),  // 출고 요청
    SHIPPED("OC"),   // 출고 완료
    CANCELLED_RELEASE("OD"); // 출고 취소

    private final String code;

    MovementStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}