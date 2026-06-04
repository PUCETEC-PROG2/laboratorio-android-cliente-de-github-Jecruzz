package ec.edu.uisek.githubclient.data

import ec.edu.uisek.githubclient.models.Repository

object MockRepoManager {
    private val repos = mutableListOf(
        Repository(1, "Repo", "Repositorio 1"),
        Repository(2, "ejemplo2", "Simulacion"),
        Repository(3, "proyecto1", "Proyecto en desarrollo"),
        Repository(4, "demo", "hola mundo xd")
    )

    fun getRepos(): List<Repository> = repos.toList()

    fun addRepo(name: String, description: String?) {
        val newId = (repos.maxOfOrNull { it.id } ?: 0) + 1
        repos.add(Repository(newId, name, description))
    }

    fun updateRepo(name: String, description: String?) {
        val index = repos.indexOfFirst { it.name == name }
        if (index != -1) {
            val oldRepo = repos[index]
            repos[index] = oldRepo.copy(description = description)
        }
    }

    fun deleteRepo(id: Long) {
        repos.removeAll { it.id == id }
    }
}
