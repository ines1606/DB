import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class QueryManager {
    private final Map<String, String> queries = new HashMap<>();
    private final Map<String, String> userFriendlyNames = new HashMap<>();

    public QueryManager(String filePath) throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream(filePath));
        for (String key : properties.stringPropertyNames()) {
            queries.put(key, properties.getProperty(key));
        }

        populateUserFriendlyNames();
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    private void populateUserFriendlyNames() {
        userFriendlyNames.put("total_sales_by_product", "Total Sales by Product");
        userFriendlyNames.put("top_selling_products", "Top Selling Products");
        userFriendlyNames.put("revenue_by_category", "Revenue by Category");
        userFriendlyNames.put("customer_orders_count", "Customer Orders Count");
        userFriendlyNames.put("average_order_price", "Average Order Price");
        userFriendlyNames.put("total_spent_per_customer", "Total Spent Per Customer");
        userFriendlyNames.put("low_stock_products", "Low Stock Products");
        userFriendlyNames.put("unpaid_customer_emails", "Unpaid Customer Emails");
        userFriendlyNames.put("top_supplier", "Supplier with Most Expensive Product");
        userFriendlyNames.put("department_most_inquiries", "Department with Most Inquiries");
        userFriendlyNames.put("employee_with_returns", "Employee Handling Returns");
        userFriendlyNames.put("completed_order_addresses", "Addresses of Completed Orders");
        userFriendlyNames.put("orders_in_september", "Orders in September 2024");
        userFriendlyNames.put("audit_dates_lowest_salary", "Audit Dates by Lowest Salary Employee");
        userFriendlyNames.put("products_below_threshold", "Products Below Stock Threshold");
        userFriendlyNames.put("brands_never_returned", "Brands Never Returned");
        userFriendlyNames.put("total_returned_orders", "Total Price of Returned Orders");
        userFriendlyNames.put("top_spender_phone_numbers", "Top Spender's Phone Numbers");
        userFriendlyNames.put("employee_most_expensive_order", "Employee handled most expensive Order");
    }

    public Map<String, String> getUserFriendlyNames() {
        return userFriendlyNames;
    }
}