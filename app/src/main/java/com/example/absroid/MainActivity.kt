package com.example.absroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    private lateinit var signInButton: Button
    private lateinit var signOutButton: Button
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signInButton = findViewById(R.id.signInButton)
        signOutButton = findViewById(R.id.signOutButton)
        statusText = findViewById(R.id.statusText)

        // Konfigurasi Google Sign-In (tanpa requestIdToken — hanya email & profile)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Jika sudah ada akun signed-in sebelumnya, tampilkan di UI
        GoogleSignIn.getLastSignedInAccount(this)?.let { account ->
            statusText.text = "Signed in: ${account.email}"
        }

        signInButton.setOnClickListener {
            startGoogleSignInWithChooser()
        }

        signOutButton.setOnClickListener {
            signOut()
        }
    }

    // Memaksa muncul chooser dengan signOut terlebih dahulu
    private fun startGoogleSignInWithChooser() {
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener {
            statusText.text = "Status: not signed in"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<com.google.android.gms.auth.api.signin.GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                // Anda sekarang memiliki info akun: account.email, account.displayName, dll.
                statusText.text = "Signed in: ${account.email}"
            } catch (e: ApiException) {
                Log.w("Absroid", "signInResult:failed code=" + e.statusCode)
                statusText.text = "Sign in failed: ${e.statusCode}"
            }
        }
    }
}
