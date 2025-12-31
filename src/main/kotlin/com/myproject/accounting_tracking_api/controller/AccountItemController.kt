package com.myproject.accounting_tracking_api.controller

import com.myproject.accounting_tracking_api.dto.AccountItemRequest
import com.myproject.accounting_tracking_api.dto.AccountItemResponse
import com.myproject.accounting_tracking_api.dto.AccountItemSearchRequest
import com.myproject.accounting_tracking_api.dto.PageResponse
import com.myproject.accounting_tracking_api.service.AccountItemService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@Tag(name = "AccountItem API", description = "정산 항목(AccountItem) CRUD API")
@RestController
@RequestMapping("/api/account-items")
class AccountItemController(private val accountItemService: AccountItemService) {

    @Operation(summary = "정산 항목 검색", description = "다양한 조건으로 정산 항목을 검색합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "검색 성공"),
        ApiResponse(responseCode = "500", description = "서버 오류")
    )
    @GetMapping
    fun searchAccountItems(
        @ParameterObject searchRequest: AccountItemSearchRequest,
        @ParameterObject @PageableDefault(size = 20, sort = ["itemDate,DESC"]) pageable: Pageable
    ): PageResponse<AccountItemResponse> {
        return accountItemService.searchAccountItems(searchRequest, pageable)
    }

    @Operation(summary = "정산 항목 생성", description = "새로운 정산 항목을 생성합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "생성 성공"),
        ApiResponse(responseCode = "400", description = "잘못된 요청"),
        ApiResponse(responseCode = "500", description = "서버 오류")
    )
    @PostMapping
    fun createAccountItem(@RequestBody request: AccountItemRequest): AccountItemResponse {
        return accountItemService.createAccountItem(request)
    }

    @Operation(summary = "정산 항목 수정", description = "기존 정산 항목의 정보를 수정합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "수정 성공"),
        ApiResponse(responseCode = "404", description = "항목을 찾을 수 없음"),
        ApiResponse(responseCode = "500", description = "서버 오류")
    )
    @PutMapping("/{id}")
    fun updateAccountItem(
        @Parameter(description = "수정할 정산 항목의 ID") @PathVariable id: Long,
        @RequestBody request: AccountItemRequest
    ): AccountItemResponse {
        return accountItemService.updateAccountItem(id, request)
    }

    @Operation(summary = "정산 항목 삭제", description = "정산 항목을 논리적으로 삭제합니다 (Soft Delete).")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "삭제 성공"),
        ApiResponse(responseCode = "404", description = "항목을 찾을 수 없음"),
        ApiResponse(responseCode = "500", description = "서버 오류")
    )
    @DeleteMapping("/{id}")
    fun deleteAccountItem(@Parameter(description = "삭제할 정산 항목의 ID") @PathVariable id: Long) {
        accountItemService.deleteAccountItem(id)
    }
}