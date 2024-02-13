package dao;

import vo.ShippingOrders;
import vo.Vehicle;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

//차량
public class VehicleDao {
    Connection conn;
    private PreparedStatement pstmt;

    public void getVehicleList() {
        Vehicle vehicle = new Vehicle();
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("select * from Vehicle")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("-----------------------------------");
            while (rs.next()) {
                System.out.printf(
                        "%-6s%-12s%-10s%-25s%-16s%-16s%n",
                        rs.getString("num"),
                        rs.getInt("delivery_driver_id"),
                        rs.getString("type"),
                        rs.getInt("contact1"),
                        rs.getString("contact2"),
                        rs.getInt("status")
                );
            }
            System.out.println("-----------------------------------");
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateVehicleByNum(int shippingOrder) {
        try {

            System.out.println("수정할 글 제목을 입력하세요.");
            String updatetitle = br.readLine();
            String sql = "UPDATE board SET " +
                    "btitle = ? " +
                    "where bno = ?";
            //PreparedStatement 얻기 및 값 지정
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatetitle);
            pstmt.setInt(2, bno);
            pstmt.executeUpdate();
            System.out.println("글 제목 수정 완료 되었습니다.");

            System.out.println("글 내용을 수정하시겠습니까? Y/N");
            yesorno = br.readLine();
            if (yesorno.equals("Y")) {
                System.out.println("수정한 글 내용을 입력하세요. 다 작성하셨으면 exit를 입력해주세요.");
                StringBuilder sb = new StringBuilder();
                String line;
                while (!(line = br.readLine()).equals("exit")) {
                    sb.append(line).append("\r\n"); // 각 줄의 끝에 \r\n 추가
                }
                sb.delete(sb.length() - 2, sb.length()); //맨마지막 개행 삭제
                String updatecontents = sb.toString();
                String sql = "UPDATE board SET " +
                        "bcontent = ? " +
                        "where bno = ?";
                //PreparedStatement 얻기 및 값 지정
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, updatecontents);
                pstmt.setInt(2, bno);
                pstmt.executeUpdate();
                System.out.println("글내용 수정 완료 되었습니다.");
            }
            System.out.println("첨부파일을 수정하시겠습니까? Y/N");
            yesorno = br.readLine();
            if (yesorno.equals("Y")) {
                System.out.println("수정할 첨부파일 명을 입력해주세요.");
                String updatebfilename = br.readLine();
                System.out.println("수정할 첨부파일 경로를 입력해주세요.");
                String updatebfilepath = br.readLine();
                String sql = "UPDATE board SET " +
                        "bfilename = ?, bfiledata = ? " +
                        "where bno = ?";
                //PreparedStatement 얻기 및 값 지정
                pstmt = conn.prepareStatement(sql);
                Boolean flag = false;
                try {
                    pstmt.setBlob(2, new FileInputStream(updatebfilepath));
                } catch (IOException e) {
                    flag = true;
                    System.out.println("파일 경로 오류입니다. 파일경로를 확인해주세요.");
                }
                if (!flag) {
                    pstmt.setString(1, updatebfilename);
                    pstmt.setInt(3, bno);
                    pstmt.executeUpdate();
                    System.out.println("첨부파일 수정 완료 되었습니다.");
                }

            }
            pstmt.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void cancelVehicleDispatchingByWaybillId(int shippingOrdersId) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String sql = stringBuilder.append("UPDATE Vehicle SET ")
                    .append("status = 0 ")
                    .append("where bno = ?")
                    .toString();
            //PreparedStatement 얻기 및 값 지정
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatetitle);
            pstmt.setInt(2, bno);
            pstmt.executeUpdate();
            System.out.println("글 제목 수정 완료 되었습니다.");
        }
        System.out.println("글 내용을 수정하시겠습니까? Y/N");
        yesorno = br.readLine();
        if (yesorno.equals("Y")) {
            System.out.println("수정한 글 내용을 입력하세요. 다 작성하셨으면 exit를 입력해주세요.");
            StringBuilder sb = new StringBuilder();
            String line;
            while (!(line = br.readLine()).equals("exit")) {
                sb.append(line).append("\r\n"); // 각 줄의 끝에 \r\n 추가
            }
            sb.delete(sb.length() - 2, sb.length()); //맨마지막 개행 삭제
            String updatecontents = sb.toString();
            String sql = "UPDATE board SET " +
                    "bcontent = ? " +
                    "where bno = ?";
            //PreparedStatement 얻기 및 값 지정
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatecontents);
            pstmt.setInt(2, bno);
            pstmt.executeUpdate();
            System.out.println("글내용 수정 완료 되었습니다.");
        }
        System.out.println("첨부파일을 수정하시겠습니까? Y/N");
        yesorno = br.readLine();
        if (yesorno.equals("Y")) {
            System.out.println("수정할 첨부파일 명을 입력해주세요.");
            String updatebfilename = br.readLine();
            System.out.println("수정할 첨부파일 경로를 입력해주세요.");
            String updatebfilepath = br.readLine();
            String sql = "UPDATE board SET " +
                    "bfilename = ?, bfiledata = ? " +
                    "where bno = ?";
            //PreparedStatement 얻기 및 값 지정
            pstmt = conn.prepareStatement(sql);
            Boolean flag = false;
            try {
                pstmt.setBlob(2, new FileInputStream(updatebfilepath));
            } catch (IOException e) {
                flag = true;
                System.out.println("파일 경로 오류입니다. 파일경로를 확인해주세요.");
            }
            if (!flag) {
                pstmt.setString(1, updatebfilename);
                pstmt.setInt(3, bno);
                pstmt.executeUpdate();
                System.out.println("첨부파일 수정 완료 되었습니다.");
            }

        }
        pstmt.close();
    } catch(
    IOException e)

    {
        e.printStackTrace();
    } catch(
    SQLException e)

    {
        e.printStackTrace();
    }
}
}
