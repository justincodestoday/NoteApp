package com.justin.mynotes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.justin.mynotes.MyApplication
import com.justin.mynotes.R
import com.justin.mynotes.databinding.FragmentAddEditNoteBinding
import com.justin.mynotes.models.Note
import com.justin.mynotes.viewModels.AddNoteViewModel

class AddNoteFragment : Fragment() {
    private lateinit var binding: FragmentAddEditNoteBinding
    private val viewModel: AddNoteViewModel by viewModels {
        AddNoteViewModel.Provider((requireContext().applicationContext as MyApplication).noteRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnSubmit.setOnClickListener {
                val title = etTitle.text.toString()
                val details = etDetails.text.toString()

                val selectedId: Int = radioGroup.checkedRadioButtonId
                val radioName = resources.getResourceEntryName(selectedId)
                val color = when (radioName.toString()) {
                    "rbOne" -> "#FFEB3B"
                    "rbTwo" -> "#FDA286"
                    "rbThree" -> "#EF9DD4"
                    "rbFour" -> "#F35E91"
                    "rbFive" -> "#69F1E4"
                    else -> "#FFFFFF"
                }

                if (validate(title, details)) {
                    val bundle = Bundle()
                    bundle.putBoolean("refresh", true)
                    setFragmentResult("from_add_note", bundle)
                    viewModel.addNote(Note(null, title, details, color))
                    Toast.makeText(requireContext(), "New note added", Toast.LENGTH_SHORT).show()
                    NavHostFragment.findNavController(requireParentFragment()).popBackStack()
                } else {
                    val snackbar =
                        Snackbar.make(it, "Please enter all the values", Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.red_200)
                    )
                    snackbar.setAction("Hide") {
                        snackbar.dismiss()
                    }
                    snackbar.show()
                }
            }
        }
    }

    fun validate(vararg list: String): Boolean {
        for (field in list) {
            if (field.isEmpty()) {
                return false
            }
        }
        return true
    }
}