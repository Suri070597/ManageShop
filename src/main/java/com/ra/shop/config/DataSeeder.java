package com.ra.shop.config;

import com.ra.shop.model.entity.*;
import com.ra.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create roles
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRoleName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setRoleName("ROLE_USER");
            roleRepository.save(userRole);
        }

        // Create admin user
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN").orElse(null);
            Role userRole = roleRepository.findByRoleName("ROLE_USER").orElse(null);

            // Admin user
            User admin = new User();
            admin.setFullName("Administrator");
            admin.setEmail("admin@shop.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setAddress("123 Admin Street");
            admin.setPhone("0123456789");
            admin.setStatus(true);
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            admin.setRoles(adminRoles);
            userRepository.save(admin);

            // Regular user
            User user = new User();
            user.setFullName("John Doe");
            user.setEmail("user@shop.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setAddress("456 User Street");
            user.setPhone("0987654321");
            user.setStatus(true);
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            user.setRoles(userRoles);
            userRepository.save(user);
        }

        // Create categories
        if (categoryRepository.count() == 0) {
            Category electronics = new Category();
            electronics.setName("Electronics");
            electronics.setStatus(true);
            categoryRepository.save(electronics);

            Category clothing = new Category();
            clothing.setName("Clothing");
            clothing.setStatus(true);
            categoryRepository.save(clothing);

            Category books = new Category();
            books.setName("Books");
            books.setStatus(true);
            categoryRepository.save(books);
        }

        // Create products
        if (productRepository.count() == 0) {
            Category electronics = categoryRepository.findAll().get(0);
            Category clothing = categoryRepository.findAll().get(1);
            Category books = categoryRepository.findAll().get(2);

            // Electronics products
            Product laptop = new Product();
            laptop.setName("Gaming Laptop");
            laptop.setPrice(new BigDecimal("999.99"));
            laptop.setStock(10);
            laptop.setCategory(electronics);
            laptop.setStatus(true);
            productRepository.save(laptop);

            Product phone = new Product();
            phone.setName("Smartphone");
            phone.setPrice(new BigDecimal("699.99"));
            phone.setStock(20);
            phone.setCategory(electronics);
            phone.setStatus(true);
            productRepository.save(phone);

            // Clothing products
            Product tshirt = new Product();
            tshirt.setName("Cotton T-Shirt");
            tshirt.setPrice(new BigDecimal("19.99"));
            tshirt.setStock(50);
            tshirt.setCategory(clothing);
            tshirt.setStatus(true);
            productRepository.save(tshirt);

            Product jeans = new Product();
            jeans.setName("Blue Jeans");
            jeans.setPrice(new BigDecimal("49.99"));
            jeans.setStock(30);
            jeans.setCategory(clothing);
            jeans.setStatus(true);
            productRepository.save(jeans);

            // Books products
            Product novel = new Product();
            novel.setName("Programming Book");
            novel.setPrice(new BigDecimal("39.99"));
            novel.setStock(15);
            novel.setCategory(books);
            novel.setStatus(true);
            productRepository.save(novel);
        }

        System.out.println("Sample data has been loaded successfully!");
        System.out.println("Admin credentials: admin@shop.com / admin123");
        System.out.println("User credentials: user@shop.com / user123");
    }
}
