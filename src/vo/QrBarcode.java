package vo;

import lombok.Data;

import java.sql.Blob;
import java.util.Date;

//QR바코드
@Data
public class QrBarcode {
    private int id;
    private int product_id;
    private Blob barcodeData;
    private Date creationDate;
}
