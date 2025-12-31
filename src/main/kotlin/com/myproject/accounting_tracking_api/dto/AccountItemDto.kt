package com.myproject.accounting_tracking_api.dto

import java.time.LocalDate

data class AccountItemRequest(
    val itemDate: LocalDate,
    val companyName: String,
    val amount: Double,
    val isPaid: Boolean
)

data class AccountItemResponse(
    val id: Long,
    val itemDate: LocalDate,
    val companyName: String,
    val amount: Double,
    val isPaid: Boolean,
    val isDeleted: Boolean
)

data class AccountItemSearchRequest(
    val startDate: String?,
    val endDate: String?,
    val companyName: String?,
    val isPaid: Boolean?,
    val isDeleted: Boolean?
)