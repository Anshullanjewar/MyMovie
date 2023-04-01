package com.example.mymovies.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mymovies.DB.AppDatabase
import com.example.mymovies.DB.UserProfile
import com.example.mymovies.DB.UserProfileDao
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var database: AppDatabase
    private lateinit var dao: UserProfileDao
    private lateinit var userProfile: UserProfile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = AppDatabase.getInstance(requireContext())
        dao = database.userProfileDao()

        binding.buttonSave.setOnClickListener {
            saveUserProfile()
        }

        loadUserProfile()
    }

    private fun loadUserProfile() {
        lifecycleScope.launch(Dispatchers.IO) {
            val profiles = dao.getAll()
            if (profiles.isNotEmpty()) {
                userProfile = profiles[0]
                withContext(Dispatchers.Main) {
                    binding.editTextName.setText(userProfile.name)
                    binding.editTextEmail.setText(userProfile.email)
                    binding.editTextPhone.setText(userProfile.phone)
                    binding.editTextUsername.setText(userProfile.address)
                }
            } else {
                userProfile = UserProfile(
                    name = "",
                    email = "",
                    phone = "",
                    address = ""
                )
            }
        }
    }

    private fun saveUserProfile() {
        userProfile.name = binding.editTextName.text.toString()
        userProfile.email = binding.editTextEmail.text.toString()
        userProfile.phone = binding.editTextPhone.text.toString()
        userProfile.address = binding.editTextUsername.text.toString()

        lifecycleScope.launch(Dispatchers.IO) {
            if (userProfile.id == 0L) {
                dao.insert(userProfile)
            } else {
                dao.update(userProfile)
            }
        }

        Toast.makeText(requireContext(), "User profile saved", Toast.LENGTH_SHORT).show()
    }
}
