package com.myproject.accounting_tracking_api.dto

import java.time.LocalDate

data class AccountItemRequest(
    val itemDate: LocalDate,
    val origin: String,
    val destination: String,
    val amount: Double,
    val isPaid: Boolean,
    val notes: String?
)

data class AccountItemResponse(
    val id: Long,
    val itemDate: LocalDate,
    val origin: String,
    val destination: String,
    val amount: Double,
    val isPaid: Boolean,
    val notes: String?,
    val isDeleted: Boolean
)

data class AccountItemSearchRequest(
    val startDate: String?,
    val endDate: String?,
    val keyword: String?,
    val isPaid: Boolean?,
    val isDeleted: Boolean?
)