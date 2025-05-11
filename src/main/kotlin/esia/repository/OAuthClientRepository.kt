package esia.repository

import esia.model.OAuthClient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface OAuthClientRepository : JpaRepository<OAuthClient, String>
