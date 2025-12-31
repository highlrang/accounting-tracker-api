package com.myproject.accounting_tracking_api.repository

import com.myproject.accounting_tracking_api.entity.AccountItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface AccountItemRepository : JpaRepository<AccountItem, Long>, JpaSpecificationExecutor<AccountItem>