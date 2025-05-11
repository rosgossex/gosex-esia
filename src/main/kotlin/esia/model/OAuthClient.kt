package esia.model

import jakarta.persistence.*

@Entity
@Table(name = "oauth_clients")
data class OAuthClient(
    @Id
    @Column(name = "client_id")
    val clientId: String,

    @Column(name = "client_name", nullable = false)
    val clientName: String,

    @Column(name = "client_secret", nullable = false)
    val clientSecret: String,

    @Column(name = "redirect_uri")
    val redirectUri: String,

    @Column(name = "scope")
    val scope: String,

    @Column(name = "authorized_grant_types")
    val authorizedGrantTypes: String
)
