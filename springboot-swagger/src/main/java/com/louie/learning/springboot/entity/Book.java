package com.louie.learning.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ApiModel ApiModelProperty可不配置
 */
//@ApiModel(value = "图书对象",description = "图书")
public class Book {
//    @ApiModelProperty(value = "主键",example = "001")
    private long id;
//    @ApiModelProperty(value = "书名",example = "Springboot")
    private String name;
//    @ApiModelProperty(value = "价格",example = "001")
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
