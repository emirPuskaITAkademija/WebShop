package shop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import shop.model.Product;

/**
 * Samo jedan objekat tipa WebShopServlet se kreira.
 * <p>
 * Za njegovo kreiranje je odgovoran nekakav SERVLET kontejner
 * <p>
 * Metode koje mi mozemo override:
 * <li>1. init
 * <li>2. doGet
 * <li>3. doPost
 * <li>4. destroy
 *
 * @author Grupa2
 */
public class WebShopServlet extends HttpServlet {

    public static final String PRODUCTS_KEY = "products";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        List<Product> productList = new ArrayList<>();
        ServletContext servletContext = getServletContext();
        String filePath = servletContext.getRealPath("products.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ";");
                int id = Integer.parseInt(tokenizer.nextToken());
                String productName = tokenizer.nextToken();
                double price = Double.parseDouble(tokenizer.nextToken());
                Product product = new Product(id, productName, price);
                productList.add(product);
            }
            servletContext.setAttribute(PRODUCTS_KEY, productList);
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Products</title>");
            out.println("</head>");
            out.println("<body>");
            List<Product> productList = (List<Product>) getServletContext().getAttribute(PRODUCTS_KEY);
            if (productList != null && !productList.isEmpty()) {
                out.println("<h3>Dostupni proizvodi</h3>");
                out.println("<table border='1'>");
                //heading
                out.println("<tr bgcolor='lightgray'><th>Naziv</th><th>Cijena</th><th>U korpu</th></tr>");
                for (Product product : productList) {
                    out.println("<tr>");
                    //naziv proizvoda
                    out.println("<td>" + product.getProductName() + "</td>");
                    //cijena proizvoda
                    out.println("<td>" + product.getPrice() + "</td>");
                    //forma za dodavanje proizvoda u korpi
                    out.println("<td>");
                    out.println("<form action='ShoppingCartServlet' method='GET'>");
                    out.println("<input type='number' size='3' name='productQuantity'/>");
                    out.println("<input type='hidden' value='"+product.getId()+"' name='productId'/>");
                    out.println("<input type='submit' value='Dodaj'/>");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            } else {
                out.println("<h3>Trenutno nismo u mogućnosti da prikažemo proizvode u web shopu</h3>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
