package com.myproject.accounting_tracking_api.service

import com.myproject.accounting_tracking_api.dto.AccountItemRequest
import com.myproject.accounting_tracking_api.dto.AccountItemResponse
import com.myproject.accounting_tracking_api.dto.AccountItemSearchRequest
import com.myproject.accounting_tracking_api.dto.PageResponse
import com.myproject.accounting_tracking_api.entity.AccountItem
import com.myproject.accounting_tracking_api.repository.AccountItemRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AccountItemService(private val accountItemRepository: AccountItemRepository) {

    fun searchAccountItems(
        searchRequest: AccountItemSearchRequest,
        pageable: Pageable
    ): PageResponse<AccountItemResponse> {
        val spec = createSpecification(searchRequest)
        val accountItems: Page<AccountItem> = accountItemRepository.findAll(spec, pageable)
        return PageResponse(
            content = accountItems.content.map { it.toResponse() },
            pageNumber = accountItems.number,
            pageSize = accountItems.size,
            totalElements = accountItems.totalElements,
            totalPages = accountItems.totalPages,
            isLast = accountItems.isLast
        )
    }

    private fun createSpecification(searchRequest: AccountItemSearchRequest): Specification<AccountItem> {
        return Specification { root, query, criteriaBuilder ->
            val predicates = mutableListOf<jakarta.persistence.criteria.Predicate>()

            searchRequest.startDate?.let {
                if (it.isNotEmpty()) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("itemDate"), LocalDate.parse(it)))
                }
            }
            searchRequest.endDate?.let {
                if (it.isNotEmpty()) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("itemDate"), LocalDate.parse(it)))
                }
            }
            searchRequest.companyName?.let {
                predicates.add(criteriaBuilder.like(root.get("companyName"), "%$it%"))
            }
            searchRequest.isPaid?.let {
                predicates.add(criteriaBuilder.equal(root.get<Boolean>("isPaid"), it))
            }

            searchRequest.isDeleted?.let {
                predicates.add(criteriaBuilder.equal(root.get<Boolean>("isDeleted"), it))
            } ?: predicates.add(criteriaBuilder.equal(root.get<Boolean>("isDeleted"), false))

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

    private fun AccountItem.toResponse(): AccountItemResponse {
        return AccountItemResponse(
            id = this.id!!,
            itemDate = this.itemDate,
            companyName = this.companyName,
            amount = this.amount,
            isPaid = this.isPaid,
            isDeleted = this.isDeleted
        )
    }

    fun createAccountItem(request: AccountItemRequest): AccountItemResponse {
        val accountItem = AccountItem(
            itemDate = request.itemDate,
            companyName = request.companyName,
            amount = request.amount,
            isPaid = request.isPaid,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        val savedAccountItem = accountItemRepository.save(accountItem)
        return savedAccountItem.toResponse()
    }

    fun updateAccountItem(id: Long, request: AccountItemRequest): AccountItemResponse {
        val accountItem = accountItemRepository.findById(id).orElseThrow {
            EntityNotFoundException("AccountItem not found with id: $id")
        }

        accountItem.itemDate = request.itemDate
        accountItem.companyName = request.companyName
        accountItem.amount = request.amount
        accountItem.isPaid = request.isPaid
        accountItem.updatedAt = LocalDateTime.now()

        val updatedAccountItem = accountItemRepository.save(accountItem)
        return updatedAccountItem.toResponse()
    }

    fun deleteAccountItem(id: Long) {
        val accountItem = accountItemRepository.findById(id).orElseThrow {
            EntityNotFoundException("AccountItem not found with id: $id")
        }
        accountItem.isDeleted = true
        accountItemRepository.save(accountItem)
    }
}