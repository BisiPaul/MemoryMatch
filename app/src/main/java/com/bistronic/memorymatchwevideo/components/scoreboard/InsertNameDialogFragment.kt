package com.bistronic.memorymatchwevideo.components.scoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.bistronic.memorymatchwevideo.R
import com.bistronic.memorymatchwevideo.databinding.FragmentDialogInsertNameBinding


/**
 * Created by paulbisioc on 12/5/2020.
 */

class InsertNameDialogFragment(
    @LayoutRes private val layout: Int,
    private val listener: InsertNameDialogFragmentInteractionListener
) : DialogFragment() {
    private lateinit var binding: FragmentDialogInsertNameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resources.getLayout(layout), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDialogInsertNameBinding.bind(view)
        setControls()
    }

    private fun setControls() {
        binding.submitBT.setOnClickListener {
            if (checkValidName())
                listener.onDone(binding.yourNameET.text.toString())
            else
                showErrorToast()
        }

        binding.yourNameET.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (checkValidName()) {
                    listener.onDone(binding.yourNameET.text.toString())
                    true
                }
                else {
                    showErrorToast()
                    false
                }
            } else
                false
        }
    }

    private fun checkValidName() : Boolean {
        return binding.yourNameET.text.length == 3
    }

    private fun showErrorToast() {
        Toast.makeText(context, resources.getString(R.string.invalid_name), Toast.LENGTH_SHORT).show()
    }

}