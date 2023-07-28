package com.cailloutr.rightnewscompose.repository

import com.cailloutr.rightnewscompose.model.User
import com.cailloutr.rightnewscompose.other.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher

interface AuthenticationRepositoryInterface {

    fun isLoggedIn(): Boolean

    suspend fun loginWithPasswordAndEmail(user: User, context: CoroutineDispatcher): Resource<AuthResult?>

    suspend fun signInWithPasswordAndEmail(user: User, context: CoroutineDispatcher) : Resource<AuthResult?>

    fun logout()

    fun getUser(): FirebaseUser?

}