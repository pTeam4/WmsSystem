package vo;

import lombok.Data;

import java.util.Date;

//QR바코드
@Data
public class QrBarcode {
    private int id;
    private int product_id;
    private int barcodeData;
    private Date creationDate;
}
