package dao;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import config.JdbcConnection;
import vo.QrBarcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

//QR바코드
public class QrBarcodeDao {
    private static Connection connection = JdbcConnection.getInstance().getConnection();

    public static String generateQrCode(String qrCodeContent) {
        int width = 300;
        int height = 300;

        // encoding option setting
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            // Encode the QR code
            BitMatrix bitMatrix = multiFormatWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, width, height, hints);

            // Convert BitMatrix to BufferedImage
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Convert BufferedImage to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            // Convert byte array to Base64 encoded string
            String base64Image = Base64.getEncoder().encodeToString(byteArray);


            return base64Image;
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveQrBarcode(QrBarcode qrBarcode) { //Qrbarcode 테이블에 저장하는 메서드
        String INSERT_QR_BARCODE_QUERY = "INSERT INTO Qr_Barcode (product_id, barcode_Data, creation_Date) VALUES (?, ?, ?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QR_BARCODE_QUERY)
        ) {

            preparedStatement.setInt(1, qrBarcode.getProduct_id());
            preparedStatement.setBlob(2, qrBarcode.getBarcodeData());
            preparedStatement.setDate(3, new java.sql.Date(qrBarcode.getCreationDate().getTime()));

            preparedStatement.executeUpdate();

            System.out.println("QrBarcode saved successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}