import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/Signupservlet"})
public class Signupservlet extends HttpServlet {

    // Caesar Cipher Encryption
    public String caesarCipherEncrypt(String plain, int key) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < plain.length(); i++) {
            char ch = plain.charAt(i);
            if (Character.isDigit(ch)) { // Encrypt only digits
                int digit = Character.getNumericValue(ch);
                digit = (digit + key) % 10; // Wrap around for digits
                encrypted.append(digit);
            } else {
                encrypted.append(ch); // Leave non-digits as is
            }
        }
        return encrypted.toString();
    }

    // Monoalphabetic Cipher Encryption
    public String monoalphabeticCipherEncrypt(String plain, String key) {
        StringBuilder encrypted = new StringBuilder();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < plain.length(); i++) {
            char ch = plain.charAt(i);
            if (Character.isLetter(ch)) {
                char lowerCh = Character.toLowerCase(ch);
                int index = alphabet.indexOf(lowerCh);
                if (index != -1) {
                    char encryptedChar = key.charAt(index);
                    encrypted.append(Character.isUpperCase(ch) ? Character.toUpperCase(encryptedChar) : encryptedChar);
                } else {
                    encrypted.append(ch);
                }
            } else {
                encrypted.append(ch);
            }
        }
        return encrypted.toString();
    }

    // Modified Caesar Cipher Encryption
    public String modifiedCaesarCipherEncrypt(String plain, int key) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < plain.length(); i++) {
            char ch = plain.charAt(i);
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) ((ch - base + key + i) % 26 + base); // Modified Caesar Cipher
            }
            encrypted.append(ch);
        }
        return encrypted.toString();
    }

    // Vernam Cipher Encryption
    public String vernamCipherEncrypt(String plain, String key) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < plain.length(); i++) {
            char ch = plain.charAt(i);
            char keyChar = key.charAt(i % key.length());
            encrypted.append((char) (ch ^ keyChar)); // XOR operation
        }
        return encrypted.toString();
    }

    // Polyalphabetic Cipher Encryption
    public String polyalphabeticCipherEncrypt(String plain, String key) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < plain.length(); i++) {
            char ch = plain.charAt(i);
            char keyChar = key.charAt(i % key.length());
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) ((ch - base + keyChar - 'a') % 26 + base);
            }
            encrypted.append(ch);
        }
        return encrypted.toString();
    }

    // Rail Fence Cipher Encryption
    public String railFenceEncrypt(String plain, int rails) {
        StringBuilder encrypted = new StringBuilder();
        int n = plain.length();
        char[][] fence = new char[rails][n];
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < n; j++) {
                fence[i][j] = '\0'; // Initialize with null character
            }
        }

        boolean directionDown = false;
        int row = 0, col = 0;

        for (int i = 0; i < n; i++) {
            if (row == 0 || row == rails - 1) {
                directionDown = !directionDown;
            }
            fence[row][col++] = plain.charAt(i);
            if (directionDown) {
                row++;
            } else {
                row--;
            }
        }

        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < n; j++) {
                if (fence[i][j] != '\0') {
                    encrypted.append(fence[i][j]);
                }
            }
        }
        return encrypted.toString();
    }

    // Simple Columnar Cipher Encryption
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
            String fn = request.getParameter("n1");
            String mn = request.getParameter("n2");
            String ln = request.getParameter("n3");
            String name = fn + " " + mn + " " + ln;
            String m_status = request.getParameter("maritalStatus");
            String mob = request.getParameter("num"); // Mobile number (VARCHAR)
            String email = request.getParameter("mail");
            String street = request.getParameter("street");
            String state = request.getParameter("state");
            String city = request.getParameter("city");
            String pinc = request.getParameter("pincode"); // Pincode (INT)
            String un = request.getParameter("uname");
            String psw = request.getParameter("pass");
            String cpsw = request.getParameter("cpass");

            // Encrypt fields
            String encryptedMob = caesarCipherEncrypt(mob, 3); // Caesar Cipher for mobile number
            String encryptedEmail = monoalphabeticCipherEncrypt(email, "qwertyuiopasdfghjklzxcvbnm"); // Monoalphabetic Cipher for email
            String encryptedPsw = simpleColumnarEncrypt(psw, "3124"); // Simple Columnar Cipher for password
            String encryptedUn = vernamCipherEncrypt(un, "secretkey"); // Vernam Cipher for username
            String encryptedStreet = polyalphabeticCipherEncrypt(street, "polykey"); // Polyalphabetic Cipher for street
            String encryptedCity = railFenceEncrypt(city, 3); // Rail Fence Cipher for city
            String encryptedPinStr = caesarCipherEncrypt(pinc, 2); // Caesar Cipher for pincode (as string)
            int encryptedPin = Integer.parseInt(encryptedPinStr); // Convert encrypted pincode to INT

            // Check if passwords match
            if (psw.equals(cpsw)) {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/College1", "app", "app");
                PreparedStatement ps = c.prepareStatement("insert into Employee values(?,?,?,?,?,?,?,?,?,?)");
                ps.setString(1, name);
                ps.setString(2, m_status);
                ps.setString(3, encryptedMob); // Encrypted mobile (VARCHAR)
                ps.setString(4, encryptedEmail); // Encrypted email (VARCHAR)
                ps.setString(5, encryptedStreet); // Encrypted street (VARCHAR)
                ps.setString(6, state); // State (no encryption)
                ps.setString(7, encryptedCity); // Encrypted city (VARCHAR)
                ps.setInt(8, encryptedPin); // Encrypted pincode (INT)
                ps.setString(9, encryptedUn); // Encrypted username (VARCHAR)
                ps.setString(10, encryptedPsw); // Encrypted password (VARCHAR)

                int check = ps.executeUpdate();
                if (check > 0) {
                    RequestDispatcher rd = request.getRequestDispatcher("index.html");
                    rd.forward(request, response);
                } else {
                    out.println("Data not saved!");
                }
            } else {
                out.println("Passwords do not match!");
            }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Signupservlet Servlet";
    }
}