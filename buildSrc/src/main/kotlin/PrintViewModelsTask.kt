import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class PrintViewModelsTask : DefaultTask() {

    @TaskAction
    fun printAllViewModels() {
        val tree: ConfigurableFileTree = project.fileTree("${project.projectDir}/src/main")
        tree.include("**/*ViewModel*.kt")
        tree.forEach { file ->
            println(file.name)
        }
    }
}