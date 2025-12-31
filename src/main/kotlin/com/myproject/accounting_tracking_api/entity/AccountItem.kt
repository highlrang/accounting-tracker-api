package com.myproject.accounting_tracking_api.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class AccountItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var itemDate: LocalDate,
    var companyName: String,
    var amount: Double,
    var isPaid: Boolean,
    var isDeleted: Boolean = false
)