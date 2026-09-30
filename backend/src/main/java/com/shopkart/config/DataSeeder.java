package com.shopkart.config;

import com.shopkart.model.*;
import com.shopkart.repository.CategoryRepository;
import com.shopkart.repository.ProductRepository;
import com.shopkart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Seeds the database on first run with the same categories and products used
 * by the ShopKart frontend, plus one admin login, so the API is usable
 * immediately without any manual setup.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedCategories();
        seedProducts();
        seedAdmin();
    }

    private void seedCategories() {
        if (categoryRepository.count() > 0) return;

        List<Category> categories = List.of(
                Category.builder().slug("electronics").label("Electronics").imageUrl("https://images.unsplash.com/photo-1496181133206-80ce9b88a853?auto=format&fit=crop&w=300&q=80").build(),
                Category.builder().slug("fashion").label("Fashion").imageUrl("https://images.unsplash.com/photo-1445205170230-053b83016050?auto=format&fit=crop&w=300&q=80").build(),
                Category.builder().slug("home").label("Home & Kitchen").imageUrl("https://images.unsplash.com/photo-1555041469-a586c61ea9bc?auto=format&fit=crop&w=300&q=80").build(),
                Category.builder().slug("beauty").label("Beauty").imageUrl("https://images.unsplash.com/photo-1596462502278-27bfdc403348?auto=format&fit=crop&w=300&q=80").build(),
                Category.builder().slug("sports").label("Sports").imageUrl("https://images.unsplash.com/photo-1461896836934-ffe607ba8211?auto=format&fit=crop&w=300&q=80").build(),
                Category.builder().slug("books").label("Books").imageUrl("https://images.unsplash.com/photo-1495446815901-a7297e633e8d?auto=format&fit=crop&w=300&q=80").build(),
                Category.builder().slug("toys").label("Toys").imageUrl("https://images.unsplash.com/photo-1594736797933-d0501ba2fe65?auto=format&fit=crop&w=300&q=80").build(),
                Category.builder().slug("grocery").label("Grocery").imageUrl("https://images.unsplash.com/photo-1542838132-92c53300491e?auto=format&fit=crop&w=300&q=80").build()
        );

        categoryRepository.saveAll(categories);
    }

    private void seedProducts() {
        Map<String, Category> bySlug = categoryRepository.findAll().stream()
                .collect(java.util.stream.Collectors.toMap(Category::getSlug, c -> c));

        long existing = productRepository.count();
        if (existing >= 120) return;

        String[][] catalog = {
                {"electronics", "Wireless Noise-Cancelling Headphones", "4999", "7999"},
                {"electronics", "Smart Fitness Watch Series 5", "3299", "5499"},
                {"electronics", "Portable Bluetooth Speaker", "1899", "2999"},
                {"electronics", "27-inch 4K Monitor", "15999", "21999"},
                {"electronics", "Mechanical Gaming Keyboard", "2499", "3999"},
                {"electronics", "Wireless Gaming Mouse", "1299", "1999"},
                {"electronics", "USB-C Fast Charger 65W", "1799", "2699"},
                {"electronics", "Premium Power Bank 20000mAh", "1499", "2299"},
                {"electronics", "Smart LED Desk Lamp", "999", "1699"},
                {"electronics", "True Wireless Earbuds Pro", "2799", "4499"},
                {"electronics", "HD Webcam with Mic", "1899", "2999"},
                {"electronics", "Laptop Cooling Stand", "899", "1399"},
                {"electronics", "Mini Projector Full HD", "6499", "8999"},
                {"electronics", "Smart Home Wi-Fi Plug", "699", "1099"},
                {"electronics", "Noise Reduction Neckband", "1199", "1899"},
                {"fashion", "Men's Classic Fit Cotton Shirt", "799", "1499"},
                {"fashion", "Women's Running Shoes", "2199", "3499"},
                {"fashion", "Laptop Backpack Water Resistant", "1499", "2499"},
                {"fashion", "Slim Fit Denim Jacket", "1799", "2999"},
                {"fashion", "Women's Casual Handbag", "1299", "2199"},
                {"fashion", "Men's Premium Polo T-Shirt", "699", "1199"},
                {"fashion", "Women's Printed Kurti", "899", "1599"},
                {"fashion", "Classic Leather Wallet", "599", "999"},
                {"fashion", "Unisex Oversized Hoodie", "1099", "1899"},
                {"fashion", "Women's Everyday Sneakers", "1599", "2499"},
                {"fashion", "Men's Formal Trousers", "999", "1799"},
                {"fashion", "Aviator Sunglasses", "799", "1399"},
                {"fashion", "Canvas Casual Shoes", "1399", "2299"},
                {"fashion", "Premium Cotton Saree", "1899", "2999"},
                {"fashion", "Travel Duffel Bag", "1199", "1999"},
                {"home", "Minimal Ceramic Coffee Mug Set", "599", "999"},
                {"home", "Stainless Steel Water Bottle 1L", "399", "699"},
                {"home", "Non-Stick Cookware Set 5 pcs", "1999", "3299"},
                {"home", "Soft Microfiber Bedsheet", "899", "1499"},
                {"home", "Modern Table Clock", "649", "999"},
                {"home", "Decorative Cushion Cover Set", "499", "799"},
                {"home", "Aroma Diffuser with LED", "1099", "1799"},
                {"home", "Kitchen Storage Container Set", "799", "1299"},
                {"home", "Premium Bath Towel Set", "699", "1199"},
                {"home", "LED Fairy String Lights", "349", "599"},
                {"home", "Wooden Serving Tray", "749", "1199"},
                {"home", "Ceramic Plant Pot Set", "599", "999"},
                {"home", "Memory Foam Pillow", "999", "1599"},
                {"home", "Laundry Organizer Basket", "649", "999"},
                {"home", "Modern Wall Art Set", "1299", "2199"},
                {"beauty", "Beauty Essentials Makeup Set", "899", "1599"},
                {"beauty", "Organic Face Serum Set", "749", "1299"},
                {"beauty", "Vitamin C Face Wash", "399", "699"},
                {"beauty", "Hydrating Sheet Mask Pack", "499", "799"},
                {"beauty", "Matte Lipstick Collection", "699", "1199"},
                {"beauty", "Daily Skin Care Combo", "1099", "1899"},
                {"beauty", "Hair Repair Shampoo", "449", "699"},
                {"beauty", "Professional Makeup Brush Set", "599", "999"},
                {"beauty", "Aloe Vera Moisturizer", "379", "599"},
                {"beauty", "Sunscreen SPF 50", "549", "899"},
                {"beauty", "Perfume Gift Set", "1299", "1999"},
                {"beauty", "Rose Face Toner", "429", "699"},
                {"beauty", "Under Eye Care Cream", "599", "999"},
                {"beauty", "Body Care Essentials Kit", "899", "1499"},
                {"beauty", "Electric Facial Cleansing Brush", "799", "1299"},
                {"sports", "Professional Badminton Racquet", "1299", "2199"},
                {"sports", "Yoga Mat with Carry Strap", "549", "899"},
                {"sports", "Adjustable Dumbbell Set", "2499", "3999"},
                {"sports", "Football Training Ball", "699", "1099"},
                {"sports", "Cricket Bat English Willow", "3499", "4999"},
                {"sports", "Resistance Band Set", "499", "799"},
                {"sports", "Sports Water Bottle", "449", "699"},
                {"sports", "Running Waist Bag", "399", "649"},
                {"sports", "Skipping Rope Pro", "299", "499"},
                {"sports", "Fitness Hand Grip Set", "249", "399"},
                {"sports", "Tennis Ball Pack", "349", "549"},
                {"sports", "Cycling Gloves", "599", "999"},
                {"sports", "Gym Training Gloves", "549", "899"},
                {"sports", "Foam Roller Recovery Kit", "899", "1499"},
                {"sports", "Sports Backpack", "999", "1599"},
                {"books", "Bestselling Fiction Novel Set", "899", "1499"},
                {"books", "Java Programming Complete Guide", "699", "999"},
                {"books", "Modern Web Development", "799", "1199"},
                {"books", "Database Systems Handbook", "749", "1099"},
                {"books", "English Communication Skills", "399", "599"},
                {"books", "Interview Preparation Guide", "499", "799"},
                {"books", "Clean Code Essentials", "599", "899"},
                {"books", "Design Patterns Explained", "649", "999"},
                {"books", "Digital Marketing Basics", "449", "699"},
                {"books", "Python for Beginners", "549", "849"},
                {"books", "Data Structures Made Easy", "699", "999"},
                {"books", "Machine Learning Starter Book", "799", "1199"},
                {"books", "Business Success Stories", "499", "799"},
                {"books", "Personal Finance Simplified", "399", "599"},
                {"books", "Travel Photography Ideas", "599", "899"},
                {"toys", "Kids' Building Blocks Set", "699", "1199"},
                {"toys", "Remote Control Racing Car", "1199", "1999"},
                {"toys", "Educational Puzzle Board", "499", "799"},
                {"toys", "Musical Learning Keyboard", "999", "1599"},
                {"toys", "Creative Art Kit for Kids", "549", "899"},
                {"toys", "Dinosaur Action Figure Set", "649", "999"},
                {"toys", "Magnetic Construction Blocks", "899", "1399"},
                {"toys", "Soft Teddy Bear", "599", "999"},
                {"toys", "Kids Doctor Play Set", "449", "699"},
                {"toys", "Remote Control Helicopter", "1499", "2299"},
                {"toys", "Wooden Alphabet Puzzle", "349", "549"},
                {"toys", "Mini Kitchen Pretend Set", "799", "1199"},
                {"toys", "Science Experiment Kit", "999", "1499"},
                {"toys", "Building Robot STEM Kit", "1299", "1999"},
                {"toys", "Kids Indoor Bowling Set", "599", "899"},
                {"grocery", "Premium Assorted Dry Fruits Box", "649", "999"},
                {"grocery", "Organic Basmati Rice 5kg", "599", "749"},
                {"grocery", "Cold Pressed Groundnut Oil", "499", "649"},
                {"grocery", "Green Tea Premium Pack", "299", "449"},
                {"grocery", "Arabica Coffee Beans", "549", "799"},
                {"grocery", "Organic Honey 500g", "399", "599"},
                {"grocery", "Mixed Nuts Family Pack", "799", "1099"},
                {"grocery", "Whole Wheat Pasta", "249", "349"},
                {"grocery", "Breakfast Oats Family Pack", "299", "449"},
                {"grocery", "Dark Chocolate Collection", "449", "699"},
                {"grocery", "Organic Peanut Butter", "349", "499"},
                {"grocery", "Masala Spice Combo", "399", "599"},
                {"grocery", "Healthy Granola Mix", "449", "649"},
                {"grocery", "Premium Green Coffee", "599", "899"},
                {"grocery", "Fresh Fruit Snack Box", "499", "699"}
        };

        int index = 1;
        for (String[] item : catalog) {
            String slug = item[0];
            String title = item[1];
            if (productRepository.findByTitleContainingIgnoreCaseAndActiveTrue(title).stream().findAny().isPresent()) {
                index++;
                continue;
            }
            double price = Double.parseDouble(item[2]);
            double mrp = Double.parseDouble(item[3]);
            double rating = 4.0 + ((index * 7) % 10) / 10.0;
            int ratingCount = 250 + ((index * 137) % 4200);
            String badge = index % 11 == 0 ? "Bestseller" : index % 7 == 0 ? "Deal" : index % 13 == 0 ? "New" : null;
            String image = "https://picsum.photos/seed/shopkart-product-" + index + "/700/700";
            Product p = product(title, bySlug.get(slug), price, mrp, rating, ratingCount, image, badge);
            productRepository.save(p);
            index++;
        }
    }

    private Product product(String title, Category category, double price, double mrp, double rating,
                             int ratingCount, String image, String badge) {
        return Product.builder()
                .title(title)
                .category(category)
                .price(BigDecimal.valueOf(price))
                .mrp(BigDecimal.valueOf(mrp))
                .rating(rating)
                .ratingCount(ratingCount)
                .imageUrl(image)
                .badge(badge)
                .stock(100)
                .active(true)
                .build();
    }

    private void seedAdmin() {
        String adminEmail = "admin@shopkart.com";
        if (userRepository.existsByEmail(adminEmail)) return;

        User admin = User.builder()
                .fullName("ShopKart Admin")
                .email(adminEmail)
                .password(passwordEncoder.encode("Admin@123"))
                .mobile("9999999999")
                .role(Role.ADMIN)
                .active(true)
                .build();

        userRepository.save(admin);
    }
}
