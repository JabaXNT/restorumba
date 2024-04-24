package com.backend.usermicroservice.models

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name = "Users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    var id: Long = 0,
    @Column(name="phone_number", unique = true)
    var phoneNumber: String = "1234567890",
    @Column(name="username")
    var name: String = "example",
    @Column(name="email")
    var email: String = "example@gmail.com",
    @Column(name="password")
    var userPassword: String = "password",
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    var role: Role = Role.USER
) : UserDetails{

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return phoneNumber
    }

    override fun getPassword(): String {
        return userPassword
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}