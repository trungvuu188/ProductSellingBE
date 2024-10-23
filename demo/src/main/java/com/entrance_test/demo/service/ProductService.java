package com.entrance_test.demo.service;

import com.entrance_test.demo.dto.request.FilterProductRequest;
import com.entrance_test.demo.dto.request.ProductCreationRequest;
import com.entrance_test.demo.dto.request.ProductImageUpload;
import com.entrance_test.demo.dto.request.SaleProductRequest;
import com.entrance_test.demo.dto.response.*;
import com.entrance_test.demo.entity.*;
import com.entrance_test.demo.entity.keys.KeyProductColor;
import com.entrance_test.demo.entity.keys.KeyProductSale;
import com.entrance_test.demo.entity.keys.KeyProductSize;
import com.entrance_test.demo.exception.AppException;
import com.entrance_test.demo.exception.ErrorCode;
import com.entrance_test.demo.repository.*;
import com.entrance_test.demo.service.imp.ProductServiceImp;
import com.entrance_test.demo.service.imp.ProductSpecificationImp;
import com.entrance_test.demo.service.imp.StockServiceImp;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductService implements ProductServiceImp {

    StockServiceImp stockServiceImp;
    ProductSpecificationImp productSpecificationImp;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    SizeRepository sizeRepository;
    StyleRepository styleRepository;
    ColorRepository colorRepository;
    ProductColorRepository productColorRepository;
    ProductSizeRepository productSizeRepository;
    ProductImageRepository productImageRepository;
    RatingRepository ratingRepository;
    ProductSaleRepository productSaleRepository;
    SaleRepository saleRepository;

    @Override
    public ProductDTO productMapper(Product product) {

        List<ColorDTO> colorDTOS = new ArrayList<>();
        List<SizeDTO> sizeDTOS = new ArrayList<>();
        List<ProductImageDTO> productImageDTOS = new ArrayList<>();

        productImageRepository.findAllByProductProductId(product.getProductId()).forEach(productImage -> {
            ProductImageDTO productImageDTO = ProductImageDTO.builder()
                    .imageData(java.util.Base64.getDecoder().decode(productImage.getImageData()))
                    .isMainDisplay(productImage.isMainDisplay())
                    .build();
            productImageDTOS.add(productImageDTO);
        });

        productColorRepository.findAllByProductProductId(product.getProductId()).forEach(productColor -> {
            Color color = productColor.getColor();
            ColorDTO colorDTO = ColorDTO.builder()
                    .colorId(color.getColorId())
                    .colorName(color.getColorName())
                    .build();
            colorDTOS.add(colorDTO);
        });

        productSizeRepository.findAllByProductProductId(product.getProductId()).forEach(productSize -> {
            Size size = productSize.getSize();
            SizeDTO sizeDTO = SizeDTO.builder()
                    .sizeId(size.getSizeId())
                    .sizeName(size.getSizeName())
                    .build();
            sizeDTOS.add(sizeDTO);
        });

        return ProductDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productDesc(product.getProductDesc())
//                .productImageDTOS(productImageDTOS)
                .productColors(colorDTOS)
                .productSizes(sizeDTOS)
                .ratingStars(getAverageRating(product.getProductId()))
                .price(product.getProductPrice())
                .build();
    }

    @Override
    public List<ProductDTO> getAllProduct(FilterProductRequest filterProductRequest) {

        Specification<Product> filters = Specification
                .where(filterProductRequest.getCateId() == 0 ? null : productSpecificationImp.categoryLike(filterProductRequest.getCateId()))
                .and(filterProductRequest.getStyleId() == 0 ? null : productSpecificationImp.styleLike(filterProductRequest.getStyleId()))
                .and(filterProductRequest.getSizeId() == 0 ? null : productSpecificationImp.hasSizeInProduct(filterProductRequest.getSizeId()))
                .and(filterProductRequest.getColorId() == 0 ? null : productSpecificationImp.hasColorInProduct(filterProductRequest.getColorId()))
                .and(filterProductRequest.getPrice() == 0 ? null : productSpecificationImp.lessThanPrice(filterProductRequest.getPrice()));

        List<Product> products = productRepository.findAll(filters);
        List<ProductSaleApplied> productSaleApplieds = stockServiceImp.getListProductSale();
        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product product : products) {
            ProductDTO productDTO = productMapper(product);
            productSaleApplieds.forEach(productSaleApplied -> {
                if(productSaleApplied.getProductIds().contains(product.getProductId())) {
                    productDTO.setSalePrice(productSaleApplied.getAmountSale());
                    productDTO.setExpiredDate(productSaleApplied.getExpiredDate());
                }
            });
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public ProductDTO getProductById(int productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));
        ProductDTO productDTO = productMapper(product);
        productDTO.setSalePrice(stockServiceImp.getProductStockPrice(productId));
        productDTO.setExpiredDate(stockServiceImp.getProductSaleExpirationDate(productId));
        return productDTO;
    }

    @Override
    public List<ProductDTO> getProductsByCategory(int categoryId) {



        return null;
    }

    @Override
    public boolean addProduct(ProductCreationRequest productCreationRequest) {

        Category category = categoryRepository.findById(productCreationRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));

        Style style = styleRepository.findById(productCreationRequest.getStyleId())
                .orElseThrow(() -> new AppException(ErrorCode.STYLE_NOTFOUND));

        Product product = Product.builder()
                .productName(productCreationRequest.getProductName())
                .productDesc(productCreationRequest.getProductDesc())
                .style(style)
                .category(category)
                .productPrice(productCreationRequest.getPrice())
                .build();

        int productId = productRepository.save(product).getProductId();

        Set<ProductColor> productColors = new HashSet<>();
        for ( int colorId : productCreationRequest.getColor() ) {
            Color color = colorRepository.findById(colorId)
                    .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOTFOUND));

            KeyProductColor keyProductColor = KeyProductColor.builder()
                    .productId(productId)
                    .colorId(colorId)
                    .build();

            ProductColor productColor = ProductColor.builder()
                    .keyProductColor(keyProductColor)
                    .color(color)
                    .product(product)
                    .build();

            productColorRepository.save(productColor);
            productColors.add(productColor);
        }

        Set<ProductSize> productSizes = new HashSet<>();
        for ( int sizeId : productCreationRequest.getSize() ) {

            Size size = sizeRepository.findById(sizeId)
                    .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOTFOUND));

            KeyProductSize productColor = KeyProductSize.builder()
                    .productId(productId)
                    .sizeId(sizeId)
                    .build();

            ProductSize productSize = ProductSize.builder()
                    .keyProductSize(productColor)
                    .size(size)
                    .product(product)
                    .build();

            productSizeRepository.save(productSize);
            productSizes.add(productSize);
        }

        ProductImageUpload productImageUpload = ProductImageUpload.builder()
                .mainDisplayImage(productCreationRequest.getMainDisplayImage())
                .descImage(productCreationRequest.getDescImage())
                .build();
        updateProductImage(productId, productImageUpload);
        return true;
    }

    @Override
    public List<SizeDTO> getAllSize() {

        List<Size> sizes = sizeRepository.findAll();
        List<SizeDTO> sizeDTOS = new ArrayList<>();

        for (Size size : sizes) {
            SizeDTO sizeDTO = SizeDTO.builder()
                    .sizeId(size.getSizeId())
                    .sizeName(size.getSizeName())
                    .build();
            sizeDTOS.add(sizeDTO);
        }

        return sizeDTOS;
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        for (Category category : categories) {
            CategoryDTO categoryDTO = CategoryDTO.builder()
                    .cateId(category.getCateId())
                    .cateTitle(category.getCateTitle())
                    .build();
            categoryDTOS.add(categoryDTO);
        }

        return categoryDTOS;
    }

    @Override
    public List<StyleDTO> getAllStyle() {

        List<Style> styles = styleRepository.findAll();
        List<StyleDTO> styleDTOS = new ArrayList<>();

        for (Style style : styles) {
            StyleDTO styleDTO = StyleDTO.builder()
                    .styleId(style.getStyleId())
                    .styleTitle(style.getStyleTitle())
                    .build();
            styleDTOS.add(styleDTO);
        }

        return styleDTOS;
    }

    @Override
    public List<ColorDTO> getAllColor() {

        List<Color> colors = colorRepository.findAll();
        List<ColorDTO> colorDTOS = new ArrayList<>();

        for (Color color : colors) {
            ColorDTO colorDTO = ColorDTO.builder()
                    .colorId(color.getColorId())
                    .colorName(color.getColorName())
                    .build();
            colorDTOS.add(colorDTO);
        }

        return colorDTOS;
    }

    @Override
    public int insertProduct(Product product) {
        return productRepository.save(product).getProductId();
    }

    @Override
    public void applySale(SaleProductRequest saleProductRequest) {

        if(saleProductRequest.getProductIds().length == 0
            || saleProductRequest.getStartDate().isEmpty()
            || saleProductRequest.getEndDate().isEmpty()) {
            return;
        }

        try {
            Date startDate = parseStringtoDate(saleProductRequest.getStartDate());
            Date endDate = parseStringtoDate(saleProductRequest.getEndDate());

            Sale sale = Sale.builder()
                    .amount_sale(saleProductRequest.getAmountSale())
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
            int saleId = saleRepository.save(sale).getSaleId();

            for(int i : saleProductRequest.getProductIds()) {
                Product product = productRepository.findById(i)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

                KeyProductSale keyProductSale = KeyProductSale.builder()
                        .productId(product.getProductId())
                        .saleId(saleId)
                        .build();

                ProductSale productSale = ProductSale.builder()
                        .keyProductSale(keyProductSale)
                        .product(product)
                        .sale(sale)
                        .build();
                productSaleRepository.save(productSale);
            }
        } catch (ParseException e) {
            throw new AppException(ErrorCode.PARSE_DATE_ERROR);
        }
    }

    @Override
    public double getAverageRating(int productId) {

        List<Rating> ratingList = ratingRepository.findAllByProductProductId(productId);
        if(ratingList.isEmpty()) return 0;

        double totalStars = 0;
        for(Rating rating : ratingList) {
            totalStars += rating.getStar();
        }
        return totalStars / ratingList.size();
    }

    @Override
    public boolean updateProductImage(int productId, ProductImageUpload productImageUpload) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));
        MultipartFile mainDisplayImage = productImageUpload.getMainDisplayImage();

        try {
            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .imageData(Base64.getEncoder().encodeToString(mainDisplayImage.getBytes()))
                    .isMainDisplay(true)
                    .build();
            productImageRepository.save(productImage);

            for(MultipartFile data : productImageUpload.getDescImage()) {
                ProductImage descImage = ProductImage.builder()
                        .product(product)
                        .imageData(Base64.getEncoder().encodeToString(data.getBytes()))
                        .isMainDisplay(false)
                        .build();
                productImageRepository.save(descImage);
            }

            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    private Date parseStringtoDate(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.parse(str);
    }
}
