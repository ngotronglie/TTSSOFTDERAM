package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CartRequestDTO;
import com.example.backend.dto.CartDetailDTO;
import com.example.backend.entity.Cart;
import com.example.backend.entity.Product;
import com.example.backend.repository.CartRepository;
import com.example.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ApiResponse<List<Cart>> findAll() {
        List<Cart> cart = cartRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách gio hang thành công", LocalDateTime.now(), cart, null);
    }

    @Override
    public ApiResponse<Cart> findById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);

        if (cart == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy gio hang");
            return new ApiResponse<>("error", "Không tìm thấy gio hang", LocalDateTime.now(), null, errorMessages);
        }

        return new ApiResponse<>("success", "Thành phố được tìm thấy", LocalDateTime.now(), cart, null);
    }

    @Override
    public ApiResponse<Cart> save(Cart cart) {
        Cart savedCart = cartRepository.save(cart);
        return new ApiResponse<>("success", "Tạo gio hang thành công", LocalDateTime.now(), savedCart, null);
    }

    @Override
    public ApiResponse<Cart> update(Long id, Cart cart) {
        Cart existing = cartRepository.findById(id).orElse(null);
        if (existing == null) {
            // Tạo một danh sách lỗi thay vì chỉ một chuỗi lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy gio hang với id: " + id);
            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy gio hang", LocalDateTime.now(), null, errorMessages);
        }

        existing.setUser_id(cart.getUser_id());
        existing.setProduct_id(cart.getProduct_id());
        existing.setQuantity(cart.getQuantity());
        existing.setUpdated_at( LocalDateTime.now());
        cartRepository.save(existing);

        return new ApiResponse<>("success", "Cập nhật gio hang thành công", LocalDateTime.now(), existing, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        Cart existing = cartRepository.findById(id).orElse(null);
        if (existing == null) {
            // Tạo một danh sách lỗi thay vì chỉ một chuỗi lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy gio hang với id: " + id);
            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy gio hang", LocalDateTime.now(), null, errorMessages);
        }
        cartRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa gio hang thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }

    @Override
    public ApiResponse<Cart> addToCart(CartRequestDTO cartRequest) {
        try {
            // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
            Optional<Cart> existingCartItem = cartRepository.findAll().stream()
                    .filter(cart -> cart.getUser_id() == cartRequest.getUserId() 
                            && cart.getProduct_id() == cartRequest.getProductId())
                    .findFirst();

            if (existingCartItem.isPresent()) {
                // Nếu đã có, cập nhật số lượng
                Cart cart = existingCartItem.get();
                cart.setQuantity(cart.getQuantity() + cartRequest.getQuantity());
                cart.setUpdated_at(LocalDateTime.now());
                Cart updated = cartRepository.save(cart);
                return new ApiResponse<>("success", "Cập nhật giỏ hàng thành công", 
                        LocalDateTime.now(), updated, null);
            } else {
                // Nếu chưa có, tạo mới
                Cart newCart = new Cart();
                newCart.setUser_id(cartRequest.getUserId());
                newCart.setProduct_id(cartRequest.getProductId());
                newCart.setQuantity(cartRequest.getQuantity());
                newCart.setCreated_at(LocalDateTime.now());
                newCart.setUpdated_at(LocalDateTime.now());
                
                Cart saved = cartRepository.save(newCart);
                return new ApiResponse<>("success", "Thêm vào giỏ hàng thành công", 
                        LocalDateTime.now(), saved, null);
            }
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add("Lỗi khi thêm vào giỏ hàng: " + e.getMessage());
            return new ApiResponse<>("error", "Lỗi khi thêm vào giỏ hàng", 
                    LocalDateTime.now(), null, errors);
        }
    }

    @Override
    public ApiResponse<Cart> updateCartItem(CartRequestDTO cartRequest) {
        try {
            Optional<Cart> existingCartItem = cartRepository.findAll().stream()
                    .filter(cart -> cart.getUser_id() == cartRequest.getUserId() 
                            && cart.getProduct_id() == cartRequest.getProductId())
                    .findFirst();

            if (existingCartItem.isPresent()) {
                Cart cart = existingCartItem.get();
                cart.setQuantity(cartRequest.getQuantity());
                cart.setUpdated_at(LocalDateTime.now());
                Cart updated = cartRepository.save(cart);
                return new ApiResponse<>("success", "Cập nhật giỏ hàng thành công", 
                        LocalDateTime.now(), updated, null);
            } else {
                List<String> errors = new ArrayList<>();
                errors.add("Không tìm thấy sản phẩm trong giỏ hàng");
                return new ApiResponse<>("error", "Không tìm thấy sản phẩm", 
                        LocalDateTime.now(), null, errors);
            }
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add("Lỗi khi cập nhật giỏ hàng: " + e.getMessage());
            return new ApiResponse<>("error", "Lỗi khi cập nhật giỏ hàng", 
                    LocalDateTime.now(), null, errors);
        }
    }

    @Override
    public ApiResponse<String> removeFromCart(Integer userId, Integer productId) {
        try {
            Optional<Cart> existingCartItem = cartRepository.findAll().stream()
                    .filter(cart -> cart.getUser_id() == userId 
                            && cart.getProduct_id() == productId)
                    .findFirst();

            if (existingCartItem.isPresent()) {
                cartRepository.delete(existingCartItem.get());
                return new ApiResponse<>("success", "Xóa sản phẩm khỏi giỏ hàng thành công", 
                        LocalDateTime.now(), "Đã xóa sản phẩm", null);
            } else {
                List<String> errors = new ArrayList<>();
                errors.add("Không tìm thấy sản phẩm trong giỏ hàng");
                return new ApiResponse<>("error", "Không tìm thấy sản phẩm", 
                        LocalDateTime.now(), null, errors);
            }
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add("Lỗi khi xóa sản phẩm khỏi giỏ hàng: " + e.getMessage());
            return new ApiResponse<>("error", "Lỗi khi xóa sản phẩm", 
                    LocalDateTime.now(), null, errors);
        }
    }

    @Override
    public ApiResponse<List<CartDetailDTO>> getUserCart(Integer userId) {
        try {
            // Lấy danh sách giỏ hàng của user
            List<Cart> userCart = cartRepository.findAll().stream()
                    .filter(cart -> cart.getUser_id() == userId)
                    .collect(Collectors.toList());

            if (userCart.isEmpty()) {
                return new ApiResponse<>("success", "Giỏ hàng trống", 
                        LocalDateTime.now(), new ArrayList<>(), null);
            }

            // Chuyển đổi Cart thành CartDetailDTO
            List<CartDetailDTO> cartDetails = new ArrayList<>();
            for (Cart cart : userCart) {
                Product product = productRepository.findById(Long.valueOf(cart.getProduct_id())).orElse(null);
                if (product != null) {
                    CartDetailDTO cartDetail = new CartDetailDTO(
                        cart.getId_cart(),
                        Integer.valueOf(cart.getUser_id()),
                        Integer.valueOf(cart.getProduct_id()),
                        product.getImage(),
                        product.getName(),
                        Integer.valueOf(cart.getQuantity()),
                        product.getPrice()
                    );
                    cartDetails.add(cartDetail);
                }
            }

            return new ApiResponse<>("success", "Lấy giỏ hàng thành công", 
                    LocalDateTime.now(), cartDetails, null);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add("Lỗi khi lấy giỏ hàng: " + e.getMessage());
            return new ApiResponse<>("error", "Lỗi khi lấy giỏ hàng", 
                    LocalDateTime.now(), null, errors);
        }
    }

    @Override
    public ApiResponse<String> clearUserCart(Integer userId) {
        try {
            // Lấy tất cả sản phẩm trong giỏ hàng của user
            List<Cart> userCart = cartRepository.findAll().stream()
                    .filter(cart -> cart.getUser_id() == userId)
                    .collect(Collectors.toList());

            if (userCart.isEmpty()) {
                return new ApiResponse<>("success", "Giỏ hàng đã trống", 
                        LocalDateTime.now(), "Không có sản phẩm nào trong giỏ hàng", null);
            }

            // Xóa tất cả sản phẩm trong giỏ hàng
            for (Cart cart : userCart) {
                cartRepository.delete(cart);
            }

            return new ApiResponse<>("success", "Xóa giỏ hàng thành công", 
                    LocalDateTime.now(), "Đã xóa " + userCart.size() + " sản phẩm khỏi giỏ hàng", null);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add("Lỗi khi xóa giỏ hàng: " + e.getMessage());
            return new ApiResponse<>("error", "Lỗi khi xóa giỏ hàng", 
                    LocalDateTime.now(), null, errors);
        }
    }

    @Override
    public ApiResponse<Long> countUserCartItems(Integer userId) {
        try {
            long totalQuantity = cartRepository.findAll().stream()
                    .filter(cart -> cart.getUser_id() == userId)
                    .mapToLong(Cart::getQuantity)
                    .sum();
            
            return new ApiResponse<>("success", "Đếm tổng số lượng sản phẩm trong giỏ hàng thành công", 
                    LocalDateTime.now(), totalQuantity, null);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add("Lỗi khi đếm số lượng sản phẩm trong giỏ hàng: " + e.getMessage());
            return new ApiResponse<>("error", "Lỗi khi đếm số lượng sản phẩm trong giỏ hàng", 
                    LocalDateTime.now(), null, errors);
        }
    }
}
