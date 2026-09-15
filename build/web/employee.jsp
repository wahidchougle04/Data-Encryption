<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Details</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f0f0f0; }
        .container { max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h1 { color: #333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <div class="container">
        

        <%!
        // Encryption method (Vernam Cipher from the provided servlet)
        public String vernamCipherEncrypt(String plain, String key) {
            StringBuilder encrypted = new StringBuilder();
            for (int i = 0; i < plain.length(); i++) {
                char ch = plain.charAt(i);
                char keyChar = key.charAt(i % key.length());
                encrypted.append((char) (ch ^ keyChar)); // XOR operation
            }
            return encrypted.toString();
        }

        // Caesar Cipher Decryption
        public String caesarCipherDecrypt(String encrypted, int key) {
            StringBuilder decrypted = new StringBuilder();
            for (int i = 0; i < encrypted.length(); i++) {
                char ch = encrypted.charAt(i);
                if (Character.isDigit(ch)) {
                    int digit = Character.getNumericValue(ch);
                    digit = (digit - key + 10) % 10; // Reverse the encryption
                    decrypted.append(digit);
                } else {
                    decrypted.append(ch);
                }
            }
            return decrypted.toString();
        }

        // Monoalphabetic Cipher Decryption
        public String monoalphabeticCipherDecrypt(String encrypted, String key) {
            StringBuilder decrypted = new StringBuilder();
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            for (int i = 0; i < encrypted.length(); i++) {
                char ch = encrypted.charAt(i);
                if (Character.isLetter(ch)) {
                    char lowerCh = Character.toLowerCase(ch);
                    int index = key.indexOf(lowerCh);
                    if (index != -1) {
                        char decryptedChar = alphabet.charAt(index);
                        decrypted.append(Character.isUpperCase(ch) ? Character.toUpperCase(decryptedChar) : decryptedChar);
                    } else {
                        decrypted.append(ch);
                    }
                } else {
                    decrypted.append(ch);
                }
            }
            return decrypted.toString();
        }
        
            // Polyalphabetic Cipher Decryption
        public String polyalphabeticCipherDecrypt(String encrypted, String key) {
            StringBuilder decrypted = new StringBuilder();
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            for (int i = 0; i < encrypted.length(); i++) {
                char ch = encrypted.charAt(i);
                if (Character.isLetter(ch)) {
                    boolean isUpper = Character.isUpperCase(ch);
                    ch = Character.toLowerCase(ch);
                    int alphabetIndex = alphabet.indexOf(ch);
                    int keyIndex = alphabet.indexOf(key.charAt(i % key.length()));
                    if (alphabetIndex != -1) {
                        int decryptedIndex = (alphabetIndex - keyIndex + 26) % 26;
                        char decryptedChar = alphabet.charAt(decryptedIndex);
                        decrypted.append(isUpper ? Character.toUpperCase(decryptedChar) : decryptedChar);
                    } else {
                        decrypted.append(ch);
                }
                }  
                else {
                    decrypted.append(ch);
                }
            }
            return decrypted.toString();
        }


        // Rail Fence Cipher Decryption
        public String railFenceDecrypt(String encrypted, int rails) {
            if (rails <= 1) return encrypted;
            char[][] fence = new char[rails][encrypted.length()];
            int r = 0, dir = 1;
            for (int i = 0; i < encrypted.length(); i++) {
                fence[r][i] = '*';
                r += dir;
                if (r == 0 || r == rails - 1) dir = -dir;
            }
            int index = 0;
            for (int i = 0; i < rails; i++) {
                for (int j = 0; j < encrypted.length(); j++) {
                    if (fence[i][j] == '*' && index < encrypted.length()) {
                        fence[i][j] = encrypted.charAt(index++);
                    }
                }
            }
            StringBuilder decrypted = new StringBuilder();
            r = 0;
            dir = 1;
            for (int i = 0; i < encrypted.length(); i++) {
                decrypted.append(fence[r][i]);
                r += dir;
                if (r == 0 || r == rails - 1) dir = -dir;
            }
            return decrypted.toString();
        }
        %>

        <%
        String username = request.getParameter("username");
        if (username != null && !username.isEmpty()) {
            String encryptedUsername = vernamCipherEncrypt(username, "secretkey");

            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/College1", "app", "app");
                String query = "SELECT * FROM Employee WHERE E_UNAME = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, encryptedUsername);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
        %>
        Hello! <b><%= username %></b>
        <h1><b><center>User Details</center></b></h1>
            <table>
                        <tr><th>Type</th><td><b>Decrypted</b></td><td><b>Encrypted</b></td>
                        <tr><th>Name</th><td><%= rs.getString("E_NAME") %></td><td><%= rs.getString("E_NAME") %></td></tr>
                        <tr><th>Marital Status</th><td><%= rs.getString("M_STATUS") %></td><td><%= rs.getString("M_STATUS") %></td></tr>
                        <tr><th>Mobile</th><td><%= caesarCipherDecrypt(rs.getString("E_MOB"), 3) %></td><td><%= rs.getString("E_MOB") %></td></tr>
                        <tr><th>Email</th><td><%= monoalphabeticCipherDecrypt(rs.getString("E_EMAIL"), "qwertyuiopasdfghjklzxcvbnm") %></td><td><%= rs.getString("E_EMAIL") %></td></tr>
                        <tr><th>Street</th><td><%= polyalphabeticCipherDecrypt(rs.getString("E_STREET"), "polykey") %></td><td><%= rs.getString("E_STREET") %></td></tr>
                        <tr><th>State</th><td><%= rs.getString("E_STATE") %></td><td><%= rs.getString("E_STATE") %></td></tr>
                        <tr><th>City</th><td><%= railFenceDecrypt(rs.getString("E_CITY"), 3) %></td><td><%= rs.getString("E_CITY") %></td></tr>
                        <tr><th>Pincode</th><td><%= caesarCipherDecrypt(String.valueOf(rs.getInt("E_PIN")), 2) %></td><td><%= rs.getString("E_PIN") %></td></tr>
                    </table>
        <%
                } else {
                    out.println("<p>User not found.</p>");
                }
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                out.println("<p>Error: " + e.getMessage() + "</p>");
            }
        } else {
            out.println("<p>No username provided.</p>");
        }
        %>
    </div>
</body>
</html>