package sum.inventory_manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import javafx.scene.control.TreeItem;
import sum.inventory_manager.vehicleController.VehicleDetails;

public class Database {
    static final String DB_URL = "jdbc:mysql://localhost/inventory_manager_db";
    static final String USER = "Madhusha";
    static final String PASS = "$UMadhusha17458";
    static String QUERY;

    public static ObservableList<Product> getAllProductData() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        QUERY = "select * from products";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                products.add(new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            }
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return products;
    }
    public static ObservableList<Product> getNeededProductData(HashSet<Integer> neededProductIds) {
        ObservableList<Product> products = FXCollections.observableArrayList();

        String str = neededProductIds.toString();
        QUERY = String.format("select * from products where id in (%s)", str.substring(1, str.length() - 1));

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                products.add(new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            }
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return products;
    }
    public static void addProduct(String name, String description, String unit) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {
            // Extract data from result set
            QUERY = String.format(
                    "insert into products (name, description, unit) values ('%s','%s','%s')",
                    name,
                    description,
                    unit);
            stmt.execute(QUERY);
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }
    public static void changeProductDetails(productController.ProductDetail productDetail, TableColumn.CellEditEvent<Product, String> event) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {
            // Extract data from result set
            if (productDetail == productController.ProductDetail.NAME) {
                QUERY = String.format(
                        "update products set name='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getProductId());
            } else if (productDetail == productController.ProductDetail.DESCRIPTION) {
                QUERY = String.format(
                        "update products set description='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getProductId());
            } else if (productDetail == productController.ProductDetail.UNIT) {
                QUERY = String.format(
                        "update products set unit='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getProductId());
            }
            stmt.execute(QUERY);
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }

    public static Customer getCustomer(int customerId) {
        QUERY = String.format("select * from customers where id=%d", customerId);
        Customer customer = null;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            rs.next();

            customer = new Customer(
                rs.getInt(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getString(5)
            );

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return customer;
    }
    public static ObservableList<Customer> getAllCustomerData() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        QUERY = "select * from customers";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                customers.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return customers;
    }
    public static ObservableList<Customer> getVehicleCustomerData(int vehicleId) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        QUERY = String.format("select * from customers where vehicle_id=%d", vehicleId);
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                customers.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return customers;
    }

    public static void changeCutomerDetails(customerController.CustomerDetail customerDetail, TableColumn.CellEditEvent<Customer, String> event) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {
            // Extract data from result set
            if (customerDetail == customerController.CustomerDetail.NAME) {
                QUERY = String.format(
                        "update customers set name='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getCustomerId());
            } else if (customerDetail == customerController.CustomerDetail.ADDRESS) {
                QUERY = String.format(
                        "update customers set address='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getCustomerId());
            } else if (customerDetail == customerController.CustomerDetail.TELEPHONE) {
                QUERY = String.format(
                        "update customers set telephone='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getCustomerId());
            } else if (customerDetail == customerController.CustomerDetail.VEHICLE_ID) {
                try {
                    int vehicleId = Integer.parseInt(event.getNewValue());
                    QUERY = String.format(
                            "update customers set vehicle_id=%d where id=%d",
                            vehicleId,
                            event.getRowValue().getCustomerId());
                } catch (NumberFormatException e) {
                    PopUp.showMessage("Enter valid number");
                    return;
                }
            }
            stmt.execute(QUERY);

            PopUp.showMessage("Updated");
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }

    public static void addCustomer(String name, int vehicleId, String address, String telephone) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {
            // Extract data from result set
            QUERY = String.format(
                    "insert into customers (name, vehicle_id, address, telephone) values ('%s', %d,'%s','%s')",
                    name,
                    vehicleId,
                    address,
                    telephone);
            stmt.execute(QUERY);

            PopUp.showMessage("Customer added");
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }

    public static void addStoreStock(int productId, double quantity, double unitPrice, LocalDate date) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {
            // Extract data from result set
            QUERY = String.format(
                    "insert into store_stock_loads (date, product_id, quantity, unit_price, is_moved_from) " +
                            "values ('%s', %d, %f, %f, %d)",
                    date,
                    productId,
                    quantity,
                    unitPrice,
                    0);
            stmt.execute(QUERY);

            QUERY = String.format(
                    "select * from store_stock where product_id = %d and unit_price = %f",
                    productId,
                    unitPrice
            );
            ResultSet rs = stmt.executeQuery(QUERY);

            if (rs.next()) {
                QUERY = String.format(
                        "update store_stock set quantity = quantity + %f where id = %d",
                        quantity,
                        rs.getInt(1)
                );
                stmt.execute(QUERY);
            } else {
                QUERY = String.format(
                        "insert into store_stock (product_id, quantity, unit_price) values (%d, %f, %f)",
                        productId,
                        quantity,
                        unitPrice);
                stmt.execute(QUERY);
            }

            PopUp.showMessage("Stock added");
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }

    public static ObservableList<StoreStock> getAllStoreStockData(boolean remaining) {
        ObservableList<StoreStock> storeStocks = FXCollections.observableArrayList();
        HashSet<Integer> productIds = new HashSet<>();
        if (remaining) {
            QUERY = "select * from store_stock where quantity > 0";
        } else {
            QUERY = "select * from store_stock";
        }
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                storeStocks.add(new StoreStock(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                ));
                productIds.add(rs.getInt(2));
            }

            ObservableList<Product> products = getNeededProductData(productIds);
            for (StoreStock item: storeStocks) {
                item.setProduct(products.filtered(a -> a.getProductId() == item.getProductId()).get(0));
            }

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return storeStocks;
    }

    public static ObservableList<StoreStockLoad> getAllStoreStockLoadsData() {
        ObservableList<StoreStockLoad> storeStockLoads = FXCollections.observableArrayList();
        HashSet<Integer> productIds = new HashSet<>();
        QUERY = "select * from store_stock_loads";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                storeStockLoads.add(new StoreStockLoad(
                        rs.getInt(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getInt(6)
                ));
                productIds.add(rs.getInt(3));
            }

            ObservableList<Product> products = getNeededProductData(productIds);
            for (StoreStockLoad item: storeStockLoads) {
                item.setProduct(products.filtered(a -> a.getProductId() == item.getProductId()).get(0));
            }

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return storeStockLoads;
    }

    public static void deleteStoreStockLoad(StoreStockLoad storeStockLoad) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {
            // Extract data from result set
            QUERY = String.format(
                    "select * from store_stock where product_id = %d and unit_price = %f",
                    storeStockLoad.getProductId(),
                    storeStockLoad.getUnitPrice());
            ResultSet rs = stmt.executeQuery(QUERY);

            if (rs.next()) {
                if (rs.getDouble(3) >= storeStockLoad.getQuantity()) {
                    QUERY = String.format(
                            "update store_stock set quantity = quantity - %f where id = %d",
                            storeStockLoad.getQuantity(),
                            rs.getInt(1)
                    );
                    stmt.execute(QUERY);

                    QUERY = String.format("delete from store_stock_loads where id = %d", storeStockLoad.getId());
                    stmt.execute(QUERY);
                    PopUp.showMessage("Store stock load deleted");
                } else {
                    PopUp.showMessage("Can not delete the load now!");
                    return;
                }
            } else {
                PopUp.showMessage("Can not delete the load!");
                return;
            }
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }

    }

    public static void addStoreSale(int vehicleId, LocalDate date, ObservableList<StoreSaleUnload> items, double saleTotal) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {
            //Add the sale
            QUERY = String.format(
                    "insert into store_sales (date, vehicle_id, sale_total) values ('%s', %d, %f)",
                    date,
                    vehicleId,
                    saleTotal);
            stmt.execute(QUERY, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int sale_id = rs.getInt(1);
            //Unload store stocks
            for (StoreSaleUnload sale : items) {
                QUERY = String.format(
                        "insert into store_stock_unloads (date, vehicle_id, stock_id, quantity, unit_price, is_sale, sale_id) values" +
                                "('%s', %d, %d, %f, %f, 1, %d)",
                        date,
                        vehicleId,
                        sale.getStoreStock().getStoreStockId(),
                        sale.getQuantity(),
                        sale.getUnitPrice(),
                        sale_id);

                stmt.execute(QUERY);

                QUERY = String.format(
                        "update store_stock set quantity = quantity - %f where id = %d",
                        sale.getQuantity(),
                        sale.getStoreStock().getStoreStockId()
                );
                stmt.execute(QUERY);
            }

            if (vehicleId != 0) {
                for (StoreSaleUnload unload : items) {
                    QUERY = String.format(
                            "insert into v%d_stock_loads (date, product_id, quantity, unit_price, is_from_store, store_sale_id) values" +
                                    "('%s', %d, %f, %f, 1, %d)",
                            vehicleId,
                            date, unload.getStoreStock().getProductId(), unload.getQuantity(), unload.getUnitPrice(), sale_id
                    );
                    stmt.execute(QUERY);

                    QUERY = String.format(
                            "select * from v%d_stock where product_id = %d and unit_price = %f",
                            vehicleId,
                            unload.getStoreStock().getProductId(),
                            unload.getUnitPrice()
                    );
                    ResultSet rs1 = stmt.executeQuery(QUERY);

                    if (rs1.next()) {
                        QUERY = String.format(
                                "update v%d_stock set quantity = quantity + %f where id = %d",
                                vehicleId,
                                unload.getQuantity(),
                                rs1.getInt(1)
                        );
                        stmt.execute(QUERY);
                    } else {
                        QUERY = String.format(
                                "insert into v%d_stock (product_id, quantity, unit_price) values (%d, %f, %f)",
                                vehicleId,
                                unload.getStoreStock().getProductId(),
                                unload.getQuantity(),
                                unload.getUnitPrice());
                        stmt.execute(QUERY);
                    }
                }
            }

            PopUp.showMessage("Sale added");
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }


    public static ObservableList<StoreSale> getAllStoreSalesData() {
        ObservableList<StoreSale> storeSales = FXCollections.observableArrayList();
        QUERY = "select * from store_sales";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                storeSales.add(new StoreSale(
                        rs.getInt(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getInt(3),
                        rs.getDouble(4)
                ));
            }
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return storeSales;       
    }

    public static void deleteStoreSale(StoreSale selectedItem) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {
            QUERY = String.format(
                    "select * from store_stock_unloads where sale_id = %d",
                    selectedItem.getId()
            );
            ResultSet rs = stmt.executeQuery(QUERY);
            HashMap<Integer, Double> unloads = new HashMap<>();
            while (rs.next()) {
                unloads.put(
                        rs.getInt(4),
                        rs.getDouble(5)
                );
            }

            //Increase store stocks
            for (Map.Entry<Integer, Double> entry : unloads.entrySet()) {
                QUERY = String.format(
                        "update store_stock set quantity=quantity+%f where id=%d",
                        entry.getValue(),
                        entry.getKey());
                stmt.execute(QUERY);
            }

            //Delete unloads
            QUERY = String.format(
                    "delete from store_stock_unloads where sale_id=%d",
                    selectedItem.getId()
            );
            stmt.execute(QUERY);


            if (selectedItem.getVehicleId() != 0) {
                QUERY = String.format(
                        "select * from v%d_stock_loads where store_sale_id = %d",
                        selectedItem.getVehicleId(),
                        selectedItem.getId()
                );
                ResultSet rs1 = stmt.executeQuery(QUERY);
                ArrayList<VehicleUnloads> vehicleUnloads = new ArrayList<>();
                while (rs1.next()) {
                    vehicleUnloads.add(new VehicleUnloads(
                            rs1.getInt(3),
                            rs1.getDouble(4),
                            rs1.getDouble(5)
                    ));
                }

                for (VehicleUnloads unload : vehicleUnloads) {
                    QUERY = String.format(
                            "update v%d_stock set quantity=quantity - %f where product_id=%d and unit_price = %f",
                            selectedItem.getVehicleId(),
                            unload.quantity,
                            unload.product_id,
                            unload.unitPrice);
                    stmt.execute(QUERY);
                }

                QUERY = String.format(
                        "delete from v%d_stock_loads where store_sale_id=%d",
                        selectedItem.getVehicleId(),
                        selectedItem.getId()
                );
                stmt.execute(QUERY);
            }
            //Delete store sale
            QUERY = String.format(
                    "delete from store_sales where id=%d",
                    selectedItem.getId()
            );
            stmt.execute(QUERY);

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }

    public static void moveVehicleStock(VehicleStock vehicleStock, double quantity, int vehicleId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            //Add to store stocks

            QUERY = String.format(
                    "select * from store_stock where product_id = %d and unit_price = %f",
                    vehicleStock.getProductId(),
                    vehicleStock.getUnitPrice()
            );
            ResultSet rs = stmt.executeQuery(QUERY);

            if (rs.next()) {
                QUERY = String.format(
                        "update store_stock set quantity = quantity + %f where id = %d",
                        quantity,
                        rs.getInt(1)
                );
                stmt.execute(QUERY);
            } else {
                QUERY = String.format(
                        "insert into store_stock (product_id, quantity, unit_price) values (%d, %f, %f)",
                        vehicleStock.getProductId(),
                        vehicleStock.getQuantity(),
                        vehicleStock.getUnitPrice());
                stmt.execute(QUERY);
            }
            rs.close();

            //Add to store loads
            QUERY = String.format(
                    "insert into store_stock_loads (date,product_id,quantity,unit_price,is_moved_from) values" +
                            "('%s', %d, %f, %f, %d)",
                    LocalDate.now(), vehicleStock.getProductId(), quantity, vehicleStock.getUnitPrice(), vehicleId
            );
            stmt.execute(QUERY, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            rs.next();
            int storeLoadId = rs.getInt(1);

            //Add to vehicle unloads
            QUERY = String.format(
                    "insert into v%d_stock_unloads (date,stock_id,quantity,unit_price,is_moved,store_stock_loads_id) " +
                            "values ('%s',%d,%f,%f,1,%d)",
                    vehicleId, LocalDate.now(), vehicleStock.getId(), quantity, vehicleStock.getUnitPrice(), storeLoadId
            );
            stmt.execute(QUERY);

            //Reduce vehicle stock
            QUERY = String.format(
                    "update v%d_stock set quantity = quantity - %f where id=%d",
                    vehicleId, quantity, vehicleStock.getId()
            );
            stmt.execute(QUERY);

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }

    public static ObservableList<VehicleStock> getAllVehicleStockData(int vehicleId, boolean remaining) {
        ObservableList<VehicleStock> vehicleStocks = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            if (remaining) {
                QUERY = String.format("select * from v%d_stock where quantity > 0", vehicleId);
            } else {
                QUERY = String.format("select * from v%d_stock", vehicleId);
            }
            ResultSet rs = stmt.executeQuery(QUERY);
            HashSet<Integer> productIds = new HashSet<>();
            while (rs.next()) {
                vehicleStocks.add(new VehicleStock(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                ));
                productIds.add(rs.getInt(2));
            }

            if (vehicleStocks.isEmpty()) {
                PopUp.showMessage("No stocks on the vehicle");
                return null;
            }

            ObservableList<Product> products = getNeededProductData(productIds);
            for (VehicleStock stock : vehicleStocks) {
                stock.setProduct(products.filtered(product -> product.getProductId() == stock.getProductId()).get(0));
            }

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return vehicleStocks;
    }

    public static ObservableList<VehicleStock> getNeededVehicleStockData(int vehicleId, HashSet<Integer> stockIds) {
        ObservableList<VehicleStock> vehicleStocks = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            String str = stockIds.toString();
            QUERY = String.format("select * from v%d_stock where id in (%s)",
                    vehicleId, str.substring(1, str.length() - 1));

            ResultSet rs = stmt.executeQuery(QUERY);
            HashSet<Integer> productIds = new HashSet<>();
            while (rs.next()) {
                vehicleStocks.add(new VehicleStock(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                ));
                productIds.add(rs.getInt(2));
            }

            ObservableList<Product> products = getNeededProductData(productIds);
            for (VehicleStock stock : vehicleStocks) {
                stock.setProduct(products.filtered(product -> product.getProductId() == stock.getProductId()).get(0));
            }

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return vehicleStocks;
    }

    public static ObservableList<VehicleStockLoad> getAllVehicleStockLoadsData(int vehicleId) {
        ObservableList<VehicleStockLoad> vehicleStockLoads = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            QUERY = String.format("select * from v%d_stock_loads", vehicleId);
            ResultSet rs = stmt.executeQuery(QUERY);
            HashSet<Integer> productIds = new HashSet<>();
            while (rs.next()) {
                vehicleStockLoads.add(new VehicleStockLoad(
                        rs.getInt(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getBoolean(6),
                        rs.getBoolean(7)
                ));
                productIds.add(rs.getInt(3));
            }

            ObservableList<Product> products = getNeededProductData(productIds);
            for (VehicleStockLoad load: vehicleStockLoads) {
                load.setProduct(products.filtered(product -> product.getProductId() == load.getProductId()).get(0));
            }

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }

        return vehicleStockLoads;
    }

    public static void addVehicleSale(int vehicleId, int customerId, LocalDate saleDate, ObservableList<VehicleSaleTableItem> saleItems, ObservableList<VehicleReturnTableItem> returnItems, double saleTotal, double returnTotal, double discounts, double credits) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {
            QUERY = String.format(
                "insert into V%d_sales (date, customer_id, sale_total, discount, returned, credit) " +
                "values ('%s', %d, %f, %f, %f, %f)",
                vehicleId,
                saleDate,
                customerId,
                saleTotal,
                discounts,
                returnTotal,
                credits
            );
            stmt.execute(QUERY, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int vehicle_sale_id = rs.getInt(1);

            for (VehicleSaleTableItem item : saleItems) {
                QUERY = String.format(
                    "insert into v%d_stock_unloads (date,stock_id,quantity,unit_price,is_sale,sale_id)" +
                    "values ('%s', %d, %f, %f, 1, %d)",
                    vehicleId,
                    saleDate,
                    item.getVehicleStock().getId(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    vehicle_sale_id
                );
                stmt.execute(QUERY);

                QUERY = String.format(
                    "update v%d_stock set quantity = quantity - %f where id = %d",
                    vehicleId,
                    item.getQuantity(),
                    item.getVehicleStock().getId()
                );
                stmt.execute(QUERY);

            }

            for (VehicleReturnTableItem item : returnItems) {
                if (!item.isDamaged()) {
                    QUERY = String.format("insert into v%d_stock_loads (date,product_id,quantity,unit_price,is_returned) values " +
                                    "('%s', %d, %f, %f, 1)",
                            vehicleId,
                            saleDate,
                            item.getProduct().getProductId(),
                            item.getQuantity(),
                            item.getUnitPrice());
                    stmt.execute(QUERY, Statement.RETURN_GENERATED_KEYS);
                    ResultSet rs1 = stmt.getGeneratedKeys();
                    rs1.next();
                    int vehicle_stock_load_id = rs1.getInt(1);

                    QUERY = String.format(
                            "insert into v%d_returns (sale_id,product_id,quantity,unit_price,is_damaged," +
                                    "v_stock_load_id) values (%d, %d, %f, %f, 0, %d)",
                            vehicleId,
                            vehicle_sale_id,
                            item.getProduct().getProductId(),
                            item.getQuantity(),
                            item.getUnitPrice(),
                            vehicle_stock_load_id
                    );
                    stmt.execute(QUERY);


                    QUERY = String.format(
                            "select * from v%d_stock where product_id = %d and unit_price = %f",
                            vehicleId,
                            item.getProduct().getProductId(),
                            item.getUnitPrice()
                    );
                    ResultSet rs2 = stmt.executeQuery(QUERY);

                    if (rs2.next()) {
                        QUERY = String.format(
                                "update v%d_stock set quantity = quantity + %f where id = %d",
                                vehicleId,
                                item.getQuantity(),
                                rs2.getInt(1)
                        );
                        stmt.execute(QUERY);
                    } else {
                        QUERY = String.format(
                                "insert into v%d_stock (product_id, quantity, unit_price) values (%d, %f, %f)",
                                vehicleId,
                                item.getProduct().getProductId(),
                                item.getQuantity(),
                                item.getUnitPrice());
                        stmt.execute(QUERY);
                    }
                } else {

                    QUERY = String.format(
                            "insert into v%d_returns (sale_id,product_id,quantity,unit_price,is_damaged) values " +
                                    "(%d, %d, %f, %f, 1)",
                            vehicleId,
                            vehicle_sale_id,
                            item.getProduct().getProductId(),
                            item.getQuantity(),
                            item.getUnitPrice()
                    );
                    stmt.execute(QUERY);

                    QUERY = String.format(
                        "insert into disposes (date, product_id,quantity,origin,remark) values " +
                        "('%s',%d,%f,%d,'Returned as damaged')",
                        saleDate,
                        item.getProduct().getProductId(),
                        item.getQuantity(),
                        vehicleId
                    );
                    stmt.execute(QUERY);
                }
            }

            if (credits != 0) {
                QUERY = String.format("insert into credits (date, customer_id, amount, sale_id) values " +
                        "('%s', %d, %f, %d)",
                        saleDate, customerId, credits, vehicle_sale_id);
                stmt.execute(QUERY);
            }

            PopUp.showMessage("Sale added");
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }

    public static ObservableList<VehicleSale> getAllVehicleSaleData(int vehicleId) {
        ObservableList<VehicleSale> vehicleSales = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            QUERY = String.format("select * from v%d_sales", vehicleId);
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                vehicleSales.add(new VehicleSale(
                        rs.getInt(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7)
                ));
            }

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return vehicleSales;
    }

    public static void deleteVehicleSale(int vehicleId, VehicleSale vehicleSale) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            QUERY = String.format(
                "select * from v%d_stock_unloads where is_sale = 1 and sale_id = %d",
                vehicleId,
                vehicleSale.getId()
            );
            ResultSet rs = stmt.executeQuery(QUERY);
            HashMap<Integer, Double> quantities = new HashMap<>();
            while (rs.next()) {
                quantities.put(rs.getInt(3), rs.getDouble(4));
            }

            for (Map.Entry<Integer, Double> entry : quantities.entrySet()) {
                QUERY = String.format("update v%d_stock set quantity = quantity + %f where id = %d",
                        vehicleId,
                        entry.getValue(),
                        entry.getKey());
                stmt.execute(QUERY);
            }
            QUERY = String.format(
                    "delete from v%d_stock_unloads where is_sale = 1 and sale_id = %d",
                    vehicleId,
                    vehicleSale.getId()
            );
            stmt.execute(QUERY);

            if (vehicleSale.getReturns() != 0) {
                QUERY = String.format("select * from v%d_returns where sale_id = %d", vehicleId, vehicleSale.getId());
                ResultSet rs1 = stmt.executeQuery(QUERY);

                ArrayList<VehicleReturns> returns = new ArrayList<>();
                while (rs1.next()) {
                    VehicleReturns ret = new VehicleReturns(
                            rs1.getBoolean(6),
                            rs1.getInt(3),
                            rs1.getDouble(4),
                            rs1.getDouble(5)
                    );
                    if (!ret.isDamaged) {
                        ret.vStockLoadId = rs1.getInt(7);
                    }

                    returns.add(ret);
                }

                QUERY = String.format("delete from v%d_returns where sale_id = %d", vehicleId, vehicleSale.getId());
                stmt.execute(QUERY);

                for (VehicleReturns ret : returns) {

                    if (ret.isDamaged) {
                        QUERY = String.format(
                                "delete from disposes where product_id = %d and quantity = %f and origin = %d",
                                ret.productId,
                                ret.quantity,
                                vehicleId
                        );
                        stmt.execute(QUERY);

                    } else {
                        QUERY = String.format("select * from v%d_stock_loads where id = %d",
                                vehicleId, ret.vStockLoadId);
                        ResultSet rs2 = stmt.executeQuery(QUERY);
                        ArrayList<VehicleUnloads> unloads = new ArrayList<>();
                        while (rs2.next()) {
                            unloads.add(new VehicleUnloads(
                                    rs2.getInt(3),
                                    rs2.getDouble(4),
                                    rs2.getDouble(5)
                            ));
                        }
                        for (VehicleUnloads unload : unloads) {
                            QUERY = String.format(
                                    "update v%d_stock set quantity = quantity - %f where product_id = %d and " +
                                            "unit_price = %f",
                                    vehicleId,
                                    unload.quantity,
                                    unload.product_id,
                                    unload.unitPrice
                            );
                            stmt.execute(QUERY);
                        }

                        QUERY = String.format(
                                "delete from v%d_stock_loads where id = %d",
                                vehicleId,
                                ret.vStockLoadId
                        );
                        stmt.execute(QUERY);
                    }
                }
            }

            QUERY = String.format("delete from v%d_sales where id = %d", vehicleId, vehicleSale.getId());
            stmt.execute(QUERY);

            if (vehicleSale.getCredits() != 0) {
                QUERY = String.format(
                        "delete from credits where date='%s' and customer_id=%d " +
                        "and amount =%f and sale_id=%d",
                        vehicleSale.getSaleDate(), vehicleSale.getCustomerId(), vehicleSale.getCredits(), vehicleSale.getId());
                stmt.execute(QUERY);
            }

            PopUp.showMessage("Vehicle Sale deleted");
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }

    public static ArrayList<Collection> viewVehicleSale(int vehicleId, VehicleSale selectedItem) {
        ArrayList<Collection> collections = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            QUERY = String.format(
                "select * from v%d_stock_unloads where sale_id = %d",
                vehicleId, selectedItem.getId()
            );
            ResultSet rs = stmt.executeQuery(QUERY);
            ArrayList<SaleUnloads> unloads = new ArrayList<>();
            HashSet<Integer> stockIds = new HashSet<>();
            while (rs.next()) {
                unloads.add(new SaleUnloads(
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getDouble(5)
                ));
                stockIds.add(rs.getInt(3));
            }

            ObservableList<VehicleStock> vehicleStocks = getNeededVehicleStockData(vehicleId, stockIds);
            ObservableList<VehicleSaleTableItem> saleItems = FXCollections.observableArrayList();
            for (SaleUnloads unload : unloads) {
                VehicleStock stock = vehicleStocks.filtered(item -> item.getId() == unload.stock_id).get(0);
                saleItems.add(new VehicleSaleTableItem(
                        stock,
                        unload.quantity,
                        unload.unit_price
                ));
            }

            collections.add(saleItems);

            if (selectedItem.getReturns() != 0) {
                QUERY = String.format(
                        "select * from v%d_returns where sale_id = %d",
                        vehicleId, selectedItem.getId()
                );
                rs = stmt.executeQuery(QUERY);
                ArrayList<VehicleSaleReturn> returns = new ArrayList<>();
                HashSet<Integer> productIds = new HashSet<>();
                while (rs.next()) {
                    returns.add( new VehicleSaleReturn(
                            rs.getInt(3),
                            rs.getDouble(4),
                            rs.getDouble(5),
                            rs.getBoolean(6)
                    ));
                    productIds.add(rs.getInt(3));
                }

                ObservableList<Product> products = getNeededProductData(productIds);
                ObservableList<VehicleReturnTableItem> returnTableItems = FXCollections.observableArrayList();
                for (VehicleSaleReturn item : returns) {
                    Product product = products.filtered(product1 -> product1.getProductId() == item.product_id).get(0);
                    returnTableItems.add(new VehicleReturnTableItem(
                            product,
                            item.quantity,
                            item.unit_price,
                            item.isDamaged
                    ));
                }
                collections.add(returnTableItems);
            }


        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }

        return collections;
    }
    public static ObservableList<StoreSaleTableItem> viewStoreSale(StoreSale storeSale) {
        ObservableList<StoreSaleTableItem> saleItems = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            QUERY = String.format(
                    "select * from store_stock_unloads where sale_id = %d",
                    storeSale.getId()
            );
            ResultSet rs = stmt.executeQuery(QUERY);
            ArrayList<SaleUnloads> unloads = new ArrayList<>();
            HashSet<Integer> stockIds = new HashSet<>();
            while (rs.next()) {
                unloads.add(new SaleUnloads(
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getDouble(6)
                ));
                stockIds.add(rs.getInt(4));
            }

            ObservableList<StoreStock> storeStocks = getNeededStoreStockData(stockIds);
            for (SaleUnloads unload : unloads) {
                StoreStock stock = storeStocks.filtered(item -> item.getStoreStockId() == unload.stock_id).get(0);
                saleItems.add(new StoreSaleTableItem(
                        stock,
                        unload.quantity,
                        unload.unit_price
                ));
            }

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }

        return saleItems;
    }

    private static ObservableList<StoreStock> getNeededStoreStockData(HashSet<Integer> stockIds) {
        ObservableList<StoreStock> storeStocks = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            String str = stockIds.toString();
            QUERY = String.format("select * from store_stock where id in (%s)",
                    str.substring(1, str.length() - 1));

            ResultSet rs = stmt.executeQuery(QUERY);
            HashSet<Integer> productIds = new HashSet<>();
            while (rs.next()) {
                storeStocks.add(new StoreStock(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                ));
                productIds.add(rs.getInt(2));
            }

            ObservableList<Product> products = getNeededProductData(productIds);
            for (StoreStock stock : storeStocks) {
                stock.setProduct(products.filtered(product -> product.getProductId() == stock.getProductId()).get(0));
            }

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return storeStocks;
    }

    public static Vehicle getVehicle(int vehicleId) {
        QUERY = String.format("select * from vehicles where id=%d", vehicleId);
        Vehicle vehicle = null;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            rs.next();

            vehicle = new Vehicle(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
            );

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return vehicle;
    }

    public static TreeItem<CreditTableItem> getCreditData(int vehicleId) {
        TreeItem<CreditTableItem> root = new TreeItem<>();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {

            LinkedList<Credit> credits = new LinkedList<>();
            HashSet<Integer> customerIds = new HashSet<>();
            QUERY = String.format("select id from customers where vehicle_id = %d", vehicleId);
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                customerIds.add(rs.getInt(1));
            }
            rs.close();

            String str = customerIds.toString();
            if (customerIds.isEmpty()) {
                PopUp.showMessage("No customers for this vehicle");
                return root;
            }
            QUERY = String.format("select * from credits where customer_id in (%s)", str.substring(1, str.length() - 1));
            rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                credits.add(new Credit(
                        rs.getInt(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getInt(5)
                ));
            }
            ObservableList<Customer> customers = getNeededCustomers(customerIds);
            for (Customer customer : customers) {

                CreditTableItem customerItem = new CreditTableItem(customer.getName());
                TreeItem<CreditTableItem> customerTreeItem = new TreeItem<>(customerItem);

                double balance = 0;
                for(Credit credit : credits) {
                    if (credit.getCustomerId() == customer.getCustomerId()) {
                        customerTreeItem.getChildren().add(new TreeItem<>(new CreditTableItem(
                                credit.getDate().toString(),
                                credit.getAmount()
                        )));
                        balance += credit.getAmount();
                    }
                }
                customerItem.setAmount(balance);
                customerItem.setCustomerId(customer.getCustomerId());
                root.getChildren().add(customerTreeItem);

            }
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return root;
    }

    private static ObservableList<Customer> getNeededCustomers(HashSet<Integer> customerIds) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String str = customerIds.toString();
        QUERY = String.format("select * from customers where id in (%s)", str.substring(1, str.length() - 1));

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                customers.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return customers;
    }

    public static void addCreditPayment(LocalDate date, int customerId, double amount) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {
            // Extract data from result set
            QUERY = String.format("insert into credits (date,customer_id,amount) values ('%s', %d, %f)",
                    date, customerId, amount * (-1));
            stmt.execute(QUERY);

            PopUp.showMessage("Payment added");
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }

    public static ReviewReturns reviewVehicle(LocalDate fromDate, LocalDate toDate, Vehicle vehicle) {
        ReviewReturns values = new ReviewReturns();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {
            if (fromDate == null && toDate != null){
                QUERY = String.format("select * from v%d_sales where date <= '%s'", vehicle.getId(), toDate);
            } else if (fromDate != null && toDate == null) {
                QUERY = String.format("select * from v%d_sales where date > '%s'", vehicle.getId(), fromDate);
            } else if (fromDate != null) {
                QUERY = String.format(
                    "select * from v%d_sales where date <= '%s' and date > '%s'",
                    vehicle.getId(),
                    toDate,
                    fromDate
                );
            } else {
                QUERY = String.format(
                        "select * from v%d_sales",
                        vehicle.getId()
                );
            }
            double saleTotal = 0, discounts = 0, returns = 0, credit = 0;
            HashSet<Integer> saleIds = new HashSet<>();

            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                saleTotal += rs.getDouble(4);
                discounts += rs.getDouble(5);
                returns += rs.getDouble(6);
                credit += rs.getDouble(7);

                saleIds.add(rs.getInt(1));
            }
            rs.close();

            values.setSaleTotal(saleTotal);
            values.setDiscounts(discounts);
            values.setReturns(returns);
            values.setCredits(credit);

            if (fromDate == null && toDate != null){
                QUERY = String.format("select * from v%d_stock_unloads where date <= '%s'", vehicle.getId(), toDate);
            } else if (fromDate != null && toDate == null) {
                QUERY = String.format("select * from v%d_stock_unloads where date > '%s'", vehicle.getId(), fromDate);
            } else if (fromDate != null) {
                QUERY = String.format("select * from v%d_stock_unloads where date <= '%s' and date > '%s'",
                        vehicle.getId(),
                        toDate,
                        fromDate);
            } else {
                QUERY = String.format("select * from v%d_stock_unloads",vehicle.getId());
            }

            rs = stmt.executeQuery(QUERY);

            ObservableList<VehicleUnload> vehicleUnloads = FXCollections.observableArrayList();
            HashSet<Integer> vehicleStockIds = new HashSet<>();
            while (rs.next()) {
                vehicleUnloads.add(new VehicleUnload(
                        rs.getInt(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getBoolean(6),
                        rs.getBoolean(8),
                        rs.getBoolean(10)
                ));
                vehicleStockIds.add(rs.getInt(3));
            }
            rs.close();

            ObservableList<VehicleReturn> returnedItems = FXCollections.observableArrayList();

            if (vehicleStockIds.isEmpty()) {
                PopUp.showMessage("Vehicle has no stocks");
                vehicleUnloads.clear();
            } else {
                ObservableList<VehicleStock> vehicleStocks = getNeededVehicleStockData(vehicle.getId(), vehicleStockIds);
                for (VehicleUnload unload : vehicleUnloads) {
                    unload.setVehicleStock(vehicleStocks.filtered(a -> a.getId() == unload.getStockId()).get(0));
                }
            }

            values.setUnloads(vehicleUnloads);

            HashSet<Integer> productIds = new HashSet<>();
            ObservableList<Product> products;

            String str = saleIds.toString();
            if (saleIds.isEmpty()) {
                PopUp.showMessage("Vehicle has no sales");
            } else {
                QUERY = String.format("select * from v%d_returns where id in (%s)", vehicle.getId(),
                        str.substring(1, (str.length() - 1)));
                rs = stmt.executeQuery(QUERY);

                while (rs.next()) {
                    returnedItems.add(new VehicleReturn(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getDouble(4),
                            rs.getDouble(5),
                            rs.getBoolean(6)
                    ));
                    productIds.add(rs.getInt(3));
                }
                rs.close();

                products = getNeededProductData(productIds);
                for (VehicleReturn item : returnedItems) {
                    item.setProduct(products.filtered(a -> a.getProductId() == item.getProductId()).get(0));
                }

            }

            values.setReturnedItems(returnedItems);

            if (fromDate == null && toDate != null){
                QUERY = String.format("select * from v%d_stock_loads where date <= '%s'", vehicle.getId(), toDate);
            } else if (fromDate != null && toDate == null) {
                QUERY = String.format("select * from v%d_stock_loads where date > '%s'", vehicle.getId(), fromDate);
            } else if (fromDate != null) {
                QUERY = String.format("select * from v%d_stock_loads where date <= '%s' and date > '%s'",
                        vehicle.getId(),
                        toDate,
                        fromDate);
            } else {
                QUERY = String.format("select * from v%d_stock_loads",vehicle.getId());
            }

            rs = stmt.executeQuery(QUERY);
            ObservableList<VehicleStockLoad> vehicleStockLoads = FXCollections.observableArrayList();
            while (rs.next()) {
                vehicleStockLoads.add(new VehicleStockLoad(
                        rs.getInt(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getBoolean(6),
                        rs.getBoolean(8)
                ));

                productIds.add(rs.getInt(3));
            }

            if (productIds.isEmpty()) {
                PopUp.showMessage("Vehicle has no stock loads");
                vehicleStockLoads.clear();
            } else {
                products = getNeededProductData(productIds);
                for (VehicleStockLoad load : vehicleStockLoads) {
                    load.setProduct(products.filtered(p -> p.getProductId() == load.getProductId()).get(0));
                }
            }
            values.setStockLoads(vehicleStockLoads);

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }

        return values;
    }

    private static class VehicleSaleReturn {
        private int product_id;
        private double quantity;
        private double unit_price;
        private boolean isDamaged;

        public VehicleSaleReturn(int product_id, double quantity, double unit_price, boolean isDamaged) {
            this.product_id = product_id;
            this.quantity = quantity;
            this.unit_price = unit_price;
            this.isDamaged = isDamaged;
        }
    }
    private static class SaleUnloads {
        private int stock_id;
        private double quantity;
        private double unit_price;

        public SaleUnloads(int stock_id, double quantity, double unit_price) {
            this.stock_id = stock_id;
            this.quantity = quantity;
            this.unit_price = unit_price;
        }
    }
    private static class VehicleReturns {
        private boolean isDamaged;
        private int vStockLoadId;
        private int productId;
        private double quantity;
        private double unitPrice;

        public VehicleReturns(boolean isDamaged, int productId, double quantity, double unitPrice) {
            this.isDamaged = isDamaged;
            this.productId = productId;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
    }

    private static class VehicleUnloads {
        private int product_id;
        private double quantity;
        private double unitPrice;
        VehicleUnloads(int product_id, double quantity, double unitPrice) {
            this.product_id = product_id;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
    }

    public static ObservableList<Vehicle> getAllVehicleData() {
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {

            QUERY = "select * from vehicles";
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                ));
            }

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
        return vehicles;
    }

    public static void addVehicle(String name, String driverName, String driverTp, String repName, String repTp) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {

            QUERY = String.format(
                    "insert into vehicles (name, driver_name, driver_tp, rep_name, rep_tp) values " +
                            "('%s', '%s', '%s', '%s', '%s')",
                    name,
                    driverName,
                    driverTp,
                    repName,
                    repTp
            );

            stmt.execute(QUERY, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int vehicle_id = rs.getInt(1);

            QUERY = String.format(
                    "CREATE TABLE `inventory_manager_db`.`v%1$d_stock` (\n" +
                    "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                    "  `product_id` INT UNSIGNED NOT NULL,\n" +
                    "  `quantity` DOUBLE NOT NULL,\n" +
                    "  `unit_price` DOUBLE NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  INDEX `v%1$d_stock_products_idx` (`product_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `v%1$d_stock_products`\n" +
                    "    FOREIGN KEY (`product_id`)\n" +
                    "    REFERENCES `inventory_manager_db`.`products` (`id`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE NO ACTION);\n",
                    vehicle_id);
            stmt.execute(QUERY);

            QUERY = String.format(
                    "CREATE TABLE `inventory_manager_db`.`v%1$d_sales` (\n" +
                            "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                            "  `date` DATE NOT NULL,\n" +
                            "  `customer_id` INT UNSIGNED NULL,\n" +
                            "  `sale_total` DOUBLE NOT NULL,\n" +
                            "  `discount` DOUBLE NOT NULL,\n" +
                            "  `returned` DOUBLE NOT NULL,\n" +
                            "  `credit` DOUBLE NOT NULL,\n" +
                            "  PRIMARY KEY (`id`),\n" +
                            "  INDEX `v%1$d_sales_customers_idx` (`customer_id` ASC) VISIBLE,\n" +
                            "  CONSTRAINT `v%1$d_sales_customers`\n" +
                            "    FOREIGN KEY (`customer_id`)\n" +
                            "    REFERENCES `inventory_manager_db`.`customers` (`id`)\n" +
                            "    ON DELETE CASCADE\n" +
                            "    ON UPDATE NO ACTION);",
                    vehicle_id);
            stmt.execute(QUERY);

            QUERY = String.format(
                    "CREATE TABLE `v%1$d_stock_loads` (\n" +
                            "  `id` int unsigned NOT NULL AUTO_INCREMENT,\n" +
                            "  `date` date NOT NULL,\n" +
                            "  `product_id` int unsigned NOT NULL,\n" +
                            "  `quantity` double NOT NULL,\n" +
                            "  `unit_price` double NOT NULL,\n" +
                            "  `is_from_store` tinyint DEFAULT '0',\n" +
                            "  `store_sale_id` int unsigned DEFAULT NULL,\n" +
                            "  `is_returned` tinyint DEFAULT '0',\n" +
                            "  `vehicle_sale_id` int unsigned DEFAULT NULL,\n" +
                            "  PRIMARY KEY (`id`),\n" +
                            "  KEY `v%1$d_stock_loads_products_idx` (`product_id`),\n" +
                            "  KEY `v%1$d_stock_loads_sales_idx` (`store_sale_id`),\n" +
                            "  KEY `v%1$d_stock_loads_vehicle_sales_idx` (`vehicle_sale_id`),\n" +
                            "  CONSTRAINT `v%1$d_stock_loads_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),\n" +
                            "  CONSTRAINT `v%1$d_stock_loads_sales` FOREIGN KEY (`store_sale_id`) REFERENCES `store_sales` (`id`),\n" +
                            "  CONSTRAINT `v%1$d_stock_loads_vehicle_sales` FOREIGN KEY (`vehicle_sale_id`) REFERENCES `v%1$d_sales` (`id`)\n" +
                            ")",
                    vehicle_id);
            stmt.execute(QUERY);



            QUERY = String.format(
                    "CREATE TABLE `inventory_manager_db`.`v%1$d_stock_unloads` (\n" +
                    "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                    "  `date` DATE NOT NULL,\n" +
                    "  `stock_id` INT UNSIGNED NOT NULL,\n" +
                    "  `quantity` DOUBLE NOT NULL,\n" +
                    "  `unit_price` DOUBLE NOT NULL,\n" +
                    "  `is_sale` TINYINT NULL DEFAULT 0,\n" +
                    "  `sale_id` INT UNSIGNED NULL,\n" +
                    "  `is_moved` TINYINT NULL DEFAULT 0,\n" +
                    "  `store_stock_loads_id` INT UNSIGNED NULL,\n" +
                    "  `is_disposed` TINYINT NULL DEFAULT 0,\n" +
                    "  `dispose_id` INT UNSIGNED NULL,\n" +
                    "  `remark` VARCHAR(255) NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  INDEX `v%1$d_stock_unloads_stocks_idx` (`stock_id` ASC) VISIBLE,\n" +
                    "  INDEX `v%1$d_stock_unloads_sales_idx` (`sale_id` ASC) VISIBLE,\n" +
                    "  INDEX `v%1$d_stock_unloads_store_stock_loads_idx` (`store_stock_loads_id` ASC) VISIBLE,\n" +
                    "  INDEX `v%1$d_stock_unloads_disposes_idx` (`dispose_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `v%1$d_stock_unloads_stocks`\n" +
                    "    FOREIGN KEY (`stock_id`)\n" +
                    "    REFERENCES `inventory_manager_db`.`v%1$d_stock` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `v%1$d_stock_unloads_sales`\n" +
                    "    FOREIGN KEY (`sale_id`)\n" +
                    "    REFERENCES `inventory_manager_db`.`v%1$d_sales` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `v%1$d_stock_unloads_store_stock_loads`\n" +
                    "    FOREIGN KEY (`store_stock_loads_id`)\n" +
                    "    REFERENCES `inventory_manager_db`.`store_stock_loads` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `v%1$d_stock_unloads_disposes`\n" +
                    "    FOREIGN KEY (`dispose_id`)\n" +
                    "    REFERENCES `inventory_manager_db`.`disposes` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);\n",
                    vehicle_id);
            stmt.execute(QUERY);

            QUERY = String.format(
                    "CREATE TABLE `inventory_manager_db`.`v%1$d_returns` (\n" +
                    "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                    "  `sale_id` INT UNSIGNED NOT NULL,\n" +
                    "  `product_id` INT UNSIGNED NOT NULL,\n" +
                    "  `quantity` DOUBLE NOT NULL,\n" +
                    "  `unit_price` DOUBLE NOT NULL,\n" +
                    "  `is_damaged` TINYINT NULL DEFAULT 0,\n" +
                    "  `v_stock_load_id` INT UNSIGNED NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  INDEX `v%1$d_returns_sales_idx` (`sale_id` ASC) VISIBLE,\n" +
                    "  INDEX `v%1$d_returns_products_idx` (`product_id` ASC) VISIBLE,\n" +
                    "  INDEX `v%1$d_returns_vehicle_loads_idx` (`v_stock_load_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `v%1$d_returns_sales`\n" +
                    "    FOREIGN KEY (`sale_id`)\n" +
                    "    REFERENCES `inventory_manager_db`.`v%1$d_sales` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `v%1$d_returns_products`\n" +
                    "    FOREIGN KEY (`product_id`)\n" +
                    "    REFERENCES `inventory_manager_db`.`products` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `v%1$d_returns_vehicle_loads`\n" +
                    "    FOREIGN KEY (`v_stock_load_id`)\n" +
                    "    REFERENCES `inventory_manager_db`.`v%1$d_stock_loads` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);\n",
                    vehicle_id);
            stmt.execute(QUERY);

        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }
    public static void changeVehicleDetails(VehicleDetails vehicleDetail, TableColumn.CellEditEvent<Vehicle, String> event) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();) {
            // Extract data from result set
            if (vehicleDetail == VehicleDetails.NAME) {
                QUERY = String.format(
                        "update vehicles set name='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getId());
            } else if (vehicleDetail == VehicleDetails.DRIVER_NAME) {
                QUERY = String.format(
                        "update vehicles set driver_name='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getId());
            } else if (vehicleDetail == VehicleDetails.DRIVER_TP) {
                QUERY = String.format(
                        "update vehicles set driver_tp='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getId());
            } else if (vehicleDetail == VehicleDetails.REP_NAME) {
                QUERY = String.format(
                        "update vehicles set rep_name='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getId());
            } else if (vehicleDetail == VehicleDetails.REP_TP) {
                QUERY = String.format(
                        "update vehicles set rep_tp='%s' where id=%d",
                        event.getNewValue(),
                        event.getRowValue().getId());
            }
            stmt.execute(QUERY);
        } catch (SQLException e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }
}
