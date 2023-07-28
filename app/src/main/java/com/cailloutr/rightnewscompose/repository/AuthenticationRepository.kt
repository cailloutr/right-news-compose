package com.cailloutr.rightnewscompose.repository

import android.util.Log
import com.cailloutr.rightnewscompose.extensions.await
import com.cailloutr.rightnewscompose.model.User
import com.cailloutr.rightnewscompose.other.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthenticationRepositoryInterface {

    override fun isLoggedIn(): Boolean = firebaseAuth.currentUser != null

    //TODO: test
    override suspend fun loginWithPasswordAndEmail(
        user: User,
        context: CoroutineDispatcher,
    ): Resource<AuthResult?> {
        return withContext(context) {
            try {
                val authResult =
                    firebaseAuth.signInWithEmailAndPassword(user.email, user.password).await()
                Resource.success(data = authResult)
            } catch (e: IllegalArgumentException) {
                Resource.error(msg = "Fill all the required fields", data = null)
            } catch (e: Exception) {
                Log.e("AuthenticationInterface", "loginWithPasswordAndEmail: $e")
                Resource.error(msg = e.toString(), data = null)
            }
        }
    }

    //TODO: test
    override suspend fun signInWithPasswordAndEmail(
        user: User,
        context: CoroutineDispatcher,
    ): Resource<AuthResult?> {
        return withContext(context) {
            try {
                val authResult =
                    firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
                Resource.success(data = authResult)
            } catch (e: IllegalArgumentException) {
                Resource.error(msg = "Fill all the required fields", data = null)
            } catch (e: Exception) {
                Log.e("AuthenticationInterface", "loginWithPasswordAndEmail: $e")
                Resource.error(msg = e.toString(), data = null)
            }
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun getUser(): FirebaseUser? = firebaseAuth.currentUser
}