import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@WebServlet("/MongoDBConnectionServlet")
public class MongoDBConnectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String input1 = request.getParameter("input1");
        String input2 = request.getParameter("input2");

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<h2>Servlet is working. Attempting MongoDB connection...</h2>");
            System.out.println("Servlet is working! Attempting MongoDB connection...");

            // MongoDB connection string
            String uri = "mongodb+srv://wahid:Wahid123@cluster0.bzg8n.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
            
            try {
                System.out.println("Connecting to MongoDB...");
                MongoClient mongoClient = MongoClients.create(uri);
                System.out.println("MongoClient object created!");

                MongoDatabase database = mongoClient.getDatabase("testing");
                System.out.println("Connected to database: " + database.getName());

                MongoCollection<Document> collection = database.getCollection("newtest");
                System.out.println("Connected to collection: " + collection.getNamespace());

                Document doc = new Document("field1", input1).append("field2", input2);
                collection.insertOne(doc);

                out.println("<h3>Data inserted successfully!</h3>");
                System.out.println("Data inserted successfully!");

                mongoClient.close();
                System.out.println("MongoClient connection closed.");

            } catch (Exception e) {
                out.println("<h3>Database Connection Failed: " + e.getMessage() + "</h3>");
                System.out.println("Database Connection Failed: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception ex) {
            System.out.println("Error occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
