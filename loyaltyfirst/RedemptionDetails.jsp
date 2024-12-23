<%@ page import="java.sql.*" %>
<%
String pid = request.getParameter("prizeid");
String cid = request.getParameter("cid");

String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    Connection conn = DriverManager.getConnection(url, "nthota2", "lynishus");

    
    String query = "SELECT p.p_description, p.points_needed, rh.r_date, ec.center_name FROM Redemption_History rh JOIN Prizes p ON rh.prize_id = p.prize_id JOIN ExchgCenters ec ON rh.center_id = ec.center_id WHERE rh.cid = " + cid + "AND rh.prize_id = " + pid;
    Statement stmt=conn.createStatement();
    ResultSet rs=stmt.executeQuery(query);
    
    
    while (rs.next()) { 
        out.print(rs.getObject(1) + "*" + rs.getObject(2) + "*" + rs.getObject(3) + "*" + rs.getObject(4) + "#");
    }
    

    conn.close();
} catch (Exception e) {
    out.println("Exception: " + e.getMessage());
    e.printStackTrace();
}
%>


