import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/loginservlet"})
public class loginservlet extends HttpServlet {

    // Vernam Cipher Encryption (for username)
    public String vernamCipherEncrypt(String plain, String key) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < plain.length(); i++) {
            char ch = plain.charAt(i);
            char keyChar = key.charAt(i % key.length());
            encrypted.append((char) (ch ^ keyChar)); // XOR operation
        }
        return encrypted.toString();
    }

    // Simple Columnar Cipher Encryption (for password)
    public String simpleColumnarEncrypt(String plain, String key) {
        int cols = key.length();
        int rows = (int) Math.ceil((double) plain.length() / cols);
        char[][] grid = new char[rows][cols];
        int index = 0;

        // Fill the grid row-wise
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (index < plain.length()) {
                    grid[i][j] = plain.charAt(index++);
                } else {
                    grid[i][j] = ' '; // Padding with space if necessary
                }
            }
        }

        // Read columns in the order of the key
        StringBuilder encrypted = new StringBuilder();
        for (char ch : key.toCharArray()) {
            int col = Character.getNumericValue(ch) - 1; // Assuming key is numeric
            for (int i = 0; i < rows; i++) {
                encrypted.append(grid[i][col]);
            }
        }
        return encrypted.toString().trim(); // Remove trailing spaces
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Fetch form data
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Encrypt username and password
            String encryptedUsername = vernamCipherEncrypt(username, "secretkey"); // Vernam Cipher
            String encryptedPassword = simpleColumnarEncrypt(password, "3124"); // Simple Columnar Cipher

            // Database connection
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/College1", "app", "app");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Employee WHERE E_UNAME = ? AND E_PASS = ?");
            ps.setString(1, encryptedUsername);
            ps.setString(2, encryptedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Login successful
                RequestDispatcher rd = request.getRequestDispatcher("employee.jsp"); // Redirect to a dashboard page
                rd.forward(request, response);
            } else {
                // Login failed
                out.println("<h1>Invalid username or password!</h1>");
            }

        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Loginservlet Servlet";
    }
}
