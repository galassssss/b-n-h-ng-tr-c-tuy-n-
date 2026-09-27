package com.example.saleappv1.controller;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.example.saleappv1.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Yêu cầu 3 & 5: Hiển thị danh sách sản phẩm, hỗ trợ tìm kiếm và lọc
     * URL tham khảo:
     * - http://localhost:8080/products
     * - http://localhost:8080/products?categoryId=1
     * - http://localhost:8080/products?keyword=iphone
     * - http://localhost:8080/products?fromPrice=5000000&toPrice=20000000
     */
    @GetMapping("/products")
    public String products(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double fromPrice,
            @RequestParam(required = false) Double toPrice,
            Model model) {

        List<Product> products = productService.filterProducts(categoryId, keyword, fromPrice, toPrice);
        List<Category> categories = productService.getAllCategories();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("fromPrice", fromPrice);
        model.addAttribute("toPrice", toPrice);

        // Tìm category name nếu đang lọc theo categoryId
        if (categoryId != null) {
            productService.getCategoryById(categoryId).ifPresent(c -> model.addAttribute("selectedCategoryName", c.getName()));
        }

        return "products";
    }

    /**
     * Yêu cầu 4: Xem chi tiết sản phẩm
     * URL tham khảo: http://localhost:8080/products/{productId} (Ví dụ: /products/1)
     */
    @GetMapping("/products/{productId}")
    public String productDetail(@PathVariable("productId") Integer productId, Model model) {
        Optional<Product> productOpt = productService.getProductById(productId);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            model.addAttribute("product", product);

            // Lấy tên danh mục tương ứng
            productService.getCategoryById(product.getCategoryId())
                    .ifPresent(category -> model.addAttribute("category", category));

            return "product-detail";
        } else {
            // Trường hợp không tìm thấy sản phẩm
            model.addAttribute("errorMessage", "Không tìm thấy sản phẩm với mã: " + productId);
            return "redirect:/products";
        }
    }
}
