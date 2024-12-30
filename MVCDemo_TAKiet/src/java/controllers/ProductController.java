/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.CategoryDAO;
import dao.ProductDAO;
import dto.CreateProductDTO;
import dto.SearchProductDTO;
import entities.Category;
import entities.Product;
import exceptions.InvalidDataException;
import exceptions.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vothimaihoa
 */
public class ProductController extends HttpServlet {

    private final String LIST = "Product";
    private final String PREPARE_CREATE = "Product?action=prepare-add";
    private final String PREPARE_UPDATE = "Product?action=prepare-update";
    private final String LIST_VIEW = "view/product/list.jsp";
    private final String CREATE_VIEW = "view/product/create.jsp";
    private final String UPDATE_VIEW = "view/product/update.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        String action = request.getParameter("action");
        if (action == null) {
            list(request, response, categoryDAO, productDAO);
        } else {
            switch (action) {
                case "prepare-add":
                    prepareAdd(request, response, categoryDAO);
                    break;
                case "add":
                    add(request, response, categoryDAO, productDAO);
                    break;
                case "prepare-update":
                    prepareUpdate(request, response, productDAO, categoryDAO);
                    break;
                case "update":
                    update(request, response);
                    break;
                case "delete":
                    delete(request, response, productDAO);
                default:
                    list(request, response, categoryDAO, productDAO);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void list(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO, ProductDAO productDAO)
            throws ServletException, IOException {
        try {
            // get category list for drop down
            List<Category> categories = categoryDAO.list();

            request.setAttribute("categories", categories);

            // get search criterias
            String categoryIdRaw = request.getParameter("category");
            String productName = request.getParameter("productName");
            String priceCategory = request.getParameter("priceCategory");
            request.setAttribute("priceCategory", priceCategory);
            // validate search fields only when search criterias a string
            Integer categoryId = null;
            if (categoryIdRaw != null && !categoryIdRaw.isEmpty()) {
                categoryId = Integer.parseInt(categoryIdRaw);
            }
            SearchProductDTO searchDTO = new SearchProductDTO(categoryIdRaw, productName);
            searchDTO.validate();

            // get search data
            List<Product> list = productDAO.list(productName, categoryId, priceCategory);
            if (list != null && !list.isEmpty()) {
                request.setAttribute("products", list);
            } else {
                throw new InvalidDataException("No Products Found!");
            }

            // hold search criteria on search bar for next request
            request.setAttribute("productName", productName);
            request.setAttribute("category", categoryIdRaw);
            request.setAttribute("priceCategory", priceCategory);

        } //      catch (ValidationException ex) {
        //            request.setAttribute("validationErrors", ex.getErrors());
        //      } 
        catch (ValidationException | InvalidDataException ex) {
            request.setAttribute("msg", ex.getMessage());
        } finally {
            if (!response.isCommitted()) {
                request.getRequestDispatcher(LIST_VIEW).forward(request, response);
            }
        }
    }

    private void prepareAdd(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO) throws ServletException, IOException {
        List<Category> categories = categoryDAO.list();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher(CREATE_VIEW).forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO, ProductDAO productDAO) throws ServletException, IOException {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String productYear = request.getParameter("productYear");
        String image = request.getParameter("image");
        String categoryId = request.getParameter("category");

        CreateProductDTO productDTO = new CreateProductDTO(name, price, productYear, image, categoryId);
        try {
            productDTO.validate();

            Category category = categoryDAO.getById(Integer.parseInt(categoryId));
            if (category == null) {
                throw new InvalidDataException("Category not found!");
            }

            // call DAO
            Product product = new Product(name, Float.parseFloat(price), Integer.parseInt(productYear), image, category);
            boolean isOk = productDAO.create(product);
            if (!isOk) {
                throw new InvalidDataException("Cannot save product to database!");
            } else {
                response.sendRedirect(LIST);
            }
        } //      catch (ValidationException ex) {
        //          request.setAttribute("validationErrors", ex.getErrors());
        //      } 
        catch (ValidationException | InvalidDataException ex) {
            request.setAttribute("msg", ex.getMessage());
            request.getRequestDispatcher(PREPARE_CREATE).forward(request, response);
        }
    }

    private void prepareUpdate(HttpServletRequest request, HttpServletResponse response, ProductDAO productDAO, CategoryDAO categoryDAO) throws ServletException, IOException {
        try {
            // Kiểm tra và lấy tham số ID từ request
            String productIdParam = request.getParameter("productId");
            if (productIdParam == null || productIdParam.isEmpty()) {
                throw new InvalidDataException("Product ID is missing!");
            }

            int productId = Integer.parseInt(productIdParam);

            // Truy vấn sản phẩm từ database
            Product product = productDAO.getById(productId);
            if (product == null) {
                throw new InvalidDataException("Product not found!");
            }

            // Truy vấn danh sách danh mục
            List<Category> categories = categoryDAO.getAll();

            // Gán sản phẩm và danh mục vào request
            request.setAttribute("product", product);
            request.setAttribute("categories", categories);

            // Forward đến trang JSP
            request.getRequestDispatcher(UPDATE_VIEW).forward(request, response);

        } catch (InvalidDataException e) {
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher(PREPARE_UPDATE).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("msg", "An unexpected error occurred!");
            request.getRequestDispatcher(PREPARE_UPDATE).forward(request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String productYear = request.getParameter("productYear");
        String image = request.getParameter("image");
        String categoryId = request.getParameter("category");

        try {
            // Xác thực dữ liệu đầu vào nếu cần (có thể sử dụng DTO hoặc tự làm validation)
            CreateProductDTO productDTO = new CreateProductDTO(name, price, productYear, image, categoryId);
            productDTO.validate();

            // Tìm kiếm category theo ID
            Category category = new CategoryDAO().getById(Integer.parseInt(categoryId));
            if (category == null) {
                throw new InvalidDataException("Category not found!");
            }

            // Tạo đối tượng Product với thông tin mới
            Product product = new Product(productId, name, Float.parseFloat(price), Integer.parseInt(productYear), image, category);

            // Cập nhật sản phẩm vào cơ sở dữ liệu
            ProductDAO productDAO = new ProductDAO();
            boolean isUpdated = productDAO.update(product);
            if (!isUpdated) {
                throw new InvalidDataException("Cannot update product!");
            }

            response.sendRedirect(LIST);

        } catch (ValidationException | InvalidDataException ex) {
            // Nếu có lỗi, truyền lỗi vào request và quay lại trang update
            request.setAttribute("msg", ex.getMessage());
            request.getRequestDispatcher(UPDATE_VIEW).forward(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response, ProductDAO productDAO) throws ServletException, IOException, ClassNotFoundException, SQLException, SQLException, SQLException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        boolean isOk = productDAO.delete(productId);

        if (!isOk) {
            throw new InvalidDataException("Cannot delete product!");
        } else {
            response.sendRedirect("Product");
        }
    }

}
