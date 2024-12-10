package org.koreait.product.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private String title;
    private String sub_title;
    private String imageUrl;
    private String pageUrl;
}
