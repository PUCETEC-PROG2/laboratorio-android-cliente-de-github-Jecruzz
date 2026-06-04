package ec.edu.uisek.githubclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ec.edu.uisek.githubclient.data.MockRepoManager
import ec.edu.uisek.githubclient.databinding.FragmentRepoFormBinding

class RepoFormFragment : Fragment() {

    private var _binding: FragmentRepoFormBinding? = null
    private val binding get() = _binding!!

    private var repoName: String? = null
    private var repoDescription: String? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repoName = it.getString(ARG_NAME)
            repoDescription = it.getString(ARG_DESCRIPTION)
            isEditMode = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isEditMode) {
            binding.etName.setText(repoName)
            binding.etName.isEnabled = false
            binding.etDescription.setText(repoDescription)
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(context, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isEditMode) {
                MockRepoManager.updateRepo(name, description)
                Toast.makeText(context, "Repositorio editado (Simulado)", Toast.LENGTH_SHORT).show()
            } else {
                MockRepoManager.addRepo(name, description)
                Toast.makeText(context, "Repositorio creado (Simulado)", Toast.LENGTH_SHORT).show()
            }
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_NAME = "repo_name"
        private const val ARG_DESCRIPTION = "repo_description"

        fun newInstance(name: String, description: String) =
            RepoFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_DESCRIPTION, description)
                }
            }
    }
}
