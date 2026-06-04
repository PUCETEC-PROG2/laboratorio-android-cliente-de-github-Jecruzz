package ec.edu.uisek.githubclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ec.edu.uisek.githubclient.R
import ec.edu.uisek.githubclient.data.MockRepoManager
import ec.edu.uisek.githubclient.databinding.FragmentRepoListBinding

class RepoListFragment : Fragment() {

    private var _binding: FragmentRepoListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadRepositories()

        binding.fabAdd.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, RepoFormFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupRecyclerView() {
        adapter = RepoAdapter(emptyList(), 
            onEdit = { repo ->
                val fragment = RepoFormFragment.newInstance(repo.name, repo.description ?: "")
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit()
            },
            onDelete = { repo ->
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar Repositorio")
                    .setMessage("¿Estás seguro de que deseas eliminar '${repo.name}'? Esta acción no se puede deshacer.")
                    .setPositiveButton("Eliminar") { _, _ ->
                        MockRepoManager.deleteRepo(repo.id)
                        Toast.makeText(context, "Repositorio eliminado (Simulado)", Toast.LENGTH_SHORT).show()
                        loadRepositories()
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        )
        binding.rvRepositories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRepositories.adapter = adapter
    }

    private fun loadRepositories() {
        val repos = MockRepoManager.getRepos()
        adapter.updateData(repos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
