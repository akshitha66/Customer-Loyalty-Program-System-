<%@ page import="java.sql.*" %>
<%
String customerId = request.getParameter("cid");

String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    Connection conn = DriverManager.getConnection(url, "nthota2", "lynishus");

    
    String query = "SELECT cname, SUM(t_points) AS total_points FROM customers c JOIN transactions t ON c.cid = t.cid WHERE c.cid = " + customerId + "group by cname";
    Statement stmt=conn.createStatement();
    ResultSet rs=stmt.executeQuery(query);
    
    if (rs.next()) {
        out.println(rs.getString("cname") + "#" + rs.getInt("total_points"));
    } else {
        out.println("<p>Customer not found.</p>");
    }

    conn.close();
} catch (Exception e) {
    out.println("Exception: " + e.getMessage());
    e.printStackTrace();
}
%>

