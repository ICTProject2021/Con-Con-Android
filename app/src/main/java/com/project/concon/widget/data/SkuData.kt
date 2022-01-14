package com.project.concon.widget.data

enum class SkuData(
    val sku: String, val position: Int, val money: String, val skuName: String) {
    MONEY_1000("money_1000", 0, "1,000", "천원"),
    MONEY_5000("money_5000", 1, "5,000", "오천원"),
    MONEY_10000("money_10000", 2, "10,000", "만원"),
    MONEY_20000("money_20000", 3, "20,000", "이만원"),
    MONEY_30000("money_30000", 4, "30,000", "삼만원"),
}