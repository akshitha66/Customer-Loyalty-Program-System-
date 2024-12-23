<%@ page import="java.sql.*" %>
<%
String fid = request.getParameter("fid");
String cid = request.getParameter("cid");
String npoints = request.getParameter("npoints");

String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    Connection conn = DriverManager.getConnection(url, "nthota2", "lynishus");

    
    String query = "UPDATE Point_Accounts SET num_of_points = num_of_points + " + npoints + "WHERE family_id = " + fid + "AND cid != " + cid;
    Statement stmt=conn.createStatement();
    int updatedrows = stmt.executeUpdate(query);
    
    
    if (updatedrows > 0) {
        out.print("Point accounts of the family members (except the specified customer) have been updated successfully.");
    } else {
        out.println("No updates were performed.");
    }
    

    conn.close();
} catch (Exception e) {
    out.println("Exception: " + e.getMessage());
    e.printStackTrace();
}
%>



