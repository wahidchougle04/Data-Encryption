import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@WebServlet("/InsertServlet")
public class InsertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get input values
        String input1 = request.getParameter("input1");
        String input2 = request.getParameter("input2");

        // MongoDB Atlas connection string (Replace with your actual URI)
        String mongoUri = "mongodb+srv://wahid:Wahid123@<cluster>.mongodb.net/?retryWrites=true&w=majority";
        
        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase("testing"); // Change to your DB name
            MongoCollection<Document> collection = database.getCollection("newtest"); // Change to your collection name

            // Create a document to insert
            Document doc = new Document("input1", input1)
                    .append("input2", input2);

            // Insert into MongoDB
            collection.insertOne(doc);

            // Response to user
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h3>Data Inserted Successfully!</h3>");
            out.println("<a href='index.html'>Go Back</a>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
