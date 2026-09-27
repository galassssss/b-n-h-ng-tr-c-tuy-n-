package com.example.saleappv1.service;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ObjectMapper objectMapper;
    private List<Product> products = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public ProductService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        loadCategories();
        loadProducts();
    }

    /**
     * Đọc danh mục từ tập tin JSON trong thư mục resources/data/categories.json
     */
    public void loadCategories() {
        try {
            ClassPathResource resource = new ClassPathResource("data/categories.json");
            try (InputStream inputStream = resource.getInputStream()) {
                this.categories = objectMapper.readValue(inputStream, new TypeReference<List<Category>>() {});
                logger.info("Đã tải thành công {} danh mục từ categories.json", categories.size());
            }
        } catch (Exception e) {
            logger.error("Lỗi khi đọc file categories.json: {}", e.getMessage(), e);
            this.categories = new ArrayList<>();
        }
    }

    /**
     * Đọc danh sách sản phẩm từ tập tin JSON trong thư mục resources/data/products.json
     */
    public void loadProducts() {
        try {
            ClassPathResource resource = new ClassPathResource("data/products.json");
            try (InputStream inputStream = resource.getInputStream()) {
                this.products = objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {});
                logger.info("Đã tải thành công {} sản phẩm từ products.json", products.size());
            }
        } catch (Exception e) {
            logger.error("Lỗi khi đọc file products.json: {}", e.getMessage(), e);
            this.products = new ArrayList<>();
        }
    }

    /**
     * Lấy toàn bộ danh sách sản phẩm
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Tìm sản phẩm theo id (Yêu cầu 4)
     */
    public Optional<Product> getProductById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        return products.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst();
    }

    /**
     * Lấy toàn bộ danh mục sản phẩm
     */
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    /**
     * Tìm danh mục theo id
     */
    public Optional<Category> getCategoryById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        return categories.stream()
                .filter(c -> id.equals(c.getId()))
                .findFirst();
    }

    /**
     * Lọc và tìm kiếm sản phẩm theo: danh mục, từ khóa tên, và khoảng giá (Yêu cầu 5)
     */
    public List<Product> filterProducts(Integer categoryId, String keyword, Double fromPrice, Double toPrice) {
        return products.stream()
                .filter(product -> {
                    // Lọc theo danh mục
                    if (categoryId != null && !categoryId.equals(product.getCategoryId())) {
                        return false;
                    }

                    // Tìm theo từ khóa (tên hoặc mô tả)
                    if (keyword != null && !keyword.trim().isEmpty()) {
                        String lowerKeyword = keyword.trim().toLowerCase();
                        boolean matchName = product.getName() != null && product.getName().toLowerCase().contains(lowerKeyword);
                        boolean matchDesc = product.getDescription() != null && product.getDescription().toLowerCase().contains(lowerKeyword);
                        if (!matchName && !matchDesc) {
                            return false;
                        }
                    }

                    // Tìm theo giá tối thiểu (fromPrice)
                    if (fromPrice != null && (product.getPrice() == null || product.getPrice() < fromPrice)) {
                        return false;
                    }

                    // Tìm theo giá tối đa (toPrice)
                    if (toPrice != null && (product.getPrice() == null || product.getPrice() > toPrice)) {
                        return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }
}
