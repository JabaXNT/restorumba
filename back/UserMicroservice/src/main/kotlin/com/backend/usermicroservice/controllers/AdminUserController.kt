package com.backend.usermicroservice.controllers

import com.backend.usermicroservice.models.User
import com.backend.usermicroservice.services.AdminService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/users")
class AdminUserController(private val adminService: AdminService) {

    @GetMapping("/username/{username}")
    fun getUserByUsername(@PathVariable username: String) = adminService.findUserByUsername(username)

    @GetMapping("/phone/{phoneNumber}")
    fun getUserByPhoneNumber(@PathVariable phoneNumber: String) = adminService.findUserByPhoneNumber(phoneNumber)

    @DeleteMapping("/username/{username}")
    fun deleteUserByUsername(@PathVariable username: String) = adminService.deleteUser(username)

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createAdmin")
    fun createAdmin(@RequestBody request: RegistrationRequest): User {
        return adminService.createAdmin(request)
    }
}