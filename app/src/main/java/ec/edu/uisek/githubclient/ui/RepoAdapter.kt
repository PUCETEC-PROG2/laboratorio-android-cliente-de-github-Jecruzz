package ec.edu.uisek.githubclient.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ec.edu.uisek.githubclient.databinding.ItemRepositoryBinding
import ec.edu.uisek.githubclient.models.Repository

class RepoAdapter(
    private var repos: List<Repository>,
    private val onEdit: (Repository) -> Unit,
    private val onDelete: (Repository) -> Unit
) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    class RepoViewHolder(val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repos[position]
        holder.binding.tvRepoName.text = repo.name
        holder.binding.tvRepoDescription.text = repo.description ?: "No description"


        holder.binding.btnEdit.setOnClickListener { onEdit(repo) }
        holder.binding.btnDelete.setOnClickListener { onDelete(repo) }
    }

    override fun getItemCount() = repos.size

    fun updateData(newRepos: List<Repository>) {
        repos = newRepos
        notifyDataSetChanged()
    }
}
