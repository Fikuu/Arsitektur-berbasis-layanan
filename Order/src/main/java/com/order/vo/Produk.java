package com.order.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "name", "description", "price" })
public class Produk {

    @JsonProperty("name")
    private String nama;

    @JsonProperty("description")
    private String deskripsi;

    @JsonProperty("price")
    private Long harga;

}
